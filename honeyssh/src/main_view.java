import com.orsoncharts.plot.PiePlot3D;
import java.awt.Color;
import java.util.*;
import java.io.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.ValueAxis;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;



public class main_view extends javax.swing.JFrame {
    
    static function function_to = new function();
    
    static Set dataSet[];
    static Map <String, command> logMap;
    static Map <String, command> improvedLogMap;
    
    static File logFile;
    static File generalDatasetFile;
    static File networkDatasetFile;
    
    static int totalGeneralCommandsInLogFile;
    static int totalNetworkCommandsInLogFile;
    static int totalOtherCommandsInLogFile;
    
    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    
    static File logFile_without_rubbish;
    static File activeAttackDataSetFile;
    static File passiveAttackDataSetFile;
    
    static int totalActiveAttackCommandsInLogFile;
    static int totalPassiveAttackCommandsInLogFile;
    static int total_UnidentifiedAttack_in_LogFile;
    
    //ACTIVE ATTACKS
    static int total_DOS_in_LogFile = 0;
    static int total_ARPSPOOFING_in_LogFile = 0;
    
    //PASSIVE ATTACKS
    static int total_SCAN_in_LogFile = 0;
    static int total_BRUTEFORCE_in_LogFile = 0;
    static int total_PACKETSNIFFING_in_LogFile = 0;
    
    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    
        
    public main_view() {
        
        initComponents();
        this.setTitle(("HoneySSH"));
        this.setVisible(true);
        
        dataSet = new HashSet[4];
        for(int i = 0 ; i<4 ; i++){
            dataSet[0] = new HashSet<String>(); //general commands data set
            dataSet[1] = new HashSet<String>(); //network commands data set
      
            dataSet[2] = new HashSet<String>(); //active_attack commands data set
            dataSet[3] = new HashSet<String>(); //passive_attack commands data set
        }
        
	logMap  = new HashMap <String, command>();
        improvedLogMap = new HashMap <String, command>();
        
	
	logFile     = new File("log_file.txt");
	generalDatasetFile = new File("general_dataset.txt");
        networkDatasetFile = new File("network_dataset.txt");
        
        totalGeneralCommandsInLogFile = 0;
        totalNetworkCommandsInLogFile = 0;
        totalOtherCommandsInLogFile = 0;
        
        logFile_without_rubbish = new File("logFile_without_rubbish.txt");
        activeAttackDataSetFile = new File("active_attack_dataset.txt");
        passiveAttackDataSetFile = new File("passive_attack_dataset.txt");
        
        totalActiveAttackCommandsInLogFile = 0;
        totalPassiveAttackCommandsInLogFile = 0;
        total_UnidentifiedAttack_in_LogFile = 0;
        
        //ACTIVE ATTACKS
        total_DOS_in_LogFile = 0;
        total_ARPSPOOFING_in_LogFile = 0;
        //-------------------------------------
        //PASSIVE ATTACKS
        total_SCAN_in_LogFile = 0;
        total_BRUTEFORCE_in_LogFile = 0;
        total_PACKETSNIFFING_in_LogFile = 0;
    
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dataSet_btn = new javax.swing.JButton();
        logFile_btn = new javax.swing.JButton();
        pieChart_btn = new javax.swing.JButton();
        top10_btn = new javax.swing.JButton();
        title = new javax.swing.JLabel();
        piechart_btn_active_passive = new javax.swing.JButton();
        piechart_btn_different_attacks = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        dataSet_btn.setText("import data-set");
        dataSet_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataSet_btnActionPerformed(evt);
            }
        });

        logFile_btn.setText("import log-file");
        logFile_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logFile_btnActionPerformed(evt);
            }
        });

        pieChart_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/piechart(1).png"))); // NOI18N
        pieChart_btn.setText("pie chart(general/network)");
        pieChart_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pieChart_btnActionPerformed(evt);
            }
        });

        top10_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/top10(1).png"))); // NOI18N
        top10_btn.setText("top 10     ");
        top10_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                top10_btnActionPerformed(evt);
            }
        });

        title.setFont(new java.awt.Font("Ubuntu", 1, 36)); // NOI18N
        title.setText("honeyssh");

        piechart_btn_active_passive.setIcon(new javax.swing.ImageIcon(getClass().getResource("/piechart(1).png"))); // NOI18N
        piechart_btn_active_passive.setText("pie chart(active/passive)");
        piechart_btn_active_passive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                piechart_btn_active_passiveActionPerformed(evt);
            }
        });

        piechart_btn_different_attacks.setIcon(new javax.swing.ImageIcon(getClass().getResource("/piechart(1).png"))); // NOI18N
        piechart_btn_different_attacks.setText("pie chart(different attacks)");
        piechart_btn_different_attacks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                piechart_btn_different_attacksActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dataSet_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91)
                        .addComponent(logFile_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pieChart_btn)
                            .addComponent(top10_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(piechart_btn_active_passive, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(piechart_btn_different_attacks, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE))))
                .addGap(43, 43, 43))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataSet_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logFile_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pieChart_btn)
                    .addComponent(piechart_btn_active_passive, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(top10_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(piechart_btn_different_attacks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dataSet_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataSet_btnActionPerformed
        try {
            dataSet[0] = function_to.add_keys_to_dataSet(dataSet[0], generalDatasetFile, "GENERAL_DATASET"); //general commands set
            dataSet[1] = function_to.add_keys_to_dataSet(dataSet[1], networkDatasetFile, "NETWORK_DATASET"); //network commands set
            
            dataSet[2] = function_to.add_keys_to_dataSet(dataSet[2], activeAttackDataSetFile, "ACTIVE_DATASET"); //active_attack commands set
            dataSet[3] = function_to.add_keys_to_dataSet(dataSet[3], passiveAttackDataSetFile, "PASSIVE_DATASET"); //passive_attack commands set
            
            //function_to.print_dataSetFile(dataSet);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_dataSet_btnActionPerformed

    private void logFile_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logFile_btnActionPerformed
        try {
            logMap = function_to.add_logs_to_logMap(logMap, logFile, dataSet);
            improvedLogMap = function_to.add_improved_logs_to_logMap(improvedLogMap, logFile_without_rubbish, dataSet);
            //function_to.print_logMapFile(logMap);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_logFile_btnActionPerformed
  
    private void pieChart_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pieChart_btnActionPerformed
        function_to.print_pieChart();
    }//GEN-LAST:event_pieChart_btnActionPerformed
  
    private void top10_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_top10_btnActionPerformed
        logMap = function_to.sortLogMapByFrequency(logMap);
        
        try {
            function_to.print_top10CommandsToFile(logMap);
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_top10_btnActionPerformed

    private void piechart_btn_active_passiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_piechart_btn_active_passiveActionPerformed
        function_to.print_pieChart_for_active_passive();
    }//GEN-LAST:event_piechart_btn_active_passiveActionPerformed

    private void piechart_btn_different_attacksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_piechart_btn_different_attacksActionPerformed
        function_to.print_pieChart_for_different_attacks();
    }//GEN-LAST:event_piechart_btn_different_attacksActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dataSet_btn;
    private javax.swing.JButton logFile_btn;
    private javax.swing.JButton pieChart_btn;
    private javax.swing.JButton piechart_btn_active_passive;
    private javax.swing.JButton piechart_btn_different_attacks;
    private javax.swing.JLabel title;
    private javax.swing.JButton top10_btn;
    // End of variables declaration//GEN-END:variables
}
