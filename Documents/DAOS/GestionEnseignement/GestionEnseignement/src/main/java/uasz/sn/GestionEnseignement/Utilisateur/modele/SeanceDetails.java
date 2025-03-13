package uasz.sn.GestionEnseignement.Utilisateur.modele;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SeanceDetails {
    private String ec;
    private String type;
    private String salle;
    private String enseignant;
    private String plageHoraire;

    // Constructeur
    public SeanceDetails(String ec, String type, String salle, String enseignant, String plageHoraire  ) {
        this.ec = ec;
        this.type = type;
        this.salle = salle;
        this.enseignant = enseignant;
        this.plageHoraire = plageHoraire;
    }

    // Getters et setters
    public String getEc() { return ec; }
    public void setEc(String ec) { this.ec = ec; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getSalle() { return salle; }
    public void setSalle(String salle) { this.salle = salle; }

    public String getEnseignant() { return enseignant; }
    public void setEnseignant(String enseignant) { this.enseignant = enseignant; }

    public String getPlageHoraire() { return plageHoraire; }
    public void setPlageHoraire(String plageHoraire) { this.plageHoraire = plageHoraire; }

}