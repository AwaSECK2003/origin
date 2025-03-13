package uasz.sn.GestionEnseignement.Utilisateur.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uasz.sn.GestionEnseignement.Authentification.modele.Utilisateur;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    private String code;
    private String coefficient;
    private String credit;
    @OneToMany(mappedBy = "ue")
    private Collection<EC> ecs;


    // Relation ManyToOne avec Semestre
    @ManyToOne
    @JoinColumn(name = "semestre_id", nullable = false)
    private Semestre semestre;  // Ajout de la relation avec Semestre

    @ManyToMany
    @JoinTable(name = "ue_classe", joinColumns = @JoinColumn(name = "ue_id"), inverseJoinColumns = @JoinColumn(name = "classe_id")
    )
    private List<Classe> classes;

    @Column(name = "est_archivee", nullable = false, columnDefinition = "boolean default false")
    private boolean estArchivee = false;

    @Column(name = "active", nullable = false, columnDefinition = "boolean default true")
    private boolean active = true;


}

