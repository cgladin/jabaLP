package carnetAdresse;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


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

    public int getNombrePersonne(){
        return this.nombrePersonne;
    }
    public Personne getPersonneFromCarnet(int i){
        return this.carnetAdresse[i];
    }
}
