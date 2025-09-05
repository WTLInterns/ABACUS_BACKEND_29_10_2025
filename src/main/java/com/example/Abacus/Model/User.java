package com.example.Abacus.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    // --------------------------common field ---------------------------
    private int userId;

    private String firstName;
    private String lastName;
    
    private String email;
    private String password;


    // --------------------------role teacher ---------------------------

    private String education;

    // ------------------------            --------------------------------//

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private MasterAdmin masterAdmin;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Teacher teacher;

    // @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    // private Student student;

    public enum Role {
        MASTER_ADMIN,
        TEACHER,
        STUDENT
    }


}
