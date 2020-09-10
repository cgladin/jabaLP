package carnetAdresse;

public class Personne {
    private String nom;
    private String prenom;
    private String adresse;
    private String numeroTel;

    public Personne(String nom,String prenom,String adresse,String numeroTel){
        this.nom=nom;
        this.prenom=prenom;
        this.adresse=adresse;
        this.numeroTel=numeroTel;
    }
    public Personne(Personne carnet){
        this.nom = carnet.nom;
        this.prenom = carnet.prenom;
        this.adresse = carnet.adresse;
        this.numeroTel = carnet.numeroTel;
    }
    public String getCritere(String critere){
        if(critere.equals("nom")){
            return this.nom;
        }
        if(critere.equals("prenom")){
            return  this.prenom;
        }
        if(critere.equals("adresse")){
            return this.adresse;
        }
        if(critere.equals("tel")){
            return this.numeroTel;
        }
        return null;
    }
    public boolean comparaisonPersonne(Personne personneAComparer){
        return ( comparerNom(personneAComparer)
                || comparerPrenom(personneAComparer)
                || comparerAdresse(personneAComparer)
                || comparerTel(personneAComparer)
                || comparaisonEgal(personneAComparer));
    }
    private boolean comparaisonEgal(Personne personneAComparer){
        return  (this.nom.equals(personneAComparer.nom)
                && this.prenom.equals(personneAComparer.prenom)
                && this.adresse.equals(personneAComparer.adresse)
                && this.numeroTel.equals(personneAComparer.numeroTel));
    }
    private boolean comparerNom(Personne personneAComparer){
        return (this.nom.compareTo(personneAComparer.nom) < 0);
    }
    private boolean comparerPrenom(Personne personneAComparer){
        return (this.nom.equals(personneAComparer.nom)
                && this.prenom.compareTo(personneAComparer.prenom) < 0);
    }
    private boolean comparerAdresse(Personne personneAComparer){
        return (this.nom.equals(personneAComparer.nom)
                && this.prenom.equals(personneAComparer.prenom)
                && this.adresse.compareTo(personneAComparer.adresse) < 0 );
    }
    private boolean comparerTel(Personne personneAComparer){
        return (this.nom.equals(personneAComparer.nom)
                && this.prenom.equals(personneAComparer.prenom)
                && this.adresse.equals(personneAComparer.adresse)
                && this.numeroTel.compareTo(personneAComparer.numeroTel) < 0);
    }
    public void afficherPersonne(){
        System.out.println("Nom : "+this.nom +" Prenom : "+this.prenom+" adresse : "+this.adresse+" Tel : "+this.numeroTel);
    }
}
