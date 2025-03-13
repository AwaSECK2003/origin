package uasz.sn.GestionEnseignement.Utilisateur.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.GestionEnseignement.Utilisateur.modele.*;
import uasz.sn.GestionEnseignement.Utilisateur.repository.ECRepository;
import uasz.sn.GestionEnseignement.Utilisateur.repository.EnseignementRepository;
import uasz.sn.GestionEnseignement.Utilisateur.repository.MaquetteRepository;
import uasz.sn.GestionEnseignement.Utilisateur.repository.UERepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MaquetteService {

    @Autowired
    private MaquetteRepository maquetteRepository;
    @Autowired
    private UERepository ueRepository;
    @Autowired
    private EnseignementRepository enseignementRepository;
    @Autowired
    private ECRepository ecRepository;


    public void ajouterMaquette(Maquette maquette) {
        maquetteRepository.save(maquette);
    }


    // Modifier une maquette existante
    public void modifierMaquette(Long id, Maquette maquette) {
        Maquette maquetteExistante = maquetteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maquette non trouvée"));
        maquetteExistante.setNom(maquette.getNom());
        maquetteExistante.setDescription(maquette.getDescription());
        maquetteExistante.setSemestre(maquette.getSemestre());
        maquetteExistante.setClasse(maquette.getClasse());
        maquetteExistante.setEstActive(maquette.isEstActive());
        maquetteExistante.setEstArchivee(maquette.isEstArchivee());
        maquetteRepository.save(maquetteExistante);
    }


    // Archiver une maquette
    public void archiverMaquette(Long id) {
        Maquette maquette = maquetteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maquette non trouvée"));
        maquette.setEstArchivee(true);
        maquetteRepository.save(maquette);
    }

    // Désarchiver une maquette
    public void desarchiverMaquette(Long id) {
        Maquette maquette = maquetteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maquette non trouvée"));
        maquette.setEstArchivee(false);
        maquetteRepository.save(maquette);
    }

   // Activer une maquette
    public void activerMaquette(Long id) {
        Maquette maquette = maquetteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maquette non trouvée"));
        maquette.setEstActive(true);
        maquetteRepository.save(maquette);
    }

    // Désactiver une maquette
    public void desactiverMaquette(Long id) {
        Maquette maquette = maquetteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maquette non trouvée"));
        maquette.setEstActive(false);
        maquetteRepository.save(maquette);
    }

    // Recherche des maquettes actives
    public List<Maquette> obtenirMaquettesActives() {
        return maquetteRepository.findByEstActiveTrue();
    }

    // Recherche des maquettes archivées
    public List<Maquette> obtenirMaquettesArchivees() {
        return maquetteRepository.findByEstArchiveeTrue();
    }

    // Recherche des maquettes archivées
    public List<Maquette> obtenirMaquettesNonArchivees() {
        return maquetteRepository.findByEstArchiveeFalse();
    }


    // Recherche d'une maquette par son nom
    public Maquette trouverParNom(String nom) {
        return maquetteRepository.findByNom(nom);
    }

    public Maquette getMaquetteById(Long id) {
        Optional<Maquette> maquette = maquetteRepository.findById(id);
        return maquette.orElse(null);
    }

    public List<UE> getUEsByMaquette(Long maquetteId) {
        Maquette maquette = maquetteRepository.findById(maquetteId)
                .orElseThrow(() -> new EntityNotFoundException("Maquette not found"));
        return ueRepository.findByClassesAndSemestre(maquette.getClasse(), maquette.getSemestre());
    }

    // Récupérer les semestres associés à une maquette
    public List<Semestre> getSemestresByMaquette(Long maquetteId) {
        Maquette maquette = maquetteRepository.findById(maquetteId)
                .orElseThrow(() -> new EntityNotFoundException("Maquette not found"));
        // Retourner la liste des semestres associés à cette maquette
        return Collections.singletonList(maquette.getSemestre());
    }


    // Récupérer les classes associées à une maquette
    public List<Classe> getClassesByMaquette(Long maquetteId) {
        Maquette maquette = maquetteRepository.findById(maquetteId)
                .orElseThrow(() -> new EntityNotFoundException("Maquette not found"));
        // Retourner la liste des classes associées à cette maquette
        return Collections.singletonList(maquette.getClasse());
    }


    // Récupérer les ECs associés à une maquette
    public List<EC> getECsByMaquette(Long maquetteId) {
        Maquette maquette = maquetteRepository.findById(maquetteId)
                .orElseThrow(() -> new EntityNotFoundException("Maquette not found"));
        // Retourner la liste des ECs associés à cette maquette
        List<UE> ues = ueRepository.findByClassesAndSemestre(maquette.getClasse(), maquette.getSemestre());
        List<EC> ecs = new ArrayList<>();
        for (UE ue : ues) {
            ecs.addAll(ue.getEcs());
        }
        return ecs;
    }


    public List<Classe> getClassesByMaquetteNom(String maquetteNom) {
        // Recherche la maquette par son nom
        Maquette maquette = maquetteRepository.findByNom(maquetteNom);

        if (maquette != null) {
            // Retourne la classe associée à la maquette
            return List.of(maquette.getClasse());
        } else {
            return null;
        }
    }

    public List<Semestre> getSemestresByMaquetteNom(String maquetteNom) {
        // Recherche la maquette par son nom
        Maquette maquette = maquetteRepository.findByNom(maquetteNom);

        if (maquette != null) {
            // Retourne le semestre associé à la maquette
            return List.of(maquette.getSemestre());
        } else {
            return null;  // Ou lancer une exception si tu veux gérer ce cas
        }
    }

    public List<EC> getECsByMaquetteNom(String maquetteNom) {
        // Recherche la maquette par son nom
        Maquette maquette = maquetteRepository.findByNom(maquetteNom);

        List<EC> ecs = new ArrayList<>();

        if (maquette != null) {
            // Récupère la classe associée à la maquette
            Classe classe = maquette.getClasse();

            // Récupère les UEs associées à la classe
            List<UE> ues = classe.getUes();

            // Pour chaque UE, récupère les ECs associés et les ajoute à la liste
            for (UE ue : ues) {
                ecs.addAll(ue.getEcs());
            }
        }

        return ecs;
    }


    public List<Classe> getClasseByMaquette(Long maquetteId) {
        Maquette maquette = maquetteRepository.findById(maquetteId).orElse(null);
        if (maquette != null) {
            // Retourner la liste contenant un seul élément classe
            return List.of(maquette.getClasse());
        }
        return List.of(); // Retourner une liste vide si la maquette n'existe pas
    }


    public List<Semestre> getSemestreByMaquette(Long maquetteId) {
        Maquette maquette = maquetteRepository.findById(maquetteId).orElse(null);
        if (maquette != null) {
            // Retourner la liste contenant un seul semestre
            return List.of(maquette.getSemestre());
        }
        return List.of(); // Retourner une liste vide si la maquette n'existe pas
    }


    public List<EC> getEcsByMaquette(Long maquetteId) {
        Maquette maquette = maquetteRepository.findById(maquetteId).orElse(null);

        if (maquette == null) {
            return new ArrayList<>(); // Retourner une liste vide si la maquette n'existe pas
        }
        // Récupérer les UEs associées à la maquette
        List<EC> ecs = new ArrayList<>();
        maquette.getClasse().getUes().forEach(ue -> ecs.addAll(ue.getEcs()));

        return ecs;
    }


    public UeEcWrapper getUesAndEcsForClasseAndSemestre(Long classeId, Long semestreId) {
        List<UE> ues = ueRepository.findByClasseIdAndSemestreId(classeId, semestreId);

        return new UeEcWrapper(ues != null ? ues : new ArrayList<>());
    }

}
