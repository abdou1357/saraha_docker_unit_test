package com.br.sarahaa.controllers;


import com.br.sarahaa.dto.AuthenticationRequest;
import com.br.sarahaa.dto.AuthenticationResponse;
import com.br.sarahaa.dto.UserDto;
import com.br.sarahaa.services.UserService;
import com.br.sarahaa.services.auth.AppUserDetailsService;
import com.br.sarahaa.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    @PostMapping(value = "/register",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> save(@RequestBody UserDto user) {
        return ResponseEntity.ok(service.save(user));
    }

    @PostMapping(value ="/login",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        // this code snippet does all the authentication process
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        System.out.println("dglsdfklmxkdfmldkxmlfkxdmlkfdxlm");
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String jwt = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(
                AuthenticationResponse.builder()
                        .token(jwt)
                        .build()
        );
    }

    @GetMapping("/{user-email}")
    public ResponseEntity<UserDto> findByEmail(
            @PathVariable("user-email") String email
    ) {
        return ResponseEntity.ok(service.findByEmail(email));
    }

    @GetMapping("/recently-joined-users")
    public ResponseEntity<List<UserDto>> recentlyJoinedUsers() {
        return ResponseEntity.ok(service.recentlyJoinedUsers());
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUser(
            @RequestParam(value = "first-name", required = false) String firstName,
            @RequestParam(value = "last-name", required = false) String lastName,
            @RequestParam(value = "user-name", required = false) String userName
    ) {
        return ResponseEntity.ok(service.searchUser(firstName, lastName, userName));
    }

}

