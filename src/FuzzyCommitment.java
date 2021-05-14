import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class FuzzyCommitment {

    private final ArrayList<ArrayList<String>> enrollment;

    public FuzzyCommitment(){
        enrollment = new ArrayList<>();
    }

    public void enrollment (String biometricData) throws NoSuchAlgorithmException {
        ArrayList<String> keyPair = new ArrayList<>();
        StringBuilder key;
        StringBuilder eccKey = new StringBuilder();

        // key and hash of key
        KeyGenerator keyGen = new KeyGenerator();
        key = new StringBuilder(keyGen.randomKey(1172));
        String hashKey = SHA256hashing(key.toString());

        // ecc of key
        ECCHamming ecc = new ECCHamming();

        //add parity to every 4 bit block of the key
        for(int i = 0; i < key.length(); i+=4)
             eccKey.append(ecc.addParity(key.substring(i,i+4)));

        // expansion of biometric data
        String data = "000" + biometricData;

        // xor of data and key (delta)
        String delta = xorStrings(data, eccKey.toString());

        keyPair.add(delta);
        keyPair.add(hashKey);

        this.enrollment.add(keyPair);
    }

    public void authentication (String biometricData) throws NoSuchAlgorithmException {
        ECCHamming ecc = new ECCHamming();
        StringBuilder xor = new StringBuilder();
        String hashKey;
        StringBuilder tryKey = new StringBuilder();

        // expansion of biometric data
        String tryData = "000" + biometricData;

        // xor of every stored delta and check if any of these is equal to the hashKey
        for(ArrayList<String> keyPair : this.enrollment) {
            xor.append(xorStrings(tryData, keyPair.get(0)));

            // decode every 4 bit Block with the correct parity bits of the given index i
            int indexParity = 0;
            for (int blockIndex = 0; blockIndex < keyPair.get(0).length(); blockIndex += 7) {
                tryKey.append(ecc.parityRemove(xor.substring(blockIndex,blockIndex+7), indexParity));
                indexParity++;
            }

            //hash
            hashKey = SHA256hashing(tryKey.toString());

            //check if the authentication was successful
            if(keyPair.get(1).equals(hashKey)) {
                System.out.println("Authentication successful");
                return;
            }
        }
        System.out.println("Authentication failed");
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
}
