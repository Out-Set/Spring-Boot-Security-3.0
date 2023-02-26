package com.example.demosecurity.Repository;

import com.example.demosecurity.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    UserInfo findById(int id);

    Optional<UserInfo> findByName(String username);

}
