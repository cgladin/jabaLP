package carnetAdresse;

import java.io.*;
import java.util.Scanner;


public class Carnet implements Serializable{
    private Personne[] carnetAdresse;
    private int nombrePersonne;
    private int[] indexPrenom;
    private int[] indexVille;
    private int[] indexTelephone;

    public Carnet(){
        this.carnetAdresse = new Personne[10];
        this.nombrePersonne = 0;
    }
    public void ajoute(){
        Scanner sc = new Scanner(System.in);
        String nom;
        String prenom;
        String ville;
        String tel;
        System.out.println("Veuillez saisir le Prenom de la personne");
        prenom = sc.nextLine();
        System.out.println("Veuillez saisir le nom de la personne");
        nom = sc.nextLine();
        System.out.println("Veuillez saisir la ville de la personne");
        ville = sc.nextLine();
        System.out.println("Veuillez saisir le numéro de la personne");
        tel = sc.nextLine();
        this.ajoutePersonne(nom, prenom, ville, tel);
    }
    public void ajoutePersonne(String nom, String prenom, String ville, String tel){
        if (!this.verifEmplacementVide()) {
            this.augmenteTailleCarnet();
        }
        this.carnetAdresse[nombrePersonne]= new Personne(nom,prenom,ville,tel);
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
        this.triIndex();
        System.out.println("Suppression Réussi");
    }
    public void selectionRecherche(){ // affichage de la selection de critere de recherche
        Scanner sc = new Scanner(System.in);
        String saisie;
        String nom = "";
        String prenom ="";
        String ville="";
        String telephone="";

        do{
            System.out.println("Quels sont les critères de recherche: " );
            System.out.println("1: nom="+nom+", 2: prenom="+prenom+", 3: ville="+ville+", 4: Telephone="+telephone+", q: confirmé \n" );
            saisie = sc.next();

            switch (saisie) {
                case "1" -> nom = this.saisirRecherche("nom", nom);
                case "2" -> prenom = this.saisirRecherche("prenom", prenom);
                case "3" -> ville = this.saisirRecherche("ville", ville);
                case "4" -> telephone = this.saisirRecherche("telephone", telephone);
                default -> {
                    if(!saisie.equals("q"))
                        System.out.println("Erreur de saisie");

                    System.out.println("Confirmation");
                }
            }
        }while(!saisie.equals("q") && verifRechercheSaisieNonVide(nom, prenom, ville, telephone));

        this.tri();
        int index;
        int i;
        boolean recherche = false; //afin de rentrer que dans 1 des boucles suivantes
        if(!nom.equals("")){
            index = this.rechercheDichotomiqueNom(nom);
            recherche=true;
            i=index;
            while(i < this.nombrePersonne && this.carnetAdresse[i].getNom().equals(nom)){
                if(!prenom.equals("") || !ville.equals("") || !telephone.equals("") ){
                    if(this.carnetAdresse[i].getPrenom().equals(prenom) && this.carnetAdresse[i].getVille().equals(ville) && this.carnetAdresse[i].getNumeroTel().equals(telephone)){
                        this.afficherIndex(i);
                    } else if (this.carnetAdresse[i].getPrenom().equals(prenom) && this.carnetAdresse[i].getVille().equals(ville)){
                        this.afficherIndex(i);
                    } else if (this.carnetAdresse[i].getPrenom().equals(prenom) && this.carnetAdresse[i].getNumeroTel().equals(telephone)){
                        this.afficherIndex(i);
                    } else if (this.carnetAdresse[i].getNumeroTel().equals(telephone)){
                        this.afficherIndex(i);
                    } else if (this.carnetAdresse[i].getVille().equals(ville)){
                        this.afficherIndex(i);
                    } else if (this.carnetAdresse[i].getPrenom().equals(prenom)){
                        this.afficherIndex(i);
                    }
                } else { // si il y a que le nom rechercher affiche les occurences
                    this.afficherIndex(i);
                }
                i=i+1;
            }
        }

        if(!prenom.equals("") && !recherche) {
            index = this.rechercheDichotomiquePrenom(prenom);
            recherche=true;
            i=index;
            while(i < this.nombrePersonne && this.carnetAdresse[i].getPrenom().equals(prenom)){
                if(!nom.equals("") || !ville.equals("") || !telephone.equals("") ){
                    if(this.carnetAdresse[i].getNom().equals(nom) && this.carnetAdresse[i].getVille().equals(ville) && this.carnetAdresse[i].getNumeroTel().equals(telephone)){
                        this.afficherIndex(i);
                    } else if (this.carnetAdresse[i].getNom().equals(nom) && this.carnetAdresse[i].getVille().equals(ville)){
                        this.afficherIndex(i);
                    } else if (this.carnetAdresse[i].getNom().equals(nom) && this.carnetAdresse[i].getNumeroTel().equals(telephone)){
                        this.afficherIndex(i);
                    } else if (this.carnetAdresse[i].getNumeroTel().equals(telephone)){
                        this.afficherIndex(i);
                    } else if (this.carnetAdresse[i].getVille().equals(ville)){
                        this.afficherIndex(i);
                    } else if (this.carnetAdresse[i].getNom().equals(nom)){
                        this.afficherIndex(i);
                    }
                } else { // si il y a que le nom rechercher affiche les occurences
                    this.afficherIndex(i);
                }
                i=i+1;
            }
        }
        if(!ville.equals("") && !recherche) { // a verif
            index = this.rechercheDichotomiqueVille(ville);
            recherche=true;
            i=index;
            while(i < this.nombrePersonne && this.carnetAdresse[i].getVille().equals(ville)){
                if(!nom.equals("") || !prenom.equals("") || !telephone.equals("") ){
                    if(this.carnetAdresse[i].getNom().equals(nom) && this.carnetAdresse[i].getPrenom().equals(prenom) && this.carnetAdresse[i].getNumeroTel().equals(telephone)){
                        this.afficherIndex(i);
                    } else if (this.carnetAdresse[i].getNom().equals(nom) && this.carnetAdresse[i].getPrenom().equals(prenom)){
                        this.afficherIndex(i);
                    } else if (this.carnetAdresse[i].getNom().equals(nom) && this.carnetAdresse[i].getNumeroTel().equals(telephone)){
                        this.afficherIndex(i);
                    } else if (this.carnetAdresse[i].getNumeroTel().equals(telephone)){
                        this.afficherIndex(i);
                    } else if (this.carnetAdresse[i].getPrenom().equals(prenom)){
                        this.afficherIndex(i);
                    } else if (this.carnetAdresse[i].getNom().equals(nom)){
                        this.afficherIndex(i);
                    }
                } else { // si il y a que le nom rechercher affiche les occurences
                    this.afficherIndex(i);
                }
                i=i+1;
            }
        }
        if(!telephone.equals("") && !recherche) { // a verif
            index = this.rechercheDichotomiqueTelephone(telephone);
            recherche=true;
            i=index;
            while(i < this.nombrePersonne && this.carnetAdresse[i].getNumeroTel().equals(telephone)){
                if(!nom.equals("") || !ville.equals("") || !prenom.equals("") ){
                    if(this.carnetAdresse[i].getNom().equals(nom) && this.carnetAdresse[i].getVille().equals(ville) && this.carnetAdresse[i].getPrenom().equals(prenom)){
                        this.afficherIndex(i);
                    } else if (this.carnetAdresse[i].getNom().equals(nom) && this.carnetAdresse[i].getVille().equals(ville)){
                        this.afficherIndex(i);
                    } else if (this.carnetAdresse[i].getNom().equals(nom) && this.carnetAdresse[i].getPrenom().equals(prenom)){
                        this.afficherIndex(i);
                    } else if (this.carnetAdresse[i].getPrenom().equals(prenom)){
                        this.afficherIndex(i);
                    } else if (this.carnetAdresse[i].getVille().equals(ville)){
                        this.afficherIndex(i);
                    } else if (this.carnetAdresse[i].getNom().equals(nom)){
                        this.afficherIndex(i);
                    }
                } else { // si il y a que le nom rechercher affiche les occurences
                    this.afficherIndex(i);
                }
                i=i+1;
            }
        }

       int saisieInt;
        do{
            System.out.println("Supprimer la personne en rentrant son index sinon -1 pour quitter");
            saisieInt = sc.nextInt();
            if(saisieInt != -1 && sc.hasNextInt()){
                this.supprimer(saisieInt);
            }
        }while (saisieInt != -1);

    }
    private void afficherIndex(int index){
        System.out.println("index :" +index);
        this.carnetAdresse[index].afficherPersonne();
    }
    private boolean verifRechercheSaisieNonVide(String nom,String prenom,String ville,String telephone){
        return (!nom.equals("") || !prenom.equals("") || !ville.equals("") || !telephone.equals(""));

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
    public void tri(){ //permet de trier tout avec une méthode
        this.triABulle();
        this.triIndex();
    }
    public void triIndex(){ // réunis seulement les tri des index
        this.triPrenom();
        this.triAdresse();
        this.triTelephone();
    }
    /*public int partition (int début, int fin) {
        Personne valeurPivot = this.carnetAdresse[début];
        int d = début+1;
        int f = fin;
        while (d < f) {
            while(d < f && this.carnetAdresse[f].comparaisonPersonne(valeurPivot) >=) f--;
            while(d < f && this.carnetAdresse[d].comparaisonPersonne(valeurPivot) <= ) d++;
            Personne temp = this.carnetAdresse[d];
            this.carnetAdresse[d]= this.carnetAdresse[f];
            this.carnetAdresse[f] = temp;
        }
        if (this.carnetAdresse[d] > valeurPivot) d--;
        this.carnetAdresse[début] = this.carnetAdresse[d];
        this.carnetAdresse[d] = valeurPivot;
        return d;
    }
    public void triRapide(int début,int fin){
        if (this.nombrePersonne < tailleCarnet()) {
            int indicePivot = partition( début, fin);
            triRapide(début, indicePivot-1);
            triRapide(indicePivot+1, fin);
        }
    }*/
    public void triABulle() {
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
    public void triPrenom(){
        int p = this.nombrePersonne - 1;
        int x;
        boolean tri = true;
        this.indexPrenom= new int[this.nombrePersonne];

        for(int i=0;i < this.nombrePersonne;i++){
            indexPrenom[i]=i;
        }
        while (tri & p > 0) {
            tri = false;
            for (int i = 0; i < p; i++) {
                if (this.carnetAdresse[this.indexPrenom[i]].getPrenom().compareTo(this.carnetAdresse[this.indexPrenom[i+1]].getPrenom()) > 0) {
                    x = this.indexPrenom[i];
                    this.indexPrenom[i] = this.indexPrenom[i + 1];
                    this.indexPrenom[i + 1] = x;
                    tri = true;
                }
            }
            p = p - 1;
        }
    }
    public void triAdresse(){
        int p = this.nombrePersonne - 1;
        int x;
        boolean tri = true;
        this.indexVille = new int[this.nombrePersonne];

        for(int i=0;i < this.nombrePersonne;i++){
            indexVille[i]=i;
        }
        while (tri & p > 0) {
            tri = false;
            for (int i = 0; i < p; i++) {
                if (this.carnetAdresse[this.indexVille[i]].getVille().compareTo(this.carnetAdresse[this.indexVille[i+1]].getVille()) > 0) {
                    x = this.indexVille[i];
                    this.indexVille[i] = this.indexVille[i + 1];
                    this.indexVille[i + 1] = x;
                    tri = true;
                }
            }
            p = p - 1;
        }
    }
    public void triTelephone(){
        int p = this.nombrePersonne - 1;
        int x;
        boolean tri = true;
        this.indexTelephone= new int[this.nombrePersonne];

        for(int i=0;i < this.nombrePersonne;i++){
            indexTelephone[i]=i;
        }
        while (tri & p > 0) {
            tri = false;
            for (int i = 0; i < p; i++) {
                if (this.carnetAdresse[this.indexTelephone[i]].getNumeroTel().compareTo(this.carnetAdresse[this.indexTelephone[i+1]].getNumeroTel()) > 0) {
                    x = this.indexTelephone[i];
                    this.indexTelephone[i] = this.indexTelephone[i + 1];
                    this.indexTelephone[i + 1] = x;
                    tri = true;
                }
            }
            p = p - 1;
        }
    }

    private int rechercheDichotomiqueNom(String critereRechercher) {
        int indice;
        int a = 0;
        int b = this.nombrePersonne - 1;
        int m = (a + b) / 2;
        while (a < b && !this.carnetAdresse[m].getNom().equals(critereRechercher)) {
            if (this.carnetAdresse[m].getNom().compareTo(critereRechercher) < 0) {
                a = m + 1;
            } else {
                b = m - 1;
            }
            m = (a + b) / 2;
        }
        if (this.carnetAdresse[m].getNom().equals(critereRechercher)) { //si une valeur est trouvé
            int aGauche = m-1;
            if(aGauche >= 0 && this.carnetAdresse[aGauche].getNom().equals(critereRechercher)){
                while(aGauche > 0 && this.carnetAdresse[aGauche].getNom().equals(critereRechercher)) { //on cherche la valeur la plus a gauche possible
                    aGauche = aGauche-1;
                }
                indice = aGauche;
            } else {
                indice = m;
            }
        } else {
            indice = -1;
        }
        return indice;
    }
    private int rechercheDichotomiquePrenom(String critereRechercher) {
        int indice;
        int a = 0;
        int b = this.nombrePersonne - 1;
        int m = (a + b) / 2;
        while (a < b && !this.carnetAdresse[this.indexPrenom[m]].getPrenom().equals(critereRechercher)) {
            if (this.carnetAdresse[this.indexPrenom[m]].getPrenom().compareTo(critereRechercher) < 0) {
                a = m + 1;
            } else {
                b = m - 1;
            }
            m = (a + b) / 2;
        }
        if (this.carnetAdresse[this.indexPrenom[m]].getPrenom().equals(critereRechercher)) {
            int aGauche = m-1;
            if(aGauche >= 0 && this.carnetAdresse[this.indexPrenom[aGauche]].getPrenom().equals(critereRechercher)){
                while(aGauche > 0 && this.carnetAdresse[this.indexPrenom[aGauche]].getPrenom().equals(critereRechercher)) { //on cherche la valeur la plus a gauche possible
                    aGauche = aGauche-1;
                }
                indice = aGauche;
            } else {
                indice = m;
            }
        } else {
            indice = -1;
        }
        return indice;
    }
    private int rechercheDichotomiqueVille(String critereRechercher) {
        int indice;
        int a = 0;
        int b = this.nombrePersonne - 1;
        int m = (a + b) / 2;
        while (a < b && !this.carnetAdresse[this.indexVille[m]].getVille().equals(critereRechercher)) {
            if (this.carnetAdresse[this.indexVille[m]].getVille().compareTo(critereRechercher) < 0) {
                a = m + 1;
            } else {
                b = m - 1;
            }
            m = (a + b) / 2;
        }
        if (this.carnetAdresse[this.indexVille[m]].getVille().equals(critereRechercher)) {
            int aGauche = m-1;
            if(aGauche >= 0 && this.carnetAdresse[this.indexVille[aGauche]].getVille().equals(critereRechercher)){
                while(aGauche > 0 && this.carnetAdresse[this.indexVille[aGauche]].getVille().equals(critereRechercher)) { //on cherche la valeur la plus a gauche possible
                    aGauche = aGauche-1;
                }
                indice = aGauche;
            } else {
                indice = m;
            }
        } else {
            indice = -1;
        }
        return indice;
    }
    private int rechercheDichotomiqueTelephone(String critereRechercher) {
        int indice;
        int a = 0;
        int b = this.nombrePersonne - 1;
        int m = (a + b) / 2;
        while (a < b && !this.carnetAdresse[this.indexTelephone[m]].getNumeroTel().equals(critereRechercher)) {
            if (this.carnetAdresse[this.indexTelephone[m]].getNumeroTel().compareTo(critereRechercher) < 0) {
                a = m + 1;
            } else {
                b = m - 1;
            }
            m = (a + b) / 2;
        }
        if (this.carnetAdresse[this.indexTelephone[m]].getNumeroTel().equals(critereRechercher)) {
            int aGauche = m-1;
            if(aGauche >= 0 && this.carnetAdresse[this.indexTelephone[aGauche]].getNumeroTel().equals(critereRechercher)){
                while(aGauche > 0 && this.carnetAdresse[this.indexTelephone[aGauche]].getNumeroTel().equals(critereRechercher)) { //on cherche la valeur la plus a gauche possible
                    aGauche = aGauche-1;
                }
                indice = aGauche;
            } else {
                indice = m;
            }
        } else {
            indice = -1;
        }
        return indice;
    }

    public void afficher(){
        this.triABulle();
        for (int i = 0 ; i < this.nombrePersonne; i++){
            this.carnetAdresse[i].afficherPersonne();
        }
    }
}
