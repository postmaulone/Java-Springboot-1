package com.techno.fromKt.service;

import com.techno.fromKt.domain.dto.request.ReqGameDto;
import com.techno.fromKt.domain.dto.response.ResGameDto;
import com.techno.fromKt.domain.dto.response.ResMessageDto;

public interface GameService {
    ResMessageDto<String> create(ReqGameDto req);
    ResMessageDto<String> update(int id, ReqGameDto req);
    ResMessageDto<java.util.List<ResGameDto>> getAll();
    ResMessageDto<ResGameDto> getById(int id);
}
