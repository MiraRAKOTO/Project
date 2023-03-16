package mg.douane.intervention.data.domaine;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "agent")
public class Agent {
    @GenericGenerator(name = "seqSc", strategy = "increment")
    @Id
    @GeneratedValue(generator = "seqSc")
    private Long idAgent;

    @Column(name = "numMatAgent", nullable = false, length = 6)
    private String numMatAgent;

    @Column(name = "nomAgent", nullable = true, length = 50)
    private String nomAgent;

    @Column(name = "prenomAgent", nullable = true, length = 30)
    private String prenomAgent;

    @Lob
    @Column(name = "photoAgent") // , columnDefinition = "BLOB")
    private byte[] photoAgent;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "telAgent", nullable = false, length = 13)
    private String telAgent;

    @Column(name = "password", nullable = false, length = 250)
    private String password;

    @Size(min = 1)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "agent_roles", joinColumns = @JoinColumn(name = "agent_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "agentProb")
    private Set<Probleme> problemes;

    @OneToMany(mappedBy = "agentRep")
    private Set<Reponse> reponses;

    @OneToMany(mappedBy = "agentFich")
    private Set<FichePoste> fichePostes;

    @OneToMany(mappedBy = "agentInt")
    private Set<Intervenant> intervenants;

    @Transient
    private String confirmPassword;

}
