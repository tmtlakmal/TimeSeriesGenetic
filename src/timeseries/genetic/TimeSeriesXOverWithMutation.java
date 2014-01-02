package timeseries.genetic;

import org.jaga.util.CombinedReproductionAlgorithm;

/**
 * 
 * @author lakmal
 *
 *	The Time series specific combined algorithm
 *
 */

public class TimeSeriesXOverWithMutation extends CombinedReproductionAlgorithm {

	public TimeSeriesXOverWithMutation() {
		super();
		insertReproductionAlgorithm(0, new TimeSeriesXOver());
		insertReproductionAlgorithm(1, new TimeSeriesMutation());
	}

	public TimeSeriesXOverWithMutation(double xOverProb, double mutProb) {
		super();
		insertReproductionAlgorithm(0, new TimeSeriesXOver(xOverProb));
		insertReproductionAlgorithm(1, new TimeSeriesMutation(mutProb));
	}

	public void setXOverProbability(double xOverProb) {
		((TimeSeriesXOver) getReproductionAlgorithm(0)).setXOverProbability(xOverProb);
	}

	public double getXOverProbability() {
		return ((TimeSeriesXOver) getReproductionAlgorithm(0)).getXOverProbability();
	}

	public void setMutationProbability(double mutProb) {
		((TimeSeriesMutation) getReproductionAlgorithm(1)).setMutationProbability(mutProb);
	}

	public double getMutationProbability() {
		return ((TimeSeriesMutation) getReproductionAlgorithm(1)).getMutationProbability();
	}

}