package uz.uzumtech.notification.configuration.kafka;

import org.springframework.kafka.support.serializer.JacksonJsonSerializer;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.ToStringSerializer;
import uz.uzumtech.notification.configuration.props.KafkaProps;
import uz.uzumtech.notification.dto.DlqDto;
import uz.uzumtech.notification.dto.NotificationDto;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ProducerConfiguration {

    KafkaProps kafkaProps;

    public Map<String, Object> objectDeserializerConfigs() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.CLIENT_ID_CONFIG, (kafkaProps.getClientId() + UUID.randomUUID()));
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProps.getBootstrapServers());
        props.put(ProducerConfig.CLIENT_DNS_LOOKUP_CONFIG, kafkaProps.getClientDnsLookup());
        props.put(ProducerConfig.ACKS_CONFIG, kafkaProps.getAcksConfig());
        props.put(ProducerConfig.RETRIES_CONFIG, kafkaProps.getRetriesConfig());
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProps.getBatchSizeConfig());
        props.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProps.getLingerMsConfig());
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, kafkaProps.getBufferMemoryConfig());
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }

    @Bean("dlqTopic")
    public KafkaTemplate<String, DlqDto> dlqTemplate() {
        return new KafkaTemplate<>(
                new DefaultKafkaProducerFactory<>(
                        objectDeserializerConfigs(),
                        ToStringSerializer::new,
                        JacksonJsonSerializer::new
                )
        );
    }

    @Bean("notificationTopic")
    public KafkaTemplate<String, NotificationDto> notificationTemplate() {
        return new KafkaTemplate<>(
                new DefaultKafkaProducerFactory<>(
                        objectDeserializerConfigs(),
                        ToStringSerializer::new,
                        JacksonJsonSerializer::new
                )
        );
    }

}
