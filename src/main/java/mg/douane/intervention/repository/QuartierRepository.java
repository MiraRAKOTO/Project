package mg.douane.intervention.repository;

import mg.douane.intervention.data.domaine.Quartier;
import mg.douane.intervention.data.domaine.Ville;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuartierRepository extends CrudRepository<Quartier, Long> {
    List<Quartier> findByVille(Ville ville);

}
