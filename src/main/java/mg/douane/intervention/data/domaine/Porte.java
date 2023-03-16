package mg.douane.intervention.data.domaine;

import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@Table(name = "porte")
public class Porte {
    @GenericGenerator(name = "seqPorte", strategy = "increment")
    @Id
    @GeneratedValue(generator = "seqPorte")
    // @GeneratedValue(strategy = GenerationType.AUTO, generator = "G1")
    // @SequenceGenerator(name = "G1", sequenceName = "LOG_SEQ")
    // @Column(name = "idRole", unique = true, nullable = false, precision = 22,
    // scale = 0)
    private long idPorte;

    @Column(name = "libellePorte", length = 20, nullable = false)
    private String libellePorte;

//    @OneToMany(mappedBy = "porte")
//    private Set<Poste> postes;

}
