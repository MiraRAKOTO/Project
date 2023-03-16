package mg.douane.intervention.repository;

import mg.douane.intervention.data.domaine.Statut;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Statusrepository extends CrudRepository<Statut, Long> {
}
