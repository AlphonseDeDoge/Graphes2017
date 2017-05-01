/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetgraphes2017;

import java.io.*;
import java.lang.*;

/**
 *
 * @author Nico
 */
public class LectureFichier {
    
    private Graphe graphe;
    private String file;
    private int nbSommets;
    private int nbAretes;
    private int[][] tab;
    
    public LectureFichier(String file) throws IOException{
        
        this.setFile(file);
        this.setNbAretes(recupNbAretes());
        this.setNbSommets(recupNbSommets());
        this.tab = new int[this.nbAretes][2];
        this.remplirTab(tab);
        /*for(int i=0;i<nbAretes;i++)
        {
            for(int j=0;j<2;j++)
            {
                System.out.print(tab[i][j]+" ");
            }
            System.out.println("");
        }
        
        System.out.println("nb Aretes"+this.nbAretes);*/
        
        this.colToGraphe();
        
    }
    
    public void colToGraphe(){
        graphe = new Graphe(this.nbSommets);
        for(int i=0;i<this.nbAretes;i++){
            graphe.setAdj(this.tab[i][0], this.tab[i][1], 1);
        }
          
        //affichage adj
        /*for(int i=0;i<nbSommets;i++)
        {
            for(int j=0;j<nbSommets;j++)
            {
                System.out.print(graphe.getAdj(i, j)+" ");
            }
            System.out.println("");
        }*/
        
        for(int i=0;i<this.nbSommets;i++){
            this.graphe.setPoint(i, 0, (int)(Math.random()*500+1));
            this.graphe.setPoint(i, 1, (int)(Math.random()*500+1));
        }
        
        /*for(int i=0;i<nbSommets;i++)
        {
            for(int j=0;j<2;j++)
            {
                System.out.print(graphe.getPoint(i, j)+" ");
            }
            System.out.println("");
        }   */
    }

    public Graphe getGraphe() {
        return graphe;
    }
    
    public void remplirTab(int[][] tab) throws FileNotFoundException, IOException{
        
        int i=0;
        Boolean vide = false;
        String ligne;
        String[] colonne = new String[100];
        BufferedReader br = new BufferedReader(new FileReader(this.getFile()));
        
        
        while(vide==false){
            
            ligne = br.readLine();
            if(ligne==null){
                vide = true;
            }
            else
            {
                colonne = ligne.split(" ");
                if(colonne[0].equals("e")){
                    tab[i][0]=Integer.parseInt(colonne[1])-1;
                    tab[i][1]=Integer.parseInt(colonne[2])-1;
                    i++;
                }
            }
        }            
    }
    
    public int recupNbSommets() throws FileNotFoundException, IOException{
        
        Boolean vide = false;
        String ligne;
        String[] colonne = new String[100];
        BufferedReader br = new BufferedReader(new FileReader(this.getFile()));
        
        while(vide==false){
            
            ligne = br.readLine();
            if(ligne==null){
                vide = true;
            }
            else
            {
                colonne = ligne.split(" ");
                if(colonne[0].equals("p")){
                    return Integer.parseInt(colonne[2]);
                }
            }
        }
        
        return -1;
    }
    
    public int recupNbAretes() throws FileNotFoundException, IOException{
        
        Boolean vide = false;
        String ligne;
        String[] colonne = new String[100];
        BufferedReader br = new BufferedReader(new FileReader(this.getFile()));
        
        while(vide==false){
            
            ligne = br.readLine();
            if(ligne==null){
                vide = true;
            }
            else
            {
                colonne = ligne.split(" ");
                if(colonne[0].equals("p")){
                    return Integer.parseInt(colonne[3]);
                }
            }
        }
        
        return -1;
    }
    
    
    
    public void setFile(String s){
        this.file=s;
    }
    
    public void setNbSommets(int nbSommets) {
        this.nbSommets = nbSommets;
    }

    public void setNbAretes(int nbAretes) {
        this.nbAretes = nbAretes;
    }
    
    public String getFile(){
        return this.file;
    }
    
    public int[][] getTab(){
        return this.tab;
    }
    
    public int getNbSommets() {
        return nbSommets;
    }

    public int getNbAretes() {
        return nbAretes;
    }
    
}
