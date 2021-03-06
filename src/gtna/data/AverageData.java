/*
 * ===========================================================
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
 * AverageData.java
 * ---------------------------------------
 * (C) Copyright 2009-2011, by Benjamin Schiller (P2P, TU Darmstadt)
 * and Contributors 
 * 
 * Original Author: Benjamin Schiller;
 * Contributors:    -;
 * 
 * Changes since 2011-05-17
 * ---------------------------------------
*/
package gtna.data;

import gtna.io.DataReader;
import gtna.io.DataWriter;
import gtna.util.Config;

import java.util.ArrayList;

public class AverageData {
	public static void generate(String destFolder, String[] folders) {
		String[] data = Config.getData();
		for (int i = 0; i < data.length; i++) {
			ArrayList<double[][]> values = new ArrayList<double[][]>();
			for (int j = 0; j < folders.length; j++) {
				String filename = folders[j]
						+ Config.get(data[i] + "_DATA_FILENAME")
						+ Config.get("DATA_EXTENSION");
				double[][] currentValues = DataReader.readDouble2D(filename);
				for (int k = 0; k < currentValues.length; k++) {
					double x = currentValues[k][0];
					double value = currentValues[k][1];
					if (k >= values.size()) {
						values.add(init(folders.length));
					}
					values.get(k)[j][0] = x;
					values.get(k)[j][1] = value;
				}
			}
			double[][] avg = new double[values.size()][2];
			for (int j = 0; j < values.size(); j++) {
				double[][] currentValues = values.get(j);
				int counter = 0;
				double sum = 0;
				double x = 0;
				for (int k = 0; k < currentValues.length; k++) {
					if (!Double.isNaN(currentValues[k][1])) {
						x += currentValues[k][0];
						sum += currentValues[k][1];
						counter++;
					}
				}
				if (counter > 0) {
					avg[j][0] = (double) x / (double) counter;
					avg[j][1] = (double) sum / (double) counter;
				} else {
					avg[j][0] = Double.NaN;
					avg[j][1] = Double.NaN;
				}
			}
			DataWriter.writeWithoutIndex(avg, data[i], destFolder);
		}
	}

	private static double[][] init(int length) {
		double[][] array = new double[length][2];
		for (int i = 0; i < length; i++) {
			array[i][0] = Double.NaN;
			array[i][1] = Double.NaN;
		}
		return array;
	}
}
