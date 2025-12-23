package uz.uzumtech.notification.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordUtils {
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-=_+";
    private static final SecureRandom RANDOM = new SecureRandom();

    public String generatePassword(int length) {
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            password.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }
        return password.toString();
    }
}

//    метод bCryptEncode() должен быть здесь или в RegistrationServiceImpl использовать PasswordEncoder???

//    private String bCryptEncode(String password) {
//        return new BCryptPasswordEncoder(12).encode(password);
//    }
