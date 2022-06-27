package dev.nano.springboottesting.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "Users")
@Data
@AllArgsConstructor @NoArgsConstructor
public class User {
    private long id;
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
