package drug.src;

import java.util.HashMap;

import drug.globals.Globals;

public class Utils {

	public static void createLiteralMap() {
		// Creating the literals map
		if (Globals.isPart1) {
			Globals.l_map_part1 = new HashMap<String, Integer>();
		} else {
			Globals.l_map_part2 = new HashMap<Integer, String>();
		}

		// * remember, all vertices loops shall start from 1 & goes up to numOfV
		for (int i = 1; i <= Globals.numOfV; i++) {
			for (int j = i; j <= Globals.numOfV; j++) {

				if (i == j) {
					continue;
				}

				for (int k = 0; k < Globals.in_K; k++) {
					int tmp_index = gt_l_index();
					String tmp_str = get_l_str(k, i, j);

					if (Globals.isPart1) {
						Globals.l_map_part1.put(tmp_str, tmp_index);
					}

					else {
						Globals.l_map_part2.put(tmp_index, tmp_str);
					}
				}
			}
		}
	}

	public static StringBuilder setP_Buff() {
		StringBuilder p_buff = new StringBuilder("");
		p_buff.append("p cnf ");
		p_buff.append(Globals.literalIndex);
		p_buff.append(" ");
		p_buff.append(Globals.numOfConstraints);
		p_buff.append("\n");
		return p_buff;
	}

	private static String get_l_str(int k, int i, int j) {
		StringBuilder sb = new StringBuilder("");
		sb.append(i);
		sb.append(",");
		sb.append(j);
		sb.append(",");
		sb.append(k);
		return sb.toString();
	}

	public static int get_l(int k, int i, int j) {
		String temp;

		// TODO: this should be fine
		if (i < j) {
			temp = get_l_str(k, i, j);
		} else {
			temp = get_l_str(k, j, i);
		}

		return Globals.l_map_part1.get(temp);
	}

	/*
	 * public static String get_l_debug(int k, int i, int j) { // TODO: check
	 * shall // be deleted String temp;
	 * 
	 * // TODO: this should be fine if (i < j) { temp = get_l_str(k, i, j); }
	 * else { temp = get_l_str(k, j, i); }
	 * 
	 * return temp; }
	 */

	public static int gt_l_index() {
		return ++Globals.literalIndex;
	}

	public static void endLineInSAT(StringBuilder buff) {
		buff.append("0\n");
		Globals.numOfConstraints++;
	}

}
