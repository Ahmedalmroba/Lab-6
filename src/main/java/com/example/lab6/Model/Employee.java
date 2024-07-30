package com.example.lab6.Model;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Employee {
        @NotEmpty
        @Size(min = 3)
        private String id;

        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z ]+$")
        @Size(min = 5)
        private String name;

        @Email
        private String email;

        @Pattern(regexp = "^05\\d{8}$")
        private String phoneNumber;

        @NotNull
        @Min(26)
        private int age;

        @NotEmpty
        @Pattern(regexp = "supervisor|coordinator")
        private String position;
        @AssertFalse
        private boolean onLeave ;

        @PastOrPresent
        private LocalDate hireDate;

        @NotNull
        @Positive
        private int annualLeave;
    }

