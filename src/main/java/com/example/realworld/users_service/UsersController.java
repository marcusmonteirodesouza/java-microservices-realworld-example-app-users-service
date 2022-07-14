package com.example.realworld.users_service;

import com.example.realworld.users_service.dto.RegisterUserRequestDto;
import com.example.realworld.users_service.dto.UserDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {
    private final UsersService usersService;

    private final JwtService jwtService;

    public UsersController(UsersService usersService, JwtService jwtService) {
        this.usersService = usersService;
        this.jwtService = jwtService;
    }

    @PostMapping("/users")
    public UserDto registerUser(@RequestBody @Validated RegisterUserRequestDto request) {
        var user =
                usersService.registerUser(request.username(), request.email(), request.password());
        var token = jwtService.makeToken(user);
        return UserDto.fromUserAndToken(user, token);
    }
}
