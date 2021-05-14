import java.security.NoSuchAlgorithmException;

public class DemoTest {
    public static void main(String[] args) throws NoSuchAlgorithmException {

        FuzzyData data = new FuzzyData();

        for(int i=0; i<100; i++){
            FuzzyCommitment fz = new FuzzyCommitment();
            String s = data.randomString(2048);
            fz.enrollment(s);
            fz.authentication(data.randomFlip(s));
        }
    }
}
