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
import cern.colt.matrix.doublealgo.Statistic;
import cern.colt.matrix.doublealgo.Statistic.VectorVectorFunction;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;

public class FuzzyCMeans {

   private DoubleMatrix2D means;
   private DoubleMatrix2D partition;
   private double fuzzification = 2.0;
   private double epsilon = 1e-4;
   private int maxIterations = 1000;
   private RandomGenerator randomGenerator = new MersenneTwister();
   private PartitionGenerator partitionGenerator = new FuzzyRandomPartitionGenerator();
//   private VectorVectorFunction distanceMeasure = Statistic.EUCLID;
   private CosineDistance distanceMeasure = new CosineDistance();
   private DoubleMatrix2D data;
   private DoubleMatrix2D SV;
   int totalIterations;
   int clusters;
   double distance = 0;
   long output;

 
   
   public FuzzyCMeans(DoubleMatrix2D data, int clusters) {
       this.data = data;
       this.clusters = clusters;
   }
   
   public FuzzyCMeans(DoubleMatrix2D data, DoubleMatrix2D SV, int clusters) {
       this.data = data;
       this.SV = SV;
       this.clusters = clusters;
   }

   public void cluster() {
      long lStartTime = System.currentTimeMillis();
      int n = data.rows(); // Number of features
      int p = data.columns(); // Dimensions of features

      partition = new SparseDoubleMatrix2D(n, clusters);
      partitionGenerator.setRandomGenerator(randomGenerator);
      partitionGenerator.generate(partition);

      means = new DenseDoubleMatrix2D(p, clusters);

      // Begin the main loop of alternating optimization
      double stepSize = epsilon;
      for (int itr = 0; itr < maxIterations && stepSize >= epsilon; ++itr) {
         // Get new prototypes (v) for each cluster using weighted median
         for (int k = 0; k < clusters; k++) {

            for (int j = 0; j < p; j++) {
               double sumWeight = 0;
               double sumValue = 0;

               for (int i = 0; i < n; i++) {
                  double Um = Math.pow(partition.getQuick(i, k), fuzzification);
                  sumWeight += Um;
                  sumValue += data.getQuick(i, j) * Um;
               }

               means.setQuick(j, k, sumValue / sumWeight);
            }
         }

         // Calculate distance measure d:
         DoubleMatrix2D distances = new DenseDoubleMatrix2D(n, clusters);
         for (int k = 0; k < clusters; k++) {
            for (int i = 0; i < n; i++) {
               // Euclidean distance calculation
               if(SV != null){
                   distance = distanceMeasure.calculateDistance(means.viewColumn(k), SV.viewRow(i));
               }else{
                   distance = distanceMeasure.calculateDistance(means.viewColumn(k), data.viewRow(i));
               }
               distances.setQuick(i, k, distance);
            }
         }

         // Get new partition matrix U:
         stepSize = 0;
         for (int k = 0; k < clusters; k++) {
            for (int i = 0; i < n; i++) {
               double u = 0;

               if (distances.getQuick(i, k) == 0) {
                  // Handle this awkward case
                  u = 1;
               } else {
                  double sum = 0;
                  for (int j = 0; j < clusters; j++) {
                     // Exact analytic solution given by Lagrange multipliers
                     sum += Math.pow(distances.getQuick(i, k) / distances.getQuick(i, j),
                                     1.0 / (fuzzification - 1.0));
                  }
                  u = 1 / sum;
               }

               double u0 = partition.getQuick(i, k);
               partition.setQuick(i, k, u);

               // Stepsize is max(delta(U))
               if (u - u0 > stepSize) {
                  stepSize = u - u0;
               }
            }
         }
         totalIterations = itr;
      }
        long lEndTime = System.currentTimeMillis();

        output = lEndTime - lStartTime;
   }

   public DoubleMatrix2D getMeans() {
      return means;
   }

   public DoubleMatrix2D getPartition() {
      return partition;
   }
   
   public double getExecTime(){
       return (double) output/1000;
   }
   
   public int getTotalIterations(){
       return totalIterations+1;
   }

   public double getFuzzification() {
      return fuzzification;
   }

   public void setFuzzification(double fuzzification) {
      this.fuzzification = fuzzification;
   }

   public double getEpsilon() {
      return epsilon;
   }

   public void setEpsilon(double epsilon) {
      this.epsilon = epsilon;
   }

   public int getMaxIterations() {
      return maxIterations;
   }

   public void setMaxIterations(int maxIterations) {
      this.maxIterations = maxIterations;
   }

   public RandomGenerator getRandomGenerator() {
      return randomGenerator;
   }

   public void setRandomGenerator(RandomGenerator random) {
      this.randomGenerator = random;
   }
}