package com.techno.fromKt.service;

import com.techno.fromKt.domain.dto.request.ReqGenreDto;
import com.techno.fromKt.domain.dto.request.ReqUserDto;
import com.techno.fromKt.domain.dto.response.ResMessageDto;
import com.techno.fromKt.domain.dto.response.ResGenreDto;

public interface GenreService {

    ResMessageDto<String> create(ReqGenreDto req);
    ResMessageDto<String> update(int id, ReqGenreDto req);
    ResMessageDto<java.util.List<ResGenreDto>> getAll();
    ResMessageDto<ResGenreDto> getById(int id);
    ResMessageDto<String> delete(int id);

}
