package com.backend.padinfo_backend.mappers;

import com.backend.padinfo_backend.dto.userInfo.CreateUserDTO;
import com.backend.padinfo_backend.dto.userInfo.UserDTO;
import com.backend.padinfo_backend.model.entity.UserInfo;
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

    public UserDTO toDTO(UserInfo userInfo) {
        return modelMapper.map(userInfo, UserDTO.class);
    }

    public UserInfo fromDTO(CreateUserDTO userDTO) {
        return modelMapper.map(userDTO, UserInfo.class);
    }

    public List<UserDTO> toDTO(List<UserInfo> usersInfo) {
        return usersInfo.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
