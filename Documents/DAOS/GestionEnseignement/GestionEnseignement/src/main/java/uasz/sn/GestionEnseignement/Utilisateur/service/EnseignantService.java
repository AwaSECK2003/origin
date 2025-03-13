package uasz.sn.GestionEnseignement.Utilisateur.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Enseignant;
import uasz.sn.GestionEnseignement.Utilisateur.repository.EnseignantRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EnseignantService {

    @Autowired
    private EnseignantRepository enseignantRepository;

    public  Enseignant ajouter(Enseignant enseignant) { return enseignantRepository.save(enseignant);}

    public List<Enseignant> lister() { return enseignantRepository.findAll();}

    public Enseignant rechercher(Long id) { return enseignantRepository.findById(id).get();}

    public Enseignant modifier(Enseignant enseignant) { return enseignantRepository.save(enseignant);}

    public void supprimer(Long id) { enseignantRepository.deleteById(id);}

    public  void activer(Long id) {
        Enseignant enseignant=enseignantRepository.findById(id).get();
        if (enseignant.isActive()==true){ enseignant.setActive(false);}
        else { enseignant.setActive(true);}
        enseignantRepository.save(enseignant);
    }

    public void archiver(Long id){
        Enseignant enseignant=enseignantRepository.findById(id).get();
        if (enseignant.isArchive()==true){
            enseignant.setArchive(false);}
        else { enseignant.setArchive(true);}
        enseignantRepository.save(enseignant);
    }


    public Optional<Enseignant> getEnseignantById(Long id) {
        return enseignantRepository.findById(id);  // Returns Optional<Enseignant>
    }







}
