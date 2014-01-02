package timeseries.genetic;

import java.util.ArrayList;
import java.util.Iterator;

import org.jaga.definitions.*;
import org.jaga.util.FittestIndividualResult;
import org.jaga.util.SimpleCollectionOfIndividuals;

/**
 * 
 * @author lakmal
 *
 *	The Time series specific genetic algorithm
 *
 */

public class TimeSeriesGA implements GeneticAlgorithm {

	private ArrayList hooks = null;

	public TimeSeriesGA() {}

	public GAResult exec(GAParameterSet params) {
		TimeSeriesDebugHook dhk = new TimeSeriesDebugHook();
		this.addHook(dhk);
		if (null == params)
			throw new NullPointerException("Parameters to a GA may not be null");

		int age = 0;
		Population pop = createInitialPopulation(params);
		FittestIndividualResult result = (FittestIndividualResult) createResult();
		for (int i = 0; i < pop.getSize(); updateIndividualFitness(pop.getMember(i++), pop, age, params));
		checkForBetterResult(result, pop, params);
		notifyInitialisationDone(pop, age, result, params);

		while (! terminationConditionApplies(pop, age, result, params)) {

			Individual best = result.getFittestIndividual();

			Population nextPop = generateNextPopulation(pop, age, result, params);

			pop = nextPop;
			age++;
			notifyGenerationChanged(pop, age, result, params);

			if (result.getFittestIndividual() != best)
				notifyFoundNewResult(pop, age, result, params);

		}

		notifyTerminationConditionApplies(pop, age, result, params);

		return result;
	}

	protected Population generateNextPopulation(Population oldPop, int age,
												GAResult result, GAParameterSet params) {
		FittestIndividualResult res = (FittestIndividualResult) result;
		Population newPop = createEmptyPopulation(params);
		Individual [] parentsSet;
		Individual [] parents;
		SimpleCollectionOfIndividuals parentsPop = new SimpleCollectionOfIndividuals();
		parentsSet = selectParentSet(oldPop, age, params);
		newPop.addAll(parentsSet);
		parentsPop.addAll(parentsSet);
		while (newPop.getSize() < params.getPopulationSize()) {
						
			parents = selectForReproduction(parentsPop, age, params);
			notifySelectedForReproduction(parents, oldPop, age, result, params);

			Individual [] children = haveSex(parents, params);
			for (int i = 0; i < children.length; i++) {
				if (null != children[i].getFitness())
					continue;
				updateIndividualFitness(children[i], oldPop, age, params);
				if (children[i].getFitness().isBetter(res.getBestFitness()))
					res.setFittestIndividual(children[i]);
			}

			notifyReproduced(children, parents, oldPop, age, result, params);
			newPop.addAll(children);
		}

		return newPop;
	}

	protected GAResult createResult() {
		return new FittestIndividualResult();
	}

	protected boolean checkForBetterResult(GAResult oldResult, Population newPop, GAParameterSet params) {

		FittestIndividualResult result = (FittestIndividualResult) oldResult;
		Fitness best = result.getBestFitness();

		final int size = newPop.getSize();
		for (int i = 0; i < size; i++) {
			Fitness f = newPop.getMember(i).getFitness();
			if (f.isBetter(best)) {
				best = f;
				result.setFittestIndividual(newPop.getMember(i));
			}
		}

		return (result.getBestFitness() != best);
	}

	protected Population createInitialPopulation(GAParameterSet params) {
		Population pop = createEmptyPopulation(params);
		while (pop.getSize() < params.getPopulationSize()) {
			Individual ind;
			ind = params.getIndividualsFactory().createRandomIndividual(params);
			pop.add(ind);
			//System.out.println(params.getPopulationSize());
		}
		return pop;
	}

	protected Population createEmptyPopulation(GAParameterSet params) {
		Population pop = new SimpleCollectionOfIndividuals();
		return pop;
	}

	protected boolean terminationConditionApplies(Population pop, int genNum,
												  GAResult result, GAParameterSet params) {
		return genNum >= params.getMaxGenerationNumber();
	}

	protected Individual [] selectForReproduction(Population pop, int age, GAParameterSet params) {

		int selCount = params.getReproductionAlgorithm().getRequiredNumberOfParents();
		if (0 > selCount)
			selCount = 2;

		SelectionAlgorithm selector = params.getSelectionAlgorithm();
		//System.out.println("pop"+pop.getSize());
		Individual [] selection = selector.select(pop, selCount, age, params);
		//System.out.println(selection.toString());
		return selection;
	}
	
