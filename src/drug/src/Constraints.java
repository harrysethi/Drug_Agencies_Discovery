package drug.src;

import drug.globals.Globals;

public class Constraints {

	public static void constraints_TakingEachEdge_notTakingEdges(
			StringBuilder buff) {
		for (int i = 1; i <= Globals.numOfV; i++) {
			for (int j = i + 1; j <= Globals.numOfV; j++) {

				if (i == j) {
					continue;
				}

				if (Globals.graph[i][j] == 1) {
					// 1. Taking each edge at-least once
					for (int k = 0; k < Globals.in_K; k++) {
						buff.append(Utils.get_l(k, i, j));
						buff.append(" ");
					}
					Utils.endLineInSAT(buff);
				} else {
					// 2. Not taking the unavailable edges
					for (int k = 0; k < Globals.in_K; k++) {
						buff.append("-");
						buff.append(Utils.get_l(k, i, j));
						buff.append(" ");
						Utils.endLineInSAT(buff);
					}
				}
				Globals.conCount++;

			}
		}
	}

	public static void constraints_noSubgraphEmpty(StringBuilder buff) {
		// 3. no subgraph should be empty
		for (int k = 0; k < Globals.in_K; k++) {
			for (int c = k + 1; c <= Globals.l_map_part1.size(); c += Globals.in_K) {
				buff.append(c);
				buff.append(" ");
			}
			Globals.conCount++;
			Utils.endLineInSAT(buff);

		}
	}

	public static void constraintsSubgraph(StringBuilder buff) {
		// Constraints_subgraph
		for (int k1 = 0; k1 < Globals.in_K; k1++) {

			for (int k2 = 0; k2 < Globals.in_K; k2++) {

				if (k1 == k2) {
					continue;
				}

				StringBuilder X_buff = new StringBuilder("");

				for (int i = 1; i <= Globals.numOfV; i++) {

					int x_index = Utils.gt_l_index();
					Globals.count++;
					X_buff.append(x_index).append(" ");

					int first_l = Utils.get_v(k1, i);
					int second_l = Utils.get_v(k2, i);

					// 1/3 clause for new X literal
					buff.append(first_l).append(" ");
					buff.append("-").append(x_index).append(" ");
					Globals.conCount++;
					Utils.endLineInSAT(buff);

					// 2/3 clause for new X literal
					buff.append("-").append(second_l).append(" ");
					buff.append("-").append(x_index).append(" ");
					Globals.conCount++;
					Utils.endLineInSAT(buff);

					// 3/3 clause for new X literal
					ConstraintsHelper.three_edges_constraint(buff, x_index,
							first_l, second_l, true, false, false);

				}
				Globals.conCount++;
				Utils.endLineInSAT(X_buff);
				buff.append(X_buff);
				Globals.pw.print(buff);
				buff = new StringBuilder("");
			}
		}
	}

	public static void addCompletenessConstraints(StringBuilder buff) {
		for (int k = 0; k < Globals.in_K; k++) {
			for (int i = 1; i <= Globals.numOfV; i++) {
				for (int j = i; j <= Globals.numOfV; j++) {

					if (i == j) {
						continue;
					}

					int first_l = Utils.get_v(k, i);
					int second_l = Utils.get_v(k, j);

					int third_l = Utils.get_l(k, i, j);
					ConstraintsHelper.three_edges_constraint(buff, third_l,
							first_l, second_l, true, true, false);

					buff.append(first_l).append(" ");
					buff.append("-").append(third_l).append(" ");
					Globals.conCount++;
					Utils.endLineInSAT(buff);

					buff.append(second_l).append(" ");
					buff.append("-").append(third_l).append(" ");
					Globals.conCount++;
					Utils.endLineInSAT(buff);

				}
			}
			Globals.pw.print(buff);
			buff = new StringBuilder("");
		}
	}
}
