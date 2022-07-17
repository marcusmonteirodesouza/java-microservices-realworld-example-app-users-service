package com.example.realworld.users_service;

import com.example.realworld.users_service.custom_exceptions.AlreadyExistsException;
import com.example.realworld.users_service.dto.LoginRequestDto;
import com.example.realworld.users_service.dto.RegisterUserRequestDto;
import com.example.realworld.users_service.dto.UserDto;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserDto> registerUser(
            @RequestBody @Validated RegisterUserRequestDto request) {
        try {
            var user =
                    usersService.registerUser(
                            request.username(), request.email(), request.password());
            var token = jwtService.makeToken(user);
            return new ResponseEntity<>(UserDto.fromUserAndToken(user, token), HttpStatus.CREATED);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserDto> registerUser(@RequestBody @Validated LoginRequestDto request) {
        try {
            if (!usersService.verifyPassword(request.email(), request.password())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            var user = usersService.getUserByEmail(request.email());

            if (user.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            var token = jwtService.makeToken(user.get());

            return new ResponseEntity<>(UserDto.fromUserAndToken(user.get(), token), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
