package drug.testMain;

import java.io.IOException;

import drug.globals.Globals;
import drug.src.Constraints;
import drug.src.ConstraintsHelper;
import drug.src.IO;
import drug.src.Utils;

public class Main {

	public static void main(String[] args) throws IOException {

		IO.readInput();

		StringBuffer buff = new StringBuffer("");

		Utils.createLiteralMap();
		System.out.println(Globals.l_map.size());

		Constraints.constraints_TakingEachEdge_notTakingEdges(buff);

		Constraints.constraints_noSubgraphEmpty(buff);

		Constraints.constraints_completeness_connected(buff);

		// Constraints_subgraph
		for (int k1 = 0; k1 < Globals.in_K; k1++) {

			for (int k2 = 0; k2 < Globals.in_K; k2++) {

				if (k1 == k2) {
					continue;
				}

				int new_l_cnt = 0;
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

						String X_str = "X" + k1 + "," + k2 + ":" + ++new_l_cnt;

						int x_index = Utils.gt_l_index();
						Globals.l_map.put(X_str, x_index);
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
		IO.printSATinput(buff);
	}
	/*
	 * private static String new_l_str(int k1, int k2, int new_l_cnt) { return;
	 * }
	 */

}
