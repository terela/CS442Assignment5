package objComp.util;

import objComp.util.First;
import objComp.util.Second;

import objComp.util.MyLogger;
import objComp.fileOperations.FileProcessor;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
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

    private class Worker implements Runnable {
        private String[] lines;

        private final int s;
        private final int e;

        private Map<First, Integer> firstMap;
        private Map<Second, Integer> secondMap;
        public Worker(int sIn, int eIn, Map<First, Integer> firstMapIn, Map<Second, Integer> secondMapIn) {
            s = sIn;
            e = eIn;
            firstMap = firstMapIn;
            secondMap = secondMapIn;
        } 

        public void setLines(String[] linesIn) {
            lines = linesIn;
        }

        /**
         *  
         *      
         *      
         *      
         **/
        @Override
        public void run() {
            String className;
            String[][] vars;

            Class cls;
            Object obj;
            vars = new String[2][];
            Method[] mth = new Method[2];
            Class[][] paramTypes = new Class[2][];
            Object[][] args = new Object[2][];

            for (Object[] o: args) {
                o = new Object[1];
            }

            for (Class[] c: paramTypes) {
                c = new Class[1];
            }
            try {
                for (int i = s; i < e; i += 3) {
                    className = getClassName(lines[i]);
		    System.out.println("Class Name: " + className);
                    vars[0] = getVarInfo(lines[i+1]);
                    vars[1] = getVarInfo(lines[i+2]);
		    
		    /*System.out.println("Vars: ");
		    for (int k = 0; k < 2; k ++) {

			    for (int l = 0; l < 3; l++) {
				    System.out.println(vars[k][l]);
			    }

		    }*/

                    cls = Class.forName(className);
                    obj = cls.newInstance();

                    for (int j = 1; j < 3; ++j) {
                        paramTypes[j][0] = PopulateObjects.classMap.get(vars[j][0]);
                        mth[j] = cls.getMethod("set" + vars[j][1], paramTypes[j]);
                        args[j][0] = paramTypes[j][0].getDeclaredConstructor(String.class).newInstance(vars[j][2]);
                        mth[j].invoke(obj, args[j]);
                    }
                    if (className.equals("First")) {
                        firstMap.compute((First)obj, (key, value) -> (value == null) ? 1 : value + 1);
                    } else {
                        secondMap.compute((Second)obj, (key, value) -> (value == null) ? 1 : value + 1);
                    }
                }
            }  catch (Exception e) {
                //
            }
        }

        private String getClassName(String line) {
            /* 
             *  takes a line containing fqn:<className>
             *  returns <className>
             */
            return line.substring(4);

        }

        private String[] getVarInfo(String line) {
            /* 
             * takes type=<typeName>, var=<varName>, value=<varValue>
             *  returns [<typeName>, <varName>, <varValue>]
             */
            //String s[] = {"int", "IntValue", "44"};	
		String[] tokens = line.split("\\s?type=|,\\s+var=|,\\s+value=");
/*
		System.out.println("Tokens: " + tokens.length + ", from:\n" + line);
		for (int k = 0; k < tokens.length; k ++) {
		
			System.out.println(tokens[k]);
		
		}
*/		
		assert (tokens.length == 4);
		return tokens;

        }


    }

    private Map<First, Integer> firstMap;
    private Map<Second, Integer> secondMap;
    private static final int num_threads = Runtime.getRuntime().availableProcessors();

    private static Integer size = null;

    private static Worker[] workers;
    private static Thread[] threads;

    private String[] lines;

    private FileProcessor fp;

    public PopulateObjects(FileProcessor fpIn) {

        fp = fpIn;
	if (size == null) {
	        size = fp.readAllLines().size();
		workers = new Worker[num_threads];
	        threads = new Thread[num_threads];
	}

        int work = (size/3)/num_threads;

                firstMap = new ConcurrentHashMap<First, Integer>();
        secondMap = new ConcurrentHashMap<Second, Integer>();

        for (int i = 0; i < num_threads-1; i++) {
            workers[i] = new Worker(i*work*3, (i+1)*work*3, firstMap, secondMap);
        }

        workers[num_threads -1] = new Worker((num_threads - 1)*work*3, size, firstMap, secondMap);

    }

    /**
     * deserObjects - reads data member values from the input file and accordingly create instances of first and second
     *
     * @return none
     *
     * THIS NEEDS PARAMETERS AND A RETURN VALUE
     */
    public void deserObjects() {
        List<String> lines = fp.readAllLines();
        String[] arr = lines.toArray(new String[size]);

	//System.out.println("Lines: " + lines.toString());

        for (int i = num_threads - 1; i >= 0; --i) {
            workers[i].setLines(arr);
            threads[i] = new Thread(workers[i]);
            threads[i].start();
        }

        try {
            for (Thread t: threads) {
                t.join();
            }
        } catch (Exception e) {
            MyLogger.printToStdout(0, "Error message in deserObjects called.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * nonDuplicateTotal - returns the total number of instances of first and second
     *
     * @return total the integer array containing the totals
     */
    public int[] totals() {

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

}
