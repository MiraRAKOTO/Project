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
@Table(name = "priorite")
public class Priorite {
    @GenericGenerator(name = "seqPrio", strategy = "increment")
    @Id
    @GeneratedValue(generator = "seqPrio")
    // @GeneratedValue(strategy = GenerationType.AUTO, generator = "G1")
    // @SequenceGenerator(name = "G1", sequenceName = "LOG_SEQ")
    // @Column(name = "idRole", unique = true, nullable = false, precision = 22,
    // scale = 0)
    private long idPriorite;

    @Column(name = "libellePriority", length = 20, nullable = false)
    private String libellePriorite;

    @OneToMany(mappedBy = "priorite")
    private Set<Probleme> problemes;

    public Priorite(long idPriorite, String libellePriorite) {
        this.idPriorite = idPriorite;
        this.libellePriorite = libellePriorite;
    }
}
