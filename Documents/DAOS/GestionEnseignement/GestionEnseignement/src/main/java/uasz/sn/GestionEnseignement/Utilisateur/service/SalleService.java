package uasz.sn.GestionEnseignement.Utilisateur.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Salle;
import uasz.sn.GestionEnseignement.Utilisateur.repository.SalleRepository;

import java.util.List;

@Service
@Transactional
public class SalleService {
    @Autowired
    private SalleRepository salleRepository;

    public Salle ajouterSalle(Salle salle) {
        return salleRepository.save(salle);
    }
    public List<Salle> afficherToutesLesSalles() {
        return salleRepository.findAll();
    }
    public Salle modifierSalle(Long id, Salle salleDetails) {
        Salle salle = salleRepository.findById(id) .orElseThrow(() -> new RuntimeException("Salle non trouvée"));
        salle.setNom(salleDetails.getNom());
        salle.setBatiment(salleDetails.getBatiment());
        salle.setCapacite(salleDetails.getCapacite());
        return salleRepository.save(salle);
    }
    public void supprimerSalle(Long id) {
        salleRepository.deleteById(id);
    }
}
