package com.sparta.sparta_spring.dto;

import lombok.Getter;
import lombok.Setter;
// setter?? 안바뀌지않나?
@Getter
@Setter
public class LoginRequestDto {
    private String username;
    private String password;
}