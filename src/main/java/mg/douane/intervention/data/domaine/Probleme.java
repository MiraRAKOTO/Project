package mg.douane.intervention.data.domaine;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import lombok.*;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@Table(name = "probleme")
public class Probleme {
    @GenericGenerator(name = "seqProb", strategy = "increment")
    @Id
    @GeneratedValue(generator = "seqProb")
    private Long idProb;

    @Column(name = "libelleProb", length = 50, nullable = false)
    private String libelleProb;

    @Column(name = "descriptionProb", length = 500, nullable = false)
    private String descriptionProb;

    @Lob
    @Column(name = "pieceJointeProb") // BLOB
    private byte[] pieceJointeProb;

    @Column(name = "fileName", length = 500, nullable = true)
    private String fileName;

    @Column(name = "fileType", length = 500, nullable = true)
    private String fileType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateEnvProb")
    private Date dateEnvProb;

    @Column(name = "confidentialiteProb")
    private boolean confidentialiteProb;

    @ManyToOne
    @JoinColumn(name = "statut_id")
    private Statut statut;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agentProb;

    @ManyToOne
    @JoinColumn(name = "cat_id")
    private Categorie probCat;

    @OneToMany(mappedBy = "prob", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private Set<Probleme> sprobs;

    @ManyToOne(fetch = FetchType.LAZY)
    private Probleme prob;

    @ManyToOne
    @JoinColumn(name = "priorite_id")
    private Priorite priorite;

    @OneToMany(mappedBy = "problemeRep")
    private Set<Reponse> reponses;

}
