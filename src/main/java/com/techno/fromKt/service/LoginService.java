package com.techno.fromKt.service;

import com.techno.fromKt.domain.dto.request.ReqLoginDto;
import com.techno.fromKt.domain.dto.response.ResLoginDto;
import com.techno.fromKt.domain.dto.response.ResMessageDto;

public interface LoginService {
    ResLoginDto login(ReqLoginDto req);
}
