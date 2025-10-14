package fr.uvsq.cprog.collex;

import java.util.ArrayList;
import java.util.List;

import fr.uvsq.cprog.collex.AdresseIP;
import fr.uvsq.cprog.collex.DnsItem;
import fr.uvsq.cprog.collex.NomMachine;

public class Dns{
    // constructeur qui chargera d la BDD : 
    public Dns (){

    }

    // méthodes 
    public DnsItem getItems(NomMachine NomMachine){
        return new DnsItem();
    }

    public DnsItem getItemps(AdresseIP adrIP){
        return new DnsItem();
    }

    // méthodes 
    public List<DnsItem> getItems( String domaine){
        return new ArrayList<DnsItem>();
    }

    // méthodes 
    public void addItem(AdresseIP adresse, NomMachine machine){

    }


    
}
