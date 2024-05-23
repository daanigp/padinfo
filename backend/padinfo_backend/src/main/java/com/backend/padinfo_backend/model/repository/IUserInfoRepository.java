package com.backend.padinfo_backend.model.repository;

import com.backend.padinfo_backend.model.entity.Role;
import com.backend.padinfo_backend.model.entity.UserInfo;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Hidden
@Repository
public interface IUserInfoRepository extends CrudRepository<UserInfo, Long> {

    Optional<UserInfo> findByUsername(String username);

    // 5. Obtener el usuario conectado
    UserInfo selectUserIsConnected();

    // 13. Obtener el UserInfo segun nombre de usuario
    UserInfo selectUserInfoByUsername(String user);

    // Obtener si existe un usuario con username x
    boolean existsByUsername(String username);

    // Obtener los roles de un usuario con id x
    List<Long> getRolesByUserId(Long id);

}
