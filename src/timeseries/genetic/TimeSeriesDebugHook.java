package timeseries.genetic;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.jaga.definitions.*;
import org.jaga.util.FittestIndividualResult;

/**
 * 
 * @author lakmal
 *
 *	The Time series specific debug hook
 *
 */


public class TimeSeriesDebugHook extends TimeSeriesGAHook {
	private  int type=0;
	public TimeSeriesDebugHook() {
	}

	private void printPopulation(Population pop, int age) {
		/*System.out.println("Population in generation " + age + " has "
						   + pop.getSize() + " members:");
		for (int i = 0; i < pop.getSize(); i++) {
			System.out.println("  " + i + ") " + pop.getMember(i));
		}*/
	}

	private void printResult(GAResult result, int age) {
		if (!(result instanceof FittestIndividualResult)) {
			return;
		}
		System.out.println("Best result (in generation " + age + "):");
		System.out.println(((FittestIndividualResult) result).
						   getFittestIndividual());
		
	}

	protected void printIndividuals(Individual[] inds) {
		for (int i = 0; i < inds.length; i++) {
			System.out.println("  " + i + ": " + inds[i]);
		}
	}
	
	public void initialisationDone(TimeSeriesGA caller, Population pop, int age,
								   GAResult result, GAParameterSet params) {
		System.out.println("\nINITIALISATION DONE.");
		printPopulation(pop, age);
		printResult(result, age);
		System.out.println("--------------------------------------------------");
	}

	public void foundNewResult(TimeSeriesGA caller, Population pop, int age,
							   GAResult result, GAParameterSet params) {
		System.out.println("\nFOUND NEW RESULT.");
		//printPopulation(pop, age);
		printResult(result, age);
		System.out.println("--------------------------------------------------");
	}

	public void generationChanged(TimeSeriesGA caller, Population pop, int age,
								  GAResult result, GAParameterSet paramss) {
		/*System.out.println("\nNEXT GENERATION.");
		printPopulation(pop, age);
		printResult(result, age);
		System.out.println("--------------------------------------------------");*/
	}

	public void terminationConditionApplies(TimeSeriesGA caller, Population pop,
											int age,
											GAResult result,
											GAParameterSet params) {
		System.out.println("\nTERMINATION APPLIED.");
		printPopulation(pop, age);
		printResult(result, age);		
		System.out.println("--------------------------------------------------");
		TimeSeriesIndividual tsi = (TimeSeriesIndividual) ((FittestIndividualResult) result).getFittestIndividual();
		this.writeToFile(tsi.getAllCofficients());
	}

	public void selectedForReproduction(TimeSeriesGA caller,
										Individual[] selectedParents,
										Population pop, int age,
										GAResult result,
										GAParameterSet params) {
		/*System.out.println("\nPARENTS SELECTED.");
		printPopulation(pop, age);
		printResult(result, age);
		System.out.println("Parents:");
		printIndividuals(selectedParents);
		System.out.println("--------------------------------------------------");*/
	}

	public void reproduced(TimeSeriesGA caller, Individual[] children,
						   Individual[] parents,
						   Population pop, int age, GAResult result,
						   GAParameterSet params) {
		/*System.out.println("\nCHILDREN PRODUCED.");
		printPopulation(pop, age);
		printResult(result, age);
		System.out.println("Parents:");
		printIndividuals(parents);
		System.out.println("Children:");
		printIndividuals(children);
		System.out.println("--------------------------------------------------");*/
	}

	public void fitnessCalculated(TimeSeriesGA caller, Individual updatedIndividual,
								  Population pop, int age,
								  GAParameterSet params) {
		/*System.out.println("\nFITNESS CALCULATED.");
		System.out.println("Updated individual: " + updatedIndividual);
		System.out.println("--------------------------------------------------");*/
	}
	
	public void writeToFile(double[] coef){
		String fileName=null;
		if(type==0){
			fileName= "/home/lakmal/workspace/KalmanFilter/TimeSeriesModule/tauCoef.txt";
		}
		else if(type==1){
			fileName= "/home/lakmal/workspace/KalmanFilter/TimeSeriesModule/rhoCoef.txt";
		}
		type++;
		PrintStream output;
		try {
			output = new PrintStream(new File(fileName));
			for(int i =0;i<coef.length;i++){
				output.println(coef[i]);
			}
			output.close();
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
			e.printStackTrace();
		}
    		
	}

}