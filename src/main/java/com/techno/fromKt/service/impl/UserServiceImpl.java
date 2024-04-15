package com.techno.fromKt.service.impl;

import com.techno.fromKt.domain.dto.request.ReqUserDto;
import com.techno.fromKt.domain.dto.response.ResMessageDto;
import com.techno.fromKt.domain.dto.response.ResUserDto;
import com.techno.fromKt.domain.entity.TypeEntity;
import com.techno.fromKt.domain.entity.UserEntity;
import com.techno.fromKt.repository.TypeRepository;
import com.techno.fromKt.repository.UserRepository;
import com.techno.fromKt.service.UserService;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    TypeRepository typeRepository;

    private void checkDupeUsr(String username) {
        UserEntity usedUsername = userRepository.findByUsername(username);
        if (usedUsername != null) {
            throw new IllegalArgumentException("Username already used");
        }
    }
    private void checkDupeEml(String email){
        UserEntity usedEmail = userRepository.findByEmail(email);
        if(usedEmail != null){
            throw new IllegalArgumentException("Email already used");
        }
    }
    private ResUserDto resUser(String username){
        UserEntity user = userRepository.findByUsername(username);
        String type = null;
        if(Objects.equals(user.getType(), "T001")){
            type = "Free";
        }
        if(Objects.equals(user.getType(), "T002")){
            type = "Premium";
        }
        return new ResUserDto(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                type
        );
    }
    public Optional<UserEntity> isExist(int id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User id not found");
        }
        return user;
    }
    private String typeAssign(String type) {
        TypeEntity typeFound = typeRepository.findByName(nullType(type));
        if (typeFound == null) {
            throw new RuntimeException("Type not found"); // Replace RuntimeException with DataNotFoundException if it's defined in your project
        }
        return typeFound.getId(); // Assuming 'getTypeId' returns a String. Make sure this is non-null or handled if it can be.
    }
    private String nullType(String type) {
        if (type.isEmpty()) {
            return "Free";
        } else {
            return type;
        }
    }

    @Override
    public ResMessageDto<ResUserDto> create(ReqUserDto req) {
        checkDupeUsr(req.getUsername());
        checkDupeEml(req.getEmail());
        Argon2 password = Argon2Factory.create();
        UserEntity input = UserEntity.builder()
                .name(req.getName())
                .username(req.getUsername())
                .email(req.getEmail())
                .password(password.toString())
                .type(typeAssign(req.getType()))
                .build();
        userRepository.save(input);
        return new ResMessageDto<>(
                200, "User added!",
                resUser(req.getUsername())
        );
    }

    @Override
    public ResMessageDto<ResUserDto> update(int id, ReqUserDto req) {
        Optional<UserEntity> user = isExist(id);
        if (user.isPresent()) {
            if (!user.get().getUsername().equals(req.getUsername()))
                checkDupeUsr(req.getUsername());
            if (!user.get().getEmail().equals(req.getEmail()))
                checkDupeEml(req.getEmail());
            Argon2 password = Argon2Factory.create();
            user.get().setName(req.getName());
            user.get().setUsername(req.getUsername());
            user.get().setEmail(req.getEmail());
            user.get().setPassword(password.hash(10, 65536, 1, req.getPassword().toCharArray()));
            user.get().setType(typeAssign(req.getType()));
            userRepository.save(user.get());
            return new ResMessageDto<>(200, "User added!", resUser(req.getUsername()));
        }
        return new ResMessageDto<>(
                200, "User updated!",
                resUser(req.getUsername())
        );
    }

    @Override
    public ResMessageDto<List<ResUserDto>> getAll() {
        return null;
    }

    @Override
    public ResMessageDto<ResUserDto> getById(int id) {
        return null;
    }
}
