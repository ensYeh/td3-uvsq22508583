package fr.uvsq.cprog.collex;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Dns {
    private String nomFichier;
    private List<String> bddContenu;

    //Constructeur de Dns : 
    //Charge la configuration depuis config.properties, crée un fichier runtime s'il n'existe pas  
    //et charge le contenu de la bdd DNS en mémoire 

    public Dns() {
        Properties props = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.err.println(" le fichier config.properties est introuvable !");
                return;
            }

            props.load(input);
            String resourceFile = props.getProperty("dns.database.filename");

            Path runtimeFile = Path.of("td3-uvsq22508583", "dns_database_runtime.txt");
            this.nomFichier = runtimeFile.toString();

            // Copier depuis resources si besoin
            if (!Files.exists(runtimeFile)) {
                try (InputStream dnsStream = getClass().getClassLoader().getResourceAsStream(resourceFile)) {
                    if (dnsStream != null) {
                        Files.copy(dnsStream, runtimeFile);
                        System.out.println("Copie initiale du fichier vers dns_database_runtime.txt");
                    } else {
                        Files.createFile(runtimeFile);
                        System.out.println("Aucun fichier source trouvé. Création d’un fichier vide 'dns_database_runtime.txt'");
                    }
                }
            }
            this.bddContenu = Files.readAllLines(runtimeFile);


        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier DNS :");
            e.printStackTrace();
        }
    }

    //  Retourne un DnsItem correspondant à une adresse IP donnée : 
    public DnsItem getItem(AdresseIP adrIP){
        NomMachine nomMac = null;
        for(String item : bddContenu){
           if(item.contains(adrIP.toString())){
               String[] parts = item.split(" ");
               String[] nomMachine = parts[1].split("\\.");
               nomMac = new NomMachine(nomMachine[0], nomMachine[1], nomMachine[2]);
               break;
           }
        }
        return new DnsItem(adrIP, nomMac);
    }

    //Retourne un DnsItem correspondant à un nom de machine donné
    public DnsItem getItem(NomMachine nomMac){
        AdresseIP adrIP = null;
        for(String item : bddContenu){
            if(item.contains(nomMac.toString())){
                String[] parts = item.split(" ");
                String[] adresse = parts[0].split("\\.");
                adrIP = new AdresseIP(Integer.parseInt(adresse[0]), Integer.parseInt(adresse[1]), Integer.parseInt(adresse[2]), Integer.parseInt(adresse[3]));
                break;
            }
        }
        return new DnsItem(adrIP, nomMac);
    }

    // Retourne la liste des DnsItem appartenant à un domaine donné trié par @ ip si 
    // l'utilisateur spécifie l'option "-a"

    public List<DnsItem> getItems(String nomDomaine){
    List<DnsItem> listeIPDomaine = new ArrayList<>();

    for(String item : bddContenu){
        String[] parts = item.split(" ");    
        String nomMachine = parts[1];      

        // Vérifie si le nom de machine se termine par le domaine recherché
        if(nomMachine.endsWith("." + nomDomaine)){
            String[] adresse = parts[0].split("\\.");
            String[] machine = nomMachine.split("\\.");
            NomMachine nomMac = new NomMachine(machine[0], machine[1], machine[2]);
            AdresseIP adrIP = new AdresseIP(
                Integer.parseInt(adresse[0]),
                Integer.parseInt(adresse[1]),
                Integer.parseInt(adresse[2]),
                Integer.parseInt(adresse[3])
            );
            listeIPDomaine.add(new DnsItem(adrIP, nomMac));
        }
    }
    return listeIPDomaine;
    }


    //Ajoute une nouvelle entrée DNS (IP + nom de machine) en vérifiant les doublons avant d'ajouter: 
    public String addItem(AdresseIP adrIP, NomMachine nomMac) {
        try {

           for (String line : bddContenu) {
                String[] parts = line.split(" "); 
                String existingIP = parts[0].trim();
                String existingNom = parts[1].trim();

                if (existingIP.equals(adrIP.toString())) {
                    return "Erreur : L'adresse IP existe déjà !";
                } else if (existingNom.equals(nomMac.toString())) {
                    return "Erreur : Le nom de machine existe déjà !";
                }
            }

            // Création d'une nouvelle ligne
            String entry = adrIP.toString() + " " + nomMac.toString();

            // Vérifie que le fichier DNS runtime existe avant d’ajouter la nouvelle entrée
            Path filePath = Path.of(this.nomFichier);
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }

            // Écrire dans le fichier :
            Files.writeString(filePath, entry + System.lineSeparator(), StandardOpenOption.APPEND);
            
            this.bddContenu.add(entry);

            return "Entrée ajoutée avec succès  ";

        } catch (IOException e) {
            e.printStackTrace();
            return "Erreur: Impossible d'écrire dans le fichier : " + e.getMessage();
        }
    }

}
