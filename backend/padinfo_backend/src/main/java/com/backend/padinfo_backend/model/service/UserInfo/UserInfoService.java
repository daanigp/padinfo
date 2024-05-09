package com.backend.padinfo_backend.model.service.UserInfo;

import com.backend.padinfo_backend.exceptions.userInfo.UserInfoDeleteException;
import com.backend.padinfo_backend.exceptions.userInfo.UserInfoNotFoundException;
import com.backend.padinfo_backend.model.entity.UserInfo;
import com.backend.padinfo_backend.model.repository.IUserInfoRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Hidden
@Service
public class UserInfoService implements IUserInfoService{

    @Autowired
    private IUserInfoRepository userInfoRepository;

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
        user.setImageURL("R.drawable.imgperfil_basic");
        return userInfoRepository.save(user);
    }

    @Override
    public UserInfo updateUserInfo(long id, UserInfo newUserInfo) {

        UserInfo user = userInfoRepository.findById(id).orElseThrow(
                () -> new UserInfoNotFoundException("Error al buscar el usuario -> " + id)
        );

        newUserInfo.setId(user.getId());
        newUserInfo.setIsConnected(user.getIsConnected());
        newUserInfo.setUser(user.getUser());
        newUserInfo.setPassword(user.getPassword());

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
    public UserInfo selectUserInfoByUsername(String user) {
        return userInfoRepository.selectUserInfoByUsername(user);
    }
}
