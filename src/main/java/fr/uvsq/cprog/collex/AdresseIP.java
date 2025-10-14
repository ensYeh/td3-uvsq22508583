package fr.uvsq.cprog.collex;

public class AdresseIP {
    private int adr; 
    private int es;
    private int se;
    private int ip;


    //Constructeur 
    public AdresseIP(int adr, int es, int se, int ip){
        this.adr = adr;
        this.es = es;
        this.se = se;
        this.ip = ip;
    }

    // getters 
    public int getAdr(){
        return adr;
    }

    public int getEs(){
        return es;
    }

    public int getSe(){
        return se;
    }

    public int getIp(){
        return ip;
    }

    // redéfinition de toString : 
     @Override
    public String toString(){
        return ((Integer)adr)+"."+((Integer)es)+"."+((Integer)se)+"."+((Integer)ip);
    }
    

}
