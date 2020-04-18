package springsecurityjwt.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idrole")
    private int id;

    @Column(name = "name")
    private String name;
}
