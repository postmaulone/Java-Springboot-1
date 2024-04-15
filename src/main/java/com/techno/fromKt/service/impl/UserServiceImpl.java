package com.techno.fromKt.service.impl;

import com.techno.fromKt.domain.dto.request.ReqUserDto;
import com.techno.fromKt.domain.dto.response.ResMessageDto;
import com.techno.fromKt.domain.dto.response.ResUserDto;
import com.techno.fromKt.domain.entity.TypeEntity;
import com.techno.fromKt.domain.entity.UserEntity;
import com.techno.fromKt.repository.TypeRepository;
import com.techno.fromKt.repository.UserRepository;
import com.techno.fromKt.service.UserService;
import com.techno.fromKt.util.JwtGenerator;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public UserEntity isExist(int id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User id not found");
        }
        return user.get();
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
    private String hashPw(String pw){
        Argon2 password = Argon2Factory.create();
        return password.hash(10, 65536 , 1, pw.toCharArray());
    }

    @Override
    public ResMessageDto<ResUserDto> create(ReqUserDto req) {
        checkDupeUsr(req.getUsername());
        checkDupeEml(req.getEmail());
        UserEntity input = UserEntity.builder()
                .name(req.getName())
                .username(req.getUsername())
                .email(req.getEmail())
                .password(hashPw(req.getPassword()))
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
        UserEntity user = isExist(id);
        if (!user.getUsername().equals(req.getUsername()))
            checkDupeUsr(req.getUsername());
        if (!user.getEmail().equals(req.getEmail()))
            checkDupeEml(req.getEmail());
        Argon2 password = Argon2Factory.create();
        user.setName(req.getName());
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(hashPw(req.getPassword()));
        user.setType(typeAssign(req.getType()));
        userRepository.save(user);
        return new ResMessageDto<>(
                200, "User added!",
                resUser(req.getUsername())
        );
    }

    @Override
    public ResMessageDto<List<ResUserDto>> getAll() {
        List<UserEntity> users = userRepository.findAll();
        List<ResUserDto> res = new ArrayList<>();
        for (UserEntity user : users){
            if(user.getUsername() != null){
                ResUserDto data = resUser(user.getUsername());
                res.add(data);
            }
        }
        return new ResMessageDto<>(200, "Success", res);
    }

    @Override
    public ResMessageDto<ResUserDto> getById(String token) {
        int id = (int) new JwtGenerator().decodeJwt(token).get("id");
        UserEntity user = isExist(id);
        ResUserDto res = resUser(user.getUsername());
        return new ResMessageDto<>(200, "Success", res);
    }
}
