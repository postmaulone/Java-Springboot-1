package com.techno.fromKt.service.impl;

import com.techno.fromKt.domain.dto.request.ReqUserDto;
import com.techno.fromKt.domain.dto.response.ResMessageDto;
import com.techno.fromKt.domain.dto.response.ResUserDto;
import com.techno.fromKt.domain.entity.UserEntity;
import com.techno.fromKt.repository.TypeRepository;
import com.techno.fromKt.repository.UserRepository;
import com.techno.fromKt.service.UserService;
import com.techno.fromKt.util.GeneralFunction;
import com.techno.fromKt.util.JwtGenerator;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.AllArgsConstructor;
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
    GeneralFunction generalFunction;

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
    private ResUserDto resUser(UserEntity user){
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
                .type(generalFunction.typeAssign(req.getType()).getId())
                .build();
        try {
            UserEntity newUser = userRepository.save(input);
            return new ResMessageDto<>(
                    200, "User added!",
                    resUser(newUser)
            );
        } catch (Exception ex){
            throw new IllegalArgumentException("User add failed: " + ex);
        }
    }

    @Override
    public ResMessageDto<ResUserDto> update(int id, ReqUserDto req) {
        UserEntity user = isExist(id);
        if (!user.getUsername().equals(req.getUsername()))
            checkDupeUsr(req.getUsername());
        if (!user.getEmail().equals(req.getEmail()))
            checkDupeEml(req.getEmail());
        user.setName(req.getName());
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(hashPw(req.getPassword()));
        user.setType(generalFunction.typeAssign(req.getType()).getId());
        try{
            UserEntity updatedUser = userRepository.save(user);
            return new ResMessageDto<>(
                    200, "User added!",
                    resUser(updatedUser)
            );
        } catch (Exception ex){
            throw new IllegalArgumentException("User update failed: " + ex);
        }
    }

    @Override
    public ResMessageDto<List<ResUserDto>> getAll() {
        try{
            List<UserEntity> users = userRepository.findAll();
            List<ResUserDto> res = new ArrayList<>();
            for (UserEntity user : users){
                if(user.getUsername() != null){
                    ResUserDto data = resUser(user);
                    res.add(data);
                }
            }
            return new ResMessageDto<>(200, "Success", res);
        } catch (Exception ex){
            throw new IllegalArgumentException("Get users failed: " + ex);
        }
    }

    @Override
    public ResMessageDto<ResUserDto> getById(String token) {
        try{
            int id = (int) new JwtGenerator().decodeJwt(token).get("id");
            UserEntity user = isExist(id);
            ResUserDto res = resUser(user);
            return new ResMessageDto<>(200, "Success", res);
        }catch (Exception ex){
            throw new IllegalArgumentException("Get user failed: " + ex);
        }
    }
}
