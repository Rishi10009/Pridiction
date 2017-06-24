package stockanalysisbayesian;
/**
 * Created by shara on 4/29/2017.
 */
import Jama.*;

import java.util.ArrayList;
import java.lang.Double;

public class BayesClassifier
{

    public double            beta;
    public Matrix            phiT;
    public ArrayList<Double> arraylistofprices;
    public int x;
    public Matrix            phi;
    public double            alpha;


    public BayesClassifier(ArrayList<Double> inputlist)
    {
        x = 4;
        alpha      = 0.005;
        arraylistofprices = inputlist;

        beta       = 11.1;
        phitransposeMatrix();
    }
    // Build transpose phi matrix
    public void phitransposeMatrix()
    {
        double [][] martixtranspose;
        martixtranspose = new double[1][x + 1];
        int i = 0;
        while (i <= x) {
            martixtranspose[0][i] = Math.pow(arraylistofprices.size(), i);

            i++;
        }
        phiT = new Matrix(martixtranspose);
    }

    public double predictPrice()
    {
        double mx=0;
        
        Matrix newSinversemtx;
        newSinversemtx = getS(getInversematrixS());

        Matrix sum = new Matrix(x + 1, 1);
        int i = 1;
        while (i <= arraylistofprices.size() - 1) {
            phimatrix(i);
            double tn = arraylistofprices.get(i-1);
            sum.plusEquals(phi.times(tn));

            i++;
        }

        // phiT * S
        Matrix productmatrix = phiT.times(newSinversemtx);

        // beta * phiT * S * sum(phi * tn)
        Matrix predictedPrice = productmatrix.times(sum);
        mx = beta * predictedPrice.get(0, 0);
        System.out.println(mx);

        
        return mx;
    }
    
    public Matrix getS(Matrix sInv)
    {
        return sInv.inverse();
    }
    //to obtain inverse of the matrix
    public Matrix getInversematrixS()
    {
        // Build alpha * I
        double [][] alphainvmatrix;
        alphainvmatrix = new double[x + 1][x + 1];
        for(int i = 0; i < x + 1; i++)
        {
            alphainvmatrix[i][i] = alpha;

        }
        Matrix alphamatrixnew = new Matrix(alphainvmatrix);

        // calculate summation
        Matrix sum = new Matrix(x + 1, 1);
        int i = 1;
        while (i <= arraylistofprices.size() - 1) {
            phimatrix(i);

            sum.plusEquals(phi);

            i++;
        }
        Matrix summation = sum.times(phiT);
        summation.timesEquals(beta); 

        // alpha * I + beta * sum
        Matrix sInv = alphamatrixnew.plus(summation);
        return sInv;
    }



    // Build phi matrix
    public void phimatrix(int n)
    {
        double[][] phimatrix;
        phimatrix = new double[x + 1][1];
        int i = 0;
        while (i< x +1) {
            phimatrix[i][0] = Math.pow(n, i);

            i++;
        }
        phi = new Matrix(phimatrix);
    }






}
