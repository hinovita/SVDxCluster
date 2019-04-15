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
import cern.colt.list.IntArrayList;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.doublealgo.Statistic;
import cern.colt.matrix.doublealgo.Statistic.VectorVectorFunction;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.javaml.core.Instance;
import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;

/**
 * Also known as: PAM (Partitioning around medoids) 
 * It is more robust to noise and outliers
 * See: http://en.wikipedia.org/wiki/K-medoids
 * Also See: Computational Complexity between K-Means and K-Medoids Clustering Algorithms
 *             for Normal and Uniform Distributions of Data Points
 * T. Velmurugan and T. Santhanam
 * Department of Computer Science, DG Vaishnav College, Chennai, India
 * @author tgee
 */
public class KMedoids {
   
   private DoubleMatrix2D partition;
   private int maxIterations = 1000;
   private RandomGenerator randomGenerator = new MersenneTwister();
   private IntArrayList medoids;
   private DoubleMatrix2D medoidToDisplay;
   private String[][] medoidString;
   private int totalIterations;
   //private VectorVectorFunction distanceMeasure = Statistic.EUCLID;
   private CosineDistance distanceMeasure = new CosineDistance();
   private DoubleMatrix2D data;
   private DoubleMatrix2D SV;
   int clusters;
   double distance = 0;
   double oldDistance = 0;
   double newDistance = 0;
   long output;

   
   public KMedoids(DoubleMatrix2D data, int clusters) {
       this.data = data;
       this.clusters = clusters;
   }
   
   public KMedoids(DoubleMatrix2D data, DoubleMatrix2D SV, int clusters) {
       this.data = data;
       this.SV = SV;
       this.clusters = clusters;
   }

