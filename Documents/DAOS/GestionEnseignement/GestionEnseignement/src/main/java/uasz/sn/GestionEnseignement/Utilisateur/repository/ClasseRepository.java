package uasz.sn.GestionEnseignement.Utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Classe;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Formation;

import java.util.List;

public interface ClasseRepository extends JpaRepository<Classe, Long> {

    Classe findByNom(String nom);
    // Récupérer les classes non archivées
    List<Classe> findByEstArchiveeFalse();

}
