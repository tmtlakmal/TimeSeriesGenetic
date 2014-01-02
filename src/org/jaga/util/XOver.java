package org.jaga.util;

import org.jaga.definitions.*;

/**
 * TODO: Complete these comments.
 *
 * <p><u>Project:</u> JAGA - Java API for Genetic Algorithms.</p>
 *
 * <p><u>Company:</u> University College London and JAGA.Org
 *    (<a href="http://www.jaga.org" target="_blank">http://www.jaga.org</a>).
 * </p>
 *
 * <p><u>Copyright:</u> (c) 2004 by G. Paperin.<br/>
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, ONLY if you include a note of the original
 *    author(s) in any redistributed/modified copy.<br/>
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.<br/>
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *    or see http://www.gnu.org/licenses/gpl.html</p>
 *
 * @author Greg Paperin (greg@jaga.org)
 *
 * @version JAGA public release 1.0 beta
 */

abstract public class XOver implements ReproductionAlgorithm {

	private static final int requiredNumberOfParents = 2;
	private double xOverProbability = 0.65;

	public XOver() {}

	public XOver(double xOverProb) {
		setXOverProbability(xOverProb);
	}

	public void setXOverProbability(double xOverProb) {
		if (xOverProb < 0.0)
			throw new IllegalArgumentException("XOverProbability may not be below 0 (is "
											   + xOverProb + ")");
		if (xOverProb > 1.0)
			throw new IllegalArgumentException("XOverProbability may not be above 1 (is "
											   + xOverProb + ")");
		this.xOverProbability = xOverProb;
	}

	public double getXOverProbability() {
		return this.xOverProbability;
	}

	public int getRequiredNumberOfParents() {
		return requiredNumberOfParents;
	}

	abstract public Class getApplicableClass();

	abstract public Individual[] reproduce(Individual[] parents, GAParameterSet params);

}