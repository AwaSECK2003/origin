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
import uasz.sn.GestionEnseignement.Utilisateur.modele.Etudiant;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Permanent;
import uasz.sn.GestionEnseignement.Utilisateur.service.EnseignantService;
import uasz.sn.GestionEnseignement.Utilisateur.service.EtudiantService;
import uasz.sn.GestionEnseignement.Utilisateur.service.PermanentService;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class PermanentController {

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private EnseignantService enseignantService;
    @Autowired
    private PermanentService permanentService;
    @Autowired
    private EtudiantService etudiantService;

    @RequestMapping(value = "/Permanent/Accueil", method = RequestMethod.GET)
    public String accueil_Permanent(Model model, Principal principal){
        Utilisateur utilisateur=utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom",utilisateur.getNom());
        model.addAttribute("prenom",utilisateur.getPrenom().charAt(0));
        return "template_Permanent";
    }

    @RequestMapping(value = "/ChefDepartement/Accueil", method = RequestMethod.GET)
    public String acceuil_ChefDepartement(Model model, Principal principal) {
        Utilisateur utilisateur=utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom",utilisateur.getNom());
        model.addAttribute("prenom",utilisateur.getPrenom().charAt(0));
        return "template_ChefDepartement";
    }

    @RequestMapping(value = "/ChefDepartement/ajouterPermanent", method = RequestMethod.POST)
    public String ajouter_Permanent(Permanent permanent){
        String password = passwordEncoder.encode("Passer123");
        permanent.setPassword(password);
        permanent.setDateCreation(new Date());
        permanent.setActive(true);
        Role role = utilisateurService.ajouter_Role(new Role("Permanent"));
        enseignantService.ajouter(permanent);
        return "redirect:/ChefDepartement/Enseignant";
    }

    @RequestMapping(value = "/ChefDepartement/modifierPermanent", method = RequestMethod.POST)
    public String modifier_Permanent(Permanent permanent){
        Permanent per_modif=permanentService.rechercher(permanent.getId());
        per_modif.setMatricule(permanent.getMatricule());
        per_modif.setNom(permanent.getNom());
        per_modif.setPrenom(permanent.getPrenom());
        per_modif.setSpecialite(permanent.getSpecialite());
        per_modif.setGrade(permanent.getGrade());
        enseignantService.modifier(per_modif);
        return "redirect:/ChefDepartement/Enseignant";
    }
    @RequestMapping(value = "/ChefDepartement/activerPermanent", method = RequestMethod.POST)
    public String activer_Permanent(Long id){
        enseignantService.activer(id);
        return "redirect:/ChefDepartement/Enseignant";
    }
    @RequestMapping(value = "/ChefDepartement/archiverPermanent", method = RequestMethod.POST)
    public String archiver_Permanent(Long id){
        enseignantService.archiver(id);
        return "redirect:/ChefDepartement/Enseignant";
    }



    @RequestMapping(value = "/ChefDepartement/ajouterEtudiant", method = RequestMethod.POST)
    public String ajouter_Etudiant(Etudiant etudiant){
        String password = passwordEncoder.encode("Passer123");
        etudiant.setPassword(password);
        etudiant.setDateCreation(new Date());
        etudiant.setActive(true);
        Role role = utilisateurService.ajouter_Role(new Role("Etudiant"));
        etudiantService.ajouter(etudiant);
        return "redirect:/ChefDepartement/Etudiant";
    }



    @RequestMapping(value = "/ChefDepartement/modifierEtudiant", method = RequestMethod.POST)
    public String modifier_Etudiant(Etudiant etudiant){
        Etudiant etu_modif=etudiantService.rechercher(etudiant.getId());
        etu_modif.setMatricule(etudiant.getMatricule());
        etu_modif.setNom(etudiant.getNom());
        etu_modif.setPrenom(etudiant.getPrenom());
        etu_modif.setAdresse(etudiant.getAdresse());
        etu_modif.setTelephone(etudiant.getTelephone());
        etudiantService.modifier(etu_modif);
        return "redirect:/ChefDepartement/Etudiant";
    }

    @RequestMapping(value = "/ChefDepartement/activerEtudiant", method = RequestMethod.POST)
    public String activer_Etudiant(Long id){
        etudiantService.activer(id);
        return "redirect:/ChefDepartement/Etudiant";
    }
    @RequestMapping(value = "/ChefDepartement/archiverEtudiant", method = RequestMethod.POST)
    public String archiver_Etudiant(Long id){
        etudiantService.archiver(id);
        return "redirect:/ChefDepartement/Etudiant";
    }



}
