package drug.testMain;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import drug.globals.Globals;
import drug.src.Constraints;
import drug.src.IO;
import drug.src.Utils;

public class Main {

	// TODO: try on multiple examples :: ensure it's not breaking on any example

	public static void main(String[] args) throws IOException {

		int partNum = Integer.parseInt(args[0]);
		String key = args[1];

		String graphFile = key + ".graph";

		String satInputFile = key + ".satinput";
		String satOutputFile = key + ".satoutput";
		String finalOutputFile = key + ".subgraphs";

		if (partNum == 1) {
			part1(graphFile, satInputFile);
		}

		else {
			part2(graphFile, satOutputFile, finalOutputFile);
		}
	}

	private static void part2(String graphFile, String satOutputFile,
			String finalOutputFile) throws FileNotFoundException, IOException {
		System.out.println("----Start with part2---");
		Globals.isPart1 = false;

		IO.readInput_part1(graphFile);
		Utils.createLiteralMap();
		Globals.pw = new PrintWriter(new BufferedWriter(new FileWriter(
				finalOutputFile)));

		IO.readInput_part2(satOutputFile);

		StringBuilder buff = new StringBuilder("");

		if (Globals.isSat == false) {
			// printing 0 if unsatisfied
			buff.append("0");
			Globals.pw.print(buff);
			Globals.pw.close();
			System.out.println("----Done with part2---");
			return;
		}

		for (int i = 0; i < Globals.in_K; i++) {
			StringBuilder inBuff = new StringBuilder("");

			Set<Integer> mySet = Globals.listOfSet.get(i);
			inBuff.append("#").append(i + 1).append(" ").append(mySet.size());
			inBuff.append("\n");

			for (int j : mySet) {
				inBuff.append(j).append(" ");
			}

			buff.append(inBuff.toString().trim());
			buff.append("\n");
		}

		Globals.pw.print(buff);
		Globals.pw.close();
		System.out.println("----Done with part2---");
	}

	private static void part1(String graphFile, String satInputFile)
			throws FileNotFoundException, IOException {
		System.out.println("----start with part1---");
		Globals.isPart1 = true;

		IO.readInput_part1(graphFile);
		Utils.createLiteralMap();
		Globals.pw = new PrintWriter(new BufferedWriter(new FileWriter(
				satInputFile)));

		StringBuilder p_buff = Utils.setP_Buff();
		Globals.pw.print(p_buff); // TODO: check - #f clauses always 0,#f
									// variables may be wrong

		StringBuilder buff = new StringBuilder("");

		Constraints.constraints_TakingEachEdge_notTakingEdges(buff);

		Globals.pw.print(buff);
		buff = new StringBuilder("");

		Constraints.constraints_noSubgraphEmpty(buff);

		Globals.pw.print(buff);
		buff = new StringBuilder("");

		Constraints.constraints_completeness_connected(buff);

		Globals.pw.print(buff);
		buff = new StringBuilder("");

		Constraints.constraints_subgraph(buff);

		Globals.pw.print(buff);
		buff = new StringBuilder("");

		// IO.printMap();
		Globals.pw.close();

		System.out.println("c: " + Globals.numOfConstraints);
		System.out.println("l: " + Globals.literalIndex);

		System.out.println("----Done with part1---");
		// IO.printSATinput(buff, satInputFile);
	}
}
