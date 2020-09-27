import carnetAdresse.Carnet;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String saisie;
        Carnet carnet = new Carnet();
        do{
            System.out.println("""
                    Saisir
                     a pour ajouter une personne
                     c pour voir le carnet
                     s pour sauvegarder
                     l pour charger
                     r pour Rechercher
                     d pour Test
                     q pour quitter""");
            saisie = sc.next();
            switch (saisie) {
                case "a" -> carnet.ajoute();
                case "c" -> carnet.afficher();
                case "s" -> carnet.sauvegarde();
                case "l" -> carnet.chargement();
                case "r" -> carnet.selectionRecherche();
                case "d" -> carnet.tri();
                case "q" -> System.out.println("exit");
                default -> System.out.println("Erreur de saisie");
            }
        }while(!saisie.equals("q"));
        sc.close();

    }
}

