package org.jaga.util;


import org.jaga.definitions.*;


import timeseries.genetic.TimeSeriesFitness;
import timeseries.genetic.TimeSeriesIndividual;

/**
 * 
 * @author lakmal
 *
 *	The Time series specific fitness calculation algorithm
 *
 */

public class PolynomialError implements FitnessEvaluationAlgorithm {
	private double[] genericPolynomial;	//both should be equal in length
	private double[] time;		//both should be equal in length

	public PolynomialError(double[] genericPolynomial,double[] time) {
		this.genericPolynomial = genericPolynomial;
		this.time = time;
	}

	public Class getApplicableClass() {
		return TimeSeriesIndividual.class;
	}

	public Fitness evaluateFitness(Individual individual, int age, Population population, GAParameterSet params) {
		double[] coef = ((TimeSeriesIndividual) individual).getAllCofficients();	
		double error=0;
		double value;
		for(int j=0;j<genericPolynomial.length;j++){
			value=0;
			for(int i=0;i<coef.length;i++){
				//System.out.println(presentTime[j]);
				value +=(coef[i]*Math.pow(time[j], i));
			}
			error+= Math.pow((value-genericPolynomial[j]), 2);
		}
		//System.out.print(error+" ");
		Fitness fit = new TimeSeriesFitness(error);
		return fit;
	}
	
	

}