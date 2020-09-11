package carnetAdresse;

import java.io.*;
import java.util.Scanner;


public class Carnet implements Serializable{
    private Personne[] carnetAdresse;
    private int nombrePersonne;

    public Carnet(){
        carnetAdresse = new Personne[1];
        nombrePersonne = 0;
    }
    public void ajoutePersonne(String nom, String prenom, String adresse, String tel){
        if (!this.verifEmplacementVide()) {
            this.augmenteTailleCarnet();
        }
        this.carnetAdresse[nombrePersonne]= new Personne(nom,prenom,adresse,tel);
        nombrePersonne = nombrePersonne + 1;
        System.out.println("Personne ajoutée \n");
    }
    private boolean verifEmplacementVide(){
        if (carnetAdresse.length != nombrePersonne) { //si le tableau n'est pas complet
            if (nombrePersonne != 0){
                return (this.carnetAdresse[nombrePersonne + 1] == null);
            } else {
                return (this.carnetAdresse[0] == null);
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

    public void sauvegarde(){
        ObjectOutputStream oos = null;
        try{
            final FileOutputStream fichier = new FileOutputStream("save.ser");
            oos = new ObjectOutputStream(fichier);
            oos.writeObject(this.carnetAdresse);
            oos.flush();
        } catch (final java.io.IOException e){
            e.printStackTrace();
        } finally {
            try {
                if(oos != null){
                    oos.flush();
                    oos.close();
                }
            } catch (final java.io.IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public void chargement(){
        ObjectInputStream ois = null;
        try{
            final FileInputStream fichier = new FileInputStream("save.ser");
            ois = new ObjectInputStream(fichier);
            //this.carnetAdresse = (this.carnetAdresse) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void supprimer(int index){
        this.carnetAdresse[index] = null;
        if(this.nombrePersonne != (index-1)){ //si on ne supprime pas la derniere case remplie
            int nbDeplacement = (nombrePersonne-index);
            for(int i=0;i<nbDeplacement;i++){ // on déplace les objets
                this.carnetAdresse[i]=this.carnetAdresse[i+1];
            }
            this.nombrePersonne = this.nombrePersonne - 1;
        }
        if((this.carnetAdresse.length-this.nombrePersonne) >= 15 ){ //on verifie qu'il y a au moins 15 place de libre pour avoir un nouveau tableau pas deja plein
            this.diminueTailleCarnet();
        }
    }
    public void selectionRecherche(){
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

            switch (saisie) {
                case "1" -> nom = this.saisirRecherche("nom", nom);
                case "2" -> prenom = this.saisirRecherche("prenom", prenom);
                case "3" -> adresse = this.saisirRecherche("adresse", adresse);
                case "4" -> telephone = this.saisirRecherche("telephone", telephone);
                default -> System.out.println("Erreur de saisie");
            }
        }while(!saisie.equals("q") && !verifRechercheSaisieNonVide(nom, prenom, adresse, telephone));
        this.trieABulle();
    }
    public boolean verifRechercheSaisieNonVide(String nom,String prenom,String adresse,String telephone){
        return (!nom.equals("") || !prenom.equals("") || !adresse.equals("") || !telephone.equals(""));

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
    public void trieABulle(/*int n*/) {
        //int p = n - 1;
        int p = this.nombrePersonne - 1;
        Personne x;
        boolean tri = true;
        while (tri & p > 0) {
            tri = false;
            for (int i = 0; i < p - 1; i++) {
                if (this.carnetAdresse[i].comparaisonPersonne(this.carnetAdresse[i+1])) {
                    x = this.carnetAdresse[i];
                    this.carnetAdresse[i] = this.carnetAdresse[i + 1];
                    this.carnetAdresse[i + 1] = x;
                    tri = true;
                }
            }
            p = p - 1;
        }
    }

    public int RechercheDichotomique(String critere,String critereRechercher) {
        int indice;
        int a = 0;
        int b = this.tailleCarnet() - 1;
        int m = (a + b) / 2;
        while (a < b && !this.carnetAdresse[m].getCritere(critere).equals(critereRechercher)) {
            if (this.carnetAdresse[m].getCritere(critere).compareTo(critereRechercher) < 0) {
                a = m + 1;
            } else {
                b = m - 1;
            }
            m = (a + b) / 2;
        }
        if (this.carnetAdresse[m].getCritere(critere).equals(critereRechercher)) {
            indice = m;
        } else {
            indice = -1;
        }
        return indice;
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
