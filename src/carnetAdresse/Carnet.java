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
    public void sauvegarde() throws IOException{
        Personne[] carnetTemp= new Personne[nombrePersonne];
        int taille = this.nombrePersonne-1;
        System.out.println(taille);
        for(int i=0;i <= taille ;i++){ //on copie pour avoir un tableau de la taille exact des donnée et donc ne pas avoir de null
            carnetTemp[i] = new Personne(this.carnetAdresse[i]);
        }
        Gson gson = new Gson();
        try {
            String json = gson.toJson(carnetTemp);
            FileWriter writer = new FileWriter("./save.json");
            writer.write(json);
            writer.close();
            System.out.println("Sauvegarde Réussie");
        } catch (IOException e) {
            System.out.println("Erreur");
            e.printStackTrace();
        }
    }

    public void chargement() throws IOException{
        Gson gson = new Gson();
        try {
            this.carnetAdresse =  gson.fromJson(new FileReader("./save.json"),Personne[].class);
            this.nombrePersonne = this.carnetAdresse.length;
            System.out.println("Chargement Réussie");
        } catch (IOException e) {
            System.out.println("Erreur");
            e.printStackTrace();
        }
    }

    public int getNombrePersonne(){
        return this.nombrePersonne;
    }
    public Personne getPersonneFromCarnet(int i){
        return this.carnetAdresse[i];
    }
}
