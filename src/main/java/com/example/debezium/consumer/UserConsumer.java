package com.example.debezium.consumer;

import com.example.debezium.model.User;
import com.example.debezium.mongoModel.MongoUser;
import com.example.debezium.mongoRepository.UserMongoRepository;
import com.example.debezium.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserConsumer {

    private final ObjectMapper mapper;
    private final UserMongoRepository userMongoRepository;
    private final UserRepository userRepository;

    @KafkaListener(topics = "mysql.db.user")
    public void consumeUser(ConsumerRecord<String, String> record) throws JsonProcessingException {
        String consumedValue = record.value();

        var jsonNode = mapper.readTree(consumedValue);
        JsonNode payload = jsonNode.path("payload");
        JsonNode after = payload.path("after");
        JsonNode before = payload.path("before");

        User beforeUser = mapper.treeToValue(before, User.class);
        User afterUser = mapper.treeToValue(after, User.class);
        log.info("values before:{} , after:{}",beforeUser,afterUser);
    }
    @KafkaListener(topics = "mongo.test_user.users")
    public void consumeMongoUser(ConsumerRecord<String, String> record) throws JsonProcessingException {
        String consumedValue = record.value();

        var jsonNode = mapper.readTree(consumedValue);
        JsonNode payload = jsonNode.path("payload");
        JsonNode after = payload.path("after");
        JsonNode updateDescription = payload.path("updateDescription");


        MongoUser afterUser = Objects.isNull(after.textValue())?null:
                mapper.readValue(after.textValue(), MongoUser.class);
        log.info("values  after:{} , updateDesc:{}",afterUser,updateDescription);
    }
}
