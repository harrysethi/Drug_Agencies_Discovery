package drug.src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import drug.globals.Globals;

public class IO {
	public static void readInput() throws FileNotFoundException, IOException {
		// TODO take input filename from arguments
		BufferedReader br = new BufferedReader(new FileReader("data/input1"));

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

	public static void printMap() {
		for (Entry<String, Integer> entry : Globals.l_map.entrySet()) {
			System.out.println(entry.getValue() + " : " + entry.getKey());
		}
	}

	public static void printSATinput(StringBuffer buff, StringBuffer p_buff)
			throws IOException {
		// TODO take output filename from arguments
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
				"data/output")));

		pw.print(p_buff.toString());
		pw.print(buff.toString());

		pw.close();
	}
}
