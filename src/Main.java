import carnetAdresse.Carnet;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String saisie;
        String nom;
        String prenom;
        String adresse;
        String tel;
        Carnet carnet = new Carnet();
        int saisieint;
        do{
            System.out.println("\nSaisir\n " +
                    "a pour ajouter une personne\n " +
                    "c pour voir le carnet\n " +
                    "s pour sauvegarder\n " +
                    "l pour charger\n " +
                    "t pour Test suppression\n " +
                    "q pour quitter");
            saisie = sc.nextLine();
            switch (saisie) {
                case "a" -> {
                    System.out.println("Veuillez saisir le Prenom de la personne");
                    prenom = sc.nextLine();
                    System.out.println("Veuillez saisir le nom de la personne");
                    nom = sc.nextLine();
                    System.out.println("Veuillez saisir l'adresse de la personne");
                    adresse = sc.nextLine();
                    System.out.println("Veuillez saisir le numéro de la personne");
                    tel = sc.nextLine();
                    carnet.ajoutePersonne(nom, prenom, adresse, tel);
                }
                case "c" -> carnet.afficher();
                case "s" -> carnet.sauvegarde();
                case "l" -> carnet.chargement();
                case "r" -> carnet.selectionRecherche();
                //case "d" -> carnet.selectionRecherche();
                case "t" -> {
                    System.out.println("Saisir un index de la personne a supprimer");
                    saisieint = sc.nextInt();
                    carnet.supprimer(saisieint);
                }
                case "q" -> System.out.println("exit");
                default -> System.out.println("Erreur de saisie");
            }
        }while(!saisie.equals("q"));
        sc.close();
    }
}
