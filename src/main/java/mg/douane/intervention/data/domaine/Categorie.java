package mg.douane.intervention.data.domaine;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@Table(name = "Categorie")
public class Categorie {
    @GenericGenerator(name = "seqSc", strategy = "increment")
    @Id
    @GeneratedValue(generator = "seqSc")
    private Long idCat;

    @Column(name = "libelleCat", length = 50, nullable = false)
    private String libelleCat;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateDebCat")
    private Date dateDebCat;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateFinCat")
    private Date dateFinCat;

    @Column(name = "descriptionCat", nullable = true, length = 500)
    private String descriptionCat;

    @OneToMany(mappedBy = "cat", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private Set<Categorie> scats;

    @ManyToOne(fetch = FetchType.LAZY)
    private Categorie cat;

    @OneToMany(mappedBy = "probCat")
    private Set<Probleme> problemes;

    @OneToMany(mappedBy = "catFich")
    private Set<FichePoste> fichePostes;
}
