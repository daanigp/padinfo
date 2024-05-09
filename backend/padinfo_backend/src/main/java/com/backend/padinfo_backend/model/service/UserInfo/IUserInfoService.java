package com.backend.padinfo_backend.model.service.UserInfo;

import com.backend.padinfo_backend.model.entity.UserInfo;

import java.util.List;

public interface IUserInfoService {
    List<UserInfo> findAll();
    UserInfo findById(long id);
    UserInfo createUserInfo(UserInfo user);
    UserInfo updateUserInfo(long id, UserInfo newUserInfo);
    void deleteUserInfo(long id);

    UserInfo selectUserInfoByUsername(String user);

    // 4. Actualizar isConnected
    void updateIsConnected(Long idUser);
}
