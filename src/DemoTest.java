import java.io.File;
import java.io.FileNotFoundException;

public class DemoTest {
    public static void main(String[] args) throws FileNotFoundException {

        //store input data
        /*
        File file = new File("bit5.txt");
        //Scanner scanner = new Scanner(file);

        FuzzyData dataFile = new FuzzyData();
        String[][] fuzzyData= dataFile.createBitstringPairsFromFile(file);

        System.out.println("*** INT **");
        FuzzyData dataInt = new FuzzyData();
        String[][] fuzzyDataInt = dataInt.createBitstringPairs(3);
        */

        //decode with hamming ecc - works!
        /*
        ECCHamming ecc = new ECCHamming();
        int actualXORvalue = 0;
        StringBuilder bitStringParity = new StringBuilder("001100010000");

        for(int i = 0; i < bitStringParity.length(); i++){
            if(bitStringParity.charAt(i) == '1')
                actualXORvalue = actualXORvalue ^ i;
        }

        System.out.println(actualXORvalue);
        System.out.println(ecc.getBinaryOfNumber(actualXORvalue,4));
         */

        //enrollment process
        String processedKey = "0110";
        ECCHamming ecc = new ECCHamming();
        System.out.println("without parity: " + processedKey);
        System.out.println("with parity:    " + ecc.addParity(processedKey));

        //authentication process
        String authenticationData = "0100011"; //Error on Position 5
        System.out.println("\n\nfuzzy Data: " + authenticationData + "  (Error on position 5)");
        System.out.println("corrected:  " + ecc.parityRemove(authenticationData, 0));
        //key generation




    }
}
