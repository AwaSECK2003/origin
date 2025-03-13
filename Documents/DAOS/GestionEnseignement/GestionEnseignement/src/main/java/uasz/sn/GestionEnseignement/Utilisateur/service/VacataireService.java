package uasz.sn.GestionEnseignement.Utilisateur.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Vacataire;
import uasz.sn.GestionEnseignement.Utilisateur.repository.VacataireRepository;

import java.util.List;

@Service
@Transactional
public class VacataireService {

    @Autowired
    private VacataireRepository vacataireRepository;

    public Vacataire ajouter(Vacataire vacataire) { return vacataireRepository.save(vacataire);}

    public List<Vacataire> lister() { return vacataireRepository.findAll();}

    public Vacataire rechercher(Long id) { return vacataireRepository.findById(id).get();}

    public Vacataire modifier(Vacataire vacataire) { return vacataireRepository.save(vacataire);}

    public void supprimer(Long id) { vacataireRepository.deleteById(id);}
}
