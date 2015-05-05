package objComp.util;

import objComp.util.First;
import objComp.util.Second;
import objComp.util.MyLogger;
import objComp.fileOperations.FileProcessor;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * PopulateObjects - contains the data structure populated with first and second objects
 */
public class PopulateObjects {

	private static final Map<String, Class> classMap;

	static {

		classMap = new HashMap<String, Class>();
		classMap.put("byte", java.lang.Byte.class);
		classMap.put("short", java.lang.Short.class);
		classMap.put("int", java.lang.Integer.class);
		classMap.put("long", java.lang.Long.class);
		classMap.put("float", java.lang.Float.class);
		classMap.put("double", java.lang.Double.class);
		classMap.put("String", java.lang.String.class);

	}


	private Map<First, Integer> firstMap;
	private Map<Second, Integer> secondMap;
	private static final int num_threads = Runtime.getRuntime().availableProcessors();
	private String[] lines;
	private FileProcessor fp;

	/**
	 * PopulateObjects Constructor - constructs a populate objects object
	 *
	 * @return the populate objects object
	 */
	public PopulateObjects(FileProcessor fpIn) {

		if (MyLogger.getMyLoggerValue() == 1) {
			MyLogger.printToStdout(2, "Constructor in PopulateObjects called.");
		}

		fp = fpIn;
		firstMap = new HashMap<First, Integer>();
		secondMap = new HashMap<Second, Integer>();

	}

	/**
	 * deserObjects - reads data member values from the input file and accordingly create instances of first and second
	 *
	 * @return none
	 */
	public void deserObjects() {

		if (MyLogger.getMyLoggerValue() == 1) {
			MyLogger.printToStdout(3, "deserObjects() in PopulateObjects called.");
		}

		List<String> lines = fp.readAllLines();
		String className;
		String[][] vars;

		Class cls;
		Object obj;
		vars = new String[2][];

		try {

			for (int i = 0; i < lines.size(); i += 3) {

				className = getClassName(lines.get(i));

				// debug
				//System.out.println("Class Name: " + className);

				vars[0] = getVarInfo(lines.get(i+1));
				vars[1] = getVarInfo(lines.get(i+2));

				// debug

				/*
				System.out.println("Vars: ");
				for (int k = 0; k < 2; k ++) {

					for (int l = 0; l < 3; l++) {
						System.out.println(vars[k][l]);
					}

				}
				*/

	cls = Class.forName(className);
	obj = cls.newInstance();

	for (int j = 0; j < 2; ++j) {

		Class[] paramTypes = {PopulateObjects.classMap.get(vars[j][1])};
		Method mth = cls.getMethod("set" + vars[j][2], paramTypes);

		Object[] args = {paramTypes[0].getDeclaredConstructor(String.class).newInstance(vars[j][3])};
		mth.invoke(obj, args);

	}

	if (className.equals("objComp.util.First")) {

		firstMap.compute((First)obj, (key, value) -> (value == null) ? 1 : value + 1);

	} else {

		secondMap.compute((Second)obj, (key, value) -> (value == null) ? 1 : value + 1);

	}
			}
		}  catch (Exception e) {

			if (MyLogger.getMyLoggerValue() == 1) {
				MyLogger.printToStdout(1, "Error message in FileProcessor called.");
			}
			e.printStackTrace();
			System.exit(1);

		} finally {}
	}


	/**
	 * total - returns the total number of instances of first and second
	 *
	 * @return total the integer array containing the totals
	 */
	public int[] totals() {

		if (MyLogger.getMyLoggerValue() == 1) {
			MyLogger.printToStdout(3, "totals() in PopulateObjects called.");
		}

		int[] total = new int[4];
		int numFirst = 0;
		int numSecond = 0;

		for (Map.Entry<First, Integer> i: firstMap.entrySet()) {

			int val = i.getValue();
			numFirst += val;

		}

		for (Map.Entry<Second, Integer> i: secondMap.entrySet()) {

			int val = i.getValue();
			numSecond += val;

		}

		// non dupe first
		total[0] = firstMap.size();
		// total first
		total[1] = numFirst;
		// non dupe second
		total[2] = secondMap.size();
		// total second
		total[3] = numSecond;

		return total;

	}

	/**
	 * getVarInfo - returns the variasble information
	 *
	 * @return a string array containing the variable information
	 */
	private String[] getVarInfo(String line) {

		if (MyLogger.getMyLoggerValue() == 1) {
			MyLogger.printToStdout(3, "getVarInfo() in PopulateObjects called.");
		}

		String[] tokens = line.split("\\s?type=|,\\s+var=|,\\s+value=");

		return tokens;

	}

	/**
	 * getClassName - returns the class name
	 *
	 * @return the class name
	 */
	private String getClassName(String line) {

		if (MyLogger.getMyLoggerValue() == 1) {
			MyLogger.printToStdout(3, "getClassName() in PopulateObjects called.");
		}

		return line.substring(4);

	}

}
