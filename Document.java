/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import control.TermList;
import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

/**
 *
 * @author User-pc
 */
public class Document {
    private int id;
    private String judul;
    private String isi;
    private TermList termlist;
    private String kategori;
    private int clusternumber;
    
    public Document(){
        termlist = new TermList();
    }
    
    public Document(String isi){
        this.isi = isi;
    }
    
    public Document(int id, String judul, String isi){
        this.id = id;
        this.judul = judul;
        this.isi = isi;
    }
    
    public void setClusterNumber(int clusternumber){
        this.clusternumber = clusternumber;
    }
    
    public int getClusterNumber(){
        return clusternumber;
    }
    
    public void setTermlist(TermList termlist){
        this.termlist = termlist;
    }
    
    public TermList getTermList(){
        return termlist;
    }
    
    public void setKategori(String kategori){
        this.kategori = kategori;
    }
    
    public String getKategori(){
        return kategori;
    }
    
    public void setJudul(String judul){
        this.judul = judul;
    }
    
    public String getJudul(){
        return judul;
    }
    
    public void setIsi(String isi){
        this.isi = isi;
    }
    
    public String getIsi(){
        return isi;
    }
}
