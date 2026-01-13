package uz.uzumtech.notification.configuration.kafka;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import uz.uzumtech.notification.configuration.props.KafkaProps;
import uz.uzumtech.notification.dto.NotificationDto;
import uz.uzumtech.notification.handler.KafkaExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConsumerConfiguration {

    KafkaProps kafkaProps;
    KafkaExceptionHandler kafkaErrorHandler;

    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProps.getBootstrapServers());
        props.put(ProducerConfig.CLIENT_DNS_LOOKUP_CONFIG, kafkaProps.getClientDnsLookup());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProps.getGroupId());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3000);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 60000);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);
        props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, true);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaProps.getAutoOffsetResetConfig());

        return props;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NotificationDto> notificationFactory() {
        return buildContainerFactory(NotificationDto.class);
    }

    private <T> ConcurrentKafkaListenerContainerFactory<String, T> buildContainerFactory(Class<T> type) {
        JacksonJsonDeserializer<T> jsonDeserializer = new JacksonJsonDeserializer<>(type, false);
        jsonDeserializer.addTrustedPackages("uz.uzumtech.notification.dto");

        DefaultKafkaConsumerFactory<String, T> consumerFactory = new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                jsonDeserializer
        );

        ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(false);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        factory.setCommonErrorHandler(kafkaErrorHandler);

        return factory;
    }
}
