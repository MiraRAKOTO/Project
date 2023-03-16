package mg.douane.intervention.service;

import mg.douane.intervention.data.domaine.Agent;
import mg.douane.intervention.data.domaine.Role;
import mg.douane.intervention.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    AgentRepository agentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Agent appUser = agentRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Login Username Invalid"));

        Set<GrantedAuthority> grantList = new HashSet<GrantedAuthority>();
        for (Role role: appUser.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getLibelleRole());
            grantList.add(grantedAuthority);
        }
        UserDetails user = (UserDetails) new User(username,appUser.getPassword(),grantList);

        return user;
    }

}