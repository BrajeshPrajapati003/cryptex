package com.cryptex.auth.mapper;

import com.cryptex.auth.dto.RegisterRequest;
import com.cryptex.auth.dto.RegisterResponse;
import com.cryptex.auth.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(target = "password", ignore = true)
    AppUser toEntity(RegisterRequest request);

    RegisterResponse toRegisterResponse(AppUser user);
}
