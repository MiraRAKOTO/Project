package mg.douane.intervention.repository;

import mg.douane.intervention.data.domaine.Porte;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PorteRepository extends CrudRepository<Porte, Long> {
}
