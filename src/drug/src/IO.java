package drug.src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import drug.globals.Globals;

public class IO {

	public static void readInput_part1(String graphFile)
			throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(graphFile));

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

	public static void readInput_part2(String satOutputFile)
			throws FileNotFoundException, IOException {

		BufferedReader br = new BufferedReader(new FileReader(satOutputFile));

		String line = br.readLine();
		if (line.equalsIgnoreCase("UNSAT")) {
			Globals.isSat = false;
			br.close();
			return;
		}

		StringTokenizer st = new StringTokenizer(br.readLine());

		int num = Integer.parseInt(st.nextToken());

		Globals.listOfSet = new LinkedList<Set<Integer>>();
		for (int i = 0; i < Globals.in_K; i++) {
			Set<Integer> newSet = new HashSet<Integer>();
			Globals.listOfSet.add(newSet);
		}

		while (num != 0) {

			if (num > Globals.l_map_part2.size()) {
				break;
			}

			if (num < 0) {
				num = Integer.parseInt(st.nextToken());
				continue;
			}

			String tmp_str = Globals.l_map_part2.get(num);
			StringTokenizer tmpStr_token = new StringTokenizer(tmp_str, ",");

			int v1 = Integer.parseInt(tmpStr_token.nextToken());
			int v2 = Integer.parseInt(tmpStr_token.nextToken());
			int K = Integer.parseInt(tmpStr_token.nextToken());

			Set<Integer> K_Set = Globals.listOfSet.get(K);
			K_Set.add(v1);
			K_Set.add(v2);

			num = Integer.parseInt(st.nextToken());
		}

		br.close();
	}

	public static void printMap() {
		for (Entry<String, Integer> entry : Globals.l_map_part1.entrySet()) {
			System.out.println(entry.getValue() + "  ->  " + entry.getKey());
		}
	}

	public static void printSATinput(StringBuffer buff, String satInputFile)
			throws IOException {
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
				satInputFile)));
		StringBuffer p_buff = Utils.setP_Buff();
		pw.print(p_buff.toString());
		pw.print(buff.toString());

		pw.close();
	}

	public static void printFinalOutput(StringBuffer buff,
			String finalOutputFile) throws IOException {
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
				finalOutputFile)));
		pw.print(buff.toString());

		pw.close();
	}
}
