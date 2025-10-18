package fr.uvsq.cprog.collex;

import java.util.Scanner;

public class DnsApp {

    public static void run(String arg){
        DnsTUI.nextCommande(arg);
    }

    public static void main(String [] args){
        Scanner sc = new Scanner(System.in);
        // Menu afficher les différentes commandes proposée par le programme : 
        System.out.println("                 *********************************");
        System.out.println("                         Gestionnaire DNS");
        System.out.println("                 *********************************");
        System.out.println("          Notre programme propose les traitements suivants :");
        System.out.println("          - Rechercher l adresse IP d un nom de machine");
        System.out.println("          - Rechercher le nom de machine associé à une adresse IP");
        System.out.println("          - Lister les machines d un domaine (option -a pour trier par IP)");
        System.out.println("          - Ajouter une nouvelle entrée DNS");
        System.out.println("          - Tapez [quit] pour quitter le programme");
        System.out.println();
        System.out.println("    Veuillez saisir vos commandes ) :");

        while (true) {
            System.out.print("> "); // l'invite de commande 
            String str = sc.nextLine().trim();

            if (str.equalsIgnoreCase("quit")) {

                QuitterApp a = new QuitterApp();
                break; 
            }

            try {
                run(str); // exécution de la commande 
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Une erreur est survenue : " + e.getMessage());
            }
        }

        sc.close();
    }
}

