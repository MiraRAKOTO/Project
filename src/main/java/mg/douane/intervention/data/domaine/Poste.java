package mg.douane.intervention.data.domaine;

import java.util.Date;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@Table(name = "poste")
public class Poste {
    @GenericGenerator(name = "seqPoste", strategy = "increment")
    @Id
    @GeneratedValue(generator = "seqPoste")
    private Long idPoste;

    @Column(name = "fonctionPoste", length = 50, nullable = false)
    private String fonctionPoste;

    @Column(name = "porte", length = 5, nullable = false)
    private String porte;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateDebPoste")
    private Date dateDebPoste;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateFinPoste")
    private Date dateFinPoste;

    // @ManyToOne
    // @JoinColumn(name = "porte_id")
    // private Porte porte;

    @ManyToOne
    @JoinColumn(name = "quartier_id")
    private Quartier quartier;

    @ManyToOne
    @JoinColumn(name = "hierarchie_id")
    private Hierarchie hierarchiePoste;

}
