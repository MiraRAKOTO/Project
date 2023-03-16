package mg.douane.intervention.repository;

import mg.douane.intervention.data.domaine.TypeHierarchie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeHierarchieRepository extends CrudRepository<TypeHierarchie, Long> {
}
