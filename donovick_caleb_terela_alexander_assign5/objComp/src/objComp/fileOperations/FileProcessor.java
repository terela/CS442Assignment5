package objComp.fileOperations;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;
import java.io.File;
import java.nio.file.Files;
import java.nio.charset.Charset;

import objComp.util.MyLogger;
/**
 * FileProcessor - contains methods to read a line and write to a file
 */
public class FileProcessor {

	private String inputFile;
    private String outputFile;

	/**
	 * Constructor - constructs a file processor object
	 *
	 * @return the file processor object
	 */
	public FileProcessor(String inputFileIn, String outputFileIn) {

		MyLogger.printToStdout(2, "Constructor in FileProcessor called.");

		inputFile = inputFileIn;
        outputFile = outputFileIn;
	}

	// read line method
	
	/**
	 * readLine - reads and returns the current line of the file as a string
	 *
	 * @return line the current line of the file
	 */
	public String readLine() {

		MyLogger.printToStdout(3, "readLine() in FileProcessor called.");

		String line = null;

		try {
			FileReader fileReader = new FileReader(inputFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			line = bufferedReader.readLine();

			bufferedReader.close();

		} catch (IOException e) {

			MyLogger.printToStdout(0, "Error message in FileProcessor called.");

			e.printStackTrace();

			System.exit(1);

		} finally {
		    return line;
		}
	}
	
	/**
	 * readAllLines - readAllLines of a files
	 *
	 * @return lines the lines in the file
	 */
    public List<String> readAllLines() {
		MyLogger.printToStdout(3, "readALlLines() in FileProcessor called.");
        List<String> lines = null;
        try {
            lines = Files.readAllLines((new File(inputFile)).toPath());
        } catch (IOException e) {
			MyLogger.printToStdout(0, "Error message in FileProcessor called.");

			e.printStackTrace();

			System.exit(1);
        } finally {
            return lines;
        }
    }
	// write to file method
	
	/**
	 * writeToFile - writes the value parameter to the outputFile parameter
	 *
	 * @return none
	 */
	public void writeToFile(String value) {

		MyLogger.printToStdout(3, "writeToFile() in FileProcessor called.");

		try {

			PrintWriter output = new PrintWriter(new FileWriter(outputFile, false));
			
			output.write(value);
			
			output.close();

		} catch (IOException e) {

			MyLogger.printToStdout(0, "Error message in FileProcessor called.");

			e.printStackTrace();

			System.exit(1);

		} finally {

		}

	}
	
	/**
	 * getInputFile - gets and returns the input file name
	 *
	 * @return inputFile the inputFile name
	 */
	public String getInputFile() {

		MyLogger.printToStdout(3, "getInputFile() in FileProcessor called.");

		return inputFile;

	}

}
