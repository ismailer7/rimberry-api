package io.idev.storeapi.service;

import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.idev.storeapi.dto.UserDto;
import io.idev.storeapi.entities.User;
import io.idev.storeapi.repos.IUserRepository;
import io.idev.storeapi.service.interfaces.IUserService;

@Service
public class UserServiceImpl implements IUserService<UserDto, Integer> {

	@Autowired
	IUserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public UserDto get(Integer id) {
		Optional<User> op = userRepository.findById(id);
		User user = op.get();
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}
	

	@Override
	public UserDto getByEmail(String email) {
		User user =  userRepository.getByEmail(email);
		UserDto userDto = null;
		if(user != null) {
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
			}
		}
		userRepository.saveAndFlush(this.modelMapper.map(userDto, User.class));
	}

}
