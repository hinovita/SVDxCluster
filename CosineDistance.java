/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import cern.colt.matrix.DoubleMatrix1D;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.AbstractSimilarity;

/**
 *
 * @author User-pc
 */
public class CosineDistance {

    public double calculateDistance(DoubleMatrix1D x, DoubleMatrix1D y) {
        if (x.size() != y.size()) {
            throw new RuntimeException("Both instances should contain the same number of values.");
        }
        double sumTop = 0;
        double sumOne = 0;
        double sumTwo = 0;

        System.out.println(x.toString());
        System.out.println(y.toString());
        for (int i = 0; i < x.size(); i++) {
            sumTop += x.getQuick(i) * y.getQuick(i);
            sumOne += x.getQuick(i) * x.getQuick(i);
            sumTwo += y.getQuick(i) * y.getQuick(i);
        }
        System.out.println("Pembilang: "+sumTop);
        //System.out.println("Nilai A: "+Math.sqrt(sumOne));
        //System.out.println("Nilai B: "+Math.sqrt(sumTwo));
        double cosSim = sumTop / (Math.sqrt(sumOne) * Math.sqrt(sumTwo));
        System.out.println("Penyebut: "+(Math.sqrt(sumOne) * Math.sqrt(sumTwo)));
        System.out.println("Cosine Distance: "+ (1-cosSim));
        if (cosSim < 0){
            cosSim = 0;//This should not happen, but does because of rounding errorsl
        }
        double cosDis = 1 - cosSim;
        return cosDis;

    }

    public double getMaximumDistance(Dataset data) {
        //TODO implement
        throw new RuntimeException("Method getMaximumDistance is not implemented in CosineSimilarity.");
    }

    public double getMinimumDistance(Dataset data) {
        // TODO implement
        throw new RuntimeException("Method getMinimumDistance is not implemented in CosineSimilarity.");
    }
}
