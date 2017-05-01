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
    
    
    // comme en terre 
    
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
    
    /*public int[][] colToAdj(String col) throws IOException{
        LectureFichier lf = new LectureFichier(col);
        
        return 
    }*/

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

    //
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
        nbColor=cmax;
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
    
    
    int fact(int n){
        if(n<+1)
            return 1;
        else
            return fact(n-1)*n;
    }
    
    int nbPermute(int coul){
        
        if(coul<2)
            return 0;
        else if(coul==2)
            return 1;
        else
            return fact(2)/(fact(coul)*fact(coul-2));
    }
        
    void initTabPermute(int[][] tabPermute,int nbper,int colmax){
        if(colmax>=2){
            int c=1,colcrnt=c+1;
            for(int i=0;i<nbper;i++){
                if(colcrnt==colmax){
                    c++;
                    colcrnt=c+1;
                }
                tabPermute[i][0]=c;
                tabPermute[i][1]=colcrnt;
                tabPermute[i][2]=0;
                colcrnt++;

            }
        }
    }
    
    int[] GLOUTON(int nbcol,int[] couleur){
        int nbper=nbPermute(nbcol);
        int colmax=colmax(couleur);
        
        int[][] tabPermute=new int[nbper][3];
        
        initTabPermute(tabPermute,nbper,colmax);
        
        int[] bestcolor=couleur;    
        
        //vérifie si couleur à permute
        
        int[][] adjtmp=adj;
        //couleur courante
        for(int i=0;i<adjtmp.length;i++){
            boolean ok=false;
            int j=0;
            //vérifie sur chaque point si au moins 1 point associé est 
            int ccrnt=couleur[i];
            do{
                if(adjtmp[i][j]==1){
                    if(couleur[j]==ccrnt+1&&couleur[j]!=colmax){ // possible soucis
                        ok=true;
                        adjtmp[i][j]=0;
                    }
                }
                j++;
            }while(!ok||j!=adjtmp[i].length);
            //si permutation il y a besoin
            if(!ok&&couleur[j]!=nbcol){
                if(verifTabPermute(i,j,tabPermute)){
                    tabPermute[i][j]=1;
                    int[] colperm=Permute(couleur,i,j);
                    ColorGlouton(colperm,i,j);
                    if(verifColMax(colperm,bestcolor)){
                        colmax=colmax(colperm);
                        bestcolor=GLOUTON(colmax,colperm);
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
    
    //effectue la colorisation du retour d'une permutation de glouton
    void ColorGlouton(int[] tabcol,int indice, int nbcol){
        for(int c=1;c<=nbcol;c++)
            if(convi1(tabcol,indice,c)){
                tabcol[indice]=c;
                ColorGlouton(tabcol,indice+1,nbcol);
                if(trouve) return;
            }
    }
    
    boolean convi1(int[] tabcol,int indice, int coul){
        for(int i=0;i<indice;i++)
            if(adj[indice][i]==1 && (tabcol[i]==coul)) return false;
        return true;
    }
    
    boolean verifTabPermute(int c1,int c2,int[][] tabp){
        for(int i=0;i<tabp.length;i++){
            if(tabp[i][0]==c1&&tabp[i][2]==c2&&tabp[i][2]!=1){
                return true;
            }
        }
        return false;
    }

    void Algo(){
        //application de DSATUR
        int k=DSATUR();
        do {
            trouve=false;
            colorexact(k);
            k--;
        }while(trouve);
        
        int[] colorG=GLOUTON(nbColor,couleur1);
        
        int nbcouleur=colmax(colorG);
    }
}
