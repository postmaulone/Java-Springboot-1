package com.techno.fromKt.service;

import com.techno.fromKt.domain.dto.request.ReqUserDto;
import com.techno.fromKt.domain.dto.response.ResMessageDto;
import com.techno.fromKt.domain.dto.response.ResUserDto;

public interface UserService {
    ResMessageDto<ResUserDto> create(ReqUserDto req);
    ResMessageDto<ResUserDto> update(int id, ReqUserDto req);
    ResMessageDto<java.util.List<ResUserDto>> getAll();
    ResMessageDto<ResUserDto> getById(String token);
//    ResMessageDto<String> delete(int id);
}
