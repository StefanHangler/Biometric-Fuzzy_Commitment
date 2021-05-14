import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;

public class DemoTest {
    public static void main(String[] args) throws NoSuchAlgorithmException {

        FuzzyData data = new FuzzyData();
        String[][] bitstrings = data.createBitstringPairs(100, 2048);

        int count = 1;

        for(String[] strArr: bitstrings){
            FuzzyCommitment fz = new FuzzyCommitment();
            System.out.println("*** Enrollment and Authentication Nr. " + count + " ***");
            fz.enrollment(strArr[0]);
            fz.authentication(strArr[1]);

            count++;
        }
    }
}
