/* ===========================================================
 * GTNA : Graph-Theoretic Network Analyzer
 * ===========================================================
 *
 * (C) Copyright 2009-2011, by Benjamin Schiller (P2P, TU Darmstadt)
 * and Contributors
 *
 * Project Info:  http://www.p2p.tu-darmstadt.de/research/gtna/
 *
 * GTNA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GTNA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * ---------------------------------------
 * RandomRingID.java
 * ---------------------------------------
 * (C) Copyright 2009-2011, by Benjamin Schiller (P2P, TU Darmstadt)
 * and Contributors 
 *
 * Original Author: benni;
 * Contributors:    -;
 *
 * Changes since 2011-05-17
 * ---------------------------------------
 *
 */
package gtna.transformation.id;

import gtna.graph.Graph;
import gtna.id.ring.RingIdentifier;
import gtna.id.ring.RingIdentifierSpace;
import gtna.id.ring.RingPartition;
import gtna.transformation.Transformation;
import gtna.transformation.TransformationImpl;
import gtna.util.Util;

import java.util.Arrays;
import java.util.Random;

/**
 * Assigns a randomly selected RingPartition to every node and stores it as a
 * property with key "ID_SPACE" in the given graph.
 * 
 * @author benni
 * 
 */
public class RandomRingIDSpace extends TransformationImpl implements
		Transformation {
	private int realities;

	private double modulus;

	private boolean wrapAround;

	public RandomRingIDSpace() {
		super("RANDOM_RING_ID_SPACE", new String[] {}, new String[] {});
		this.realities = 1;
		this.modulus = 1.0;
		this.wrapAround = true;
	}

	public RandomRingIDSpace(int realities, double modulus, boolean wrapAround) {
		super("RANDOM_RING_ID_SPACE", new String[] { "REALITIES", "MODULUS",
				"WRAP_AROUND" }, new String[] { "" + realities, "" + modulus,
				"" + wrapAround });
		this.realities = realities;
		this.modulus = modulus;
		this.wrapAround = wrapAround;
	}

	@Override
	public Graph transform(Graph graph) {
		Random rand = new Random();
		for (int r = 0; r < this.realities; r++) {
			RingPartition[] partitions = new RingPartition[graph.getNodes().length];
			RingIdentifierSpace idSpace = new RingIdentifierSpace(partitions, this.modulus,
					this.wrapAround);
			RingIdentifier[] ids = new RingIdentifier[graph.getNodes().length];
			for (int i = 0; i < ids.length; i++) {
				ids[i] = RingIdentifier.rand(rand, idSpace);
			}
			Arrays.sort(ids);
			for (int i = 0; i < partitions.length; i++) {
				partitions[i] = new RingPartition(ids[i], ids[(i + 1)
						% ids.length]);
			}
			Util.randomize(partitions, rand);
			graph.addProperty(graph.getNextKey("ID_SPACE"), idSpace);
		}
		return graph;
	}

	@Override
	public boolean applicable(Graph g) {
		return true;
	}

}
