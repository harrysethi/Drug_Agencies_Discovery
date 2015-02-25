package drug.testMain;

import java.io.IOException;

import drug.globals.Globals;
import drug.src.Constraints;
import drug.src.IO;
import drug.src.Utils;

public class Main {

	public static void main(String[] args) throws IOException {

		IO.readInput();

		StringBuffer buff = new StringBuffer("");

		Utils.createLiteralMap();

		Constraints.constraints_TakingEachEdge_notTakingEdges(buff);

		Constraints.constraints_noSubgraphEmpty(buff);

		Constraints.constraints_completeness_connected(buff);

		// Constraints_subgraph
		for (int k = 0; k < Globals.in_K; k++) {

			for (int i1 = 1; i1 <= Globals.numOfV; i1++) {
				for (int j1 = i1; j1 <= Globals.numOfV; j1++) {

				}
			}

		}

		IO.printMap();

		StringBuffer p_buff = Utils.setP_Buff();

		IO.printSATinput(buff, p_buff);
	}

}
