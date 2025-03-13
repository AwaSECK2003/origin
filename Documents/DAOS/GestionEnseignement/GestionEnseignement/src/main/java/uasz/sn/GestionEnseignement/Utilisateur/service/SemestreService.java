package uasz.sn.GestionEnseignement.Utilisateur.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.GestionEnseignement.Utilisateur.modele.EC;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Semestre;
import uasz.sn.GestionEnseignement.Utilisateur.modele.UE;
import uasz.sn.GestionEnseignement.Utilisateur.repository.ECRepository;
import uasz.sn.GestionEnseignement.Utilisateur.repository.SemestreRepository;
import uasz.sn.GestionEnseignement.Utilisateur.repository.UERepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SemestreService    {


    @Autowired
    private SemestreRepository semestreRepository;
    @Autowired
    private UERepository ueRepository;
    @Autowired
    private ECRepository ecRepository;

    public List<Semestre> getAllSemestres() {
        return semestreRepository.findAll();
    }

    public Optional<Semestre> getSemestreById(Long id) {
        return semestreRepository.findById(id);
    }

    public Semestre ajouterSemestre(Semestre semestre) {
        return semestreRepository.save(semestre);
    }

    public void supprimerSemestre(Long id) {
        semestreRepository.deleteById(id);
    }
    // Nouvelle méthode pour récupérer les UE d'un semestre
    public List<UE> getUEsBySemestre(Long semestreId) {
        return ueRepository.findBySemestreId(semestreId);
    }

    public Semestre modifierSemestre(Semestre semestre) {
        Optional<Semestre> semestreExistant = semestreRepository.findById(semestre.getId());
        if (semestreExistant.isPresent()) {
            return semestreRepository.save(semestre);
        } else {
            throw new RuntimeException("Semestre introuvable");
        }
    }

    public List<EC> getECsByUE(Long ueId) {
        return ecRepository.findByUeId(ueId);
    }




}
