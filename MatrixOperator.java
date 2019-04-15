/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import cern.colt.matrix.DoubleMatrix2D;
import java.util.Arrays;

/**
 *
 * @author WIN8
 */
public class MatrixOperator {
    
    public double[][] transpose(double[][] data){
        System.out.println("--------------transpose----------------");
        double[][] transposedMatrix = new double[data[0].length][data.length];
        for(int rows = 0; rows < data.length; rows++){
            for(int cols = 0; cols < data[0].length; cols++){
                transposedMatrix[cols][rows] = data[rows][cols];
            }
        }
        for(double[] i:transposedMatrix){//2D arrays are arrays of arrays
            System.out.println(Arrays.toString(i));
        }
        return transposedMatrix;
    }
    
    public double[][] multiply(DoubleMatrix2D data1, DoubleMatrix2D data2){
        System.out.println("------------multiply------------");
        double[][] multipliedMatrix = new double[data1.rows()][data2.columns()];
        for(int i=0; i<data1.rows(); i++){
            for(int j=0; j<data2.columns(); j++){
                for(int k=0; k<data1.columns(); k++){
                    multipliedMatrix[i][j] += data1.get(i, k) * data2.get(k, j);
                }System.out.print(multipliedMatrix[i][j] + "  ");
            }System.out.print("\n");
        }
        return multipliedMatrix;
    }
    
}
