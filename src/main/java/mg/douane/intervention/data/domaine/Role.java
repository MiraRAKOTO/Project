package mg.douane.intervention.data.domaine;

import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@Table(name = "role")
public class Role {
    @GenericGenerator(name = "seqRole", strategy = "increment")
    @Id
    @GeneratedValue(generator = "seqRole")
    private long idRole;

    @Column(name = "libelleRole", length = 20, nullable = false)
    private String libelleRole;

    public Role() {
    }

    public Role(long idRole, String libelleRole) {
        this.idRole = idRole;
        this.libelleRole = libelleRole;
    }
}
