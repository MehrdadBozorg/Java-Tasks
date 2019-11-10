
import java.io.IOException;
import java.util.Date;

public class MainClass {
    public static void main(String[] args) throws IOException {
        Levenshtein lev = new Levenshtein();
        lev.genTest("test2.csv", "result2.csv");
        lev.genTest("test1.csv", "result1.csv");
        lev.genTest("test3.csv", "result3.csv");
        lev.genTest("test4.csv", "result4.csv");
        lev.genTest("test5.csv", "result5.csv");


    }

}
