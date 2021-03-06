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
 * Chord.java
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
package gtna.networks.p2p.chord;

import gtna.graph.Edges;
import gtna.graph.Graph;
import gtna.graph.Node;
import gtna.networks.Network;
import gtna.networks.NetworkImpl;
import gtna.routing.RoutingAlgorithm;
import gtna.transformation.Transformation;
import gtna.transformation.id.RandomChordIDSpace;

import java.math.BigInteger;

/**
 * @author benni
 * 
 */
public class Chord extends NetworkImpl implements Network {
	private int bits;

	private boolean uniform;

	public static Chord[] get(int[] nodes, int bits, boolean uniform,
			RoutingAlgorithm ra, Transformation[] t) {
		Chord[] nw = new Chord[nodes.length];
		for (int i = 0; i < nodes.length; i++) {
			nw[i] = new Chord(nodes[i], bits, uniform, ra, t);
		}
		return nw;
	}

	public static Chord[] get(int nodes, int[] bits, boolean uniform,
			RoutingAlgorithm ra, Transformation[] t) {
		Chord[] nw = new Chord[bits.length];
		for (int i = 0; i < bits.length; i++) {
			nw[i] = new Chord(nodes, bits[i], uniform, ra, t);
		}
		return nw;
	}

	public static Chord[] get(int nodes, int bits, boolean[] uniform,
			RoutingAlgorithm ra, Transformation[] t) {
		Chord[] nw = new Chord[uniform.length];
		for (int i = 0; i < uniform.length; i++) {
			nw[i] = new Chord(nodes, bits, uniform[i], ra, t);
		}
		return nw;
	}

	public Chord(int nodes, int bits, boolean uniform, RoutingAlgorithm ra,
			Transformation[] t) {
		super("CHORD", nodes, new String[] { "BITS", "ID_SELECTION" },
				new String[] { "" + bits, uniform ? "uniform" : "random" }, ra,
				t);
		this.bits = bits;
		this.uniform = uniform;
	}

	@Override
	public Graph generate() {
		Graph graph = new Graph(this.description());
		Node[] nodes = Node.init(this.nodes(), graph);
		graph.setNodes(nodes);
		RandomChordIDSpace t = new RandomChordIDSpace(this.bits, this.uniform);
		graph = t.transform(graph);

		ChordIdentifierSpace idSpace = (ChordIdentifierSpace) graph
				.getProperty("ID_SPACE_0");
		ChordPartition[] partitions = (ChordPartition[]) idSpace
				.getPartitions();

		Edges edges = new Edges(nodes, nodes.length * this.bits);
		for (Node node : nodes) {
			ChordPartition p = partitions[node.getIndex()];
			BigInteger id = p.getSucc().getId();
			// BigInteger predID = p.getPred().getId();
			BigInteger succID = id.add(BigInteger.ONE)
					.mod(idSpace.getModulus());

			// int predIndex = this.find(partitions, p.getPred(),
			// node.getIndex());
			int succIndex = this.find(partitions, new ChordIdentifier(idSpace,
					succID), node.getIndex());

			BigInteger add = BigInteger.ONE;
			int[] fingerIndex = new int[this.bits];
			BigInteger[] fingerID = new BigInteger[this.bits];
			for (int i = 0; i < this.bits; i++) {
				fingerID[i] = id.add(add).mod(idSpace.getModulus());
				fingerIndex[i] = this.find(partitions, new ChordIdentifier(
						idSpace, fingerID[i]), node.getIndex());
				add = add.shiftLeft(1);
			}

			// System.out.println(p);
			// System.out.println("  pred = " + predID + " => "
			// + partitions[predIndex]);
			// System.out.println("  succ = " + succID + " => "
			// + partitions[succIndex]);
			// for (int i = 0; i < fingerIndex.length; i++) {
			// System.out.println("  f[" + i + "] = " + fingerID[i] + " => "
			// + partitions[fingerIndex[i]]);
			// }
			// System.out.println();

			// edges.add(node.getIndex(), predIndex);
			edges.add(node.getIndex(), succIndex);
			for (int finger : fingerIndex) {
				edges.add(node.getIndex(), finger);
			}
		}
		edges.fill();

		graph.getTimer().end();
		return graph;
	}

	private int find(ChordPartition[] partitions, ChordIdentifier id, int start) {
		int index = start;
		while (!partitions[index].contains(id)) {
			index = (index + 1) % partitions.length;
		}
		return index;
	}

}
