# Java-Tasks
# Levenshtein Algorithm in java
The class Levenshtein contains methods to calculate levenshtein distance between two strings in two cases and test their accuracy and performance.

** levenshtein methods:
- The levenshtein distance in this class is calculated by dynamic programing with two 1-dimensional arrays with the length of two assessing strings (input arguments).
- The algorithm is run on O(n) for memory and O(n^2) for timing, which n is the max lenght of two strings. 
- Two cases of levenshtein distance functions are differentiated by the number of arguments. 
- The first one with two string arguments, calculates the distance without any threshold critera on the maximum distance.
- The second one takes three arguments and applies maximum distance condition to break the loop and return expected value (maxDist +1) in the case of passing this threshold.
- The condition of passing max threshold is checked by checking the head and tail of cost array, because the swaping makes updating in both sides.
- Two methods, could be more concise to consider DRY rule more, but as I liked to test heir timing performance in nanosecond, one extra line for condition could affect that and so I kept it


** Testing and performance:
- Method genTest; tests the accuracy of levenshtein methods and calculates their execution's duration as metrics to measure their performance.
- Input: a csv file contains 3 columns which are indicating first and second strings to be compared and the third one is standing for maximum distance threshold, which could be blank for no distance threshold. Many samples are contained in the directory testFiles and called in main method.
- Output: a csv file with 5 columns, which first two cpolumns are indicating strings to be compared, the third one shows the possible max distance and is blank in the case of pure distance without threshold assumption. The fourth column indicates the result of Levenshtein distance between two strings and finally the last column shows the timing duration in nanoseconds.


** Auxillary methods:

- Method getDuration; calculates the execution time of levenshtein method run over two given strings. 
- Method performanceMeasure; which is called by genTest, gets a row from test input file, separate it into strings and maxDist and then called getDuration many times (indicated by argument iterCount) and then return the mean value of durations as metrics for performance measurement. This is a good way to prevent random values generated because of possible load of system. 
- Method writeCsv; is called by the genTest at the end of operations to write results on output result csv file.


** Performance results:
- Although the max distance limitation will affect on timing, it will be shown in the case of real distance be far away from indicated threshold. To check the randomness of values, statistical tests could be applied but are not necessary as the effects could be explained without significance tests' results and so are neglected in this task.

- The maximum distance threshold doesn't have any effect on memory alocation as memory is assigned at the begining of running algorithm and is fixed in dynamic programming.

