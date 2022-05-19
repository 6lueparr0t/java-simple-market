package com.rgbplace.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUid(String uid);
    public User findByUidAndPw(String uid, String pw);
}
