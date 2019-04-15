/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import cern.colt.matrix.DoubleMatrix2D;
import java.util.Arrays;
import java.util.List;
import entity.Document;
import control.TermList;

/**
 *
 * @author WIN8
 */
public class DimensionReduction {
    
    private TermList globalTermList;
    private List<Document> lstDoc;
    private double[][] US;
    private double[][] newMatrixTerm;
    
    public DimensionReduction(TermList globalTermList, List<Document> lstDoc, double[][] US){
        this.globalTermList = globalTermList;
        this.lstDoc = lstDoc;
        this.US = US;
        newMatrixTerm = new double[lstDoc.size()][lstDoc.size()];
    }
    
    public void doReduksiDimensi(){
        int termIdx = 0;
        for(int i=0; i<lstDoc.size(); i++){
            System.out.println(lstDoc.get(i).getJudul());
            double[][] docTerm = new double[lstDoc.get(i).getTermList().getTotalTerm()][lstDoc.size()];
            for(int j=0; j<lstDoc.get(i).getTermList().getTotalTerm(); j++){
                for(int k=0; k<globalTermList.getTotalTerm(); k++){
                    if(globalTermList.getTermAt(k).getTerm().equals(
                            lstDoc.get(i).getTermList().getTermAt(j).getTerm())){
                        termIdx = k;
                        break;
                    }
                }
                docTerm[j] = US[termIdx];
                System.out.println(Arrays.toString(docTerm[j]));
            }
            System.out.println("\n");
            reduceMatrix(docTerm, i);
            System.out.println("\n\n");
        }
    }
    
    private void reduceMatrix(double[][] docTerm, int idx){
        System.out.println(idx+1);
        double[][] ans = new double[docTerm[0].length][docTerm.length];
        for(int rows = 0; rows < docTerm.length; rows++){
            for(int cols = 0; cols < docTerm[0].length; cols++){
                ans[cols][rows] = docTerm[rows][cols];
            }
        }
        
        for(int i=0; i<ans.length; i++){//2D arrays are arrays of arrays
            double temp = 0;
            for(int j=0; j<ans[i].length; j++){
                temp += ans[i][j];
            }
            newMatrixTerm[idx][i] = temp/ans[i].length;
            System.out.println(temp/ans[i].length);
//            System.out.println(Arrays.toString(i));
        }
    }
    
    public double[][] getReducedMatrix(){
        return newMatrixTerm;
    }
    
}
