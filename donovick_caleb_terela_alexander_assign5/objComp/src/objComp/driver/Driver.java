package objComp.driver;

import objComp.util.First;
import objComp.util.Second;
import objComp.fileOperations.FileProcessor;
import objComp.util.PopulateObjects;
import objComp.util.MyLogger;

/**
 * Driver - tests the objComp program
 */
public class Driver {

	public static void main(String args[]) {

		// validate command line arguments

		if (args.length < 2) {
			System.err.println("Usage: to run, edit the build.xml file to enter args\nOr refer to the README.txt for proper usage!");
			System.exit(1);
		}

		try {
			Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.err.println("Second parameter must be an integer!");
			System.exit(1);
		} finally {}

		try {
			Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			System.err.println("Third parameter must be an integer!");
			System.exit(1);
		} finally {}

		String inputFile = args[0];
		//String outputFile = args[1];
		int n = Integer.parseInt(args[1]);
		int myLoggerValue = Integer.parseInt(args[2]);

		long startTime = System.currentTimeMillis();

		// start of N loop

		FileProcessor fileProcessor;
		PopulateObjects populateObjects = null;

		for (int i = 0; i < n; i++) {

			fileProcessor = new FileProcessor(inputFile);
			populateObjects = new PopulateObjects(fileProcessor);

			populateObjects.deserObjects();

		}

		long finishTime = System.currentTimeMillis();

		long total_time = (finishTime - startTime) / n;

		//System.out.println("Total iteration time: " + total_time + " millisecond.");

		int[] totals = populateObjects.totals();

		if (myLoggerValue == 0) {

			System.out.println("Number of non-duplidate First objects: " + totals[0]);
			System.out.println("Total Number of First objects: " + totals[1]);
			System.out.println("Number of non-duplidate Second objects: " + totals[2]);
			System.out.println("Total Number of Second objects: " + totals[3]);

			System.out.println("Total time: " + total_time + " milliseconds.");

		}

	}
}
