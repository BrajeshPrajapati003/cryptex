package com.cryptex.auth.mapper;

import com.cryptex.auth.dto.response.LoginResponse;
import com.cryptex.auth.dto.request.RegisterRequest;
import com.cryptex.auth.dto.response.RegisterResponse;
import com.cryptex.auth.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(target = "password", ignore = true)
    AppUser toEntity(RegisterRequest request);

    RegisterResponse toRegisterResponse(AppUser user);

    LoginResponse toLoginResponse(AppUser user);
}
