package uz.uzumtech.notification.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.uzumtech.notification.dto.request.SendingRequest;
import uz.uzumtech.notification.dto.response.SendingResponse;
import uz.uzumtech.notification.service.NotificationService;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("api/notification")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {
    NotificationService notificationService;

    @PostMapping("/sending")
    public ResponseEntity<SendingResponse> send(@Valid @RequestBody SendingRequest request) {
        SendingResponse response = notificationService.send(request);
        return ResponseEntity.ok(response);
    }
}
