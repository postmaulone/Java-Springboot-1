package com.techno.fromKt.service;

import com.techno.fromKt.domain.dto.request.ReqFavoriteDto;
import com.techno.fromKt.domain.dto.request.ReqGameDto;
import com.techno.fromKt.domain.dto.response.ResFavoriteDto;
import com.techno.fromKt.domain.dto.response.ResGameDto;
import com.techno.fromKt.domain.dto.response.ResMessageDto;

import java.util.List;

public interface FavoriteService {
    ResMessageDto<String> create(ReqFavoriteDto req, String token);
    ResMessageDto<String> update(int id, ReqFavoriteDto req);
    ResMessageDto<List<ResFavoriteDto>> getAll();
    ResMessageDto<ResFavoriteDto> getById(int id, String token);
    ResMessageDto<String> delete(int id);
}
