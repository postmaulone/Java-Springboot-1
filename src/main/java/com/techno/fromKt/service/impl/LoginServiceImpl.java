package com.techno.fromKt.service.impl;

import com.techno.fromKt.domain.dto.request.ReqLoginDto;
import com.techno.fromKt.domain.dto.response.ResLoginDto;
import com.techno.fromKt.domain.dto.response.ResMessageDto;
import com.techno.fromKt.domain.dto.response.ResUserDto;
import com.techno.fromKt.domain.entity.UserEntity;
import com.techno.fromKt.exception.SomethingWrongException;
import com.techno.fromKt.repository.UserRepository;
import com.techno.fromKt.service.LoginService;
import com.techno.fromKt.util.JwtGenerator;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;

    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public ResLoginDto login(ReqLoginDto req) {
        UserEntity userExist = userRepository.findByEmail(req.getEmail());
        if (userExist == null) {
            throw new SomethingWrongException("Username does not exist");
        }

        Argon2 argon2 = Argon2Factory.create();
        boolean matchPassword = argon2.verify(userExist.getPassword(), req.getPassword().toCharArray());
        if (!matchPassword) {
            throw new SomethingWrongException("Invalid password");
        }

        ResUserDto userData = new ResUserDto(
                userExist.getId(),
                userExist.getName(),
                userExist.getUsername(),
                userExist.getEmail(),
                userExist.getType()
        );

        String email = userExist.getEmail();
        String token = new JwtGenerator().createJwt(userData);
        return new ResLoginDto(email, token);
    }
}
