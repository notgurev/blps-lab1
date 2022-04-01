package foxgurev.blps.auth.entity;

import foxgurev.blps.auth.user.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@Table(name = "creator")
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @Size(min = 10, max = 50)
//    @NotEmpty
//    @Column(name = "email", unique = true)
//    private String email;
//
//    @Size(min = 4)
//    @NotEmpty
//    @Column(name = "password", nullable = false)
//    private String password;
//
////    @Column(name = "age")
////    private String age;
//
//    @Column(name = "role")
//    private Role role;
//
////    @Column(name = "token")
////    private String token; //todo remove
//}
