package uasz.sn.GestionEnseignement.Utilisateur.service;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Batiment;
import uasz.sn.GestionEnseignement.Utilisateur.repository.BatimentRepository;

import java.util.List;

@Service
@Transactional
public class BatimentService {

    @Autowired
    private BatimentRepository batimentRepository;

    public Batiment ajouterBatiment(Batiment batiment) {
        return batimentRepository.save(batiment);
    }

    public List<Batiment> afficherTousLesBâtiments() {
        return batimentRepository.findAll();
    }

    public Batiment obtenirBatimentParId(Long id) {
        return batimentRepository.findById(id).orElseThrow(() -> new RuntimeException("Bâtiment non trouvé"));
    }

    public Batiment modifierBatiment(Long id, Batiment batiment) {
        Batiment batimentExistant = batimentRepository.findById(id).orElseThrow(() -> new RuntimeException("Bâtiment non trouvé"));
        batimentExistant.setNom(batiment.getNom());
        batimentExistant.setNbEtages(batiment.getNbEtages());
        batimentExistant.setDescription(batiment.getDescription());
        return batimentRepository.save(batimentExistant);
    }

    public void supprimerBatiment(Long id) {
        Batiment batimentExistant = batimentRepository.findById(id).orElseThrow(() -> new RuntimeException("Bâtiment non trouvé"));
        batimentRepository.delete(batimentExistant);
    }

    public Batiment rechercherBatimentParId(Long batimentId) {
        return batimentRepository.findById(batimentId).orElse(null); // Renvoie null si non trouvé
    }



}
