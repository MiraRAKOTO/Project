package mg.douane.intervention.repository;

import mg.douane.intervention.data.domaine.Hierarchie;
import mg.douane.intervention.data.domaine.Poste;
import mg.douane.intervention.data.domaine.Quartier;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PosteRepository extends CrudRepository<Poste, Long> {
    List<Poste> findByQuartier(Quartier quartier);

    @Query("SELECT p FROM Poste p WHERE p.quartier = ?1 AND p.hierarchiePoste = ?2")
    List<Poste> findByQuartierAndHierarchie(Quartier quartier, Hierarchie hierarchie);
}
