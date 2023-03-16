package mg.douane.intervention.repository;

import mg.douane.intervention.data.domaine.Ville;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VilleRepository extends CrudRepository<Ville, Long> {
}
