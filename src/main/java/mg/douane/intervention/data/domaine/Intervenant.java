package mg.douane.intervention.data.domaine;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@Table(name = "intervenant")
public class Intervenant {
    @GenericGenerator(name = "seqInt", strategy = "increment")
    @Id
    @GeneratedValue(generator = "seqInt")
    private Long idInt;

    @ManyToOne
    @JoinColumn(name = "agent_idInt")
    private Agent agentInt;

    @ManyToOne
    @JoinColumn(name = "prob_idInt")
    private Probleme probInt;

}
