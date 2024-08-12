package com.backend.padinfo_backend.mappers;

import com.backend.padinfo_backend.dto.userInfo.Authentication.CreateUserDTO;
import com.backend.padinfo_backend.dto.userInfo.Authentication.LoginUserDTO;
import com.backend.padinfo_backend.dto.userInfo.UpdateUserInfoDTO;
import com.backend.padinfo_backend.dto.userInfo.UserDTO;
import com.backend.padinfo_backend.dto.userInfo.UserDTORequest;
import com.backend.padinfo_backend.model.entity.UserInfo;
import com.backend.padinfo_backend.model.repository.IRoleRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserInfoMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IRoleRepository roleRepository;

    public UserDTO toDTO(UserInfo userInfo) {
        return modelMapper.map(userInfo, UserDTO.class);
    }

    public UserInfo fromDTO(CreateUserDTO newUserDTO) {

        modelMapper.typeMap(CreateUserDTO.class, UserInfo.class).addMappings(mapper ->{
            mapper.using(new RolesListConverter(roleRepository)).map(CreateUserDTO::getRolIds, UserInfo::setRoles);
        });

        return modelMapper.map(newUserDTO, UserInfo.class);
    }

    public UserInfo fromDTO(LoginUserDTO userDTO) {
        return modelMapper.map(userDTO, UserInfo.class);
    }

    public UserInfo fromDTO(UserDTORequest userDTO) {
        return modelMapper.map(userDTO, UserInfo.class);
    }

    public UserInfo fromDTO(UpdateUserInfoDTO userInfoDTO) {
        return modelMapper.map(userInfoDTO, UserInfo.class);
    }

    public List<UserDTO> toDTO(List<UserInfo> usersInfo) {
        return usersInfo.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
