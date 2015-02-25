package drug.src;

import drug.globals.Globals;

public class Constraints {

	public static void constraints_TakingEachEdge_notTakingEdges(
			StringBuffer buff) {
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

	public static void constraints_noSubgraphEmpty(StringBuffer buff) {
		// 3. no subgraph should be empty
		for (int k = 0; k < Globals.in_K; k++) {
			for (int c = k + 1; c <= Globals.l_map_part1.size(); c += Globals.in_K) {
				buff.append(c);
				buff.append(" ");
			}
			Utils.endLineInSAT(buff);
		}
	}

	public static void constraints_completeness_connected(StringBuffer buff) {
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

				}
			}
		}
	}
}
