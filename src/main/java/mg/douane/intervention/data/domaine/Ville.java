package mg.douane.intervention.data.domaine;

import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@Table(name = "ville")
public class Ville {
    @GenericGenerator(name = "seqVile", strategy = "increment")
    @Id
    @GeneratedValue(generator = "seVille")
    // @GeneratedValue(strategy = GenerationType.AUTO, generator = "SeqTdVille")
    // @SequenceGenerator(name = "SeqIdVille", sequenceName = "LOG_SEQ")
    private Long idVille;

    @Column(name = "libelleVille", nullable = false, length = 50)
    private String libelleVille;

    @OneToMany(mappedBy = "ville")
    private Set<Quartier> quartiers;

}
