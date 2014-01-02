package gen;

/**
 * @author damith
 * 
 * The implementation of the main algorithm 
 * */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;

import stat.Kendall;
import stat.Spearman;
import timeseries.genetic.TimeSeriesImplementation;

public class MainAlgorithm {
	public static double[] getPattern(double[] X) {
		double[] P = new double[X.length - 1];
		for (int i = 0; i < X.length - 1; i++) {

			if (X[i] < X[i + 1]) {
				P[i] = 1;
			} else {
				P[i] = 0;
			}
		}
		return X;
	}

	public static void main(String[] args) {
		Random r = new Random();
		int maxOffset = 0;
		double[] taus ;
		double[] series = {2,3,4,3,5,6,4,7,8,7,
		          9,8,7,8,9,9,10,12,13,11,
		          14,15,15,16,17, 21, 24, 27,28, 29,
		           29,29,29,29,31, 35,15,16,17,18,
		           16,14,18,20,19,18,16,19,18,18,
		           23,28,29,27,27,25,32,34,35,36,
		           37,35,36,37,38,40,38,37,36,38,
		           42,44,45,47,32,33,29,30,31,32,
		           35,38,37,42,44,46,47,48,49,52,
		           53,55,56,57,59,63,62,45,43,42};
		System.out.print("[SERIES]:");
		for (int i = 0; i < series.length; i++) {
			//series[i] = r.nextInt() * 1.0;
			System.out.print(" " + series[i]);
		}
		System.out.println();

		int present = 10;
		int maxOfst = (int)series.length-2*present;
		taus= new double[maxOfst];
		int uprLimit = series.length-present-1;
		System.out.println("******using kendall's tau as probabilistic measure************");
		System.out.print("[TAUS]:");
		double[] presentSeries = new double[present];
		double[] pastSeries = new double[present];
		for (int offset = 0; offset < maxOfst; offset++) {
			for (int i = 0; i < present; i++) {
				pastSeries[present-1 - i] = series[uprLimit - i - offset];
				presentSeries[present-1 - i] = series[series.length-1 - i];
			}
			taus[(maxOfst-1) - offset] =
			                   Kendall.Tau(MainAlgorithm.getPattern(presentSeries),
			                               MainAlgorithm.getPattern(pastSeries));
			System.out.print(" " + taus[(maxOfst-1) - offset]);
		}
		System.out.println();

		int m = MainAlgorithm.getMaxOffset(taus);
		System.out.println("[BEST MATCH]: starting from " + (series.length-present - m) + " ");
		double[] bestMatch = new double[present];
		double[] time = new double[present];
		for (int i = 0; i < present; i++) {
			bestMatch[present-1 - i] = series[series.length-1 - i - m];
			time[present-1-i] = series.length-1 - i - m;
		}

		TimeSeriesImplementation tsa = new TimeSeriesImplementation();
		tsa.exec(bestMatch, time);

		System.out.println("**************************************************************");

		System.out.println();
		System.out.println("*****using spearman's rho as probabilistic measure************");
		System.out.print("[RHOS]:");

		for (int offset = 0; offset < maxOfst; offset++) {
			for (int i = 0; i < present; i++) {
				pastSeries[present-1 - i] = series[uprLimit - i - offset];
				presentSeries[present-1 - i] = series[series.length-1 - i];
			}
			taus[(maxOfst-1) - offset] =
			                   Spearman.Rho(MainAlgorithm.getPattern(presentSeries),
			                               MainAlgorithm.getPattern(pastSeries));
			System.out.print(" " + taus[(maxOfst-1) - offset]);
		}
		System.out.println();

		m = MainAlgorithm.getMaxOffset(taus);
		System.out.println("[BEST MATCH]: starting from " + (series.length-present - m) + " ");
		bestMatch = new double[present];
		time = new double[present];
		for (int i = 0; i < present; i++) {
			bestMatch[present-1 - i] = series[series.length-1 - i - m];
			time[present-1-i] = series.length-1 - i - m;
		}
		/*for (int offset = 0; offset < 10; offset++) {
			for (int i = 0; i < present; i++) {
				pastSeries[4 - i] = series[14 - i - offset];
				presentSeries[4 - i] = series[19 - i];
			}
			taus[9 - offset] =
			                   Spearman.Rho(MainAlgorithm.getPattern(presentSeries),
			                                MainAlgorithm.getPattern(pastSeries));
			System.out.print(" " + taus[9 - offset]);
		}
		System.out.println();

		m = MainAlgorithm.getMaxOffset(taus);
		System.out.println("[BEST MATCH]: " + (10 - m) + "th SET");

		for (int i = 0; i < 5; i++) {
			bestMatch[4 - i] = series[19 - i - m];
		}
		*/
		
		

		tsa.exec(bestMatch, time);

		System.out.println("**************************************************************");

	}

	public static int getMaxOffset(double[] X) {
		int mx = 0;
		for (int i = 0; i < X.length; i++) {
			if (X[mx] < X[i]) {
				mx = i;
			}
		}
		return mx;
	}
	
	
}
