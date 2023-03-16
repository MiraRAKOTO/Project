package mg.douane.intervention.repository;

import mg.douane.intervention.data.domaine.Priorite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrioriterRepository extends CrudRepository<Priorite, Long> {
}
