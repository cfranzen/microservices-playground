package de.cfranzen.notification;

import de.cfranzen.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;

    public void send(NotificationRequest request) {
        repository.save(
                Notification.builder()
                        .toCustomerId(request.toCustomerId())
                        .toCustomerEmail(request.toCustomerName())
                        .sender("cfranzen")
                        .message(request.message())
                        .sentAt(LocalDateTime.now())
                        .build()
        );
    }
}
