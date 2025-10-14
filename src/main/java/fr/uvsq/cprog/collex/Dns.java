package fr.uvsq.cprog.collex;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Dns {
    private String fichiername;
    private List<String> bddCont;

    public Dns(){
        //Constructeur pour charger la bdd depuis le fichier "BDD du DNS"
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            props.load(fis);
            this.fichiername = props.getProperty("dns.database.nomfichier");
            System.out.println(" le Fichier de la BDD DNS chargé avec succès  : " + fichiername);
            this.bddCont = Files.readAllLines(Path.of(this.fichiername));
        } catch (IOException e) {
            System.err.println("Impossible de charger le fichier de propriétés erreur !! : " + e.getMessage());
        }
    }

    public DnsItem getItem(AdresseIP adrIP){
        //return :  une instance de DnsItem à partir d'une adresseIP
        NomMachine nomMac = null;
        for(String item : bddCont){
           if(item.contains(adrIP.toString())){
               String[] parts = item.split(" ");
               String[] nomMachine = parts[0].split("\\.");
               nomMac = new NomMachine(nomMachine[0], nomMachine[1], nomMachine[2]);
               break;
           }
        }
        return new DnsItem(adrIP, nomMac);
    }

    public DnsItem getItem(NomMachine nomMac){
        //return  : une instance de DnsItem à partir d'un nom de machine
        AdresseIP adrIP = null;
        for(String item : bddCont){
            if(item.contains(nomMac.toString())){
                String[] parts = item.split(" ");
                String[] adresse = parts[1].split("\\.");
                adrIP = new AdresseIP(Integer.parseInt(adresse[0]), Integer.parseInt(adresse[1]), Integer.parseInt(adresse[2]), Integer.parseInt(adresse[3]));
                break;
            }
        }
        return new DnsItem(adrIP, nomMac);
    }

    public List<DnsItem> getItems(String nomDomaine){
        //Return :  une liste d'instances de DnsItem à partir d'un nom de domaine
        List<DnsItem> listeIPDomaine = new ArrayList<DnsItem>();
        for(String item : bddCont){
            if(item.contains(nomDomaine+" ")){
                String[] parts = item.split(" ");
                String[] machine = parts[0].split("\\.");
                String[] adresse = parts[1].split("\\.");
                NomMachine nomMac = new NomMachine(machine[0], machine[1], machine[2]);
                AdresseIP adrIP = new AdresseIP(Integer.parseInt(adresse[0]), Integer.parseInt(adresse[1]), Integer.parseInt(adresse[2]), Integer.parseInt(adresse[3]));
                listeIPDomaine.add(new DnsItem(adrIP, nomMac));
            }
        }
        return listeIPDomaine;
    }

    public void addItem(AdresseIP adrIP, NomMachine nomMac) throws IOException {
        //Ajout d'une ligne dans le fichier de la bdd au Dns 
        int i = 0;
        while(i<bddCont.size()){
            if(bddCont.get(i).contains(nomMac.toString()+" ")){
                break;
            }
            i++;
        }
        if(i==bddCont.size()){
            List<String> dnsItem = new ArrayList<String>();
            dnsItem.add(nomMac.toString()+" "+adrIP.toString());
            Files.write(Path.of(this.fichiername), dnsItem, StandardOpenOption.APPEND);
        }
    }
}
