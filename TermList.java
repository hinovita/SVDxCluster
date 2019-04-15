/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.ArrayList;
import java.util.List;
import entity.Term;

/**
 *
 * @author User-pc
 */
public class TermList {
    List<Term> lstterm;
    
    public TermList(){
        lstterm = new ArrayList<>();
    }
    
    public TermList(List<Term> lstterm){
        this.lstterm = lstterm;
    }
    
    public void addTerm(Term term){
        lstterm.add(term);
    }
    
    public Term getTermAt(int idx){
        return lstterm.get(idx);
    }
    
    public int getTotalTerm(){
        return lstterm.size();
    }
    
    public String[] toStringArray(){
        String[] temp = new String[lstterm.size()];
        for(int i=0; i<temp.length; i++){
            temp[i] = lstterm.get(i).getTerm();
        }
        return temp;
    }
    
    public Term checkTerm(String strterm){
        Term term = null;
        for(int i=0; i<lstterm.size(); i++){
            if(strterm.equals(lstterm.get(i).getTerm())){
                term = lstterm.get(i);
                break;
            }
        }
        return term;
    }
}
