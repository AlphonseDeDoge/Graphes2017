package projetgraphes2017;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Interface extends javax.swing.JFrame {

    Graphe graphe;
    int n;
    int p;
    String path;
    Boolean fichierCol=false;
    Boolean init=false;
    String affiche;
    /**
     * Creates new form Interface
     */
    public Interface() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        fenetreGraphe = new javax.swing.JPanel();
        scoreboard = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nbSommets = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        proba = new javax.swing.JTextField();
        generer = new javax.swing.JButton();
        afficherCol = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Board = new javax.swing.JTextArea();
        enregistrer = new javax.swing.JButton();
        parcourir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        fenetreGraphe.setBackground(new java.awt.Color(255, 255, 255));
        fenetreGraphe.setPreferredSize(new java.awt.Dimension(600, 600));

        javax.swing.GroupLayout fenetreGrapheLayout = new javax.swing.GroupLayout(fenetreGraphe);
        fenetreGraphe.setLayout(fenetreGrapheLayout);
        fenetreGrapheLayout.setHorizontalGroup(
            fenetreGrapheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 231, Short.MAX_VALUE)
        );
        fenetreGrapheLayout.setVerticalGroup(
            fenetreGrapheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 362, Short.MAX_VALUE)
        );

        getContentPane().add(fenetreGraphe, java.awt.BorderLayout.CENTER);

        scoreboard.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridLayout(6, 1));

        jLabel1.setText("Nombre de sommets :");
        jPanel1.add(jLabel1);
        jPanel1.add(nbSommets);

        jLabel2.setText("Probabilité d'arete :");
        jPanel1.add(jLabel2);
        jPanel1.add(proba);

        generer.setText("Generer graphe");
        generer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genererActionPerformed(evt);
            }
        });
        jPanel1.add(generer);

        afficherCol.setText("Afficher coloration");
        afficherCol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                afficherColActionPerformed(evt);
            }
        });
        jPanel1.add(afficherCol);

        scoreboard.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.BorderLayout());
        jPanel2.add(jLabel3, java.awt.BorderLayout.SOUTH);

        Board.setEditable(false);
        Board.setColumns(20);
        Board.setRows(5);
        jScrollPane1.setViewportView(Board);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        enregistrer.setText("Enregistrer graphe");
        enregistrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enregistrerActionPerformed(evt);
            }
        });
        jPanel2.add(enregistrer, java.awt.BorderLayout.SOUTH);

        scoreboard.add(jPanel2, java.awt.BorderLayout.LINE_START);

        parcourir.setText("Parcourir...");
        parcourir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parcourirActionPerformed(evt);
            }
        });
        scoreboard.add(parcourir, java.awt.BorderLayout.SOUTH);

        getContentPane().add(scoreboard, java.awt.BorderLayout.LINE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void genererActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genererActionPerformed
        if(this.fichierCol){
            //Recup le path du fichier, convertir le fichier .col en adj et en points
            try {
            LectureFichier lf = new LectureFichier(this.path);
            this.graphe = new Graphe(lf.getNbSommets());
            this.n=lf.getNbSommets();
            for(int i=0;i<lf.getNbSommets();i++){
                this.graphe.setPoint(i, 0, lf.getGraphe().getPoint(i, 0));
                this.graphe.setPoint(i, 1, lf.getGraphe().getPoint(i, 1));
            }
            
            for(int i=0;i<n;i++)
            {
                for(int j=0;j<n;j++)
                {
                    this.graphe.setAdj(i, j, lf.getGraphe().getAdj(i, j));
                }
            }
            
            init = true;
            
        } catch (IOException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }
        repaint();
        }
        else{
            this.n = Integer.parseInt(this.nbSommets.getText());
            this.p = Integer.parseInt(this.proba.getText());
            graphe = new Graphe(n,p);
            graphe.genere();
            init=true;
        }
        repaint(); 
        
        this.affiche="";
        this.affiche="INFORMATION GRAPHE :\n";
        this.affiche+="nombre de sommet : "+n+"\n";
        this.affiche+="nombre d'arête : "+graphe.getNbArete()+"\n";
        this.Board.setText(affiche);
    }//GEN-LAST:event_genererActionPerformed

    private void parcourirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parcourirActionPerformed
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter fnef=new FileNameExtensionFilter("colonne","col","Résultat","res");
        fc.setFileFilter(fnef);
        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            this.path=f.getAbsolutePath();
            System.out.println("fichier : "+f.getPath());
        }
        this.fichierCol=true;
    }//GEN-LAST:event_parcourirActionPerformed

    private void afficherColActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_afficherColActionPerformed
        this.affiche="";
        this.affiche="INFORMATION GRAPHE :\n";
        this.affiche+="nombre de sommet : "+n+"\n";
        this.affiche+="nombre d'arête : "+graphe.getNbArete()+"\n";
        affiche+=graphe.Algo()+"\n";
        affiche+="\n";
        for(int i=0;i<n;i++)
            affiche+="      point "+i+" : "+graphe.getCouleur(i)+"\n";
        this.Board.setText(affiche);
    }//GEN-LAST:event_afficherColActionPerformed

    private void enregistrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enregistrerActionPerformed
        try {
            EcritureFichier ef = new EcritureFichier("G:/Users/Nico/Documents/NetBeansProjects/ProjetGraphes2017/src","test.res",this.graphe);
        } catch (IOException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_enregistrerActionPerformed
    
    public void drawCenteredCircle(Graphics g,int x,int y,int radius){
        g.fillOval(x-radius, y-radius, radius*2, radius*2);
    }
   
    
    @Override
    public void paint(Graphics g)
    {
        if(init){
        super.paint(g);
        g.setColor(Color.BLACK);
        g.drawRect(50, 50 , 500, 500); //Permet de voir le cadre du dessin
        g.setColor(Color.RED);
        for(int i=0;i<n;i++)
        {
            drawCenteredCircle(g,graphe.getPoint(i, 0)+50,graphe.getPoint(i, 1)+50,5);
        }
        g.setColor(Color.BLACK);
        for(int i=0;i<n;i++)
        {
            for(int j=i+1;j<n;j++)
            {
                if(graphe.getAdj(i, j)==1){
                    g.drawLine(graphe.getPoint(i, 0)+50,graphe.getPoint(i, 1)+50, graphe.getPoint(j, 0)+50, graphe.getPoint(j, 1)+50);   
                }
            }
        }
        }
        else{
            super.paint(g);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Board;
    private javax.swing.JButton afficherCol;
    private javax.swing.JButton enregistrer;
    private javax.swing.JPanel fenetreGraphe;
    private javax.swing.JButton generer;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nbSommets;
    private javax.swing.JButton parcourir;
    private javax.swing.JTextField proba;
    private javax.swing.JPanel scoreboard;
    // End of variables declaration//GEN-END:variables
}
