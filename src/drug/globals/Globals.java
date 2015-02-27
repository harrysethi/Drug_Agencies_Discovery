package drug.globals;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Globals {

	public static boolean isPart1;

	public static int graph[][];
	public static int numOfV;
	public static int numOfE;
	public static int in_K;

	public static Map<String, Integer> l_map_part1;

	public static int numOfConstraints;

	public static int literalIndex;
	public static int count = 0;
	public static long conCount = 0;

	// specifically for part2
	public static boolean isSat = true;
	public static Map<Integer, String> l_map_part2;

	public static List<Set<Integer>> listOfSet;

	public static PrintWriter pw;
}
