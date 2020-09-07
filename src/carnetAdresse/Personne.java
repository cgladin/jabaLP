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
            return numeroTel;
        }
        return null;
    }
    public void afficherPersonne(){
        System.out.println("Nom : "+this.nom +" Prenom : "+this.prenom+" adresse : "+this.adresse+" Tel : "+this.numeroTel);
    }
}
