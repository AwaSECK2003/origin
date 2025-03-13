package uasz.sn.GestionEnseignement.Utilisateur.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Classe;
import uasz.sn.GestionEnseignement.Utilisateur.modele.EC;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Semestre;
import uasz.sn.GestionEnseignement.Utilisateur.modele.UE;
import uasz.sn.GestionEnseignement.Utilisateur.repository.ClasseRepository;
import uasz.sn.GestionEnseignement.Utilisateur.repository.ECRepository;
import uasz.sn.GestionEnseignement.Utilisateur.repository.UERepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UEService {

    @Autowired
    private UERepository ueRepository;
    @Autowired
    private ECRepository ecRepository;
    @Autowired
    private ECService ecService;
    @Autowired
    private ClasseRepository classeRepository;

    public void ajouterUE(UE ue) { ueRepository.save(ue);}

    public List<UE> afficherToutUE() { return ueRepository.findAll();}

    public UE afficherUE(Long id) { return ueRepository.getById(id);
    }

    public void modifierUE(UE ue) {
        UE ueUpdate=ueRepository.getById(ue.getId());
        ueUpdate.setCode(ue.getCode());
        ueUpdate.setLibelle(ue.getLibelle());
        ueUpdate.setCredit(ue.getCredit());
        ueUpdate.setCoefficient(ue.getCoefficient());
        ueUpdate.setClasses(ue.getClasses());
        ueRepository.save(ueUpdate);
    }

    public void supprimerUE(UE ue) {
        ueRepository.delete(ue);
    }

    public List<EC> afficherLesEC(UE ue){
        return ueRepository.findByUE(ue);
    }

    public UE getUEById(Long id) {
        return ueRepository.findById(id).orElseThrow(() -> new RuntimeException("UE introuvable"));
    }

    public List<UE> getAllUEsBySemestre(Long semestreId) {
        return ueRepository.findBySemestreId(semestreId);
    }

    public List<EC> afficherLesECsParUE(UE ue) {
        return ecRepository.findByUe(ue);
    }

    public List<UE> afficherUesParClasse(Long classeId) {
        Classe classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new RuntimeException("Classe introuvable"));
        return classe.getUes();
    }

    public List<UE> afficherUesParClasseEtSemestre(Classe classe, Semestre semestre) {
        // Récupérer les UEs associées à la classe et au semestre
        return ueRepository.findByClassesAndSemestre(classe, semestre);
    }


    // Archiver une UE
    public void archiverUE(Long id) {
        UE ue = ueRepository.findById(id).orElseThrow(() -> new RuntimeException("UE introuvable"));
        ue.setEstArchivee(true);
        ueRepository.save(ue);
    }

    // Désarchiver une UE
    public void desarchiverUE(Long id) {
        UE ue = ueRepository.findById(id).orElseThrow(() -> new RuntimeException("UE introuvable"));
        ue.setEstArchivee(false);
        ueRepository.save(ue);
    }

    // Activer une UE
    public void activerUE(Long id) {
        UE ue = ueRepository.findById(id).orElseThrow(() -> new RuntimeException("UE introuvable"));
        ue.setActive(true);
        ueRepository.save(ue);
    }

    // Désactiver une UE
    public void desactiverUE(Long id) {
        UE ue = ueRepository.findById(id).orElseThrow(() -> new RuntimeException("UE introuvable"));
        ue.setActive(false);
        ueRepository.save(ue);
    }

}
