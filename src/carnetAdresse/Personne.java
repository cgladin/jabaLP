package carnetAdresse;
import java.io.Serializable;

public class Personne  implements Serializable {
    private final String nom;
    private final String prenom;
    private final String ville;
    private final String numeroTel;

    public Personne(String nom, String prenom, String ville, String numeroTel){
        this.nom=nom;
        this.prenom=prenom;
        this.ville = ville;
        this.numeroTel=numeroTel;
    }
    public Personne(Personne carnet){
        this.nom = carnet.nom;
        this.prenom = carnet.prenom;
        this.ville = carnet.ville;
        this.numeroTel = carnet.numeroTel;
    }
    public boolean comparaisonPersonne( Personne personneAComparer){
        return ( comparerNom(personneAComparer)
                || comparerPrenom(personneAComparer)
                || comparerAdresse(personneAComparer)
                || comparerTel(personneAComparer)
                || comparaisonEgal(personneAComparer));
    }
    private boolean comparaisonEgal(Personne personneAComparer){
        return  (this.nom.equals(personneAComparer.nom)
                && this.prenom.equals(personneAComparer.prenom)
                && this.ville.equals(personneAComparer.ville)
                && this.numeroTel.equals(personneAComparer.numeroTel));
    }
    private boolean comparerNom(Personne personneAComparer){
        return (this.nom.compareTo(personneAComparer.nom) > 0);
    }
    private boolean comparerPrenom(Personne personneAComparer){
        return (this.nom.equals(personneAComparer.nom)
                && this.prenom.compareTo(personneAComparer.prenom) > 0);
    }
    private boolean comparerAdresse(Personne personneAComparer){
        return (this.nom.equals(personneAComparer.nom)
                && this.prenom.equals(personneAComparer.prenom)
                && this.ville.compareTo(personneAComparer.ville) > 0 );
    }
    private boolean comparerTel(Personne personneAComparer){
        return (this.nom.equals(personneAComparer.nom)
                && this.prenom.equals(personneAComparer.prenom)
                && this.ville.equals(personneAComparer.ville)
                && this.numeroTel.compareTo(personneAComparer.numeroTel) > 0);
    }
    public void afficherPersonne(){
        System.out.println("Nom: "+this.nom +" Prenom: "+this.prenom+" Adresse: "+this.ville +" Telephone: "+this.numeroTel);
    }
    public String getNom(){
        return this.nom;
    }
    public String getPrenom() {
        return this.prenom;
    }
    public String getVille(){
        return this.ville;
    }
    public String getNumeroTel(){
        return this.numeroTel;
    }
}
