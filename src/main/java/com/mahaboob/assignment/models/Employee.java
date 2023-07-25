package com.mahaboob.assignment.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;

    @Field
    @NotBlank
    private String name;

    @Field
    @NotBlank
    private String phoneNumber;

    @Field
    @NotBlank
    @Email
    private String email;

    @Field
    @UUID
    private String managerId;

    @Field
    @URL
    private String profileImage;

}