   public void cluster() {
      long lStartTime = System.currentTimeMillis();
      int n = data.rows(); // Number of features
      System.out.println("Baris data: "+n);
      int p = data.columns(); // Dimensions of features
      medoidToDisplay = new DenseDoubleMatrix2D(clusters, 1);
      medoidString = new String[clusters][1];

      partition = new SparseDoubleMatrix2D(n, clusters);
      medoids = new IntArrayList(clusters);

      IntArrayList randomOrdering = new IntArrayList(n);
      for (int i = 0; i < n; ++i) {
         randomOrdering.setQuick(i, i);
      }

      // Choose the medoids by shuffling the data
       System.out.println("Choose the medoids by shuffling the data");
      for (int i = 0; i < clusters; ++i) {
         // k is the index of the remaining possibilities
          try {
            UniformIntegerDistribution uniform = new UniformIntegerDistribution(randomGenerator, i, n-1);
            int k = uniform.sample();

            // Swap x(i) and x(k)
            int medoid = randomOrdering.getQuick(k);
            randomOrdering.setQuick(k, i);                     
            medoids.setQuick(i, medoid);
            System.out.println("medoid ke-"+(int)(i+1) + ": D" + (int)(medoid+1));
            medoidToDisplay.set(i, 0, medoid+1);
            medoidString[i][0] = "D" +(int)(medoid+1);   
          } catch (NumberIsTooLargeException e) {
              JOptionPane.showMessageDialog(null, "k tidak boleh sama atau lebih besar dari jumlah dokumen", 
                      "Gagal", JOptionPane.ERROR_MESSAGE);
          }
      }

      boolean changedMedoid = true;

      // Begin the main loop of alternating optimization
       System.out.println("\nBegin the main loop of alternating optimization");
      for (int itr = 0; itr < maxIterations && changedMedoid; ++itr) {
         // Get new partition matrix U by
         // assigning each object to the nearest medoid
         for (int i = 0; i < n; i++) {            
            double minDistance = Double.MAX_VALUE;
            int closestCluster = 0;

            for (int k = 0; k < clusters; k++) {
               // U = 1 for the closest medoid
               // U = 0 otherwise
               int medoid = medoids.getQuick(k);
//               double distance = distanceMeasure.apply(data.viewRow(medoid), data.viewRow(i));
               System.out.println("medoid: D"+(int)(medoid+1) + " dokumen: D"+(int)(i+1));
               if(SV != null){
                   distance = distanceMeasure.calculateDistance(data.viewRow(medoid), SV.viewRow(i));
               }else{
                   distance = distanceMeasure.calculateDistance(data.viewRow(medoid), data.viewRow(i));
               }
               if (distance < minDistance) {
                  minDistance = distance;
                  closestCluster = k;
               }
            }

            if (partition.getQuick(i, closestCluster) == 0) {

               for (int k = 0; k < clusters; k++) {
                  partition.setQuick(i, k, (k == closestCluster) ? 1 : 0);
               }
            }
         }
         
          System.out.println(partition);

         // Try to find a better set of medoids
          //System.out.println("\nTry to find a better set of medoids");
         changedMedoid = false;
         for (int k = 0; k < clusters; k++) {
              int medoid = medoids.getQuick(k);
            System.out.println("\nTry to find a better set of medoids");
            // For each non-medoid in the cluster
//            int medoid = medoids.getQuick(k);
               
            for (int i = 0; i < n; ++i) {
               int bestMedoid = medoid;
               double lowestCostDelta = 0;
               if (i != medoid && partition.getQuick(i, k) > 0) {
                  // Calculate the change in cost by swapping this configuration
                  int costDelta = 0;
                  for (int j = 0; j < n; ++j) {
                     if (partition.getQuick(j, k) > 0) { 
                         //System.out.println("medoid: D"+(int)(medoid+1) + " dokumen: D"+(int)(i+1));
                        if(SV != null){
                            oldDistance = distanceMeasure.calculateDistance(data.viewRow(medoid), SV.viewRow(j));
                            newDistance = distanceMeasure.calculateDistance(data.viewRow(i), SV.viewRow(j));
                        }else{
                            oldDistance = distanceMeasure.calculateDistance(data.viewRow(medoid), data.viewRow(j));
                            newDistance = distanceMeasure.calculateDistance(data.viewRow(i), data.viewRow(j));
                        }
                         System.out.println("dokumen: D"+(int)(medoid+1) + " dokumen: D"+(int)(j+1)+": "+oldDistance);
                         System.out.println("dokumen: D"+(int)(i+1) + " dokumen: D"+(int)(j+1)+": "+newDistance);
                        costDelta += newDistance - oldDistance;
                     }
                  }

                  if (costDelta < lowestCostDelta) {
                     bestMedoid = i;
                     lowestCostDelta = costDelta;
                  }

                  if (bestMedoid != medoid) {
                     medoids.setQuick(k, bestMedoid);
                     changedMedoid = true;
                      System.out.println("Terjadi perubahan medoid dari "+(int)(k+1)+" ke "+(int)(bestMedoid+1)+"\n");
                  }
                  else{
                      System.out.println("Tidak terjadi perubahan medoid\n");
                  }
               }
            }
         }
         totalIterations = itr;
      }
       System.out.println("Medoids here: "+medoids);
       long lEndTime = System.currentTimeMillis();
       
       output = lEndTime - lStartTime;
   }
   
   public IntArrayList getMedoids() {
      return medoids;
   }
   
   public DoubleMatrix2D getMedoidsToDisplay(){
       return medoidToDisplay;
   }
   
   public String[][] getMedoidString(){
       return medoidString;
   }
   
   public double getExecTime(){
       return (double) output/1000;
   }

   public DoubleMatrix2D getPartition() {
      return partition;
   }
   
   public int getTotalIterations(){
       return totalIterations+1;
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

//   public VectorVectorFunction getDistanceMeasure() {
//      return distanceMeasure;
//   }
//
//   public void setDistanceMeasure(VectorVectorFunction distanceMeasure) {
//      this.distanceMeasure = distanceMeasure;
//   }
}