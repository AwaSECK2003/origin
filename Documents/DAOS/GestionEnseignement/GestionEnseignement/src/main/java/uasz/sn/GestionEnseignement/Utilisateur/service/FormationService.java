package uasz.sn.GestionEnseignement.Utilisateur.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Formation;
import uasz.sn.GestionEnseignement.Utilisateur.repository.FormationRepository;

import java.util.List;

@Service
@Transactional
public class FormationService {

    @Autowired
    private FormationRepository formationRepository;

    public void ajouterFormation(Formation formation) {
        formationRepository.save(formation);
    }

    public List<Formation> afficherToutesFormations() {
        return formationRepository.findAllActives();
    }

    // Méthode modifiée pour afficher une formation par son nom
    public Formation afficherFormationParNom(String nom) {
        return formationRepository.rechercherFormationParNom(nom).stream()
                .findFirst() // Si plusieurs formations ont le même nom, on retourne la première
                .orElseThrow(() -> new RuntimeException("Formation introuvable avec le nom : " + nom));
    }

    public void modifierFormation(Formation formation) {
        Formation formationExistante = formationRepository.findById(formation.getId())
                .orElseThrow(() -> new RuntimeException("Formation introuvable"));
        formationExistante.setNom(formation.getNom());
        formationExistante.setCode(formation.getCode());
        formationExistante.setDateDebut(formation.getDateDebut());
        formationExistante.setDateFin(formation.getDateFin());
        formationRepository.save(formationExistante);
    }

    public void archiverFormation(Long id) {
        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formation introuvable"));
        formation.setArchive(true);
        formationRepository.save(formation);
    }

    public List<Formation> rechercherFormationParNom(String nom) {
        return formationRepository.rechercherFormationParNom(nom);
    }

    public Formation findByNom(String nom) {
        return formationRepository.rechercherFormationParNom(nom).stream()
                .findFirst() // Si plusieurs formations ont le même nom, on retourne la première
                .orElseThrow(() -> new RuntimeException("Formation introuvable avec le nom : " + nom));
    }



    public Formation rechercherFormationParCode(String code) {
        List<Formation> formations = formationRepository.rechercherFormationParCode(code);

        if (!formations.isEmpty()) {
            // Retourner la première formation trouvée
            return formations.get(0);  // Ou logiquement choisir l'élément que l'utilisateur préfère
        }
        return null;
    }


    public List<Formation> findAll() {
        return formationRepository.findAll(); // Appel à la méthode du repository pour récupérer toutes les formations
    }

    public List<Formation> getFormationsParNiveau(int niveau) {
        return formationRepository.findByNiveau(niveau);
    }

    public Formation findById(Long id) {
        return formationRepository.findById(id).orElse(null);  // Utilisation de Optional
    }

}
