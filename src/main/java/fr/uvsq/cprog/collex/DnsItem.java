package fr.uvsq.cprog.collex;


public class DnsItem {
    private AdresseIP adrIP ; 
    private NomMachine nomMachine;

    // constructeur 
    public DnsItem(){

    }
    
    public DnsItem(AdresseIP adrIP, NomMachine NomMachine){
        this.adrIP = adrIP;
        this.nomMachine = NomMachine;
    }

    //getters 
        public AdresseIP getAdrIP() {
        return adrIP;
    }

    public NomMachine getNomMac(){
        return nomMachine;
    }
}
