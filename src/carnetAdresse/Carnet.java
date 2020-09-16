package carnetAdresse;

import java.io.*;
import java.util.Scanner;


public class Carnet implements Serializable{
    private Personne[] carnetAdresse;
    private int nombrePersonne;
    private int[][] indexCarnetTrie;

    public Carnet(){
        this.carnetAdresse = new Personne[10];
        this.nombrePersonne = 0;
        this.indexCarnetTrie = new int[10][3]; //10 ligne et 3 colonne/attribut sans compter le nom
    }
    public void ajoute(){
        Scanner sc = new Scanner(System.in);
        String nom;
        String prenom;
        String adresse;
        String tel;
        System.out.println("Veuillez saisir le Prenom de la personne");
        prenom = sc.nextLine();
        System.out.println("Veuillez saisir le nom de la personne");
        nom = sc.nextLine();
        System.out.println("Veuillez saisir l'adresse de la personne");
        adresse = sc.nextLine();
        System.out.println("Veuillez saisir le numéro de la personne");
        tel = sc.nextLine();
        this.ajoutePersonne(nom, prenom, adresse, tel);
        sc.close();
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
    private int tailleCarnet(){
        return this.carnetAdresse.length;
    }
    private void augmenteTailleCarnet(){
        int taille = this.tailleCarnet()-1;
        int newTaille = this.tailleCarnet()+10;
        Personne[] carnetTemp= new Personne[newTaille];
        for (int i=0;i <= taille ;i++){ //on copie les donnée existante dans un tableau
            carnetTemp[i] = new Personne(this.carnetAdresse[i]);
        }
        this.carnetAdresse = new Personne[newTaille]; //on recrée un tableau plus grand
        this.carnetAdresse= carnetTemp;
        System.out.println("Taille augmenté de 10, taille actuelle "+this.tailleCarnet());
    }
    private void diminueTailleCarnet(){
        int taille = this.tailleCarnet()-10;
        Personne[] carnetTemp= new Personne[taille];

        for(int i=0; i < nombrePersonne ;i++){
            carnetTemp[i] = new Personne(this.carnetAdresse[i]);
        }
        this.carnetAdresse = new Personne[taille]; // on récrée un tableau plus petit
        this.carnetAdresse=carnetTemp;
        System.out.println("Taille diminué de 10, taille actuelle "+this.tailleCarnet());
    }

    public void sauvegarde(){
        ObjectOutputStream oos;
        try{
            final FileOutputStream fichier = new FileOutputStream("save.ser");
            oos = new ObjectOutputStream(fichier);
            oos.writeObject(this);
            oos.flush();
            oos.close();
            System.out.println("Carnet Sauvegardé");
        } catch (final java.io.IOException e){
            e.printStackTrace();
        }
    }
    public void chargement(){
        Carnet buff;
        ObjectInputStream ois;
        try{
            final FileInputStream fichier = new FileInputStream("save.ser");
            ois = new ObjectInputStream(fichier);
            buff = (Carnet) ois.readObject();
            ois.close();
            this.carnetAdresse = buff.carnetAdresse;
            this.nombrePersonne = buff.nombrePersonne;
            System.out.println("Carnet chargé");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void supprimer(int index){
        this.carnetAdresse[index] = null;
        if(this.nombrePersonne-1 != index){ //si on ne supprime pas la derniere case remplie
            int nbDeplacement = ((nombrePersonne-1)-index);
            for(int i=0; i < nbDeplacement ;i++){ // on déplace les objets
                this.carnetAdresse[index]=this.carnetAdresse[index+1];
                index=index+1;
            }
        }
        this.nombrePersonne = this.nombrePersonne - 1;
        if((this.carnetAdresse.length-this.nombrePersonne) >= 15 ){ //on verifie qu'il y a au moins 15 place de libre pour avoir un nouveau tableau pas deja plein
            this.diminueTailleCarnet();
        }
        System.out.println("Suppression Réussi");
    }
    public void selectionRecherche(){ // affichage de la selection de critere de recherche
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
                default -> {
                    if(!saisie.equals("q"))
                        System.out.println("Erreur de saisie");

                    System.out.println("Confirmation");
                }
            }
        }while(!saisie.equals("q") && verifRechercheSaisieNonVide(nom, prenom, adresse, telephone));
        this.trieABulle();
        int inom = this.rechercheDichotomique("nom",nom);

        System.out.println(inom);
        if(inom > -1)
            this.carnetAdresse[inom].afficherPersonne();

    }
    private boolean verifRechercheSaisieNonVide(String nom,String prenom,String adresse,String telephone){
        return (!nom.equals("") || !prenom.equals("") || !adresse.equals("") || !telephone.equals(""));

    }
    private String saisirRecherche(String critere, String critereSaisie){ //permet de saisir,modifier,supprimer un critère
        Scanner sc = new Scanner(System.in);
        String saisie;
        boolean test= false;
        if(critereSaisie.equals("")) {
            System.out.println("Saisir "+critere+" à rechercher: ");
            critereSaisie = sc.next();
        } else {
            do {
                System.out.println("1 : modifier, 2: Supprimer");
                saisie = sc.next();
                if (saisie.equals("1") || saisie.equals("2")) {
                    if (saisie.equals("1")) {
                        System.out.println("Modifier: ");
                        critereSaisie = sc.next();
                    }
                    if (saisie.equals("2")) {
                        critereSaisie = "";
                    }
                    test=true;
                } else {
                    System.out.println("Erreur de saisie");
                }
            }while(!test);
        }
        return critereSaisie;
    }
    public void trieABulle() {
        int p = this.nombrePersonne - 1;
        Personne x;
        boolean tri = true;
        while (tri & p > 0) {
            tri = false;
            for (int i = 0; i < p; i++) {
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
    public void triSecondaire(){

    }

    private int rechercheDichotomique(String critere,String critereRechercher) {
        int indice;
        int a = 0;
        int b = this.nombrePersonne - 1;
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
        this.trieABulle();
        for (int i = 0 ; i < this.nombrePersonne; i++){
            this.carnetAdresse[i].afficherPersonne();
        }
    }
}
