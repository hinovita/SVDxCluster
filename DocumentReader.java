/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import entity.Document;

/**
 *
 * @author User-pc
 */
public class DocumentReader {
    private String folderpath;
    private List<Document> lstDoc;
    private int nCategory;
    
    public DocumentReader(String folderpath){
        this.folderpath = folderpath;
    }
    
    public int getNumberofCategory(){
        return nCategory;
    }
    
    public void readDocuments() throws FileNotFoundException, IOException{
        lstDoc = new ArrayList<>();
        File folders = new File(folderpath);
        System.out.println(folderpath);
        nCategory = folders.list().length;
        String[] folderNames = new String[folders.list().length];
        folderNames = folders.list();
        for (String folderName : folderNames) {
            System.out.println(folderName);
            System.out.println(folders);
            File folder = new File(folders + "\\" + folderName + "\\");
            List<File> files = Arrays.asList(folder.listFiles(new FileFilter(){
                @Override
                public boolean accept(File f){
                    return f.isFile() && f.getName().endsWith(".txt");
                }
            }));
            if (files != null){
                BufferedReader in = null;
                int id=0;
                for (File f : files) {
                    in = new BufferedReader(new FileReader(f));
                    //System.out.println(f.getAbsolutePath());
                    String isidokumen = new String();
                    String s = null;
                    while((s = in.readLine()) != null){
                        isidokumen = isidokumen + (s+"\n");
                    }
                    id++;
                    System.out.println(f.getAbsolutePath());
//                    Document myDoc = new Document(id, f.getAbsolutePath(), sb.toString());
                    Document myDoc = new Document(id, f.getAbsolutePath(), isidokumen);                   
                    myDoc.setKategori(folderName);
                    lstDoc.add(myDoc);
                }
            }
        }
        System.out.println("\n---------------tf idf------------------------\n");
    }
    
    public List<Document> getListDocuments(){
        return lstDoc;
    }
}
