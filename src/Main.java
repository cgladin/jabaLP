import carnetAdresse.Carnet;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String saisie;
        Carnet carnet = new Carnet();
        do{
            System.out.println("\nSaisir\n " +
                    "a pour ajouter une personne\n " +
                    "c pour voir le carnet\n " +
                    "s pour sauvegarder\n " +
                    "l pour charger\n " +
                    "r pour Rechercher\n " +
                    "d pour Test\n " +
                    "q pour quitter");
            saisie = sc.nextLine();
            switch (saisie) {
                case "a" -> carnet.ajoute();
                case "c" -> carnet.afficher();
                case "s" -> carnet.sauvegarde();
                case "l" -> carnet.chargement();
                case "r" -> carnet.selectionRecherche();
                case "d" -> carnet.trieABulle();
                case "q" -> System.out.println("exit");
                default -> System.out.println("Erreur de saisie");
            }
        }while(!saisie.equals("q"));
        sc.close();
    }
}
