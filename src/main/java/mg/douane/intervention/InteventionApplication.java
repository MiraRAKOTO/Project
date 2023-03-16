package mg.douane.intervention;

import mg.douane.intervention.data.domaine.Agent;
import mg.douane.intervention.data.domaine.Priorite;
import mg.douane.intervention.data.domaine.Role;
import mg.douane.intervention.data.domaine.Statut;
import mg.douane.intervention.repository.AgentRepository;
import mg.douane.intervention.repository.PrioriterRepository;
import mg.douane.intervention.repository.RoleRepository;
import mg.douane.intervention.repository.Statusrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class InteventionApplication extends SpringBootServletInitializer {

    @Autowired
    Statusrepository statusrepository;

    @Autowired
    PrioriterRepository prioriterRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
	AgentRepository agentRepository;


    public static void main(String[] args) {
        SpringApplication.run(InteventionApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(InteventionApplication.class);
    }

    @PostConstruct
    public void initAll() {
        try {
            List<Statut> statuts = Stream.of(
                    new Statut((long) 1, "News"), new Statut((long) 2, "en attente"), new Statut((long) 3, "resolu"), new Statut((long) 4, "transferer")
            ).collect(Collectors.toList());
            statusrepository.saveAll(statuts);
        } catch (Exception e) {
        }

        try {
            List<Priorite> priorites = Stream.of(
                    new Priorite((long) 1, "Urgent"), new Priorite((long) 2, "Normal"), new Priorite((long) 3, "Faible")
            ).collect(Collectors.toList());
            prioriterRepository.saveAll(priorites);
        } catch (Exception e) {
        }

		try {
			List<Role> roles = Stream.of(
					new Role((long) 1, "ROLE_ADMIN"), new Role((long) 2, "ROLE_USER"), new Role((long) 3, "AGENT")
			).collect(Collectors.toList());
			roleRepository.saveAll(roles);
		} catch (Exception e) {

		}

		Optional<Agent> agentOptional = agentRepository.findByUsername("admin@gmail.com");
		if(!agentOptional.isPresent()) {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
			Optional<Role> roleOptional = roleRepository.findById((long)1);
			Agent agent = new Agent();
			agent.setNumMatAgent("Admin1");
			agent.setNomAgent("Administrateur");
			agent.setPrenomAgent("Admin");
			agent.setTelAgent("0343403434");
			agent.setUsername("admin@gmail.com");
			agent.setPassword(bCryptPasswordEncoder.encode("1234"));
			Set<Role> roleSet = new HashSet<Role>();
			roleSet.add(roleOptional.get());
			agent.setRoles(roleSet);
			agentRepository.save(agent);
		}


    }
}
