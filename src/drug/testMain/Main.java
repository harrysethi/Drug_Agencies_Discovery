package drug.testMain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import drug.globals.Globals;
import drug.src.Constraints;
import drug.src.ConstraintsHelper;
import drug.src.IO;
import drug.src.Utils;

public class Main {

	public static void main(String[] args) throws IOException {

		int partNum = Integer.parseInt(args[1]);
		String key = args[2];

		String graphFile = key + ".graph";

		String satInputFile = key + ".satinput";
		String satOutputFile = key + ".satoutput";
		String finalOutputFile = key + ".subgraphs";

		if (partNum == 1) {
			part1(graphFile, satInputFile);
		}

		else {
			part2(graphFile, satOutputFile, finalOutputFile);
		}
	}

	private static void part2(String graphFile, String satOutputFile,
			String finalOutputFile) throws FileNotFoundException, IOException {
		Globals.isPart1 = false;

		IO.readInput_part1(graphFile);
		Utils.createLiteralMap();

		IO.readInput_part2(satOutputFile);

		StringBuffer buff = new StringBuffer("");

		if (Globals.isSat == false) {
			// printing 0 if unsatisfied
			buff.append("0");
			return;
		}

		for (int i = 0; i < Globals.in_K; i++) {
			StringBuffer inBuff = new StringBuffer("");

			Set<Integer> mySet = Globals.listOfSet.get(i);
			inBuff.append("#").append(i + 1).append(" ").append(mySet.size());
			inBuff.append("\n");

			for (int j : mySet) {
				inBuff.append(j).append(" ");
			}

			buff.append(inBuff.toString().trim());
			buff.append("\n");
		}

		IO.printFinalOutput(buff, finalOutputFile);
	}

	private static void part1(String graphFile, String satInputFile)
			throws FileNotFoundException, IOException {
		Globals.isPart1 = true;

		IO.readInput_part1(graphFile);
		Utils.createLiteralMap();

		StringBuffer buff = new StringBuffer("");

		System.out.println(Globals.l_map_part1.size());

		Constraints.constraints_TakingEachEdge_notTakingEdges(buff);

		Constraints.constraints_noSubgraphEmpty(buff);

		Constraints.constraints_completeness_connected(buff);

		// Constraints_subgraph
		for (int k1 = 0; k1 < Globals.in_K; k1++) {

			for (int k2 = 0; k2 < Globals.in_K; k2++) {

				if (k1 == k2) {
					continue;
				}

				// int new_l_cnt = 0;
				StringBuffer X_buff = new StringBuffer("");

				for (int i = 1; i <= Globals.numOfV; i++) {
					for (int j = i; j <= Globals.numOfV; j++) {

						if (i == j) {
							continue;
						}

						// TODO: shall be fine
						if (Globals.graph[i][j] != 1) {
							continue;
						}

						// X_str = "X" + k1 + "," + k2 + ":" + ++new_l_cnt;

						int x_index = Utils.gt_l_index();
						// Globals.l_map_part1.put(X_str, x_index);
						X_buff.append(x_index).append(" ");

						int first_l = Utils.get_l(k1, i, j);
						int second_l = Utils.get_l(k2, i, j);

						// 1/3 clause for new X literal
						buff.append(first_l).append(" ");
						buff.append("-").append(x_index).append(" ");
						Utils.endLineInSAT(buff);

						// 2/3 clause for new X literal
						buff.append("-").append(second_l).append(" ");
						buff.append("-").append(x_index).append(" ");
						Utils.endLineInSAT(buff);

						// 3/3 clause for new X literal
						ConstraintsHelper.three_edges_constraint(buff, x_index,
								first_l, second_l, true, false, false);
					}
				}
				Utils.endLineInSAT(X_buff);
				buff.append(X_buff);
			}
		}

		IO.printMap();
		IO.printSATinput(buff, satInputFile);
	}

}
