package com.fyp.profileservice.profile;

import com.fyp.profileservice.profile.DTO.UserDTO;
import fyp.common.entity.User;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProfileApplicationTests {

	@Test
	void contextLoads() {
		ModelMapper modelMapper = new ModelMapper();
		User user = new User();
		UserDTO map = modelMapper.map(user, UserDTO.class);
		System.out.println(map.toString());
	}

}
