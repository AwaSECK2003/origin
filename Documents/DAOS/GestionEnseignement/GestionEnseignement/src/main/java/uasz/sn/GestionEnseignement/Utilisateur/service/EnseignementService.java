package uasz.sn.GestionEnseignement.Utilisateur.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.GestionEnseignement.Utilisateur.modele.EC;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Enseignement;
import uasz.sn.GestionEnseignement.Utilisateur.modele.TypeEnseignement;
import uasz.sn.GestionEnseignement.Utilisateur.repository.ECRepository;
import uasz.sn.GestionEnseignement.Utilisateur.repository.EnseignementRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EnseignementService {

    @Autowired
    private EnseignementRepository enseignementRepository;
    @Autowired
    private ECRepository ecRepository;

    // Méthode pour récupérer tous les enseignements
    public List<Enseignement> getAllEnseignements() {
        return enseignementRepository.findAll();
    }

    // Méthode pour récupérer un enseignement par son ID
    public Optional<Enseignement> getEnseignementById(Long id) {
        return enseignementRepository.findById(id);
    }


    public Enseignement ajouterEnseignement(Enseignement enseignement) {

        EC ec = ecRepository.findById(enseignement.getEc().getId())
                .orElseThrow(() -> new RuntimeException("EC non trouvé"));

        // Créer les copies pour chaque type avec les heures spécifiques
        Enseignement enseignementCM = new Enseignement();
        enseignementCM.setTotalHeures(ec.getCm()); // Heures de CM
        enseignementCM.setType(TypeEnseignement.CM);
        enseignementCM.setMaquette(enseignement.getMaquette());
        enseignementCM.setClasse(enseignement.getClasse());
        enseignementCM.setSemestre(enseignement.getSemestre());
        enseignementCM.setEc(enseignement.getEc());

        Enseignement enseignementTP = new Enseignement();
        enseignementTP.setTotalHeures(ec.getTp()); // Heures de TP
        enseignementTP.setType(TypeEnseignement.TP);
        enseignementTP.setMaquette(enseignement.getMaquette());
        enseignementTP.setClasse(enseignement.getClasse());
        enseignementTP.setSemestre(enseignement.getSemestre());
        enseignementTP.setEc(enseignement.getEc());

        Enseignement enseignementTD = new Enseignement();
        enseignementTD.setTotalHeures(ec.getTd()); // Heures de TD
        enseignementTD.setType(TypeEnseignement.TD);
        enseignementTD.setMaquette(enseignement.getMaquette());
        enseignementTD.setClasse(enseignement.getClasse());
        enseignementTD.setSemestre(enseignement.getSemestre());
        enseignementTD.setEc(enseignement.getEc());

        // Sauvegarder les copies dans la base de données
        enseignementRepository.save(enseignementCM);
        enseignementRepository.save(enseignementTP);
        enseignementRepository.save(enseignementTD);

        return enseignement;
    }



    public Enseignement modifierEnseignement(Long id, Enseignement enseignementModifie) {
        // Récupérer l'enseignement existant
        Enseignement enseignement = enseignementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enseignement non trouvé avec l'ID : " + id));

        // Récupérer l'EC correspondant depuis la base de données
        EC ec = ecRepository.findById(enseignementModifie.getEc().getId())
                .orElseThrow(() -> new RuntimeException("EC non trouvé"));

        // Mise à jour des propriétés de l'enseignement
        enseignement.setTotalHeures(getHeuresParType(enseignementModifie.getType(), ec)); // Heures spécifiques au type
        enseignement.setType(enseignementModifie.getType());
        enseignement.setMaquette(enseignementModifie.getMaquette());
        enseignement.setClasse(enseignementModifie.getClasse());
        enseignement.setSemestre(enseignementModifie.getSemestre());
        enseignement.setEc(enseignementModifie.getEc());

        // Sauvegarde des modifications dans la base de données
        return enseignementRepository.save(enseignement);
    }

    // Méthode utilitaire pour récupérer les heures en fonction du type d'enseignement
    private int getHeuresParType(TypeEnseignement type, EC ec) {
        switch (type) {
            case CM:
                return ec.getCm();
            case TP:
                return ec.getTp();
            case TD:
                return ec.getTd();
            default:
                throw new IllegalArgumentException("Type d'enseignement non valide : " + type);
        }
    }


    // Méthode pour supprimer un enseignement par son ID
    public void supprimerEnseignement(Long id) {
        enseignementRepository.deleteById(id);
    }

    public List<Enseignement> getEnseignementsByClasseAndSemestre(Long classeId, Long semestreId) {
        return enseignementRepository.findByClasseIdAndSemestreId(classeId, semestreId);
    }

}
