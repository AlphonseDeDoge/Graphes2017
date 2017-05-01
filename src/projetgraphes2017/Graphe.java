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
    //nombre de sommet / probabilité d'arete entre chaque points
    private int n,proba;
    //liste liaisons entre chaque sommet
    private int[][] adj;
    //
    private int[] couleur1, couleur2, DSAT, Degre;
    private boolean trouve=false;
    private int[][] point;
    
    private int nbColor;
    
    
    
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
    
    public int getCouleur(int i){
        return couleur2[i];
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
    
    public void setAdj(int i,int j,int val){
        this.adj[i][j] = val;
    }
    
    public int getPoint(int i,int j) {
        return point[i][j];
    }
    
    public void setPoint(int i,int j,int val){
        this.point[i][j] = val;
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
    
    public void equals(Graphe g1){
       for(int i=0;i<n;i++)
       {
           for(int j=0;j<n;j++)
           {
               this.adj[i][j] = g1.getAdj(i, j);
           }
       }
    }

    //vérifie si les voisins de x on déjà la couleur voulu.
    boolean convient(int x, int c){
        for(int i=0;i<x;i++)
            if(adj[x][i]==1 && (couleur1[i]==c)) return false;
        return true;
    }

    //vérifie si les voisins de x on déjà la couleur voulu.
    boolean convient2(int x, int c){
        for(int i=0;i<n;i++)
            if(adj[x][i]==1 && (couleur2[i]==c)) return false;
        return true;
    }

    void colorRR(int x, int k){
        if(x==n){
            trouve=true;
        }
        else
        for(int c=1;c<=k;c++)
            if(convient(x,c)){
                couleur1[x]=c;
                colorRR(x+1,k);
                if(trouve) return;
            }
    }

    //
    void colorexact(int k){
        for(int i=0;i<n;i++)
            couleur1[i]=0;
            colorRR(0,k);
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
        /*System.out.println("DSAT: coloration en "+cmax+"couleurs");
        for(int i=0;i<n;i++)
            System.out.println("couleur de "+i+" : "+couleur2[i]);*/
        nbColor=cmax;
        return cmax;
    }
    
    int nbPermute(int coul){  
        int x=0;
        for(int i=1;i<coul;i++)
            x+=i;
        return x;
            
    }
        
    void initTabPermute(int[][] tabPermute,int nbper,int colmax){
        if(colmax>=2){
            int c=1,colcrnt=c+1;
            for(int i=0;i<nbper;i++){
                tabPermute[i][0]=c;
                tabPermute[i][1]=colcrnt;
                tabPermute[i][2]=0;
                if(colcrnt==colmax){
                    c++;
                    colcrnt=c+1;
                }
                else
                    colcrnt++;
            }
        }
    }
    
    int[] GLOUTON(int nbcol,int[] couleur){
        int nbper=nbPermute(nbcol);
        int colmax=colmax(couleur);
        
        int[][] tabPermute=new int[nbper][3];
        
        initTabPermute(tabPermute,nbper,colmax);
        
        int[] bestcolor=new int[n];
        for(int i=0;i<n;i++)
            bestcolor[i]=couleur[i];
        
        //vérifie si couleur à permute
        
        int[][] adjtmp=new int[n][n];
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                adjtmp[i][j]=adj[i][j];
        //couleur courante
        for(int i=0;i<adjtmp.length;i++){
            boolean ok=false;
            int j=0;
            while(!ok && j<adjtmp[i].length-1){
                if(adjtmp[i][j]==1){
                    if(couleur[j]==couleur[i]+1){ // possible soucis&&couleur[j]!=colmax
                        ok=true;
                        adjtmp[i][j]=0;
                        adjtmp[j][i]=0;
                    }
                }
                j++;
            }
            j=couleur[i]+1;

            //si permutation il y a besoin
            if(!ok&&j<=colmax){
                if(verifTabPermute(couleur[i],j,tabPermute)){
                    
                    for(int k=0;k<tabPermute.length;k++)
                        if(tabPermute[k][0]==couleur[i] && tabPermute[k][1]==j && tabPermute[k][2]==0)
                            tabPermute[k][2]=1;
                    
                    int[] colperm=Permute(couleur,i,j);
                    
                    ColorGlouton(colperm);
                    if(verifColMax(colperm,bestcolor)){
                        colmax=colmax(colperm);
                        bestcolor=colperm; //non récursivité
                        //bestcolor=GLOUTON(colmax,colperm); //récursivité
                    }
                }
            }
        }        
        return bestcolor;
    }
    
    boolean verifColMax(int[] a,int[] b){
        int amax=0,bmax=0;
        for(int i=0;i<a.length;i++){
            if(a[i]>amax)
                amax=a[i];
            if(b[i]>bmax)
                bmax=b[i];
        }
        if(amax>=bmax)
            return false;
        return true;
    }
    
    int[] Permute(int[] couleur,int c1,int c2){
        int colmax=0;
        int[] coltmp=new int[couleur.length];
        colmax=colmax(couleur);
        int nb=0;
        for(int i=1;i<=colmax;i++){
            for(int j=0;j<coltmp.length;j++){
                if(i==c1){
                    if(couleur[j]==c2){
                        coltmp[nb]=j;
                        nb++;
                    }
                }
                else if(i==c2){
                    if(couleur[j]==c1){
                        coltmp[nb]=j;
                        nb++;
                    }
                }
                else if(couleur[j]==i){
                    coltmp[nb]=j;
                    nb++;   
                }
            }
        }
        return coltmp;
    }
    
    int colmax(int[] t){
        int x=0;
        for(int i=0;i<t.length;i++)
            if(x<t[i])
                x=t[i];
        return x;
    }
    
    void ColorGlouton(int[] tabcol){
        int[] tabp=new int[n];
        for(int i=0;i<n;i++){
            tabp[i]=tabcol[i];
            tabcol[i]=0;
        }
        for(int i=0;i<n;i++){
            int p=tabp[i];
            int c=1,j=0;
            while(j<n){
                if(adj[p][j]==1 && tabcol[j]==c){
                    j=0;
                    c++;
                }
                else
                    j++;
            }
            tabcol[p]=c;
        } 
    }
    
    boolean verifTabPermute(int c1,int c2,int[][] tabp){
        boolean ok=false;
        for(int i=0;i<tabp.length;i++){
            if(tabp[i][0]==c1 && tabp[i][1]==c2 && tabp[i][2]==0){
                ok=true;
            }
        }
        return ok;
    }
    
    int getNbArete(){
        int nb=0;
        for(int i=0;i<adj.length;i++){
            for(int j=i;j<adj[i].length;j++)
                if(adj[i][j]==1)
                    nb++;
        }
        return nb;
    }

    String Algo(){
        //application de DSATUR

        int k=DSATUR();
        
        
        
        int[] colorG=GLOUTON(nbColor,couleur2);
        
        int nbcouleur=colmax(colorG);
        
        if(verifColMax(colorG,couleur2))
            couleur2=colorG;
        
        return "\nColoration :\n    - DSATUR : "+nbColor+"\n    - Glouton : "+nbcouleur;
    }
}
