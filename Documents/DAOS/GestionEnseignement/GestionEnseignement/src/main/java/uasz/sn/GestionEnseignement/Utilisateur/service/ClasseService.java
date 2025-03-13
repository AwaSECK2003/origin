package uasz.sn.GestionEnseignement.Utilisateur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Classe;
import uasz.sn.GestionEnseignement.Utilisateur.modele.EC;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Formation;
import uasz.sn.GestionEnseignement.Utilisateur.modele.UE;
import uasz.sn.GestionEnseignement.Utilisateur.repository.ClasseRepository;
import uasz.sn.GestionEnseignement.Utilisateur.repository.FormationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClasseService {
    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private FormationRepository formationRepository;

    // Ajouter une classe avec formation
    public Classe ajouterClasse(Long formationId, Classe classe) {
        Formation formation = formationRepository.findById(formationId)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));
        classe.setFormation(formation); // Associe la formation à la classe
        return classeRepository.save(classe);
    }

    // Modifier une classe
    public Classe modifierClasse(Long id, Classe classeDetails) {
        Classe classe = classeRepository.findById(id).orElseThrow(() -> new RuntimeException("Classe non trouvée"));
        classe.setNom(classeDetails.getNom());
        classe.setDescription(classeDetails.getDescription());
        classe.setEstArchivee(classeDetails.isEstArchivee());
        return classeRepository.save(classe);
    }

    // Archiver une classe
    public Classe archiverClasse(Long id) {
        Classe classe = classeRepository.findById(id).orElseThrow(() -> new RuntimeException("Classe non trouvée"));
        classe.setEstArchivee(true);
        return classeRepository.save(classe);
    }

    // Rechercher une classe par son nom
    public Classe rechercherClasseParNom(String nom) {
        return classeRepository.findByNom(nom);
    }

    public Optional<Classe> rechercherClasseParId(Long id) {
        return classeRepository.findById(id); // Retourne un Optional<Classe>
    }


    // Afficher toutes les classes
    public List<Classe> afficherToutesClasses() {
        return classeRepository.findAll();
    }

    // Afficher toutes les classes non archivées
    public List<Classe> afficherClassesNonArchivees() {
        // Récupérer les classes où estArchivee = false
        return classeRepository.findByEstArchiveeFalse();
    }
    // Méthode pour obtenir une classe par son ID
    public Classe obtenirClasseParId(Long id) {
        return classeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Classe non trouvée"));
    }

    // Méthode pour obtenir une classe par son nom
    public Classe obtenirClasseParNom(String nom) {
        return classeRepository.findByNom(nom);
    }

    public Classe getClasseById(Long id) {
        Optional<Classe> classe = classeRepository.findById(id); return classe.orElse(null);
    }

    public void supprimerClasse(Long id) {
        Classe classe = classeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classe non trouvée"));
        classeRepository.delete(classe);
    }

    public List<Classe> getAllClasses() {
        return classeRepository.findAll();
    }

    public Optional<Classe> getClassesById(Long id) {
        return classeRepository.findById(id);
    }



}
