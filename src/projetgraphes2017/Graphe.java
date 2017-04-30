/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetgraphes2017;

import java.io.IOException;

/**
 *
 * @author Nico
 */
public class Graphe {
    private int n,proba; //nombre de sommets
    private int[][] adj;
    private int[] couleur1, couleur2, DSAT, Degre;
    private boolean trouve=false;
    private int[][] point;
    
    public Graphe(int nbSommets,int probaAretes){
        setN(nbSommets);
        setProba(probaAretes);
        this.adj=new int[nbSommets][nbSommets];
        this.couleur1=new int[nbSommets];
        this.couleur2=new int[nbSommets];
        this.DSAT=new int[nbSommets];
        this.Degre=new int[nbSommets];
        this.point=new int[nbSommets][2];
    }
    
    public Graphe(int nbSommets){
        setN(nbSommets);
        this.adj=new int[nbSommets][nbSommets];
        this.couleur1=new int[nbSommets];
        this.couleur2=new int[nbSommets];
        this.DSAT=new int[nbSommets];
        this.Degre=new int[nbSommets];
        this.point=new int[nbSommets][2];
    }
    
    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getProba() {
        return proba;
    }

    public void setProba(int proba) {
        this.proba = proba;
    }

    public int getAdj(int i,int j) {
        return adj[i][j];
    }
    
    public void setAdj(int i,int j,int val) {
        this.adj[i][j] = val;
    }

    public void setPoint(int i,int j,int val) {
        this.point[i][j] = val;
    }
    
    public int getPoint(int i,int j) {
        return point[i][j];
    }
    
    public void genere(){
      for(int i=0;i<n;i++){
        point[i][0]=(int)(Math.random()*500+1);
        point[i][1]=(int)(Math.random()*500+1);
        for(int j=i+1;j<n;j++)
            if((int)(Math.random()*100) < this.proba)
                adj[i][j]=adj[j][i]=1;
            else adj[i][j]=adj[j][i]=0;
       }
    }

    void affichegraphe(){
        for(int i=0;i<this.n;i++)
        {
            for(int j=0;j<this.n;j++)
            {
                if(adj[i][j]==0)
                    System.out.print("0 ");
                else
                    System.out.print("1 ");
            }
            System.out.println("");
        }
    }


    boolean convient(int x, int c){
        for(int i=0;i<x;i++)
            if(adj[x][i]==1 && (couleur1[i]==c)) return false;
        return true;
    }


    boolean convient2(int x, int c){
        for(int i=0;i<n;i++)
            if(adj[x][i]==1 && (couleur2[i]==c)) return false;
        return true;
    }

    void colorRR(int x, int k){
        if(x==n){
            System.out.println("Colorisation en "+k+" couleurs trouvée");
            for(int i=0;i<n;i++)
                System.out.println("Couleur de "+i+" : "+couleur1[i]);
            trouve=true;
        }
        else
        for(int c=1;c<=k;c++)
            if(convient(x,c)){
                couleur1[x]=c;
                //System.out.println("couleur de "+x+" : "+couleur1[x]);
                colorRR(x+1,k);
                if(trouve) return;
            }
         //return false;
    }

    void colorexact(int k){
        for(int i=0;i<n;i++)
            couleur1[i]=0;
            colorRR(0,k);
            if(!trouve)
                System.out.println("Pas de colorisation en "+k+"couleurs");
    }

    int dsatMax(){
        int maxDeg=-1,maxDSAT=-1,smax=0;
        for(int i=0;i<n;i++)
        if(couleur2[i]==0 && (DSAT[i]>maxDSAT || (DSAT[i]==maxDSAT && Degre[i]>maxDeg))){
            maxDSAT=DSAT[i]; maxDeg=Degre[i]; smax=i;}
        //System.out.println("Sommet "+smax+" choisi");
        return smax;
    }

    int DSATUR(){
        int nb=0,c,x,cmax=0;
        for(int i=0;i<n;i++){
            couleur2[i]=0; DSAT[i]=0; Degre[i]=0;
            for(int j=0;j<n;j++)
                if(adj[i][j]==1) Degre[i]++;
            DSAT[i]=Degre[i];
        }

        while(nb<n){  // tant qu'on a pas colorié tous les sommets
            c=1;
            x=dsatMax(); // on choisit le sommet de DSAT max non encore colorié
            while(!convient2(x,c)) c++; // on cherche la plus petite couleur disponible pour ce sommet
            for(int j=0; j<n;j++){ // mise à jour des DSAT 
                if(adj[x][j]==1 && convient2(j,c)) DSAT[j]++; // j n'avait aucun voisin colorié c,on incrémente donc son DSAT
                if(DSAT[j]>Degre[j]) DSAT[j]=1;  // j n'avait aucun voisin colorié, son DSAT passe donc à 1
            }
            couleur2[x]=c;
            if(cmax<c) cmax=c;
            nb++;
        }
        System.out.println("DSAT: coloration en "+cmax+"couleurs");
        for(int i=0;i<n;i++)
            System.out.println("couleur de "+i+" : "+couleur2[i]);
        return cmax;
    }

    /*public void main(String[] args){
        int p,k;
        System.out.println("Nombre de sommets: ");
        Scanner S=new Scanner(System.in);
        int n=S.nextInt();
        //System.out.println(n);
        System.out.println("Probabilité d'arête: ");
        S=new Scanner(System.in);
        p=S.nextInt();
        genere();

        for(int i=0;i<n;i++){
            System.out.println("Sommet "+i+":");
            for(int j=0;j<n;j++)
                if(adj[i][j]==1)
                    System.out.println(j+" ");
            System.out.println();
        }

        affichegraphe();

        k=DSATUR();

        do {
            trouve=false;
            colorexact(k);
            k--;
        }while(trouve);
    }*/
}
