package com.nhnacademy.springmvc.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StudentRegisterRequest {
    @NotBlank
    private String name;

    @Email
    private String email;

    @Min(0)
    @Max(100)
    private int score;

    @Length(max = 200)
    private String comment;

    public StudentRegisterRequest() {
    }

    public StudentRegisterRequest(String name, String email, int score, String comment) {
        this.name = name;
        this.email = email;
        this.score = score;
        this.comment = comment;
    }
}
