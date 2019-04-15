package control;


import cern.colt.matrix.DoubleMatrix2D;
import boundary.Centroid;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author WIN8
 */
public class PopupController {
    
    public void showPusatCluster(DoubleMatrix2D pusat){
        Centroid pusatCluster = new Centroid();
        pusatCluster.populatePusatCluster(pusat);
        pusatCluster.setVisible(true);
    }
    
    public void showPusatCluster(String[][] pusat){
        Centroid pusatCluster = new Centroid();
        pusatCluster.populatePusatCluster(pusat);
        pusatCluster.setVisible(true);
    }
    
}
