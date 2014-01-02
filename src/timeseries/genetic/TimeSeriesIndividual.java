package timeseries.genetic;

import org.jaga.definitions.Fitness;
import org.jaga.definitions.Individual;
/**
 * 
 * @author lakmal
 *
 *	The Time series specific individual
 *
 */

public class TimeSeriesIndividual implements Individual {

	private TimeSeriesBitString content;
	private Fitness fitness;

	public TimeSeriesIndividual(TimeSeriesBitString content){
		this.content = content;
	}
	
	public TimeSeriesBitString getContent(){
		return content;
	}
	
	public double[] getAllCofficients(){
		int noOfCoef = TimeSeriesBitString.getBitStringOrder()+1;
		double[] coEf = new double[noOfCoef];
		for(int i=0;i<noOfCoef;i++){
			coEf[i] = content.getCoefficient(i);
		}
		return coEf;
	}
	
	public String toString(){
		double[] v = getAllCofficients();
		String coefStr = new String();
		for(int i=0;i<v.length;i++){
			coefStr += i+" => "+ v[i]+" | ";
		}
		coefStr+=("fitness = "+fitness+"\n");
		return coefStr;
	}
	
	
	@Override
	public Fitness getFitness() {
		return fitness;
	}

	@Override
	public void setFitness(Fitness fitness) {
		this.fitness = fitness;		
	}

}