package com.techno.fromKt.service;

import com.techno.fromKt.domain.dto.request.ReqGameDto;
import com.techno.fromKt.domain.dto.response.ResGameDto;
import com.techno.fromKt.domain.dto.response.ResMessageDto;

import java.util.List;

public interface GameService {
    ResMessageDto<String> create(ReqGameDto req);
    ResMessageDto<String> update(int id, ReqGameDto req);
    ResMessageDto<List<ResGameDto>> getAll(String token);
    ResMessageDto<ResGameDto> getById(int id, String token);
    ResMessageDto<String> delete(int id);
}
