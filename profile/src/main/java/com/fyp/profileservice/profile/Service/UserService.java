package com.fyp.profileservice.profile.Service;

import com.fyp.profileservice.profile.DTO.UserDTO;
import com.fyp.profileservice.profile.Enum.UserRoleEnum;
import com.fyp.profileservice.profile.Repository.RoleRepository;
import com.fyp.profileservice.profile.Repository.UserRepository;
import fyp.common.entity.Role;
import fyp.common.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ModelMapper modelMapper;

    public UserDTO save(UserDTO userDTO) throws Exception {
        User mappedUser = modelMapper.map(userDTO, User.class);
        User userByUsernameAndPassword = userRepository.findUserByUsername(mappedUser.getUsername());
        if (userByUsernameAndPassword != null) {
            throw new Exception("User exists");
        }
        else {
            mappedUser.setEnabled(true);
            mappedUser.setAccountNonExpired(false);
            mappedUser.setCredentialsNonExpired(false);
            mappedUser.setAccountNonLocked(false);
            if (userDTO.getUserRoleEnum() == UserRoleEnum.ROLE_TRADER) {
                List<Role> userRoles = new ArrayList<>();
                userRoles.add(roleRepository.findRoleById(Long.valueOf(UserRoleEnum.ROLE_TRADER.getValue())));
                mappedUser.setRoles(userRoles);
            }
            if (userDTO.getUserRoleEnum() == UserRoleEnum.ROLE_INVESTOR) {
                List<Role> userRoles = new ArrayList<>();
                userRoles.add(roleRepository.findRoleById(Long.valueOf(UserRoleEnum.ROLE_INVESTOR.getValue())));
                mappedUser.setRoles(userRoles);
            }
            User savedUser = userRepository.save(mappedUser);
            if (savedUser == null){
                throw new Exception("Server error");
            }
            return modelMapper.map(savedUser, UserDTO.class);
        }

    }

    public UserDTO getUserByUsername(String username){
        User userByUsername = userRepository.findUserByUsername(username);
        if (userByUsername != null ){
            return modelMapper.map(userByUsername, UserDTO.class);
        }
        return null;
    }

    public Long getUserIdByUsername(String username, UserRoleEnum userRole){
        User userByUsername = userRepository.findUserByUsername(username);
        if (userByUsername != null ){
            if (userByUsername.getRoles().get(0).getName().equals(userRole.getLabel().toLowerCase())){
                return userByUsername.getId();
            }
            return null;
        }
        return null;
    }
}
