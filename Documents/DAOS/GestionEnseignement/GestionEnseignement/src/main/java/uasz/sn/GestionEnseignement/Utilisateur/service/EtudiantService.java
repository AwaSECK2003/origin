package uasz.sn.GestionEnseignement.Utilisateur.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Enseignant;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Etudiant;
import uasz.sn.GestionEnseignement.Utilisateur.repository.EtudiantRepository;

import java.util.List;

@Service
@Transactional
public class EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    public Etudiant ajouter(Etudiant etudiant) { return etudiantRepository.save(etudiant);}

    public List<Etudiant> lister() { return etudiantRepository.findAll();}

    public Etudiant rechercher(Long id) { return etudiantRepository.findById(id).get();}

    public Etudiant modifier(Etudiant etudiant) { return etudiantRepository.save(etudiant);}

    public void supprimer(Long id) { etudiantRepository.deleteById(id);}


    public  void activer(Long id) {
        Etudiant etudiant=etudiantRepository.findById(id).get();
        if (etudiant.isActive()){ etudiant.setActive(false);}
        else { etudiant.setActive(true);}
        etudiantRepository.save(etudiant);
    }

    public void archiver(Long id){
        Etudiant etudiant=etudiantRepository.findById(id).get();
        if (etudiant.isArchive()){
            etudiant.setArchive(false);}
        else { etudiant.setArchive(true);}
        etudiantRepository.save(etudiant);
    }
}
