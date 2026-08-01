package com.cryptex.notification.controller;

import com.cryptex.common.response.ApiResponse;
import com.cryptex.notification.dto.request.SendNotificationRequest;
import com.cryptex.notification.dto.response.NotificationResponse;
import com.cryptex.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<ApiResponse<NotificationResponse>> sendNotification(
            @Valid @RequestBody SendNotificationRequest request
            ){

        NotificationResponse response =
                notificationService.sendNotification(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notification sent successfully",
                        response
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NotificationResponse>> getNotification(
            @PathVariable UUID id
            ){

        NotificationResponse response =
                notificationService.getNotification(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notification retrieved successfully",
                        response
                )
        );
    }

}
