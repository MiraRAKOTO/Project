package mg.douane.intervention.data.domaine;

import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@Table(name = "fichePoste")
public class FichePoste {
    @GenericGenerator(name = "seqFPoste", strategy = "increment")
    @Id
    @GeneratedValue(generator = "seqFPoste")
    private Long idFich;

    @ManyToOne
    @JoinColumn(name = "agent_idFich")
    private Agent agentFich;

    @ManyToOne
    @JoinColumn(name = "poste_idFich")
    private Poste posteFich;

    @ManyToOne
    @JoinColumn(name = "cat_idFich")
    private Categorie catFich;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateDebFich")
    private Date dateDebFich;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateFinFich")
    private Date dateFinFich;

}
