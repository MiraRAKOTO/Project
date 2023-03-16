package mg.douane.intervention.repository;

import mg.douane.intervention.data.domaine.Agent;
import mg.douane.intervention.data.domaine.Intervenant;
import mg.douane.intervention.data.domaine.Probleme;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntervenantRepository extends CrudRepository<Intervenant, Long> {

    List<Intervenant> findByAgentInt(Agent agent);

    Intervenant findByProbInt(Probleme probleme);
}
