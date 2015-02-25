package drug.testMain;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import drug.globals.Globals;

public class Main {

	public static void main(String[] args) throws IOException {

		readInput();

		StringBuffer buff = new StringBuffer("");

		createLiteralMap();

		constraints_TakingEachEdge_notTakingEdges(buff);

		constraints_noSubgraphEmpty(buff);

		// Constraints - triangles
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

							int first_l = get_l(k, i1, j1);
							int second_l = get_l(k, i2, j2);
							int third_l;

							if (i1 == j2) {
								third_l = get_l(k, i2, j1);
								triangle_constraints(buff, first_l, second_l,
										third_l);
							}

							else if (i2 == j1) {
								third_l = get_l(k, i1, j2);
								triangle_constraints(buff, first_l, second_l,
										third_l);
							}

							else {
								// checking the graph as connected
								if (Globals.graph[i1][j2] == 1) {
									third_l = get_l(k, i1, j2);
									three_edges_constraint(buff, third_l,
											first_l, second_l, true, true,
											false);
								}

								if (Globals.graph[j1][i2] == 1) {
									third_l = get_l(k, j1, i2);
									three_edges_constraint(buff, third_l,
											first_l, second_l, true, true,
											false);
								}

								if (Globals.graph[j1][j2] == 1) {
									third_l = get_l(k, j1, j2);
									three_edges_constraint(buff, third_l,
											first_l, second_l, true, true,
											false);
								}

								if (Globals.graph[i1][i2] == 1) {
									third_l = get_l(k, i1, i2);
									three_edges_constraint(buff, third_l,
											first_l, second_l, true, true,
											false);
								}

							}

						}
					}

				}
			}
		}

		printMap();

		StringBuffer p_buff = setP_Buff();

		printSATinput(buff, p_buff);
	}

	private static void triangle_constraints(StringBuffer buff, int first_l,
			int second_l, int third_l) {
		three_edges_constraint(buff, third_l, first_l, second_l, true, true,
				false);

		three_edges_constraint(buff, third_l, first_l, second_l, true, false,
				true);

		three_edges_constraint(buff, third_l, first_l, second_l, false, true,
				true);
	}

	private static void three_edges_constraint(StringBuffer buff, int third_l,
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

		endLineInSAT(buff);
	}

	private static void printSATinput(StringBuffer buff, StringBuffer p_buff)
			throws IOException {
		// TODO take output filename from arguments
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
				"data/output")));

		pw.print(p_buff.toString());
		pw.print(buff.toString());

		pw.close();
	}

	private static void readInput() throws FileNotFoundException, IOException {
		// TODO take input filename from arguments
		BufferedReader br = new BufferedReader(new FileReader(
				"data/input1"));

		StringTokenizer st = new StringTokenizer(br.readLine());

		Globals.numOfV = Integer.parseInt(st.nextToken());
		Globals.numOfE = Integer.parseInt(st.nextToken());
		Globals.in_K = Integer.parseInt(st.nextToken());

		Globals.graph = new int[Globals.numOfV + 1][Globals.numOfV + 1];

		// Marking the edges
		for (int k = 0; k < Globals.numOfE; k++) {
			st = new StringTokenizer(br.readLine());
			int i = Integer.parseInt(st.nextToken());
			int j = Integer.parseInt(st.nextToken());
			Globals.graph[i][j] = 1;
			Globals.graph[j][i] = 1;
		}

		br.close();
	}

	private static void createLiteralMap() {
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

	private static void printMap() {
		for (Entry<String, Integer> entry : Globals.l_map.entrySet()) {
			System.out.println(entry.getValue() + " : " + entry.getKey());
		}
	}

	private static void constraints_noSubgraphEmpty(StringBuffer buff) {
		// 3. no subgraph should be empty
		for (int k = 0; k < Globals.in_K; k++) {
			for (int c = k + 1; c <= Globals.l_map.size(); c += Globals.in_K) {
				buff.append(c);
				buff.append(" ");
			}
			endLineInSAT(buff);
		}
	}

	private static void constraints_TakingEachEdge_notTakingEdges(
			StringBuffer buff) {
		for (int i = 1; i <= Globals.numOfV; i++) {
			for (int j = i + 1; j <= Globals.numOfV; j++) {

				if (i == j) {
					continue;
				}

				if (Globals.graph[i][j] == 1) {
					// 1. Taking each edge at-least once
					for (int k = 0; k < Globals.in_K; k++) {
						buff.append(get_l(k, i, j));
						buff.append(" ");
					}
					endLineInSAT(buff);
				} else {
					// 2. Not taking the unavailable edges
					for (int k = 0; k < Globals.in_K; k++) {
						buff.append("-");
						buff.append(get_l(k, i, j));
						buff.append(" ");
						endLineInSAT(buff);
					}
				}

			}
		}
	}

	private static StringBuffer setP_Buff() {
		StringBuffer p_buff = new StringBuffer("");
		p_buff.append("p cnf ");
		p_buff.append(Globals.l_map.size());
		p_buff.append(" ");
		p_buff.append(Globals.numOfConstraints);
		p_buff.append("\n");
		return p_buff;
	}

	private static void endLineInSAT(StringBuffer buff) {
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

	private static int get_l(int k, int i, int j) {
		String temp;

		if (i < j) {
			temp = get_l_str(k, i, j);
		} else {
			temp = get_l_str(k, j, i);
		}

		return Globals.l_map.get(temp);
	}
}
