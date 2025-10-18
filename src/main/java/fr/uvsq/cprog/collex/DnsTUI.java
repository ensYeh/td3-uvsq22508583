package fr.uvsq.cprog.collex;

import java.util.regex.Pattern;

public class DnsTUI {


    // Analyser le texte saisi par l'utilisateur et retourne la commande correspondante : 
    public static Commande nextCommande(String texteUtilisateur){
        // Expressions réguliéres des pour les différentes saisies : 
        String hostnameRegex = "^(?!\\d+\\.\\d+\\.\\d+\\.\\d+$)([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        String ipv4Regex = "^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)$";
        String domainRegex = "^ls(\\s+-a)?\\s+[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)+$";
        String regexAdd = "^add\\s+((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\s+[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)+$";
        
        // Compilation des expressions régulières pour réutilisation
        Pattern hostPattern = Pattern.compile(hostnameRegex);
        Pattern ipPattern = Pattern.compile(ipv4Regex);
        Pattern domaineRegex = Pattern.compile(domainRegex);
        Pattern regexAjout = Pattern.compile(regexAdd);
        
        if(texteUtilisateur.equals("quit")){
            return new QuitterApp();
        }
        else if (ipPattern.matcher(texteUtilisateur).matches()) {
            return new RechercherNom(texteUtilisateur);

        } else if (hostPattern.matcher(texteUtilisateur).matches()) {
            return new RechercherIP(texteUtilisateur);

        } else if(domaineRegex.matcher(texteUtilisateur).matches()) {
            return new RechercherMachinesDomaine(texteUtilisateur);
        }

        else if(regexAjout.matcher(texteUtilisateur).matches()) {
            return new AjouterItemBD(texteUtilisateur);
        }
        
        else {
            return null;
        }
    }

    // Afficher le résultat dans la console 
    public static void affiche(String result){
        System.out.println(result);
    }
}
