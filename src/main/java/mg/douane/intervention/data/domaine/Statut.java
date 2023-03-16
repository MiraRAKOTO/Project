package mg.douane.intervention.data.domaine;

import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "statut")
public class Statut {

    @GenericGenerator(name = "seqStat", strategy = "increment")
    @Id
    @GeneratedValue(generator = "seqStat")
    // @GeneratedValue(strategy = GenerationType.AUTO, generator = "G1")
    // @SequenceGenerator(name = "G1", sequenceName = "LOG_SEQ")
    // @Column(name = "idRole", unique = true, nullable = false, precision = 22,
    // scale = 0)
    private long idStatut;

    @Column(name = "libelleStatut", length = 20, nullable = false)
    private String libelleStatut;

    @OneToMany(mappedBy = "statut")
    private Set<Probleme> problemes;

    public Statut(long idStatut, String libelleStatut) {
        this.idStatut = idStatut;
        this.libelleStatut = libelleStatut;
    }
}
