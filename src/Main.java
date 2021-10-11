public class Main {

    public static void main(String[] args) {
	    Cipher cipher = new Cipher(true);
        String message = cipher.encryption("sometext");
        System.out.println(message);
        System.out.println(cipher.getFrequencyAnalysis(message));
        System.out.println(cipher.decryption(message));
    }
}