	protected Individual[] selectParentSet(Population pop, int age, GAParameterSet params){
		TimeSeriesSelection selector = (TimeSeriesSelection) params.getSelectionAlgorithm();
		
		//System.out.println("pop"+pop.getSize());
		Individual [] selection = (Individual[])selector.selectParents(pop, params);
		//System.out.println(selection.toString());
		return selection;
	}

	protected Individual [] haveSex(Individual [] parents, GAParameterSet params) {
		ReproductionAlgorithm cosyPlace = params.getReproductionAlgorithm();
		Individual [] oops = cosyPlace.reproduce(parents, params);
		return oops;
	}

	protected void updateIndividualFitness(Individual indiv, Population pop,
										   int genNum, GAParameterSet params) {
		FitnessEvaluationAlgorithm tester = params.getFitnessEvaluationAlgorithm();
		Fitness fitness = tester.evaluateFitness(indiv, genNum, pop, params);
		indiv.setFitness(fitness);
		updateFitnessCalculated(indiv, pop, genNum, params);
		
	}

	public boolean addHook(TimeSeriesGAHook hook) {
		if (null == hook)
			return false;
		else if (null == this.hooks)
			this.hooks = new ArrayList();
		else if (this.hooks.contains(hook))
			return false;
		this.hooks.add(hook);
		return true;
	}

	public boolean removeHook(TimeSeriesGAHook hook) {
		if (null == hook)
			return false;
		else if (null == this.hooks)
			return false;
		else if (!this.hooks.contains(hook))
			return false;
		this.hooks.remove(hook);
		if (0 == this.hooks.size())
			this.hooks = null;
		return true;
	}

	protected void notifyInitialisationDone(Population pop, int age,
											GAResult result, GAParameterSet params) {
		if (!params.getUseMainAlgorithmHooks())
			return;
		if (null == hooks)
			return;
		for (Iterator hook = hooks.iterator(); hook.hasNext();
			((TimeSeriesGAHook) hook.next()).initialisationDone(this, pop, age, result, params)
		);
	}

	protected void notifyFoundNewResult(Population pop, int age,
										GAResult result, GAParameterSet params) {
		if (!params.getUseMainAlgorithmHooks())
			return;
		if (null == hooks)
			return;
		for (Iterator hook = hooks.iterator(); hook.hasNext();
			((TimeSeriesGAHook) hook.next()).foundNewResult(this, pop, age, result, params)
		);
	}

	protected void notifyGenerationChanged(Population pop, int age,
										   GAResult result, GAParameterSet params) {
		if (!params.getUseMainAlgorithmHooks())
			return;
		if (null == hooks)
			return;
		for (Iterator hook = hooks.iterator(); hook.hasNext();
			((TimeSeriesGAHook) hook.next()).generationChanged(this, pop, age, result, params)
		);
	}

	protected void notifyTerminationConditionApplies(Population pop, int age,
													 GAResult result, GAParameterSet params) {
		if (!params.getUseMainAlgorithmHooks())
			return;
		if (null == hooks)
			return;
		for (Iterator hook = hooks.iterator(); hook.hasNext();
			((TimeSeriesGAHook) hook.next()).terminationConditionApplies(this, pop, age, result, params)
		);
	}

	protected void notifySelectedForReproduction(Individual [] selectedParents, Population pop,
												 int age, GAResult result, GAParameterSet params) {
		if (!params.getUseMainAlgorithmHooks())
			return;
		if (null == hooks)
			return;
		for (Iterator hook = hooks.iterator(); hook.hasNext();
			((TimeSeriesGAHook) hook.next()).selectedForReproduction(this, selectedParents,
																 pop, age, result, params)
		);
	}

	protected void notifyReproduced(Individual [] children, Individual [] parents, Population pop,
									int age, GAResult result, GAParameterSet params) {
		if (!params.getUseMainAlgorithmHooks())
			return;
		if (null == hooks)
			return;
		for (Iterator hook = hooks.iterator(); hook.hasNext();
			((TimeSeriesGAHook) hook.next()).reproduced(this, children, parents,
													pop, age, result, params)
		);
	}

	protected void updateFitnessCalculated(Individual updated, Population pop,
										   int age, GAParameterSet params) {
		if (!params.getUseMainAlgorithmHooks())
			return;
		if (null == hooks)
			return;
		for (Iterator hook = hooks.iterator(); hook.hasNext();
			((TimeSeriesGAHook) hook.next()).fitnessCalculated(this, updated,
														   pop, age, params)
		);
	}
}