package carnetAdresse;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Carnet {
    private Personne[] carnetAdresse;
    private int nombrePersonne;

    public Carnet(){
        carnetAdresse = new Personne[1];
        nombrePersonne = 0;
    }
    public void ajoutePersonne(String nom, String prenom, String adresse, String tel){
        if(this.verifEmplacementVide()){
            this.carnetAdresse[nombrePersonne]= new Personne(nom,prenom,adresse,tel);
            nombrePersonne = nombrePersonne + 1;
        } else {
            this.augmenteTailleCarnet();
            this.carnetAdresse[nombrePersonne]= new Personne(nom,prenom,adresse,tel);
            nombrePersonne = nombrePersonne + 1;
        }
    }
    private boolean verifEmplacementVide(){
        if (carnetAdresse.length != nombrePersonne) { //si le tableau n'est pas complet
            if (nombrePersonne != 0){
                if(this.carnetAdresse[nombrePersonne+1] == null){
                    return true;
                } else {
                    return false;
                }
            } else {
                if(this.carnetAdresse[0] == null){
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }
    public int tailleCarnet(){
        return this.carnetAdresse.length;
    }
    private void augmenteTailleCarnet(){

        Personne[] carnetTemp= new Personne[this.tailleCarnet()];
        int taille = this.tailleCarnet()-1;
        int newTaille = this.tailleCarnet()+10;

        for (int i=0;i <= taille ;i++){ //on copie les donnée existante dans un tableau
            carnetTemp[i] = new Personne(this.carnetAdresse[i]);
        }

        this.carnetAdresse = new Personne[newTaille]; //on recrée un tableau plus grand

        for (int i=0;i <= taille ;i++){ // on remet les donnée qu'il y avait avant
            this.carnetAdresse[i] = new Personne(carnetTemp[i]);
        }
    }
    private void diminueTailleCarnet(){
        int taille = this.tailleCarnet()-10;
        Personne[] carnetTemp= new Personne[nombrePersonne];

        for(int i=0; i < nombrePersonne ;i++){
            carnetTemp[i] = new Personne(this.carnetAdresse[i]);
        }
        this.carnetAdresse = new Personne[taille]; // on récrée un tableau plus petit

        for (int i=0;i < nombrePersonne ;i++){ // on remet les donnée qu'il y avait avant
            this.carnetAdresse[i] = new Personne(carnetTemp[i]);
        }
    }

    public void sauvegarde() throws IOException{

    }

    public void chargement() throws IOException{

    }
    public void supprimer(int index){
        this.carnetAdresse[index] = null;
        this.nombrePersonne = this.nombrePersonne - 1;
        if(this.nombrePersonne != (index-1)){ //si on ne supprime pas la derniere case remplie
            int nbDeplacement = (nombrePersonne-index);
            for(int i=0;i<nbDeplacement;i++){ // on déplace les objets
                this.carnetAdresse[i]=this.carnetAdresse[i+1];
            }
        }
        if((this.carnetAdresse.length-this.nombrePersonne) >= 15 ){ //on verifie qu'il y a au moins 15 place de libre pour avoir un nouveau tableau pas deja plein
            this.diminueTailleCarnet();
        }
    }
    public void recherche(){
        Scanner sc = new Scanner(System.in);
        String saisie;
        String nom = "";
        String prenom ="";
        String adresse="";
        String telephone="";

        do{
            System.out.println("Quels sont les critères de recherche: " );
            System.out.println("1: nom="+nom+", 2: prenom="+prenom+", 3: adresse="+adresse+", 4: Telephone="+telephone+", q: confirmé \n" );
            saisie = sc.next();

            switch (saisie){
                case "1":
                    nom = this.saisirRecherche("nom",nom);
                    break;
                case "2":
                    prenom = this.saisirRecherche("prenom",prenom);
                    break;
                case "3":
                    adresse = this.saisirRecherche("adresse",adresse);
                    break;
                case "4":
                    telephone = this.saisirRecherche("telephone",telephone);
                    break;
                default:
                    System.out.println("Erreur de saisie");
                    break;
            }
        }while(!saisie.equals("q") && verifRechercheSaisieNonVide(nom,prenom,adresse,telephone) != true);

    }
    public boolean verifRechercheSaisieNonVide(String nom,String prenom,String adresse,String telephone){
        if(!nom.equals("") || !prenom.equals("") || !adresse.equals("") || !telephone.equals("")){
            return true;
        } else {
            return false;
        }

    }
    public String saisirRecherche(String critere, String critereSaisie){
        Scanner sc = new Scanner(System.in);
        String saisie;
        if(critereSaisie.equals("")) {
            System.out.println("Saisir "+critere+" à rechercher: ");
            critereSaisie = sc.next();
        } else {
            do {
                System.out.println("1 : modifier, 2: Supprimer");
                saisie = sc.next();
                if (saisie.equals("1") || saisie.equals("2")) {
                    if (saisie.equals("1")) {
                        System.out.println("Modifier le telephone: ");
                        critereSaisie = sc.next();
                    }
                    if (saisie.equals("2")) {
                        critereSaisie = "";
                    }
                } else {
                    System.out.println("Erreur de saisie");
                }
            }while(!saisie.equals("1") || !saisie.equals("2"));
        }
        return critereSaisie;
    }

    public void tri(String critere){
        int p = nombrePersonne - 1;
        boolean exg = true;
        while (exg && p>0){
            exg=false;
            for(int i = 0;i<p-1; i++){
                /*if(this.carnetAdresse[i].getCritere(critere).compareTo(this.carnetAdresse[i]));*/
            }
        }
    }
    public void afficher(){
        for (int i = 0 ; i <= this.getNombrePersonne()-1; i++){
            this.carnetAdresse[i].afficherPersonne();
        }
    }

    public int getNombrePersonne(){
        return this.nombrePersonne;
    }

}
