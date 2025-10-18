package fr.uvsq.cprog.collex;

public class DnsItem {
    private AdresseIP adresseIP;
    private NomMachine nomMac;

    //constructeur 
    public DnsItem(AdresseIP adresseIP, NomMachine nomMac){
        this.adresseIP = adresseIP;
        this.nomMac = nomMac;
    }

    //getters 
    public AdresseIP getAdrIP() {
        return adresseIP;
    }

    public NomMachine getNomMac(){
        return nomMac;
    }

    //redéfinition de toString : 
    @Override
    public String toString(){
        return adresseIP.toString()+" "+nomMac.toString();
    }
}
