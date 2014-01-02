package timeseries.genetic;


import org.jaga.definitions.*;

/**
 * 
 * @author lakmal
 *
 *	The Time series specific GA hook
 *
 */

public class TimeSeriesGAHook {

	public TimeSeriesGAHook() {
	}

	public void initialisationDone(TimeSeriesGA caller, Population pop, int age,
								   GAResult result, GAParameterSet params) {
		// override and implement this method to take a apecific action.
	}

	public void foundNewResult(TimeSeriesGA caller, Population pop, int age,
							   GAResult result, GAParameterSet params) {
		// override and implement this method to take a apecific action.
	}

	public void generationChanged(TimeSeriesGA caller, Population pop, int age,
								  GAResult result, GAParameterSet params) {
		// override and implement this method to take a apecific action.
	}

	public void terminationConditionApplies(TimeSeriesGA caller, Population pop, int age,
											GAResult result, GAParameterSet params) {
		// override and implement this method to take a apecific action.
	}

	public void selectedForReproduction(TimeSeriesGA caller, Individual [] selectedParents,
										Population pop, int age, GAResult result,
										GAParameterSet params) {
		// override and implement this method to take a apecific action.
	}

	public void reproduced(TimeSeriesGA caller, Individual [] children, Individual [] parents,
						   Population pop, int age, GAResult result, GAParameterSet params) {
		// override and implement this method to take a apecific action.
	}

	public void fitnessCalculated(TimeSeriesGA caller, Individual updatedIndividual,
								  Population pop, int age, GAParameterSet params) {
		// override and implement this method to take a apecific action.
	}

}