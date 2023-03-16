package mg.douane.intervention.repository;

import mg.douane.intervention.data.domaine.Agent;
import mg.douane.intervention.data.domaine.FichePoste;
import mg.douane.intervention.data.domaine.Categorie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FichePosteRepository extends CrudRepository<FichePoste, Long> {

    List<FichePoste> findByCatFich(Categorie sousCategorie);

    FichePoste findByAgentFich(Agent agent);
}
