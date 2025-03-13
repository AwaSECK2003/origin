package uasz.sn.GestionEnseignement.Utilisateur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uasz.sn.GestionEnseignement.Authentification.modele.Role;
import uasz.sn.GestionEnseignement.Authentification.modele.Utilisateur;
import uasz.sn.GestionEnseignement.Authentification.service.UtilisateurService;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Permanent;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Vacataire;
import uasz.sn.GestionEnseignement.Utilisateur.service.EnseignantService;
import uasz.sn.GestionEnseignement.Utilisateur.service.VacataireService;

import java.security.Principal;
import java.util.Date;

@Controller
public class VacataireController {

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private EnseignantService enseignantService;
    @Autowired
    private VacataireService vacataireService;


    @RequestMapping(value = "/Vacataire/Accueil", method = RequestMethod.GET)
    public String lister_Etudiant(Model model, Principal principal){
        Utilisateur utilisateur=utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom",utilisateur.getNom());
        model.addAttribute("prenom",utilisateur.getPrenom().charAt(0));
        return "template_Vacataire";
    }

    @RequestMapping(value = "/ChefDepartement/ajouterVacataire", method = RequestMethod.POST)
    public String ajouter_Vacataire(Vacataire vacataire){
        String password = passwordEncoder.encode("Passer123");
        vacataire.setPassword(password);
        vacataire.setDateCreation(new Date());
        vacataire.setActive(true);
        Role role = utilisateurService.ajouter_Role(new Role("Vacataire"));
        enseignantService.ajouter(vacataire);
        return "redirect:/ChefDepartement/Enseignant";
    }

    @RequestMapping(value = "/ChefDepartement/modifierVacataire", method = RequestMethod.POST)
    public String modifier_Vacataire(Vacataire vacataire){
        Vacataire vacataire_modif=vacataireService.rechercher(vacataire.getId());
        vacataire_modif.setNom(vacataire.getNom());
        vacataire.setPrenom(vacataire.getPrenom());
        vacataire_modif.setSpecialite(vacataire.getSpecialite());
        vacataire_modif.setNiveau(vacataire.getNiveau());
        enseignantService.modifier(vacataire_modif);
        return "redirect:/ChefDepartement/Enseignant";
    }
    @RequestMapping(value = "/ChefDepartement/activerVacataire", method = RequestMethod.POST)
    public String activer_Vacataire(Long id){
        enseignantService.activer(id);
        return "redirect:/ChefDepartement/Enseignant";
    }
    @RequestMapping(value = "/ChefDepartement/archiverVacataire", method = RequestMethod.POST)
    public String archiver_Vacataire(Long id){
        enseignantService.archiver(id);
        return "redirect:/ChefDepartement/Enseignant";
    }
}
