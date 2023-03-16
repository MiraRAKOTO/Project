package mg.douane.intervention.data.domaine;

import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@Table(name = "reponse")
public class Reponse {
    @GenericGenerator(name = "seqRep", strategy = "increment")
    @Id
    @GeneratedValue(generator = "seqRep")
    private Long idRep;

    @Column(name = "libelleRep", length = 50, nullable = false)
    private String libelleRep;

    @Column(name = "descriptionRep", length = 500, nullable = false)
    private String descriptionRep;

    @Lob
    @Column(name = "pieceJointeRep") // , columnDefinition = "BYTEA") //BLOB
    private byte[] pieceJointeRep;

    @Column(name = "fileName", length = 500, nullable = true)
    private String fileName;

    @Column(name = "fileType", length = 500, nullable = true)
    private String fileType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateEnvRep")
    private Date dateEnvRep;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agentRep;

    @ManyToOne
    @JoinColumn(name = "probleme_id")
    private Probleme problemeRep;

}
