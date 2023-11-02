package io.idev.storeapi.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.idev.storapi.enums.Role;
import io.idev.storeapi.entities.User;
import io.idev.storeapi.model.UserDto;
import io.idev.storeapi.repos.IUserRepository;
import io.idev.storeapi.service.interfaces.IUserService;

@Service
public class UserServiceImpl implements IUserService<UserDto, Integer> {

	IUserRepository userRepository;

	private ModelMapper modelMapper;

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
	public void updateUserWithFields(String username, Map<String, String> fields) {
		UserDto userDto = getByEmail(username);
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
		userRepository.saveAndFlush(this.modelMapper.map(userDto, User.class));
	}

	public String getUserRolesByEmail(String email) {
		UserDto userDto = getByEmail(email);
		String[] roles = userDto.getRoles().split(",");
		for (int i = 0; i < roles.length; i++) {
			switch (roles[i]) {
			case "0":
				roles[i] = Role.STAFF.name();
				break;
			case "1":
				roles[i] = Role.ADMIN.name();
				break;
			case "2":
				roles[i] = Role.SUPERVISOR.name();
				break;
			}
		}
		return String.join("|", roles);
	}

	@Override
	public List<UserDto> getAll() {
		List<User> userList = this.userRepository.findAll();
		return userList.stream().map(user -> this.modelMapper.map(user, io.idev.storeapi.model.UserDto.class))
				.collect(Collectors.toList());
	}

}
