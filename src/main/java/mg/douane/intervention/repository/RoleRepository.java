package mg.douane.intervention.repository;

import mg.douane.intervention.data.domaine.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

}