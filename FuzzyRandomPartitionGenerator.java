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
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;

public class FuzzyRandomPartitionGenerator implements PartitionGenerator {

   private RandomGenerator randomGenerator;

   public FuzzyRandomPartitionGenerator() {
      randomGenerator = new MersenneTwister();
   }

   @Override
   public void generate(DoubleMatrix2D partition) {
      for (int i = 0; i < partition.rows(); ++i) {
         // Randomise
         double sum = 0;
         for (int k = 0; k < partition.columns(); ++k) {
            double u = randomGenerator.nextDouble();
            partition.setQuick(i, k, u);
            sum += u;
         }

         // Normalise the weights
         for (int k = 0; k < partition.columns(); ++k) {
            partition.setQuick(i, k, partition.getQuick(i, k) / sum);
         }
      }
   }

   public RandomGenerator getRandomGenerator() {
      return randomGenerator;
   }

   @Override
   public void setRandomGenerator(RandomGenerator random) {
      this.randomGenerator = random;
   }
}
