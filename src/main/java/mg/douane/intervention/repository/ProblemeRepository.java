package mg.douane.intervention.repository;

import mg.douane.intervention.data.domaine.Agent;
import mg.douane.intervention.data.domaine.Priorite;
import mg.douane.intervention.data.domaine.Probleme;
import mg.douane.intervention.data.domaine.Statut;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemeRepository extends CrudRepository<Probleme, Long> {

    @Query("SELECT p FROM Probleme p WHERE p.statut.idStatut = 3")
    List<Probleme> findAllResolu();

    @Query("SELECT p FROM Probleme p WHERE p.statut.idStatut <> 3")
    List<Probleme> findAllNotResolu();

    List<Probleme> findByAgentProb(Agent agent);

    List<Probleme> findByStatut(Statut statut);

    @Query("SELECT p FROM Probleme p WHERE p.statut = ?1 AND p.priorite = ?2")
    List<Probleme> findByStatutAndPrioriter(Statut statut, Priorite priorite);
}
