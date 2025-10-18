package fr.uvsq.cprog.collex;

public class RechercherNom implements Commande {
    String texteCommande;

    public RechercherNom(String texteCommande) {
        this.texteCommande = texteCommande;
        execute();
    }

    @Override
    public void execute() {
        Dns dns = new Dns();
        AdresseIP ip = new AdresseIP(texteCommande);

        DnsItem item = dns.getItem(ip);

        if (item == null || item.getNomMac() == null) {
            DnsTUI.affiche("Aucune entrée trouvée pour l adresse IP : " + texteCommande);
            return;
        }

        // L'IP existe dans la base
        DnsTUI.affiche(item.getNomMac().toString());
    }
}

