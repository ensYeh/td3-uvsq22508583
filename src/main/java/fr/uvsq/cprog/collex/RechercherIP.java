package fr.uvsq.cprog.collex;

public class RechercherIP implements Commande{
    String texteCommande;

    //constructeur 
    public RechercherIP(String texteCommande){
        this.texteCommande = texteCommande;
        execute();
    }

    // implémentation de la méthode execute pour rechercher une adresse IP dans la BDD 
    @Override
    public void execute() {
        Dns dns = new Dns();
        NomMachine nm = new NomMachine(texteCommande);
        DnsItem item = dns.getItem(nm);
        DnsTUI.affiche(item.getAdrIP().toString());
    }
}
