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
			Utils.endLineInSAT(buff);
		}
	}

	public static void constraints_subgraph(StringBuilder buff) {
		// Constraints_subgraph
		for (int k1 = 0; k1 < Globals.in_K; k1++) {

			for (int k2 = 0; k2 < Globals.in_K; k2++) {

				if (k1 == k2) {
					continue;
				}

				// int new_l_cnt = 0;
				StringBuilder X_buff = new StringBuilder("");

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
				Globals.pw.print(buff);
				buff = new StringBuilder("");
			}
		}
	}

	public static void constraints_completeness_connected(StringBuilder buff) {
		for (int k = 0; k < Globals.in_K; k++) {

			for (int i1 = 1; i1 <= Globals.numOfV; i1++) {
				for (int j1 = i1; j1 <= Globals.numOfV; j1++) {

					if (i1 == j1) {
						continue;
					}

					// Second edge for the pair
					for (int i2 = 1; i2 <= Globals.numOfV; i2++) {
						for (int j2 = i2; j2 <= Globals.numOfV; j2++) {

							if (i2 == j2) {
								continue;
							}

							if (i1 == i2 && j1 == j2) {
								continue;
							}

							int first_l = Utils.get_l(k, i1, j1);
							int second_l = Utils.get_l(k, i2, j2);
							int third_l;

							// 4. constraint - triangles_completeness
							if (i1 == j2) {
								third_l = Utils.get_l(k, i2, j1);
								ConstraintsHelper.triangle_constraint(buff,
										first_l, second_l, third_l);
							}

							else if (i2 == j1) {
								third_l = Utils.get_l(k, i1, j2);
								ConstraintsHelper.triangle_constraint(buff,
										first_l, second_l, third_l);
							}

							else {
								// 5. constraint - connected
								ConstraintsHelper
										.checkingGraphConnected_constraint(
												buff, k, i1, j1, i2, j2,
												first_l, second_l);

							}

						}
					}

					Globals.pw.print(buff);
					buff = new StringBuilder("");
				}
			}
		}
	}
}
