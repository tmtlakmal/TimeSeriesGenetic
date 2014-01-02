package timeseries.genetic;

import org.jaga.definitions.*;
import org.jaga.util.ReusableTimeSeriesGA;

/**
 * 
 * @author lakmal
 *
 *	The Time series implementation
 *
 */

public class TimeSeriesImplementation {

	public TimeSeriesImplementation() {
	}

	public void exec(double[] pol, double[] time) {

		GAParameterSet params = new TimeSeriesParameterSet(pol,time);
		//params.setPopulationSize(100);
		//((NDecimalsIndividualSimpleFactory) params.getIndividualsFactory()).setConstraint(0, new RangeConstraint(-10, 10));

		ReusableTimeSeriesGA ga = new ReusableTimeSeriesGA(params);
		ga.addHook(new TimeSeriesDebugHook());

		int repeat = 1;
		System.out.println("\n\n");
		System.out.println("This is a simple demo for the \"Genetic Algorithms in Java\"-Package.");
		System.out.println("This software is developed by Greg Paperin at the University College London.");
		System.out.println("All materials connected to this software are under the GNU licence.");
		System.out.println("\n");
		System.out.println("Running the algorithm " + repeat + " times.");
		System.out.println("The parameters are: \n" + params);
		System.out.println("\n\n");
		for (int i = 0; i < repeat; i++) {
			System.out.println("** Run " + i + ". **");
			GAResult result = ((ReusableTimeSeriesGA) ga).exec();
			System.out.println("Result is: " + result);
			System.out.println("\n");
		}
		System.out.println("\nDemo finished.");
		System.out.println("Please, visit http://www.jaga.org to check for latest updates.");
	}

	public static void main(String[] unusedArgs) {
		double [] poly = {20,25,28};
		double [] time = {2,3,4};
		TimeSeriesImplementation demo = new TimeSeriesImplementation();
		demo.exec(poly ,time);

	}
}