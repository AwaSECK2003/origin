package uasz.sn.GestionEnseignement.Utilisateur.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.GestionEnseignement.Utilisateur.modele.EC;
import uasz.sn.GestionEnseignement.Utilisateur.modele.UE;
import uasz.sn.GestionEnseignement.Utilisateur.repository.UERepository;
import uasz.sn.GestionEnseignement.Utilisateur.service.UEService;
import uasz.sn.GestionEnseignement.Utilisateur.repository.ECRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ECService {

    @Autowired
    private ECRepository ecRepository;
    @Autowired
    private UERepository ueRepository;

    public void ajouterEC(EC ec) { ecRepository.save(ec);}



    public List<EC> afficherToutEC() { return ecRepository.findAll();}

    public EC afficherEC(Long id) { return ecRepository.getById(id);}

    public void modifierEC(EC ec){
        EC ecUpdate=ecRepository.getById(ec.getId());
        ecUpdate.setCode(ec.getCode());
        ecUpdate.setLibelle(ec.getLibelle());
        ecUpdate.setCm(ec.getCm());
        ecUpdate.setTd(ec.getTd());
        ecUpdate.setTp(ec.getTp());
        ecUpdate.setTpe(ec.getTpe());
        ecUpdate.setCoefficient(ec.getCoefficient());
        ecUpdate.setUe(ec.getUe());
        ecRepository.save(ecUpdate);

    }

    public void supprimerEC(EC ec) { ecRepository.delete(ec);}

    public Optional<EC> getECById(Long id) {
        return ecRepository.findById(id);
    }

    public List<EC> afficherLesECsParUEId(Long ueId) {
        return ecRepository.findByUeId(ueId);
    }
    // Méthodes pour archiver, désarchiver, activer et désactiver
    public void archiverEC(Long id) {
        EC ec = ecRepository.getById(id);
        ec.setEstArchivee(true); // Archiver l'EC
        ecRepository.save(ec);
    }

    public void desarchiverEC(Long id) {
        EC ec = ecRepository.getById(id);
        ec.setEstArchivee(false); // Désarchiver l'EC
        ecRepository.save(ec);
    }

    public void activerEC(Long id) {
        EC ec = ecRepository.getById(id);
        ec.setActive(true); // Activer l'EC
        ecRepository.save(ec);
    }

    public void desactiverEC(Long id) {
        EC ec = ecRepository.getById(id);
        ec.setActive(false); // Désactiver l'EC
        ecRepository.save(ec);
    }



}
