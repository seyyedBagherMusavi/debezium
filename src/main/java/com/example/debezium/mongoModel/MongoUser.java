package com.example.debezium.mongoModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MongoUser {

    @Id
    private ObjectId _id;

    private Long id;

    private String name;

}
