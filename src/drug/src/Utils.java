package drug.src;

import java.util.HashMap;

import drug.globals.Globals;

public class Utils {

	public static void createLiteralMap() {
		// Creating the literals map
		Globals.l_map = new HashMap<String, Integer>();

		// * remember, all vertices loops shall start from 1 & goes up to numOfV
		int literalIndex = 0;
		for (int i = 1; i <= Globals.numOfV; i++) {
			for (int j = i; j <= Globals.numOfV; j++) {

				if (i == j) {
					continue;
				}

				for (int k = 0; k < Globals.in_K; k++) {
					Globals.l_map.put(get_l_str(k, i, j), ++literalIndex);
				}
			}
		}
	}

	public static StringBuffer setP_Buff() {
		StringBuffer p_buff = new StringBuffer("");
		p_buff.append("p cnf ");
		p_buff.append(Globals.l_map.size());
		p_buff.append(" ");
		p_buff.append(Globals.numOfConstraints);
		p_buff.append("\n");
		return p_buff;
	}

	public static void endLineInSAT(StringBuffer buff) {
		buff.append("0\n");
		Globals.numOfConstraints++;
	}

	private static String get_l_str(int k, int i, int j) {
		StringBuffer sb = new StringBuffer("");
		sb.append(i);
		sb.append(",");
		sb.append(j);
		sb.append(":");
		sb.append(k);
		return sb.toString();
	}

	public static int get_l(int k, int i, int j) {
		String temp;

		if (i < j) {
			temp = get_l_str(k, i, j);
		} else {
			temp = get_l_str(k, j, i);
		}

		return Globals.l_map.get(temp);
	}

}
