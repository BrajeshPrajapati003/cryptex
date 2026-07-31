package com.cryptex.notification.entity;

import com.cryptex.common.entity.BaseEntity;
import com.cryptex.notification.enums.NotificationChannel;
import com.cryptex.notification.enums.NotificationStatus;
import com.cryptex.notification.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "notifications")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Notification extends BaseEntity {

    @Column(nullable = false)
    private String recipient;

    @Column(nullable = false)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String body; // HTML -> 10KB, 20KB, 50KB, ...

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    private NotificationChannel channel;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    private String failureReason;

    @Builder.Default
    private Integer retryCount = 0;

    private Instant sentAt;
}
