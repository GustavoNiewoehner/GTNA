///*
// * ===========================================================
// * GTNA : Graph-Theoretic Network Analyzer
// * ===========================================================
// * 
// * (C) Copyright 2009-2011, by Benjamin Schiller (P2P, TU Darmstadt)
// * and Contributors
// * 
// * Project Info:  http://www.p2p.tu-darmstadt.de/research/gtna/
// * 
// * GTNA is free software: you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or
// * (at your option) any later version.
// * 
// * GTNA is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// * 
// * You should have received a copy of the GNU General Public License
// * along with this program. If not, see <http://www.gnu.org/licenses/>.
// * 
// * ---------------------------------------
// * NetworkFragmentation.java
// * ---------------------------------------
// * (C) Copyright 2009-2011, by Benjamin Schiller (P2P, TU Darmstadt)
// * and Contributors 
// * 
// * Original Author: Benjamin Schiller;
// * Contributors:    -;
// * 
// * Changes since 2011-05-17
// * ---------------------------------------
// */
//package gtna.trash.metrics.networkFragmentation;
//
//
//// TODO reimplement NetworkFragmentation
//public abstract class NetworkFragmentation {
//	// public abstract class NetworkFragmentation extends MetricImpl implements
//	// Metric {
//	// public static final int DEGREE_DESC = 1;
//	//
//	// public static final int IN_DEGREE_DESC = 2;
//	//
//	// public static final int OUT_DEGREE_DESC = 3;
//	//
//	// public static final int RANDOM = 4;
//	//
//	// private int order;
//	//
//	// private boolean bidirectional;
//	//
//	// private double[] mcs;
//	//
//	// private double[] aics;
//	//
//	// private double[] noc;
//	//
//	// private double anoc;
//	//
//	// private double mnoc;
//	//
//	// private double por;
//	//
//	// private Timer timer;
//	//
//	// public NetworkFragmentation(int order, boolean bidirectional) {
//	// super(key(order, bidirectional));
//	// this.order = order;
//	// this.bidirectional = bidirectional;
//	// }
//	//
//	// public void computeData(Graph g, Network n, Hashtable<String, Metric> m)
//	// {
//	// this.timer = new Timer();
//	// int steps = Math.min(Config.getInt("NETWORK_FRAGMENTATION_STEPS"),
//	// g.nodes.length);
//	// this.mcs = new double[steps];
//	// this.aics = new double[steps];
//	// this.noc = new double[steps];
//	// Random rand = new Random(System.currentTimeMillis());
//	// Node[] sorted = null;
//	// if (this.order == DEGREE_DESC) {
//	// sorted = NodeSorting.degreeDesc(g.nodes, rand);
//	// } else if (this.order == IN_DEGREE_DESC) {
//	// sorted = NodeSorting.inDegreeDesc(g.nodes, rand);
//	// } else if (this.order == OUT_DEGREE_DESC) {
//	// sorted = NodeSorting.outDegreeDesc(g.nodes, rand);
//	// } else if (this.order == RANDOM) {
//	// sorted = NodeSorting.random(g.nodes, rand);
//	// }
//	// for (int round = 0; round < steps; round++) {
//	// int number = round * g.nodes.length / steps;
//	// boolean[] removed = removed(number, sorted);
//	// int[] sizes = null;
//	// if (this.bidirectional) {
//	// sizes = this.clusterSizesBidirectional(g.nodes, removed);
//	// } else {
//	// sizes = this.clusterSizesUnidirectional(g.nodes, removed);
//	// }
//	// this.mcs[round] = Util.max(sizes);
//	// if (sizes.length == 1) {
//	// this.aics[round] = 0;
//	// } else {
//	// this.aics[round] = (double) (Util.sum(sizes) - this.mcs[round])
//	// / (double) (sizes.length - 1);
//	// }
//	// this.noc[round] = sizes.length;
//	// }
//	// this.anoc = Util.avg(this.noc);
//	// this.mnoc = Util.max(this.noc);
//	// this.por = this.computePOR(this.mcs);
//	// this.timer.end();
//	// }
//	//
//	// private double computePOR(double[] mcs) {
//	// int nodes = (int) mcs[0];
//	// for (int i = 0; i < mcs.length; i++) {
//	// int removed = i * nodes / mcs.length;
//	// int gap = nodes - removed - (int) mcs[i];
//	// if (gap > mcs[i]) {
//	// return i;
//	// }
//	// }
//	// return mcs.length;
//	// }
//	//
//	// private int[] clusterSizesBidirectional(Node[] nodes, boolean[] removed)
//	// {
//	// ArrayList<Integer> sizes = new ArrayList<Integer>();
//	// boolean[] visited = new boolean[nodes.length];
//	// for (int i = 0; i < nodes.length; i++) {
//	// if (removed[nodes[i].index()] || visited[nodes[i].index()]) {
//	// continue;
//	// }
//	// int size = 0;
//	// Queue<Node> queue = new LinkedList<Node>();
//	// queue.add(nodes[i]);
//	// visited[nodes[i].index()] = true;
//	// while (!queue.isEmpty()) {
//	// size++;
//	// Node current = queue.poll();
//	// Node[] out = current.out();
//	// for (int j = 0; j < out.length; j++) {
//	// if (!visited[out[j].index()] && !removed[out[j].index()]) {
//	// queue.add(out[j]);
//	// visited[out[j].index()] = true;
//	// }
//	// }
//	// Node[] in = current.in();
//	// for (int j = 0; j < in.length; j++) {
//	// if (!visited[in[j].index()] && !removed[in[j].index()]) {
//	// queue.add(in[j]);
//	// visited[in[j].index()] = true;
//	// }
//	// }
//	// }
//	// sizes.add(size);
//	// }
//	// return Util.toIntegerArray(sizes);
//	// }
//	//
//	// private int[] clusterSizesUnidirectional(Node[] nodes, boolean[] removed)
//	// {
//	// ArrayList<Integer> sizes = new ArrayList<Integer>();
//	// boolean[] visited = new boolean[nodes.length];
//	// for (int i = 0; i < nodes.length; i++) {
//	// if (removed[nodes[i].index()] || visited[nodes[i].index()]) {
//	// continue;
//	// }
//	//
//	// Queue<Node> queueFW = new LinkedList<Node>();
//	// boolean[] visitedFW = new boolean[nodes.length];
//	// visitedFW[nodes[i].index()] = true;
//	// queueFW.add(nodes[i]);
//	// while (!queueFW.isEmpty()) {
//	// Node current = queueFW.poll();
//	// Node[] out = current.out();
//	// for (int j = 0; j < out.length; j++) {
//	// if (!visitedFW[out[j].index()] && !removed[out[j].index()]) {
//	// queueFW.add(out[j]);
//	// visitedFW[out[j].index()] = true;
//	// }
//	// }
//	// }
//	//
//	// Queue<Node> queueBW = new LinkedList<Node>();
//	// boolean[] visitedBW = new boolean[nodes.length];
//	// visitedBW[nodes[i].index()] = true;
//	// queueBW.add(nodes[i]);
//	// while (!queueBW.isEmpty()) {
//	// Node current = queueBW.poll();
//	// Node[] in = current.in();
//	// for (int j = 0; j < in.length; j++) {
//	// if (!visitedBW[in[j].index()] && !removed[in[j].index()]) {
//	// queueBW.add(in[j]);
//	// visitedBW[in[j].index()] = true;
//	// }
//	// }
//	// }
//	//
//	// int size = 0;
//	// for (int j = 0; j < visited.length; j++) {
//	// if (visitedFW[j] && visitedBW[j]) {
//	// visited[j] = true;
//	// size++;
//	// }
//	// }
//	// sizes.add(size);
//	// }
//	// return Util.toIntegerArray(sizes);
//	// }
//	//
//	// private boolean[] removed(int number, Node[] sorted) {
//	// boolean[] removed = new boolean[sorted.length];
//	// for (int i = 0; i < number; i++) {
//	// removed[sorted[i].index()] = true;
//	// }
//	// return removed;
//	// }
//	//
//	// private static String key(int order, boolean bidirectional) {
//	// String key = "NF_";
//	// if (bidirectional) {
//	// key += "B_";
//	// } else {
//	// key += "U_";
//	// }
//	// if (order == DEGREE_DESC) {
//	// key += "D";
//	// } else if (order == IN_DEGREE_DESC) {
//	// key += "DI";
//	// } else if (order == OUT_DEGREE_DESC) {
//	// key += "DO";
//	// } else if (order == RANDOM) {
//	// key += "R";
//	// }
//	// return key;
//	// }
//	//
//	// public Value[] getValues(Value[] values) {
//	// Value ANOC = new Value(this.key() + "_ANOC", this.anoc);
//	// Value MNOC = new Value(this.key() + "_MNOC", this.mnoc);
//	// Value POR = new Value(this.key() + "_POR", this.por);
//	// Value RT = new Value(this.key() + "_RT", this.timer.rt());
//	// return new Value[] { ANOC, MNOC, POR, RT };
//	// }
//	//
//	// public boolean writeData(String folder) {
//	// DataWriter.writeWithIndex(this.aics, this.key() + "_AICS", folder);
//	// DataWriter.writeWithIndex(this.mcs, this.key() + "_MCS", folder);
//	// DataWriter.writeWithIndex(this.noc, this.key() + "_NOC", folder);
//	// return false;
//	// }
//}
