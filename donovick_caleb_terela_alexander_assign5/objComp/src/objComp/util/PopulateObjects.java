package objComp.util;

import objComp.util.First;
import objComp.util.Second;

import objComp.util.MyLogger;
import objComp.fileOperations.FileProcessor;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * PopulateObjects - contains the data structure populated with first and second objects
 */
public class PopulateObjects {
    private class Worker implements Runnable {
        private final String[] lines;

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
         *  TODO:
         *      get className
         *      get varInfo
         *      
         **/
        @Override
        public void run() {
            String className;
            String[][] vars;

            Class cls;
            Object obj;
            Method[] mth = new Method[2];
            Class[][] paramTypes = new Class[2][];
            Object[][] args = new Object[2][];

            for (Object[] o: args) {
                o = new Object[1];
            }

            for (Class[] c: paramTypes) {
                c = new Class[1];
            }

            for (int i = s; i < e; i += 3) {
                className = getClassName(lines[i]);
                vars[0] = getVarInfo(lines[i+1]);
                vars[1] = getVarInfo(lines[i+2]);


                cls = Class.forName(className);
                obj = cls.newInstance();

                for (int j = 0; j < 2; ++j) {
                    paramTypes[j][0] = stringToClass(vars[j][0]);
                    mth[j] = cls.getMethod("set" + vars[j][1], paramTypes[j]);
                    args[j][0] = stringToObject(paramTypes[j][0], vars[j][2]);
                    cls.invoke(obj, args[j]);
                }
                if (className.equals("First")) {
                    firstMap.compute(obj, (key, value) -> (value == null) ? 1 : value + 1);
                } else {
                    secondMap.compute(obj, (key, value) -> (value == null) ? 1 : value + 1);
                }
            }
        }

        private final static String getClassName(String line) {
            /* 
             * TODO:
             *  takes a line containing fqn:<className>
             *  returns <className>
             */

        }

        private final static String[3] getVarInfo(String line) {
            /* 
             * TODO:
             * takes type=<typeName>, var=<varName>, value=<varValue>
             *  returns [<typeName>, <varName>, <varValue>]
             */
        }

        private final static Class stringToClass(String className) {
            /**
             * TODO:
             * takes "int|double|String"
             * returns Integer.TYPE|Double.TYPE|String.class
             */
        }

        private final static Object stringToObject(Class cls, String val) {
            /**
             * TODO:
             * return object of type cls with value val
             */
        }

    }

    private Map<First, Integer> firstMap;
    private Map<Second, Integer> secondMap;
    private static final int num_threads = Runtime.getRuntime().availableProcessors();

    private int size;

    private Worker[num_threads] workers;
    private Thread[num_threads] threads;

    private String[] lines;

    private FileProcessor fp;

    public PopulateObjects(FileProcessor fpIn) {

        fp = fpIn;
        size = fp.readAllLines.size();
        arr = new String[size];

        int work = (size/3)/num_threads;

        workers = new Worker[num_threads];
        threads = new Thread[num_threads];
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
}
