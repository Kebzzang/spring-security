package com.keb.jwt.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //increment
    private long id;
    private String username;
    private String password;
    private String roles; //USER, ADMIN

    public List<String> getRoleList(){ //롤이 여러개인 유저가 있을 수 있으므로 이렇게 만들어여
        if(this.roles.length()>0){
            return Arrays.asList(this.roles.split(",")); //콤마로 구분해서 리턴
        }
        return new ArrayList<>();
    }

}
