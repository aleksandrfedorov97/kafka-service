package com.example.orderstatusservice.listener;

import com.example.ordercore.model.kafka.messages.OrderEvent;
import com.example.ordercore.model.kafka.messages.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;


@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventListener {

    private final KafkaTemplate<String, OrderStatus> kafkaTemplate;

    @Value("${app.kafka.topics.orderStatusTopic}")
    private String orderStatusTopicName;

    @KafkaListener(
            topics = "${app.kafka.topics.orderTopic}",
            groupId = "${app.kafka.groups.orderEventGroupId}",
            containerFactory = "orderStatusConcurrentKafkaListenerContainerFactory"
    )
    public void listen(
            @Payload OrderEvent orderEvent,
            @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
            @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
            @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp
    ) {
        log.info("Received message: {}", orderEvent);
        log.info("Key: {}; Partition: {}; Topic: {}; Timestamp: {}", key, partition, topic, timestamp);


        OrderStatus orderStatus = OrderStatus.builder()
                .status("CREATED")
                .date(Instant.now())
                .build();

        kafkaTemplate.send(orderStatusTopicName, orderStatus);
    }
}
