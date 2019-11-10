import java.io.*;
import java.util.*;

public class Levenshtein {

    //Calculate Levenshtein distance without maxDistance threshold.
    public int levenshtein(String lhs, String rhs) {
        int len0 = lhs.length() + 1;
        int len1 = rhs.length() + 1;

        // the array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++)
            cost[i] = i;

        // dynamically computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++) {
            // initial cost of skipping prefix in String s1
            newcost[0] = j;

            // transformation cost for each letter in s0
            for (int i = 1; i < len0; i++) {
                // matching current letters in both strings
                int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int cost_replace = cost[i - 1] + match;
                int cost_insert = cost[i] + 1;
                int cost_delete = newcost[i - 1] + 1;

                // keep minimum cost
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }

            // swap cost/newcost arrays
            int[] swap = cost;
            cost = newcost;
            newcost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[len0 - 1];
    }


    //Calculate Levenshtein distance by applying maxDistance threshold.
    public int levenshtein(String lhs, String rhs, int maxDist) {
        int len0 = lhs.length() + 1;
        int len1 = rhs.length() + 1;

        // the array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++)
            cost[i] = i;

        // dynamically computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++) {
            // initial cost of skipping prefix in String s1
            newcost[0] = j;

            // transformation cost for each letter in s0
            for (int i = 1; i < len0; i++) {
                // matching current letters in both strings
                int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int cost_replace = cost[i - 1] + match;
                int cost_insert = cost[i] + 1;
                int cost_delete = newcost[i - 1] + 1;

                // keep minimum cost
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);

                //Check if the min of head or tail of costs be more than max value, return the expected maxDist + 1.
                // This is because of updating costs takes place up and down ward in dynamic programming by swapping.
                if (Math.min(newcost[0], newcost[len0 - 1]) > maxDist) {
                    return maxDist + 1;
                }
            }


            // swap cost/newcost arrays
            int[] swap = cost;
            cost = newcost;
            newcost = swap;
        }
        //The distance is the cost for transforming all letters in both strings
        return cost[len0 - 1];
    }


    //Calculate duration in nanosecond without breaking threshold.
    public long getDuration(String s, String t) {
        long startTime = System.nanoTime();

        levenshtein(s, t);   //Measure execution time for this method

        long endTime = System.nanoTime();

        long durationInNano = (endTime - startTime);  //Total execution time in nano seconds

        return durationInNano;
    }


    //Calculate duration in millisecond with breaking threshold.
    public long getDuration(String s, String t, int maxDist) {
        long startTime = System.nanoTime();

        levenshtein(s, t, maxDist);   //Measure execution time for this method

        long endTime = System.nanoTime();

        long durationInNano = (endTime - startTime);  //Total execution time in nano seconds

        return durationInNano;
    }


    //The general test function, gets input file contains 2 strings and possible max distance number in each row.
    // Write the results in outputFile, in which, each row contains: input row, result and duration in nano second.
    public void genTest(String inputFile, String outputFile) throws IOException {
        File csvFile = new File("./testFiles/" + inputFile);
        if (csvFile.isFile()) {
            BufferedReader csvReader = new BufferedReader(new FileReader("./testFiles/" + inputFile));
            String row;
            int counter = 1;
            ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
            while ((row = csvReader.readLine()) != null) {//Read test file line by line.
                String[] data = row.split(",");
                int levDist = 0;
                if (data.length == 2) {//Call levenshtein for 2 arguments, the regular one.
                    levDist = levenshtein(data[0], data[1]);
                } else if (data.length == 3) { //Check if the string has 3 slots, then the third indicates maxDistance.
                    int distance = Integer.valueOf(data[2]);
                    levDist = levenshtein(data[0], data[1], distance);
                }

                double performance = performanceMeasure(row, 100);
                ArrayList<String> tempList = new ArrayList<String>();
                tempList.add(row);
                tempList.add(Integer.toString(levDist));
                tempList.add(Double.toString(performance));
                rows.add(tempList);
                System.out.println("Levenshtein distance " + counter + ": " + levDist);
                counter++;
            }
            csvReader.close();
            writeCsv(rows, outputFile);
        }
    }


    //Write the list of results on the file, indicated by outputFile.
    public void writeCsv(ArrayList<ArrayList<String>> rows, String outputFile) throws IOException {
        FileWriter csvWriter = new FileWriter("./testFiles/" + outputFile);
        csvWriter.append("String1");
        csvWriter.append(",");
        csvWriter.append("String2");
        csvWriter.append(",");
        csvWriter.append("Maximum distance");
        csvWriter.append(",");
        csvWriter.append("Levenshtein Distance");
        csvWriter.append(",");
        csvWriter.append("Duration in nanoseconds");
        csvWriter.append("\n");

        for (List<String> rowData : rows) {
            csvWriter.append(String.join(",", rowData));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }


    //For each row, calculate the duration many times (indicated by iterCount) and then return the average.
    //This prevents random effects on performance measurement and brings us mean value of duration as performance metric
    public double performanceMeasure(String row, int iterCount) {
        long[] durations = new long[iterCount];
        String[] data = row.split(",");

        //Stands for the case of no threshold.
        if (data.length == 2) {
            for (int i = 0; i < iterCount; i++) {
                durations[i] = getDuration(data[0], data[1]);
            }
        }
        else if(data.length == 3){ //Assume to have a maxDist threshold.
            int dist = Integer.valueOf(data[2]);
            for (int i = 0; i < iterCount; i++) {
                durations[i] = getDuration(data[0], data[1], dist);
            }
        }

        return Arrays.stream(durations).average().orElse(Double.NaN);
    }

}
