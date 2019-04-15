/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.sun.istack.internal.logging.Logger;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import sun.util.logging.PlatformLogger;
import entity.Document;
import control.DocumentReader;
import control.TermList;
import control.Tokenizer;

/**
 *
 * @author User-pc
 */
public class Weight {
    
    private List<Document> lstDoc;
    private DocumentReader docreader;
    private TermList globaltermlist;
    private ArrayList<double[]> vecspace;
    private int nCluster;
    public double[][] hasilPembobotan;
    
    public Weight(String folderpath){
        docreader = new DocumentReader(folderpath);
        readDocument();
    }
    
    private void readDocument(){
        try{
            docreader.readDocuments();
            lstDoc = docreader.getListDocuments();
            Tokenizer tokenizer = new Tokenizer();
            for(int i=0; i<lstDoc.size(); i++){
                Document doc = lstDoc.get(i);
                tokenizer.tokenize(doc);
                doc.setTermlist(tokenizer.getCurrentTokenList());
                //System.out.println(doc.getJudul() + " = " + Arrays.toString(tokenizer.getCurrentTokenList().toStringArray()) + "\n");
//                System.out.println(doc.getJudul() + " = " + doc.getIsi() + "\n");
            }
            globaltermlist = tokenizer.getTokenList();
            nCluster = docreader.getNumberofCategory();
        }catch(FileNotFoundException ex){
            
        }catch(IOException ex){
            
        }
    }
    
    public int getNumberOfClusterDoc(){
        return nCluster;
    }
    
    public void doPembobotan(){
        double[][] d = new double[globaltermlist.getTotalTerm()][lstDoc.size()];
        hasilPembobotan = new double[globaltermlist.getTotalTerm()][lstDoc.size()];
        for(int i=0; i<lstDoc.size(); i++){
            String sdocs[] = lstDoc.get(i).getTermList().toStringArray();
            System.out.println(lstDoc.get(i).getJudul() + " : " + Arrays.toString(lstDoc.get(i).getTermList().toStringArray()));
            for(int j=0; j<hasilPembobotan.length; j++){
                hasilPembobotan[j][i] = tf(sdocs, globaltermlist.getTermAt(j).getTerm()) * 
                        idf(lstDoc, globaltermlist.getTermAt(j).getTerm());
//                hasilPembobotan[j][i] = tf(sdocs, globaltermlist.getTermAt(j).getTerm());
//                System.out.println(tf(sdocs, globaltermlist.getTermAt(j).getTerm()) + " * " 
//                        + idf(lstDoc, globaltermlist.getTermAt(j).getTerm()) + " = ");
                System.out.println(globaltermlist.getTermAt(j).getTerm()+": "+hasilPembobotan[j][i]+" = d["+j+"]["+i+"]");
            }
            System.out.print("\n");
        }
        System.out.println("-------------Pembobotan selesai--------------");
//            for(int i=0; i<globaltermlist.getTotalTerm(); i++){
//                System.out.println(globaltermlist.getTermAt(i).getTerm());
//            }
    }
    
    
    public double[][] getHasilPembobotan(){
        return hasilPembobotan;
    }
    
    public List<Document> getListDocument(){
        return lstDoc;
    }
    
    public TermList getGlobalTermList(){
        return globaltermlist;
    }
    
    static double tf(String[] doc, String term){
        double n = 0;
        for(String s: doc){
            if(s.equalsIgnoreCase(term)){
                n++;
            }
        }
        return n;
//        return n/doc.length;
    }
    
    static double idf(List<Document> listdoc, String term){
        double n = 0;
//        System.out.println("n = "+listdoc.size());
        for(int i=0; i<listdoc.size(); i++){
            for(int j=0; j<listdoc.get(i).getTermList().getTotalTerm(); j++){
                String s = listdoc.get(i).getTermList().getTermAt(j).getTerm();
                if(s.equalsIgnoreCase(term)){
                    n++;
                    break;
                }
            }
        }
//        System.out.println("df = "+n);
//        System.out.println("idf = "+Math.log10(2));
        return Math.log10(listdoc.size()/n);
    }
}
