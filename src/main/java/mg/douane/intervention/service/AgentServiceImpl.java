package mg.douane.intervention.service;

import mg.douane.intervention.data.domaine.Agent;
import mg.douane.intervention.data.domaine.FichePoste;
import mg.douane.intervention.data.dto.ChangePasswordDto;
import mg.douane.intervention.repository.AgentRepository;
import mg.douane.intervention.repository.FichePosteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgentServiceImpl implements AgentService {

    @Autowired
    AgentRepository repository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    FichePosteRepository fichePosteRepository;

    @Override
    public Iterable<Agent> getAllAgents() {
        return repository.findAll();
    }

    @Override
    public Iterable<Agent> getAllAgentsWithFiche() {
        List<Agent> agents = new ArrayList<>();
        List<FichePoste> fichePostes = (List<FichePoste>) fichePosteRepository.findAll();
        for(int i=0; i < fichePostes.size(); i++) {
            agents.add(fichePostes.get(i).getAgentFich());
        }
        return agents;
    }

    private boolean checkUserEmailAvailable(Agent agent) throws Exception {
        Optional<Agent> agentFound = repository.findByUsername(agent.getUsername());
        if (agentFound.isPresent()) {
            throw new Exception("UserName no disponible");
        }
        return true;
    }

    private boolean checkPasswordValid(Agent agent) throws Exception {
        if (agent.getConfirmPassword() == null || agent.getConfirmPassword().isEmpty()) {
            throw new Exception("Confirm Password is required");
        }

        if (!agent.getPassword().equals(agent.getConfirmPassword())) {
            throw new Exception("Password and Confirm Password no equals");
        }
        return true;
    }

    @Override
    public Agent createAgent(Agent agent) throws Exception {
        if (checkUserEmailAvailable(agent) && checkPasswordValid(agent)) {
            String encodePassword = bCryptPasswordEncoder.encode(agent.getPassword());
            agent.setPassword(encodePassword);
            agent = repository.save(agent);
        }
        return agent;
    }

    @Override
    public Agent getAgentById(long id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new Exception("This Agent no exist"));
    }

    @Override
    public Agent updateAgent(Agent fromUser) throws Exception {
        Agent toUser = getAgentById(fromUser.getIdAgent());
        mapAgent(fromUser, toUser);
        return repository.save(toUser);
    }

    protected void mapAgent(Agent from, Agent to) {
        to.setNumMatAgent(from.getNumMatAgent());
        to.setNomAgent(from.getNomAgent());
        to.setPrenomAgent(from.getPrenomAgent());
        to.setPhotoAgent(from.getPhotoAgent());
        to.setUsername(from.getUsername());
        to.setTelAgent(from.getTelAgent());
        to.setRoles(from.getRoles());
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deleteAgent(long id) throws Exception {
        Agent user = getAgentById(id);
        repository.delete(user);
    }

    @Override
    public Agent ChangePasswordDto(ChangePasswordDto form) throws Exception {
        Agent agent = getAgentById(form.getId());

        if (!isLoggedAgentADMIN() && !agent.getPassword().equals(form.getCurrentPassword())) {
            throw new Exception("Current Password invalid");
        }

        if (agent.getPassword().equals(form.getNewPassword())) {
            throw new Exception("New password must be different password actual");
        }

        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            throw new Exception("New Password and Current Password no equals");
        }

        String encodePassword = bCryptPasswordEncoder.encode(form.getNewPassword());
        agent.setPassword(encodePassword);
        return repository.save(agent);
    }

    private boolean isLoggedAgentADMIN() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails loggedUser = null;
        if (principal instanceof UserDetails) {
            loggedUser = (UserDetails) principal;

            loggedUser.getAuthorities().stream().filter(x -> "ADMIN".equals(x.getAuthority())).findFirst().orElse(null); // loggedUser
            // =
            // null;
        }
        return loggedUser != null ? true : false;
    }
}
