package mg.douane.intervention.repository;

import mg.douane.intervention.data.domaine.Agent;
import mg.douane.intervention.data.domaine.Probleme;
import mg.douane.intervention.data.domaine.Reponse;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReponseRepository extends CrudRepository<Reponse, Long> {

    List<Probleme> findByAgentRep(Agent agent);

    List<Reponse> findByProblemeRep(Probleme probleme);

    @Query("SELECT r FROM Reponse r WHERE r.idRep IN (SELECT max(e.idRep) FROM Reponse e GROUP BY ?1) ORDER BY r.idRep DESC")
    Reponse findAllByPrblme(Probleme probleme);

    Reponse findByFileName(String fileName);
}
