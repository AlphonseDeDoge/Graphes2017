/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetgraphes2017;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Nico
 */
public class EcritureFichier {
    private String path;
    private String name;
    private Graphe graphe;
    
    public EcritureFichier(String path,String name,Graphe g) throws IOException{
        this.path=path;
        this.name=name;
        this.graphe = new Graphe(g.getN());
        for(int i=0;i<g.getN();i++){
                this.graphe.setPoint(i, 0, g.getPoint(i, 0));
                this.graphe.setPoint(i, 1, g.getPoint(i, 1));
            }
            
            for(int i=0;i<g.getN();i++)
            {
                for(int j=0;j<g.getN();j++)
                {
                    this.graphe.setAdj(i, j, g.getAdj(i, j));
                }
            }
        this.ecriture(this.graphe);
    }
    
    public void ecriture(Graphe g) throws IOException{
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path+"/"+name))){
            bw.write("c "+this.name+"\n");
            bw.write("p edge "+this.graphe.getN()+" "+this.graphe.getNbArete()+"\n");
            for(int i=0;i<g.getN();i++)
            {
                for(int j=i;j<g.getN();j++)
                {
                    if(g.getAdj(i, j)==1){//adj de g = 1 on marque i et j dans le fichier
                        bw.write("e "+(i+1)+" "+(j+1)+"\n");
                    }
                }
            }
            for(int i=0;i<g.getN();i++){
                bw.write("r "+i+" "+g.getCouleur(i)+"\n");
            }
            
            System.out.println("Ecriture ok");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
