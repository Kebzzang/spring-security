package com.keb.sercurity1.Repository;

import com.keb.sercurity1.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsername(String username);
    //CRUD 함수를 JpaRepository가 들고 있음
    //@Repository 어노테이션 없이 ok


}
