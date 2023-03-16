package mg.douane.intervention.data.domaine;

import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@Table(name = "quartier")
public class Quartier {
    @GenericGenerator(name = "seqQuar", strategy = "increment")
    @Id
    @GeneratedValue(generator = "seqQuar")
    // @GeneratedValue(strategy = GenerationType.AUTO, generator = "G1")
    // @SequenceGenerator(name = "G1", sequenceName = "LOG_SEQ")
    // @Column(name = "idRole", unique = true, nullable = false, precision = 22,
    // scale = 0)
    private long idQuartier;

    @Column(name = "libelleQuartier", length = 50, nullable = false)
    private String libelleQuartier;

    @OneToMany(mappedBy = "quartier")
    private Set<Poste> postes;

    @ManyToOne
    @JoinColumn(name = "ville_id")
    private Ville ville;

}
