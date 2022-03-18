package foxgurev.blps.auth.user;

import foxgurev.blps.auth.role.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotBlank
    private String userLogin;

    @Column
    @NotBlank
    @Email
    private String email;

    @Column
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User(@NotBlank String userLogin, @NotBlank @Email String email, String password) {
        this.userLogin = userLogin;
        this.email = email;
        this.password = password;
    }
}
