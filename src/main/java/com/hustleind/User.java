package com.hustleind;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="user")
@NoArgsConstructor
@EqualsAndHashCode
public class User {
    @Getter
    @Setter
    @Id
    @SequenceGenerator(name = "userSeq", sequenceName = "USER_SEQUENCE", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersSeq")
    private int id;

    @Getter
    @Setter
    @Column(unique = true)
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
