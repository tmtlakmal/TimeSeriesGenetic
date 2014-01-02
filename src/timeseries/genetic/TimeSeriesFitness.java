package timeseries.genetic;

import org.jaga.definitions.Fitness;

/**
 * 
 * @author lakmal
 *
 *	The Time series specific fitness
 *
 */

public class TimeSeriesFitness implements Fitness {

	private double value = +0.0;

	private TimeSeriesFitness() {}

	public TimeSeriesFitness(double fitnessValue) {
		resetValue(fitnessValue);
	}

	public void resetValue(double fitnessValue) {
		if (Double.isNaN(fitnessValue))
			throw new IllegalArgumentException("Agrument is not a number");
		else if (fitnessValue == Double.NEGATIVE_INFINITY)
			this.value = -Double.MAX_VALUE;
		else if (fitnessValue == Double.POSITIVE_INFINITY)
			this.value = Double.MAX_VALUE;
		else
			this.value = fitnessValue;
	}

	public double getValue() {
		return this.value;
	}

	public boolean isBetter(Fitness fitness) {
		if (null == fitness)
			return true;
		TimeSeriesFitness absFit = (TimeSeriesFitness) fitness;
		return this.getValue() < absFit.getValue();
	}

	public boolean isWorse(Fitness fitness) {
		if (null == fitness)
			return false;
		TimeSeriesFitness absFit = (TimeSeriesFitness) fitness;
		return this.getValue() > absFit.getValue();
	}

	public String toString() {
		return Double.toString(value);
	}

}