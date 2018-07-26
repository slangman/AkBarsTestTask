package com.hustleind;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

@Entity
public class User {
    @SequenceGenerator(name = "userSeq", sequenceName = "USER_SEQUENCE", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersSeq")
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String passwordHash;

    @Getter
    @Setter
    private String fName;

    @Getter
    @Setter
    private String lName;

    @Getter
    @Setter
    private String mName;

    @Getter
    @Setter
    private String mobileNumber;

    @Getter
    @Setter
    private String userPicURL;

    @Getter
    @Setter
    private int enabled;
}
