package uasz.sn.GestionEnseignement.Utilisateur.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private  String libelle;
    private String coefficient;
    private int cm;
    private int td;
    private int tp;
    private int tpe;
    @ManyToOne
    @JoinColumn(name = "ue_id")
    private UE ue;

    @Column(name = "est_archivee", nullable = false, columnDefinition = "boolean default false")
    private boolean estArchivee = false;

    @Column(name = "active", nullable = false, columnDefinition = "boolean default true")
    private boolean active = true;

    @OneToMany(mappedBy = "ec", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enseignement> enseignements;


}
