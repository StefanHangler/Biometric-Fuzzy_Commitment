import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class FuzzyCommitment {

    //mit de indexe geht des eher ned so, ma kann ja mehrere enrollen, woher weiß i dann welches delta i brauch
    public String[][] enrollment;
    public int indexEnrollment;

    public String[][] authentication;
    public int indexAuthentication;

    public String delta;

    public FuzzyCommitment(){
        enrollment = new String[10][2];
        indexEnrollment = 0;

        authentication = new String[10][2];
        indexAuthentication = 0;

        delta = "";
    }

    public String xorStrings(String inputData, String key){
        StringBuilder xorString = new StringBuilder();

        if(inputData.length() == key.length()){
            for(int i = 0; i < inputData.length(); i++) {
                xorString.append((inputData.charAt(i) ^ key.charAt(i)));
            }
        }
        else
            throw new RuntimeException("Given Strings do not have the same length -> same length needed for XOR-operation \n" +
                    "input data: " + inputData + "\n  length = " + inputData.length() +
                    "key data: " + key+ "\n  length = " + key.length());

        return xorString.toString();
    }

    public String SHA256hashing(String bits) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(
                bits.getBytes(StandardCharsets.UTF_8));
        System.out.println(Arrays.toString(encodedhash));

        return new String(encodedhash);
    }

    public void enrollment (String biometricData) throws NoSuchAlgorithmException {

        // key and hash of key
        KeyGenerator keyGen = new KeyGenerator();
        String key = keyGen.randomKey(1172);
        String hashKey = SHA256hashing(key);

        // ecc of key
        ECCHamming ecc = new ECCHamming();
        String eccKey = ecc.addParity(key);

        // expansion of biometric data
        String data = "000" + biometricData;

        // xor of data and key (delta)
        String xor = xorStrings(data, eccKey);

        enrollment[indexEnrollment][0] = hashKey;
        enrollment[indexEnrollment][1] = xor;

        indexEnrollment++;


        //nur für speichern mal
        delta = xor;
    }

    public void authentication (String biometricData) throws NoSuchAlgorithmException {
        ECCHamming ecc = new ECCHamming();

        // expansion of biometric data
        String tryData = "000" + biometricData;

        // xor of data and delta
        String xor = xorStrings(tryData, delta);

        // decode
        String tryKey = ecc.parityRemove(xor, 0);

        // hash
        String hashKey = SHA256hashing(tryKey);

        authentication[indexAuthentication][0] = hashKey;
        authentication[indexAuthentication][1] = delta;

        indexAuthentication++;

        /*
        //überlegung für authentication
        for (int i = 0; i < 10; i++){
            delta = enrollment[i][1]
            newKey = xor(tryData, delta)
            newKey.removeparity
            newHash = hash(xor)
            wenn newHash == enrollment[i][0] --> successsss
        }
        */
    }
}
