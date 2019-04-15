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
import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;

public class HardRandomPartitionGenerator implements PartitionGenerator {

   private RandomGenerator randomGenerator;

   public HardRandomPartitionGenerator() {
      randomGenerator = new MersenneTwister();
   }

   @Override
   public void generate(DoubleMatrix2D partition) {
      // Initialise U randomly
      partition.assign(0);

      UniformIntegerDistribution uniform = new UniformIntegerDistribution(randomGenerator, 0, partition.columns() - 1);

      for (int i = 0; i < partition.rows(); ++i)
      {
         // Randomise
         int k = uniform.sample();
         partition.setQuick(i, k, 1);
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
