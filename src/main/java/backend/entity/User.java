package backend.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "creator")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 10, max = 50)
    @NotEmpty
    @Column(name = "email", unique = true)
    private String email;

    @Size(min = 4)
    @NotEmpty
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "age")
    private String age;

    @Column(name = "role")
    private Role role;

    @Column(name = "token")
    private String token;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
//    private List<Pin> pins = new ArrayList<>();
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
//    private List<Board> boards = new ArrayList<>();
//
//    public void addPinToUser(Pin pin) {
//        pin.setUser(this);
//        pins.add(pin);
//    }
//
//    public void addBoardToUser(Board board) {
//        board.setUser(this);
//        boards.add(board);
//    }
}
