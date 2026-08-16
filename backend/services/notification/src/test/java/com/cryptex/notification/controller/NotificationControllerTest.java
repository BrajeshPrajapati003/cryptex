package com.cryptex.notification.controller;

import com.cryptex.common.exception.GlobalExceptionHandler;
import com.cryptex.notification.dto.request.SendNotificationRequest;
import com.cryptex.notification.dto.response.NotificationResponse;
import com.cryptex.notification.enums.NotificationStatus;
import com.cryptex.notification.enums.NotificationType;
import com.cryptex.notification.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private NotificationService notificationService;

    @Test
    void shouldSendNotificationSuccessfully() throws Exception {

        // Arrange
        SendNotificationRequest request =
                new SendNotificationRequest(
                        "ex1@tester.com",
                        NotificationType.EMAIL_VERIFICATION,
                        Map.of(
                                "name", "Brajesh",
                                "verificationLink", "http://localhost/verify"
                        )
                );

        UUID id = UUID.randomUUID();

        NotificationResponse response =
                new NotificationResponse(
                        id,
                        NotificationStatus.SENT,
                        Instant.now()
                );

        when(notificationService.sendNotification(request))
                .thenReturn(response);

        /*
        When the controller calls the service with this request,
        pretend the service successfully returned this response.
         */

        // Act & Assert
        mockMvc.perform(
                        post("/api/v1/notifications")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request)) // converts our Java record into JSON
                )
                .andExpect(status().isOk());

        // Verify controller delegated to service
        verify(notificationService)
                .sendNotification(request);
    }

    @Test
    void shouldGetNotificationSuccessfully() throws Exception {

        // Arrange
        UUID id = UUID.randomUUID();

        NotificationResponse response =
                new NotificationResponse(
                        id,
                        NotificationStatus.SENT,
                        Instant.now()
                );

        when(notificationService.getNotification(id))
                .thenReturn(response);

        // Act & Assert
        mockMvc.perform(
                get("/api/v1/notifications/{id}", id)
        ).andExpect(status().isOk());

        // Verify service interaction
        verify(notificationService)
                .getNotification(id);
    }

    @Test
    void shouldReturnBadRequestWhenRequestIsInvalid() throws Exception{

        SendNotificationRequest request =
                new SendNotificationRequest(
                        "",
                        NotificationType.EMAIL_VERIFICATION,
                        Map.of()
                );

        mockMvc.perform(
                post("/api/v1/notifications")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());

        verify(notificationService, never())
                .sendNotification(any(SendNotificationRequest.class));
    }
}
