package com.bertolini.CentralAPI.controller;

import com.bertolini.CentralAPI.domain.User;
import com.bertolini.CentralAPI.schema.security.JWTTokenDTO;
import com.bertolini.CentralAPI.schema.user.UserAuthenticationData;
import com.bertolini.CentralAPI.service.security.TokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private final TokenService tokenService;
    private AuthenticationManager manager;

    @PostMapping
    public ResponseEntity userLogin(@RequestBody @Valid UserAuthenticationData data) {
        var token = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var authentication = manager.authenticate(token);

        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new JWTTokenDTO(tokenJWT));
    }

}
