package uz.uzumtech.notification.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FirebaseConfiguration {

    @Value("${app.firebase-config-path}")
    String configPath;

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        InputStream serviceAccount = new ClassPathResource(configPath).getInputStream();

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);
        return FirebaseMessaging.getInstance(app);
    }
}
