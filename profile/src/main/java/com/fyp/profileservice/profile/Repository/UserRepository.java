package com.fyp.profileservice.profile.Repository;

import fyp.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    public User findUserByUsernameAndPassword(String username, String password);
    public User findUserByUsername(String username);
}
