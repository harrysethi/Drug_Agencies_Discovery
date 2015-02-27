package drug.src;

import drug.globals.Globals;

public class ConstraintsHelper {

	public static void three_edges_constraint(StringBuilder buff, int third_l,
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

		Globals.conCount++;
		Utils.endLineInSAT(buff);
	}

	public static void triangle_constraint(StringBuilder buff, int first_l,
			int second_l, int third_l) {
		three_edges_constraint(buff, third_l, first_l, second_l, true, true,
				false);

		three_edges_constraint(buff, third_l, first_l, second_l, true, false,
				true);

		three_edges_constraint(buff, third_l, first_l, second_l, false, true,
				true);
	}

	public static void checkingGraphConnected_constraint(StringBuilder buff,
			int k, int i1, int j1, int i2, int j2, int first_l, int second_l) {
		int third_l;
		// checking the graph as connected

		if (i1 != j2) {
			third_l = Utils.get_l(k, i1, j2);
			three_edges_constraint(buff, third_l, first_l, second_l, true,
					true, false);
		}

		if (j1 != i2) {
			third_l = Utils.get_l(k, j1, i2);
			three_edges_constraint(buff, third_l, first_l, second_l, true,
					true, false);
		}

		if (j1 != j2) {
			third_l = Utils.get_l(k, j1, j2);
			three_edges_constraint(buff, third_l, first_l, second_l, true,
					true, false);
		}

		if (i1 != i2) {
			third_l = Utils.get_l(k, i1, i2);
			three_edges_constraint(buff, third_l, first_l, second_l, true,
					true, false);
		}
	}
}
