package uasz.sn.GestionEnseignement.Authentification.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uasz.sn.GestionEnseignement.Authentification.modele.Utilisateur;
import uasz.sn.GestionEnseignement.Authentification.repository.UtilisateurRepository;

@Service
public class UtilisateurDetailsService implements UserDetailsService {
    private UtilisateurRepository utilisateurRepository;

    public UtilisateurDetailsService(UtilisateurRepository utilisateurRepository){
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByUsername(username);
        String[] roles = utilisateur.getRoles().stream().map(u->u.getRole()).toArray(String[]::new);
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder().username(utilisateur.getUsername()).password(utilisateur.getPassword()).roles(roles).build();

        return userDetails;
    }

}
