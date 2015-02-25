package drug.src;

import drug.globals.Globals;

public class ConstraintsHelper {

	public static void three_edges_constraint(StringBuffer buff, int third_l,
			int first_l, int second_l, boolean isF_neg, boolean isS_neg,
			boolean isT_neg) {

		if (first_l > second_l) {
			return;
		}

		if (isF_neg) {
			buff.append("-");
		}
		buff.append(first_l);
		buff.append(" ");

		if (isS_neg) {
			buff.append("-");
		}
		buff.append(second_l);
		buff.append(" ");

		if (isT_neg) {
			buff.append("-");
		}
		buff.append(third_l);
		buff.append(" ");

		Utils.endLineInSAT(buff);
	}

	/*
	 * public static void three_edges_constraint2(StringBuffer buff, String
	 * third_l, String first_l, String second_l, boolean isF_neg, boolean
	 * isS_neg, boolean isT_neg) {
	 * 
	 * if (first_l > second_l) { return; }
	 * 
	 * if (isF_neg) { buff.append("-"); } buff.append(first_l);
	 * buff.append(" ");
	 * 
	 * if (isS_neg) { buff.append("-"); } buff.append(second_l);
	 * buff.append(" ");
	 * 
	 * if (isT_neg) { buff.append("-"); } buff.append(third_l);
	 * buff.append(" ");
	 * 
	 * Utils.endLineInSAT(buff); }
	 */

	public static void triangle_constraint(StringBuffer buff, int first_l,
			int second_l, int third_l) {
		three_edges_constraint(buff, third_l, first_l, second_l, true, true,
				false);

		three_edges_constraint(buff, third_l, first_l, second_l, true, false,
				true);

		three_edges_constraint(buff, third_l, first_l, second_l, false, true,
				true);
	}

	public static void checkingGraphConnected_constraint(StringBuffer buff,
			int k, int i1, int j1, int i2, int j2, int first_l, int second_l) {
		int third_l;
		// checking the graph as connected
		if (Globals.graph[i1][j2] == 1) {
			third_l = Utils.get_l(k, i1, j2);
			three_edges_constraint(buff, third_l, first_l, second_l, true,
					true, false);
		}

		if (Globals.graph[j1][i2] == 1) {
			third_l = Utils.get_l(k, j1, i2);
			three_edges_constraint(buff, third_l, first_l, second_l, true,
					true, false);
		}

		if (Globals.graph[j1][j2] == 1) {
			third_l = Utils.get_l(k, j1, j2);
			three_edges_constraint(buff, third_l, first_l, second_l, true,
					true, false);
		}

		if (Globals.graph[i1][i2] == 1) {
			third_l = Utils.get_l(k, i1, i2);
			three_edges_constraint(buff, third_l, first_l, second_l, true,
					true, false);
		}
	}
}
