package com.sparta.sparta_spring.controller;

import com.sparta.sparta_spring.dto.*;
import com.sparta.sparta_spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    // DI 주입
    private final UserService userService;

    // 요구사항1. 회원 가입
    @Validated
    @PostMapping("/signup")
    public ResponseEntity<BlogDto.SendMessage> signup(@RequestBody @Valid UserDto.SignupRequest signupRequestDto, BindingResult bindingResult) {
        return userService.signup(signupRequestDto, bindingResult);
    }


    // 요구사항2. 로그인
    @PostMapping("/login")
    public ResponseEntity<BlogDto.SendMessage> login(@RequestBody UserDto.LoginRequest loginRequestDto) {
        return userService.login(loginRequestDto);
    }
}