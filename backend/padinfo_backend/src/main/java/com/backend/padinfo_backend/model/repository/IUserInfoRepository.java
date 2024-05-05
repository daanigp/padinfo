package com.backend.padinfo_backend.model.repository;

import com.backend.padinfo_backend.model.entity.UserInfo;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Hidden
@Repository
public interface IUserInfoRepository extends CrudRepository<UserInfo, Long> {
}
