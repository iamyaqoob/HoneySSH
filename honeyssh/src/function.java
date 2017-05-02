import java.awt.Color;
import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Map.Entry;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class function extends main_view{
    
    BufferedReader br;
    Iterator it;
    
    public Set add_keys_to_dataSet (Set dataSet, File dataSetFile, String dataSetType) throws Exception{
		String s = "";
		br = new BufferedReader(new FileReader(dataSetFile));
                
                if(dataSetType.equals("GENERAL_DATASET")) {
                    while((s = br.readLine()) != null) {
                        if(s.equals("")){
                            continue;
                        }
                        else{
                            dataSet.add(s);
                        }
                    }
                }
                else if(dataSetType.equals("NETWORK_DATASET")) {
                    while((s = br.readLine()) != null) {
                        if(s.equals("")){
                            continue;
                        }
                        else{
                            dataSet.add(s);
                        }
                    }
                }
                else if(dataSetType.equals("ACTIVE_DATASET")) {
                    while((s = br.readLine()) != null) {
                        if(s.equals("")){
                            continue;
                        }
                        else{
                            dataSet.add(s);
                        }
                    }
                }
		else if(dataSetType.equals("PASSIVE_DATASET")) {
                    while((s = br.readLine()) != null) {
                        if(s.equals("")){
                            continue;
                        }
                        else{
                            dataSet.add(s);
                        }
                    }
                }
		
                //System.out.println(dataSet);
                //System.out.println(dataSet[3]);
                
		br.close();
		return dataSet;
    }
    
    public Map<String, command> add_logs_to_logMap (Map <String, command> logMap, File logFile, Set dataSet[]) throws Exception{
		String s = "";
		br = new BufferedReader(new FileReader(logFile));
		while((s = br.readLine()) != null) {
                        String blocks[] = s.split(" ");
			if(dataSet[0].contains(blocks[0])) {   
				if(logMap.containsKey(s)){
					logMap.get(s).frequency++;
				}
				else {
                                        logMap.put(s, new command("GENERAL", 1));                                        
				}                               
                                totalGeneralCommandsInLogFile++;
			}
                        
                        else if(dataSet[1].contains(s)) {
				if(logMap.containsKey(s)) {
					logMap.get(s).frequency++;
				}
				else {
                                    logMap.put(s, new command("NETWORK", 1));                                        
                                }
                                
                                totalNetworkCommandsInLogFile++;
                        
			}
                        
                        else {
                            totalOtherCommandsInLogFile++;
                        }
		} 
		br.close();
		return logMap;
    }
   
    Map<String, command> add_improved_logs_to_logMap (Map <String, command> improvedLogMap, File logFile_without_rubbish, Set dataSet[]) throws Exception{
                String s = "";
		br = new BufferedReader(new FileReader(logFile_without_rubbish));
		while((s = br.readLine()) != null) {
                        String blocks[] = s.split(" ");
                        //System.out.println(s);
                        //System.out.println(blocks[0]);
			if(dataSet[2].contains(blocks[0])) {
                                //System.out.println("present in netowk");
				if(improvedLogMap.containsKey(s)){
					improvedLogMap.get(s).frequency++;
				}
				else {
                                        improvedLogMap.put(s, new command("ACTIVE", 1));                                        
				}                               
                                
                                totalActiveAttackCommandsInLogFile++;
                                
                                //DOS_ATTACK
                                if(s.indexOf("ping")!= -1){
                                    //System.out.println("dos --> "+ blocks[blocks.length-1]);
                                    int sixty5k = Integer.parseInt(blocks[blocks.length-1]);
                                    if(sixty5k > 65500){
                                        total_DOS_in_LogFile++;
                                    }
                                }
                                
                                //ARP_SPOOFING
                                if(s.indexOf("arpspoof")!= -1){
                                    //System.out.println("ARPSPOOOOOF");
                                    total_ARPSPOOFING_in_LogFile++;
                                }
			}
                        
                        else if(dataSet[3].contains((blocks[0]))) {
                                //System.out.println("--------------------------");
				if(improvedLogMap.containsKey(s)) {
					improvedLogMap.get(s).frequency++;
				}
				else {
                                    improvedLogMap.put(s, new command("PASSIVE", 1));                                        
                                }
                                
                                totalPassiveAttackCommandsInLogFile++;
                                
                                //SCAN_ATTACK
                                if(s.indexOf("nmap")!= -1){
                                    //System.out.println("NMAAAPPPPP");
                                    total_SCAN_in_LogFile++;
                                }
                                
                                //BRUTE_FORCE
                                if(s.indexOf("select")!= -1){
                                    //System.out.println("SELECTTTTTTTTTTTTT");
                                    total_BRUTEFORCE_in_LogFile++;
                                }
                                
                                //PACKET_SNIFFING
                                if(s.indexOf("tcpdump")!= -1){
                                    //System.out.println("TCPDUMPPPPPPP");
                                    total_PACKETSNIFFING_in_LogFile++;
                                }
			}
                        else{
                            total_UnidentifiedAttack_in_LogFile++;
                        }
                }
                br.close();
		return improvedLogMap;
    } 
    
    public void print_logMapFile (Map <String, command> logMap) throws Exception {
		File outFile_for_logMap = new File("processedLogMap.txt");
		FileOutputStream fos = new FileOutputStream(outFile_for_logMap);
                PrintWriter pw = new PrintWriter(fos);
        
		for (Map.Entry<String, command> entry : logMap.entrySet()) {
                    String key = entry.getKey();
                    int value = entry.getValue().frequency;
                    String type = entry.getValue().type;
                    
                    pw.write(key+ " -> "+ value + " -> " + type);
                    pw.println();
                }
                
                pw.flush();
                fos.close();
    }
    
    public void print_dataSetFile (Set dataSet[]) throws Exception {
		File outFile_for_logMap = new File("processedDataSet.txt");
		FileOutputStream fos = new FileOutputStream(outFile_for_logMap);
                PrintWriter pw = new PrintWriter(fos);
                
                pw.write("[ GENERAL_DATASET ] \n");
                pw.println();
		it = dataSet[0].iterator();
                while(it.hasNext()) {
                    pw.write(it.next() + "" + "\n");
                   // pw.println();
                }
                
                pw.write("\n------------------------------------- \n");
                pw.println();
                
                pw.write("[ NETWORK_DATASET ] \n");
                pw.println();   
                it = dataSet[1].iterator();
                while(it.hasNext()) {
                    pw.write(it.next() + "" + "\n");
                    //pw.println();
                }
                /*
                pw.write("\n------------------------------------- \n");
                pw.println();
                
                pw.write("[ HACKING_DATASET ] \n");
                pw.println();   
                it = dataSet[2].iterator();
                while(it.hasNext()) {
                    pw.write(it.next() + "");
                    pw.println();
                }
                */
                pw.flush();
                fos.close();
    }
    
    public void print_pieChart() {
        
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        
        //System.out.println("totalGeneralCommandsInLogFile "+ totalGeneralCommandsInLogFile);
        //System.out.println("totalNetworkCommandsInLogFile "+totalNetworkCommandsInLogFile);
        //System.out.println("totalOtherCommandsInLogFile "+totalOtherCommandsInLogFile);
        
        pieDataset.setValue("GENERAL_COMMANDS", totalGeneralCommandsInLogFile);
        pieDataset.setValue("NETWORK_COMMANDS", totalNetworkCommandsInLogFile);
        pieDataset.setValue("OTHER_COMMANDS", totalOtherCommandsInLogFile);
        
        JFreeChart chart = ChartFactory.createPieChart("Pie Chart for general/network", pieDataset, true, true, true);
        ChartFrame frame = new ChartFrame("Pie Chart for general/network", chart);
        
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0} {2}"));
        
        frame.setVisible(true);
        frame.setSize(700, 500);
        
        try{
            //function_to.savePieChartasPNGFile(chart);
        }   
        catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void print_pieChart_for_active_passive() {
        
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        
        //System.out.println("totalActiveAttackCommandsInLogFile "+ totalActiveAttackCommandsInLogFile);
        //System.out.println("totalPassiveAttackCommandsInLogFile "+totalPassiveAttackCommandsInLogFile);
        
        pieDataset.setValue("ACTIVE", totalActiveAttackCommandsInLogFile);
        pieDataset.setValue("PASSIVE", totalPassiveAttackCommandsInLogFile);
        
        JFreeChart chart = ChartFactory.createPieChart("Pie Chart for active-passive", pieDataset, true, true, true);
        ChartFrame frame = new ChartFrame("Pie Chart for active-passive", chart);
        
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0} {2}"));
        
        frame.setVisible(true);
        frame.setSize(700, 500);
        
        try{
            function_to.savePieChartasPNGFile(chart);
        }   
        catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void print_pieChart_for_different_attacks() {
        
        DefaultPieDataset pieDataset = new DefaultPieDataset();
       
        //System.out.println("ACTIVE:");
        //System.out.println("total_DOS_in_LogFile "+ total_DOS_in_LogFile);
        //System.out.println("total_ARPSPOOFING_in_LogFile "+total_ARPSPOOFING_in_LogFile);
        
        //System.out.println("PASSIVE:");
        //System.out.println("total_SCAN_in_LogFile "+ total_SCAN_in_LogFile);
        //System.out.println("total_BRUTEFORCE_in_LogFile "+total_BRUTEFORCE_in_LogFile);
        //System.out.println("total_PACKETSNIFFING_in_LogFile "+total_PACKETSNIFFING_in_LogFile);
        
        pieDataset.setValue("DOS (ACTIVE)", total_DOS_in_LogFile);
        pieDataset.setValue("ARP SPOOFING (ACTIVE)", total_ARPSPOOFING_in_LogFile);
        
        pieDataset.setValue("SCAN (PASSIVE)", total_SCAN_in_LogFile);
        pieDataset.setValue("BRUTE FORCE (PASSIVE)", total_BRUTEFORCE_in_LogFile);
        pieDataset.setValue("PACKET SNIFFING (PASSIVE)", total_PACKETSNIFFING_in_LogFile);
        
        pieDataset.setValue("UNIDENTIFIED", total_UnidentifiedAttack_in_LogFile);
        
        JFreeChart chart = ChartFactory.createPieChart("Pie Chart for different attacks", pieDataset, true, true, true);
        ChartFrame frame = new ChartFrame("Pie Chart for different attacks", chart);
        
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0} {2}"));
        
        frame.setVisible(true);
        frame.setSize(700, 500);
        
        try{
            function_to.savePieChartasPNGFile(chart);
        }   
        catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void savePieChartasPNGFile(JFreeChart chart) throws Exception{
        ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
        File pngFile = new File("Pie-Chart.png");
        ChartUtilities.saveChartAsPNG(pngFile, chart, 700, 500, info);
    }
    
    public void print_barChart() {
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        int count = 0;
        for (Map.Entry<String, command> entry : logMap.entrySet()) {
            String command = entry.getKey();
            int freq = entry.getValue().frequency;
            String type = entry.getValue().type;
            
            barDataset.setValue(freq, type, command);
            count++;
        }
        
        //System.out.println("total commands in barchat: " + count);
        
        JFreeChart chart = ChartFactory.createBarChart("Frquency Bar Graph", "Command", "Frequency", barDataset,
                                                      PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRangeGridlinePaint(Color.black);   
        
        NumberAxis yAxisRange = (NumberAxis) plot.getRangeAxis(); //setting RANGE of Y-AXIS
        yAxisRange.setRange(0, 10);
        
        BarRenderer barRenderer = (BarRenderer) plot.getRenderer();
        //barRenderer.setMaximumBarWidth(.02); // set maximum width to 2% of chart
        //barRenderer.setSeriesPaint(0, Color.ORANGE);
        
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);  //making commands in y axis print vertical
         
        ChartFrame frame = new ChartFrame("Frquency Bar Graph", chart);
        frame.setVisible(true);
        frame.setSize(1000, 500);
         
        try{
            //function_to.saveBarChartasPNGFile(chart);
        }   
        catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void saveBarChartasPNGFile(JFreeChart chart) throws Exception{
        ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
        File pngFile = new File("Bar-Chart.png");
        ChartUtilities.saveChartAsPNG(pngFile, chart, 1000, 500, info);
    }
    
    public <K, V extends Comparable<? super V>> Map<String, command> sortLogMapByFrequency( Map<String, command> map ) {
        List<Map.Entry<String, command>> list =
        new LinkedList<Map.Entry<String, command>>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<String, command>>() {
            public int compare( Map.Entry<String, command> o1, Map.Entry<String, command> o2 ) {
                if (o1.getValue().frequency > o2.getValue().frequency) {
                  return -1;
                } 
                else {
                     return 0;
                }
            }
        } );

        Map<String, command> result = new LinkedHashMap<String, command>();
        for (Map.Entry<String, command> entry : list ) {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }
    
    public void print_top10CommandsToFile(Map<String, command> logMap) throws Exception{
        File outFile_for_logMap = new File("TOP10_COMMANDS.txt");
        FileOutputStream fos = new FileOutputStream(outFile_for_logMap);
        PrintWriter pw = new PrintWriter(fos);
                
        Iterator<Entry<String, command>> iterator = logMap.entrySet().iterator();
        Map.Entry<String, command> entry;
                
        int i = 1;
                
	while(iterator.hasNext() && i <= 10) {
            entry = (Map.Entry<String, command>) iterator.next();
            String key = entry.getKey();
            int value = entry.getValue().frequency;
            String type = entry.getValue().type;
            
            int distance = (i+"").length();
            
            pw.write(i + ": ");
             while(distance != 2){
                pw.write(" ");
                distance++;
            }
            
            distance = key.length();
                    
            pw.write("[Command: \"" + key +"\"");
            while(distance != 15){
                pw.write(" ");
                distance++;
            }
            
            key = value + "";
            distance = key.length();
            
            pw.write(" Freqency: "+ value);
            while(distance != 5){
                pw.write(" ");
                distance++;
            }
            
            pw.write(" Type: " + type + " ]");
            
            pw.println();
            i++;
        }
           
        pw.flush();
        fos.close();
    }
    
     public static boolean isValidIP(String ipAddr){
         
        Pattern ptn = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
        Matcher mtch = ptn.matcher(ipAddr);
        return mtch.find();
    }
     
}

