/* package fr.uvsq.cprog.collex;
import java.util.List;

public class RechercherMachinesDomaine implements Commande{
    String texteCommande;

    // constructeur 
    public RechercherMachinesDomaine(String texteCommande){
        this.texteCommande = texteCommande;
        execute();
    }

    // Implémentation de la méthode execute pour rechercher machine d'un domain : 
    @Override
    public void execute(){
        Dns dns = new Dns();
        List<DnsItem> liste = dns.getItems(texteCommande);
        StringBuilder resultat = new StringBuilder();
        for (DnsItem item : liste) {
            resultat.append(item.toString()).append(System.lineSeparator());
        }
        String res = resultat.toString();
        DnsTUI.affiche(res);
    }
}
*/

package fr.uvsq.cprog.collex;

import java.util.List;
import java.util.Comparator;

public class RechercherMachinesDomaine implements Commande {
    String texteCommande;

    // Constructeur
    public RechercherMachinesDomaine(String texteCommande) {
        this.texteCommande = texteCommande;
        execute();
    }

    @Override
    public void execute() {
        Dns dns = new Dns();

        // Initialisation
        boolean trierParIP = false;
        String nomDomaine = null;

        // Séparer la commande en mots
        String[] parts = texteCommande.trim().split("\\s+");

        for (String part : parts) {
            if (part.equals("ls")) continue;       // ignorer le mot 'ls'
            if (part.equals("-a")) trierParIP = true;  // option -a détectée
            else nomDomaine = part;               // le reste est le domaine
        }

        if (nomDomaine == null) {
            DnsTUI.affiche("Erreur : nom de domaine manquant !");
            return;
        }

        // Récupérer la liste des machines du domaine
        List<DnsItem> liste = dns.getItems(nomDomaine);

        // Trier selon l'option
        if (trierParIP) {
            // Tri par IP
            liste.sort(Comparator.comparing(DnsItem::getAdrIP, (ip1, ip2) -> {
                String[] p1 = ip1.toString().split("\\.");
                String[] p2 = ip2.toString().split("\\.");
                for (int i = 0; i < 4; i++) {
                    int cmp = Integer.parseInt(p1[i]) - Integer.parseInt(p2[i]);
                    if (cmp != 0) return cmp;
                }
                return 0;
            }));
        } else {
            // Tri par nom de machine
            liste.sort(Comparator.comparing(item -> item.getNomMac().toString()));
        }

        // Construire le résultat à afficher
        StringBuilder resultat = new StringBuilder();
        for (DnsItem item : liste) {
            resultat.append(item.toString()).append(System.lineSeparator());
        }

        DnsTUI.affiche(resultat.toString());
    }
}


