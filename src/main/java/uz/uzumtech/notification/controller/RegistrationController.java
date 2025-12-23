package uz.uzumtech.notification.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.uzumtech.notification.dto.request.RegistrationRequest;
import uz.uzumtech.notification.dto.response.RegistrationResponse;
import uz.uzumtech.notification.service.RegistrationService;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("api/notification")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationController {
    RegistrationService service;

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponse> signUp(@RequestBody @Valid RegistrationRequest request) {
        RegistrationResponse response = service.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
