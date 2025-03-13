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

import java.security.Principal;
import java.util.Date;

@Controller
public class EtudiantController  {
   @Autowired
   private  UtilisateurService utilisateurService;

   @Autowired
   private EtudiantService etudiantService;

   @Autowired
   PasswordEncoder passwordEncoder;
   @Autowired
   private EnseignantService enseignantService;

   @RequestMapping(value = "/Etudiant/Accueil", method = RequestMethod.GET)
    public String acceuil_Etudiant(Model model, Principal principal) {
       Utilisateur utilisateur=utilisateurService.rechercher_Utilisateur(principal.getName());
       model.addAttribute("nom",utilisateur.getNom());
       model.addAttribute("prenom",utilisateur.getPrenom().charAt(0));
       return "template_Etudiant";
   }

   @RequestMapping(value = "/ResponsableClasse/Accueil", method = RequestMethod.GET)
    public String acceuil_ResponsableClasse(Model model, Principal principal) {
       Utilisateur utilisateur=utilisateurService.rechercher_Utilisateur(principal.getName());
       model.addAttribute("nom",utilisateur.getNom());
       model.addAttribute("prenom",utilisateur.getPrenom().charAt(0));
       return "template_ResponsableClasse";
   }



  /* @RequestMapping(value = "/ChefDepartement/ajouterEtudiant", method = RequestMethod.POST)
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

   */

}
