package timeseries.genetic;

import org.jaga.definitions.*;
import org.jaga.util.SimpleCollectionOfIndividuals;


/**
 * 
 * @author lakmal
 *
 *	The Time series specific selection algorithm
 *
 */

public class TimeSeriesSelection implements SelectionAlgorithm {

	private static final Class applicableFitnessClass = TimeSeriesFitness.class;
	public static final double MIN_FITNESS_LIMIT = -Double.MAX_VALUE;

	private double minFitness = MIN_FITNESS_LIMIT;

	public TimeSeriesSelection() {}
	

	public double getMinFitness() {
		return this.minFitness;
	}

	

	public Class getApplicableFitnessClass() {
		return applicableFitnessClass;
	}

	

	public Individual [] select(Population population, int howMany, int age, GAParameterSet params) {
		
		RandomGenerator rnd = params.getRandomGenerator();
		int p1Index = rnd.nextInt(0, population.getSize());
		int p2Index = rnd.nextInt(0, population.getSize());
		//System.out.println(p1Index+"  "+p2Index);
		Individual[] ind = {(TimeSeriesIndividual)population.getMember(p1Index),(TimeSeriesIndividual)population.getMember(p2Index)};
		return ind;
	}


	@Override
	public Individual select(Population population, int age,
			GAParameterSet params) throws ClassCastException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Individual[] selectParents(Population population, GAParameterSet params){
		SimpleCollectionOfIndividuals bestPop = new SimpleCollectionOfIndividuals();
		SimpleCollectionOfIndividuals currentPop= new SimpleCollectionOfIndividuals();;
		for(int i=0;i<population.getSize();i++){
			currentPop.add((TimeSeriesIndividual) population.getMember(i));
		}
		
		for(int i=0;i<population.getSize()/2;i++){
			bestPop.add(((SimpleCollectionOfIndividuals) currentPop).removeBestIndividual());
			//System.out.println("size "+currentPop.getSize());
		}
		return bestPop.getAllMembers();
	}

	

}