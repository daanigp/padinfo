package com.backend.padinfo_backend.model.service.UserInfo;

import com.backend.padinfo_backend.model.entity.UserInfo;

import java.util.List;
import java.util.Optional;

public interface IUserInfoService {
    List<UserInfo> findAll();
    UserInfo findById(long id);
    UserInfo createUserInfo(UserInfo user);
    UserInfo updateUserInfo(long id, UserInfo newUserInfo);
    void deleteUserInfo(long id);
    Optional<UserInfo> findByUsername(String username);

    UserInfo addUser(UserInfo user, List<Long> rolIds);
    void remove(UserInfo user);



    // 4. Actualizar isConnected
    void updateIsConnected(Long idUser);
    UserInfo selectUserIsConnected();
    UserInfo selectUserInfoByUsername(String user);

    boolean existsByUsername(String username);

}
