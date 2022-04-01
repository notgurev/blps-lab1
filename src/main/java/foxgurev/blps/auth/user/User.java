package foxgurev.blps.auth.user;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.domain.Persistable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true)
public class User implements UserDetails, Persistable<Long> {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NaturalId
    @Email
    @Size(max = 255)
    @NotBlank
    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Include
    private String email;

    @Size(max = 255)
    @NotBlank
    @Column(nullable = false)
    private String username;

    @Size(max = 255)
    @NotBlank
    @Column(nullable = false)
    private String password;

    @Size(max = 63)
    @NotBlank
    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(STRING)
    @Column(nullable = false)
    private Role role;

    public User(@Email @Size(max = 255) @NotBlank String email, @Size(max = 255) @NotBlank String username, @Size(max = 255) @NotBlank String password, @Size(max = 63) @NotBlank String phoneNumber, Role role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}
