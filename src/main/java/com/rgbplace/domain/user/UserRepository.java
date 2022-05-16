package com.rgbplace.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    public User findById(String id);
    public User findByIdAndPswd(String id, String pswd);
}
