package uasz.sn.GestionEnseignement.Utilisateur.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Permanent;
import uasz.sn.GestionEnseignement.Utilisateur.repository.PermanentRepository;

import java.util.List;

@Service
@Transactional
public class PermanentService {

    @Autowired
    private PermanentRepository permanentRepository;

    public Permanent ajouter(Permanent permanent) { return permanentRepository.save(permanent);}

    public List<Permanent> lister() { return permanentRepository.findAll();}

    public Permanent rechercher(Long id) { return permanentRepository.findById(id).get();}

    public Permanent modifier(Permanent permanent) { return permanentRepository.save(permanent);}

    public void supprimer(Long id) { permanentRepository.deleteById(id);}
}
