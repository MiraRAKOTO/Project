package mg.douane.intervention.repository;

import mg.douane.intervention.data.domaine.Agent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgentRepository extends CrudRepository<Agent, Long> {

    public Optional<Agent> findByUsername(String username);

}