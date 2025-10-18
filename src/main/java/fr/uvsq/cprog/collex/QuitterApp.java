package fr.uvsq.cprog.collex;

public class QuitterApp implements Commande{

    public QuitterApp(){
        execute();
    }

    // Implémentation de la méthode de l'interface : 
    @Override
    public void execute(){
        System.out.println(" Merci d'avoir essayer notre programme !! Au revoir !");
        System.exit(0);
    }
}
