CS442 Design Patterns
Spring 2015
ASSIGNMENT #5 README FILE

Due Date: <Monday, May 4, 2015>
Submission Date: <Monday, May 4, 2015>
Grace Period Used This Project: <0> Days
Grace Period Remaining: <0> Days
Author(s): <CALEB DONOVICK> <ALEXANDER TERELA>
e-mail(s): <CDONOVI1@BINGHAMTON.EDU> <ATERELA1@BINGHAMTON.EDU>


PURPOSE:

This assignment models java reflection and object comparison

PERCENT COMPLETE:

We believe that we have completed 100% of this project.

PARTS THAT ARE NOT COMPLETE:

N/A

BUGS:

None

FILES:

  Included with this project are 6 files:

  Driver - tests the objComp program
  FileProcessor - contains methods to read the input file a line at a time
  First - models the first object
  Second - models the second object
  PopulateObjects - contains the data structure populated with first and second objects
  MyLogger - simulates the java logger class

  README.txt, the text file you are presently reading

	In the README.txt, there are 4 levels that the MyLogger class can be set to:
		- 0: Outputs the 5 output lines of the Driver
		- 1: Only output from PopulateObjects is printed
		- 2: Output is printed when constructors are called
		- 3: Output is printed when other methods are called
		- 4: No output is printed at all

JUSTIFICATION:

We chose to use the hash map data structure in order to track when duplicate First or Second objects existed in the input file and because a hash map has a O(1) complexity for searching and inserting into the data structure. For further optimizations, we utilized flags in our build.xml file in order to allocate a higher amount of memory to improve program performance and called the aggressive optimization flag for the JVM.

SAMPLE OUTPUT:

Alexs-MacBook-Air-6:objComp alex$ ant -buildfile src/build.xml -Darg0=input/inputbig.txt -Darg1=5 -Darg2=0 all run
Buildfile: /Users/alex/Documents/BU/Junior Spring 2015/CS 442/Assignment5/donovick_caleb_terela_alexander_assign5/objComp/src/build.xml

prepare:

objComp:

compile_all:

all:

jar:

run:
     [java] Number of non-duplidate First objects: 237
     [java] Total Number of First objects: 474
     [java] Number of non-duplidate Second objects: 263
     [java] Total Number of Second objects: 526
     [java] Total time: 89 milliseconds.

BUILD SUCCESSFUL
Total time: 1 second

TO COMPILE:

Assuming you are in the directory containing this README:
extract the files
“make”
in command line: ant -buildfile src/build.xml all

TO RUN:

## To run, edit the build.xml to enter args (search for arg0, arg1, arg2, arg3)
Assuming you are in the directory containing this README:

ex. ant -buildfile src/build.xml -Darg0=‘inputFile.txt’ -Darg1=NUM_ITERATIONS ex. ‘0’, or ‘1’, or ‘2’, or ‘3’, or ‘4’ Darg2=DUBUG_VALUE ex. ‘0’, or ‘1’, or ‘2’, or ‘3’, or ‘4 run

ant -buildfile src/build.xml -Darg0=inputsmall.txt -Darg1=5 -Darg2=0 run

EXTRA CREDIT:

We created two new methods in First and Second, setIntValue, which uses the box primitive Integer type and setDoubleValue, which uses the box primitive Double type. In our deserObjects method, we utilized the built in java method getDeclaredConstructor to read these box primitive types. This allowed us to further generalize our code than required in the assignment guidelines and allowed us to prevent it being cluttered with if statements.

BIBLIOGRAPHY:

This serves as evidence that we are in no way intending Academic Dishonesty.
<CALEB DONOVICK> <ALEXANDER TERELA>

ACKNOWLEDGEMENT:

  During the coding process one or more of the following people may have been
  consulted. Their help is greatly appreciated.

	A. Thompson
