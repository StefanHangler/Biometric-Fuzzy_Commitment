public class KeyGenerator {

    /**
     * creates a random bit-string with given length
     * @param lengthOfKey
     * @return the random key
     */
    public String randomKey(int lengthOfKey){
        StringBuilder randKey = new StringBuilder();

        for(int i = 0; i < lengthOfKey; i++){
            double rand = Math.random();
            if (rand <= 0.5)
                randKey.append('0');
            else
                randKey.append('1');
        }
        return randKey.toString();
    }
}


