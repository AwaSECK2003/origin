package uasz.sn.GestionEnseignement.Utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Classe;
import uasz.sn.GestionEnseignement.Utilisateur.modele.EC;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Maquette;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Semestre;

import java.util.List;

public interface MaquetteRepository extends JpaRepository<Maquette, Long> {

    // Rechercher une maquette par son nom
    Maquette findByNom(String nom);

    // Recherche des maquettes actives
    List<Maquette> findByEstActiveTrue();

    // Recherche des maquettes archivées
    List<Maquette> findByEstArchiveeTrue();

    // Recherche des maquettes non archivées
    List<Maquette> findByEstArchiveeFalse();

    List<Maquette> findByClasseIdAndSemestreId(Long classeId, Long semestreId);


}

