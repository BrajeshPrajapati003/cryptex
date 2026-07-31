package com.cryptex.notification.mapper;

import com.cryptex.notification.dto.response.NotificationResponse;
import com.cryptex.notification.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMapper {

    NotificationResponse toResponse(Notification notification);

    /*
    No toEntity() -> the service constructs the entity
    because login determines:
        subject, body, channel, status, retryCount
    MapStruct shouldn't guess those.
     */
}
