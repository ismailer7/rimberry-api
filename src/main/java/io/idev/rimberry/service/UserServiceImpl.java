package io.idev.rimberry.service;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.idev.rimberry.entities.User;
import io.idev.rimberry.repos.IUserRepository;
import io.idev.rimberry.service.interfaces.IUserService;
import io.idev.rimberry.utils.ImageUtils;
import io.idev.rimberry.enums.Role;
import io.idev.storeapi.model.UserDto;

@Service
public class UserServiceImpl implements IUserService<UserDto, Integer> {

	IUserRepository userRepository;

	private ModelMapper modelMapper;

	private static int MAX_PER_PAGE = 5;
	
	
	public UserServiceImpl(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public UserDto get(Integer id) {
		Optional<User> op = userRepository.findById(id);
		User user = op.get();
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

	@Override
	public UserDto getByEmail(String email) {
		User user = userRepository.getByEmail(email);
		UserDto userDto = null;
		if (user != null) {
			userDto = this.modelMapper.map(user, UserDto.class);
		}
		return userDto;
	}
	
	@Override
	public void updateUserWithFields(Integer userId, Map<String, String> fields) {
		UserDto userDto = get(userId);
		for (Map.Entry<String, String> entry : fields.entrySet()) {
			switch (entry.getKey()) {
			case "token":
				userDto.setToken(entry.getValue());
				break;
			case "logged":
				userDto.setIsLogged(entry.getValue().equalsIgnoreCase("true") ? true : false);
				break;
			}
		}
		userDto.setUpdated(OffsetDateTime.now());
		userRepository.saveAndFlush(this.modelMapper.map(userDto, User.class));
	}
	
	@Override
	public void updateUserWithFields(String email, Map<String, String> fields) {
		UserDto userDto = getByEmail(email);
		for (Map.Entry<String, String> entry : fields.entrySet()) {
			switch (entry.getKey()) {
			case "token":
				userDto.setToken(entry.getValue());
				break;
			case "logged":
				userDto.setIsLogged(entry.getValue().equalsIgnoreCase("true") ? true : false);
				break;
			}
		}
		userDto.setUpdated(OffsetDateTime.now());
		userRepository.saveAndFlush(this.modelMapper.map(userDto, User.class));
	}

	public String getUserRolesByEmail(String email) {
		UserDto userDto = getByEmail(email);
		String roles = userDto.getRoles();
		return getRawRoles(roles);
	}

	public String getRawRoles(String roles) {
		String arrayRoles[] = roles.split(",");
		for (int i = 0; i < arrayRoles.length; i++) {
			switch (arrayRoles[i]) {
			case "0":
				arrayRoles[i] = Role.RECEPTION.name();
				break;
			case "1":
				arrayRoles[i] = Role.ADMIN.name();
				break;
			case "2":
				arrayRoles[i] = Role.SUPERVISOR.name();
				break;
			case "3":
				arrayRoles[i] = Role.HR.name();
				break;
			}
		}
		return String.join("|", arrayRoles);
	}

	@Override
	public List<UserDto> getAll() {
		List<User> userList = this.userRepository.findAll();
		return userList.stream().filter(user -> !user.getIsDeleted()).map(user -> {
			user.setRoles(getRawRoles(user.getRoles()));
			return this.modelMapper.map(user, io.idev.storeapi.model.UserDto.class);
		}).collect(Collectors.toList());
	}

	@Override
	public void edit(UserDto t) {
		User user = userRepository.findById(t.getId()).get();
		if(t.getFirstName() != null && !t.getFirstName().isEmpty()) {
			user.setFirstName(t.getFirstName()); 
		}
		if(t.getLastName() != null && !t.getLastName().isEmpty()) {
			user.setLastName(t.getLastName()); 
		}
		if(t.getEmail() != null && !t.getEmail().isEmpty()) {
			user.setEmail(t.getEmail()); 
		}
		if(t.getRoles() != null && !t.getRoles().isEmpty()) {
			user.setRoles(t.getRoles()); 
		}
		user.setUpdated(new Date());
		userRepository.saveAndFlush(user);
	}

	@Override
	public void add(UserDto t) {
		PasswordEncoder pe = new BCryptPasswordEncoder();
		User user = new User();
		user.setFirstName(t.getFirstName());
		user.setLastName(t.getLastName());
		user.setEmail(t.getEmail());
		user.setGender(t.getGender());
		user.setPassword(pe.encode(t.getPassword()));		
		user.setAvatar(ImageUtils.getAvatar());
		user.setIsActive(t.getIsActive());
		user.setIsDeleted(false);
		user.setIsLogged(false);
		user.setCreated(new Date());
		user.setUpdated(new Date());
		user.setToken(null);
		user.setUserName(generateUN(t.getFirstName(), t.getLastName()));
		user.setRoles(t.getRoles());
		userRepository.saveAndFlush(user);
	}

	public String generateUN(String firstname, String lastname) {
		int randomNum = ThreadLocalRandom.current().nextInt(1000, 9999);
		String username = firstname.charAt(0) + lastname.charAt(0) + randomNum + "";
		return username;
	}

	@Override
	public void delete(Integer l) {
		User user = userRepository.getById(l);
		user.setIsDeleted(true);
		userRepository.saveAndFlush(user);
	}

	@Override
	public Page<UserDto> getByPage(int page) {
		// in case total element < per page element => max page element = total element.
		Page<User> pageUser = this.userRepository.findAllByisDeletedFalse(PageRequest.of(page, MAX_PER_PAGE));		
		return pageUser.map(user -> {
			user.setRoles(getRawRoles(user.getRoles()));
			return this.modelMapper.map(user, io.idev.storeapi.model.UserDto.class);
		});
	}

	@Override
	public List<UserDto> lookup(String text) {
		List<User> userList = null;
		if(text.matches("-?\\d+(\\.\\d+)?")) {
			userList = this.userRepository.lookupById(Integer.valueOf(text));
		} else {
			if(text.contains("@")) {
				userList = this.userRepository.lookupByEmail(text);
			} else {
				userList = this.userRepository.lookupByFirstnameOrLastName(text);
			}
		}
		
		return userList.stream().filter(user -> !user.getIsDeleted()).map(user -> {
			user.setRoles(getRawRoles(user.getRoles()));
			return this.modelMapper.map(user, UserDto.class);
		}).collect(Collectors.toList());
	}

}
