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
    
    public PopulateObjects(FileProcessor fpIn) {
        fp = fpIn;
        firstMap = new HashMap<First, Integer>();
        secondMap = new HashMap<Second, Integer>();     
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
        String className;
        String[][] vars;

        Class cls;
        Object obj;
        vars = new String[2][];
        try {
            for (int i = 0; i < lines.size(); i += 3) {
                className = getClassName(lines.get(i));
                System.out.println("Class Name: " + className);
                vars[0] = getVarInfo(lines.get(i+1));
                vars[1] = getVarInfo(lines.get(i+2));

                /*System.out.println("Vars: ");
                  for (int k = 0; k < 2; k ++) {

                  for (int l = 0; l < 3; l++) {
                  System.out.println(vars[k][l]);
                  }

                  }*/

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
			MyLogger.printToStdout(0, "Error message in FileProcessor called.");
			e.printStackTrace();
			System.exit(1);
        } finally {}
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

    private String[] getVarInfo(String line) {
        /* 
         * takes type=<typeName>, var=<varName>, value=<varValue>
         *  returns [<typeName>, <varName>, <varValue>]
         */
        //String s[] = {"int", "IntValue", "44"};	
        String[] tokens = line.split("\\s?type=|,\\s+var=|,\\s+value=");
        return tokens;

    }
    private String getClassName(String line) {
        /* 
         *  takes a line containing fqn:<className>
         *  returns <className>
         */
        return line.substring(4);

    }

}
