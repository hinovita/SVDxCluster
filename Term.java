/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.List;

/**
 *
 * @author User-pc
 */
public class Term {
    private String term;
    private int ada;
    private double idf;
    List<Document> doc;
    
    public Term(){
    }
    
    public Term(String term){
        this.term = term;
    }
    
    public void setTerm(String term){
        this.term = term;
    }
    
    public String getTerm(){
        return this.term;
    }
}
