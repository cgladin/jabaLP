import carnetAdresse.Carnet;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String saisie;
        String nom;
        String prenom;
        String adresse;
        String tel;
        Carnet carnet = new Carnet();

        do{
            System.out.println("Saisir a pour ajouter une personne \nc pour voir le carnet \n" +
                    "s pour sauvegarder \nd pour charger \nq pour quitter ");
            saisie = sc.next();
            switch (saisie){
                case "a" :
                    System.out.println("Veuillez saisir le Prenom de la personne");
                    prenom = sc.next();
                    System.out.println("Veuillez saisir le nom de la personne");
                    nom = sc.next();
                    System.out.println("Veuillez saisir l'adresse de la personne");
                    adresse = sc.next();
                    System.out.println("Veuillez saisir le numéro de la personne");
                    tel = sc.next();

                    carnet.ajoutePersonne(nom,prenom,adresse,tel);
                    break ;
                case "c" :
                    carnet.afficher();
                    break;
                case "s":
                    carnet.sauvegarde();
                    break;
                case "d":
                    carnet.chargement();
                    break;
                case "r":
                    carnet.recherche();
                    break;
                case "q" :
                    System.out.println("exit");
                    break;
                default:
                    System.out.println("Erreur de saisie");
                    break;
            }
        }while(!saisie.equals("q"));
        sc.close();
    }
}
