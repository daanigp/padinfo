package com.backend.padinfo_backend.model.service.UserInfo;

import com.backend.padinfo_backend.exceptions.userInfo.UserInfoDeleteException;
import com.backend.padinfo_backend.exceptions.userInfo.UserInfoNotFoundException;
import com.backend.padinfo_backend.model.entity.Role;
import com.backend.padinfo_backend.model.entity.UserInfo;
import com.backend.padinfo_backend.model.repository.IRoleRepository;
import com.backend.padinfo_backend.model.repository.IUserInfoRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Hidden
@Service
public class UserInfoService implements IUserInfoService{

    @Autowired
    private IUserInfoRepository userInfoRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public List<UserInfo> findAll() {
        return (List<UserInfo>) userInfoRepository.findAll();
    }

    @Override
    public UserInfo findById(long id) {
        return userInfoRepository.findById(id).orElseThrow(
                () -> new UserInfoNotFoundException(id)
        );
    }

    @Override
    public UserInfo createUserInfo(UserInfo user) {
        user.setIsConnected(0);
        user.setImageURL("imgperfil_basic");
        return userInfoRepository.save(user);
    }

    @Override
    public UserInfo updateUserInfo(long id, UserInfo newUserInfo) {

        UserInfo user = userInfoRepository.findById(id).orElseThrow(
                () -> new UserInfoNotFoundException("Error al buscar el usuario -> " + id)
        );

        newUserInfo.setId(user.getId());
        newUserInfo.setIsConnected(user.getIsConnected());
        newUserInfo.setUsername(user.getUsername());
        newUserInfo.setPassword(user.getPassword());
        newUserInfo.setRoles(user.getRoles());

        return userInfoRepository.save(newUserInfo);
    }

    @Override
    public void deleteUserInfo(long id) {
        try {
            userInfoRepository.deleteById(id);
        } catch (Exception ex) {
            throw new UserInfoDeleteException(id, ex);
        }
    }

    @Override
    public Optional<UserInfo> findByUsername(String username) {
        return userInfoRepository.findByUsername(username);
    }

    @Override
    public UserInfo addUser(UserInfo user, List<Long> rolIds) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        user.setRoles((List<Role>) roleRepository.findAllById(rolIds));

        return userInfoRepository.save(user);
    }

    @Override
    public void remove(UserInfo user) {
        userInfoRepository.delete(user);
    }

    @Override
    public void updateIsConnected(Long idUser) {
        UserInfo userInfo = userInfoRepository.findById(idUser).orElseThrow(
                () -> new UserInfoNotFoundException("No hay usuarios con ese id -> " + idUser)
        );

        if (userInfo.getIsConnected() == 0) {
            userInfo.setIsConnected(1);
        } else {
            userInfo.setIsConnected(0);
        }

        userInfoRepository.save(userInfo);
    }

    @Override
    public Integer selectUserIsConnectedByUserId(Long id)
    {
        return userInfoRepository.selectUserIsConnectedByUserId(id);
    }

    @Override
    public UserInfo selectUserInfoByUsername(String user) {
        return userInfoRepository.selectUserInfoByUsername(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        if (userInfoRepository.existsByUsername(username)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Long> getRolesByUserId(Long id) {
        UserInfo userInfo = userInfoRepository.findById(id).orElseThrow(
                () -> new UserInfoNotFoundException("No hay usuarios con ese id -> " + id)
        );

        return userInfoRepository.getRolesByUserId(id);
    }
}
