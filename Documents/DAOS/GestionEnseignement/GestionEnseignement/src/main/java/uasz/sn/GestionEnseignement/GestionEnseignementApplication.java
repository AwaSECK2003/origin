package uasz.sn.GestionEnseignement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import uasz.sn.GestionEnseignement.Authentification.modele.Role;
import uasz.sn.GestionEnseignement.Authentification.service.UtilisateurService;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Enseignant;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Etudiant;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Permanent;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Vacataire;
import uasz.sn.GestionEnseignement.Utilisateur.service.EnseignantService;

import java.util.Date;

@SpringBootApplication
public class GestionEnseignementApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GestionEnseignementApplication.class, args);
	}

	@Autowired
	private UtilisateurService utilisateurService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EnseignantService enseignantService;

	@Override
	public void run(String... args) throws Exception {
		// Vérifiez et créez des rôles
		Role permanent = utilisateurService.ajouter_Role(new Role("Permanent"));
		Role vacataire = utilisateurService.ajouter_Role(new Role("Vacataire"));
		Role chefDepartement = utilisateurService.ajouter_Role(new Role("ChefDepartement"));
		Role coordonnateurLicences = utilisateurService.ajouter_Role(new Role("CoordonnateurLicences"));
		Role etudiant = utilisateurService.ajouter_Role(new Role("Etudiant"));
		Role  responsableMasters = utilisateurService.ajouter_Role(new Role("ResponsableMasters"));

		// Définir le mot de passe encodé
		String password = passwordEncoder.encode("Passer123");

		// Vérifiez et ajoutez le premier utilisateur
		if (utilisateurService.rechercher_Utilisateur("idiop@uasz.sn") == null) {
			Permanent user_1 = new Permanent();
			user_1.setNom("DIOP");
			user_1.setPrenom("Ibrahima");
			user_1.setUsername("idiop@uasz.sn");
			user_1.setPassword(password);
			user_1.setDateCreation(new Date());
			user_1.setActive(true);
			user_1.setSpecialite("Web Semantique");
			user_1.setMatricule("ID2024");
			user_1.setGrade("Professeur");
			enseignantService.ajouter(user_1);
			utilisateurService.ajouter_UtilisateurRoles(user_1, permanent);
		}

		// Vérifiez et ajoutez le deuxième utilisateur
		if (utilisateurService.rechercher_Utilisateur("cmalack@uasz.sn") == null) {
			Vacataire user_2 = new Vacataire();
			user_2.setNom("MALACK");
			user_2.setPrenom("Camir");
			user_2.setUsername("cmalack@uasz.sn");
			user_2.setPassword(password);
			user_2.setDateCreation(new Date());
			user_2.setActive(true);
			user_2.setSpecialite("Ingénierie de Connaissances");
			user_2.setNiveau("Doctorant");
			enseignantService.ajouter(user_2);
			utilisateurService.ajouter_UtilisateurRoles(user_2, vacataire);
		}

        // Ajoutez un utilisateur avec le rôle de ChefDepartement
		if (utilisateurService.rechercher_Utilisateur("sdiagne@uasz.sn") == null) {
			Permanent user = new Permanent();
			user.setNom("DIAGNE");
			user.setPrenom("Serigne");
			user.setUsername("sdiagne@uasz.sn");
			user.setPassword(password);
			user.setDateCreation(new Date());
			user.setActive(true);
			user.setSpecialite("Base de données");
			user.setMatricule("SD2024");
			user.setGrade("Professeur");
			enseignantService.ajouter(user);
			utilisateurService.ajouter_UtilisateurRoles(user, chefDepartement); }


		// Ajoutez un utilisateur responsable des masters
		if (utilisateurService.rechercher_Utilisateur("rmasters@uasz.sn") == null) {
			Permanent user = new Permanent();
			user.setNom("FAYE");
			user.setPrenom("Youssou");
			user.setUsername("rmasters@uasz.sn");
			user.setPassword(password);
			user.setDateCreation(new Date());
			user.setActive(true);
			user.setSpecialite("Reseaux et Telecoms");
			user.setMatricule("YF2024");
			user.setGrade("Professeur");
			enseignantService.ajouter(user);
			utilisateurService.ajouter_UtilisateurRoles(user, responsableMasters);
		}


		// Ajoutez un utilisateur coordonnateur des licences
		if (utilisateurService.rechercher_Utilisateur("clicences@uasz.sn") == null) {
			Permanent user = new Permanent();
			user.setNom("CISSE");
			user.setPrenom("Alioune");
			user.setUsername("clicences@uasz.sn");
			user.setPassword(password);
			user.setDateCreation(new Date());
			user.setActive(true);
			user.setSpecialite("Developpement WEB/MOBILE");
			user.setMatricule("AC2024");
			user.setGrade("Professeur");
			enseignantService.ajouter(user);
			utilisateurService.ajouter_UtilisateurRoles(user, coordonnateurLicences);
		}


		// Ajout de l'étudiant (pour visionner uniquement)
		if (utilisateurService.rechercher_Utilisateur("od@uasz.sn") == null) {
			Etudiant etudiant_User = new Etudiant();
			etudiant_User.setNom("DABO");
			etudiant_User.setPrenom("Ouleye");
			etudiant_User.setUsername("od@uasz.sn");
			etudiant_User.setPassword("Passer123");
			etudiant_User.setDateCreation(new Date());
			etudiant_User.setActive(true);
			etudiant_User.setMatricule("OD2024");
			etudiant_User.setAdresse("Kénia");
			etudiant_User.setTelephone("778217892");
			utilisateurService.ajouter_Utilisateur(etudiant_User);
			utilisateurService.ajouter_UtilisateurRoles(etudiant_User, etudiant);
		}


	}

}


