/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetgraphes2017;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nico
 */
public class Interface extends javax.swing.JFrame {

    Graphe graphe;
    int n;
    int p;
    Boolean init=false;
    /**
     * Creates new form Interface
     */
    public Interface() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        parcourir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        fenetreGraphe.setBackground(new java.awt.Color(255, 255, 255));
        fenetreGraphe.setPreferredSize(new java.awt.Dimension(600, 600));

        javax.swing.GroupLayout fenetreGrapheLayout = new javax.swing.GroupLayout(fenetreGraphe);
        fenetreGraphe.setLayout(fenetreGrapheLayout);
        fenetreGrapheLayout.setHorizontalGroup(
            fenetreGrapheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 333, Short.MAX_VALUE)
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
        jPanel1.add(afficherCol);

        scoreboard.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.BorderLayout());
        jPanel2.add(jLabel3, java.awt.BorderLayout.SOUTH);

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
        this.n = Integer.parseInt(this.nbSommets.getText());
        this.p = Integer.parseInt(this.proba.getText());
        graphe = new Graphe(n,p);
        graphe.genere();
        init=true;
        repaint(); //pas sûr que ce soit vraiment ici
    }//GEN-LAST:event_genererActionPerformed

    private void parcourirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parcourirActionPerformed
        try {
            
            LectureFichier lf = new LectureFichier("src/queen10.col");
            graphe = new Graphe(lf.getNbSommets());
            for(int i=0;i<lf.getNbSommets();i++)
            {
                for(int j=i+1;j<lf.getNbSommets();j++)
                {
                    graphe.setAdj(i, j, lf.getAdj(i, j));
                    
                }
            }  
        } catch (IOException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }
        init=true;
        repaint();
    }//GEN-LAST:event_parcourirActionPerformed
    
    public void drawCenteredCircle(Graphics g,int x,int y,int radius){
        g.fillOval(x-radius, y-radius, radius*2, radius*2);
    }
   
    
    @Override
    public void paint(Graphics g)
    {
        if(init){
        super.paint(g);
        g.setColor(Color.BLACK);
        for(int i=0;i<n;i++)
        {
            drawCenteredCircle(g,graphe.getPoint(i, 0)+50,graphe.getPoint(i, 1)+50,5);
        }
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
    private javax.swing.JButton afficherCol;
    private javax.swing.JPanel fenetreGraphe;
    private javax.swing.JButton generer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField nbSommets;
    private javax.swing.JButton parcourir;
    private javax.swing.JTextField proba;
    private javax.swing.JPanel scoreboard;
    // End of variables declaration//GEN-END:variables
}
