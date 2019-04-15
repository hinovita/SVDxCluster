/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

/**
 *
 * @author User-pc
 */
import cern.colt.matrix.DoubleMatrix2D;
import org.apache.commons.math3.random.RandomGenerator;

public interface PartitionGenerator {

   void generate(DoubleMatrix2D partition);

   void setRandomGenerator(RandomGenerator randomGenerator);
}