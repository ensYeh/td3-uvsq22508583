package fr.uvsq.cprog.collex;

public class NomMachine {
    private String nom;
    private String qualifie;
    private String machine;

    // constructeur classique 
    public NomMachine(String nom, String qualifie, String machine){
        this.nom = nom;
        this.qualifie = qualifie;
        this.machine = machine;
    }

    // Constructeur avec nom complet sous forme nom.qualifie.machine 
    public NomMachine(String fullName) {

        // Vérifie que le nom respecte le format approprié 
        if (!fullName.matches("^[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)+$")) {
            throw new IllegalArgumentException(" Nom de la machine est invalide " + fullName);
        }

        String[] parts = fullName.split("\\."); // séparrer les différente parties avec ''.''
        if (parts.length < 3) {
            throw new IllegalArgumentException(" Le nom de machine doit contenir 3 parties ");
        }

        this.nom = parts[0];
        this.qualifie = parts[1];
        this.machine = parts[2];
    }

    // getters 
    public String getNom(){
        return nom;
    }

    public String getQualifie(){
        return qualifie;
    }

    public String getMachine(){
        return machine;
    }

    // redénition de toString 
    @Override
    public String toString(){
        return nom+"."+qualifie+"."+machine;
    }
}
