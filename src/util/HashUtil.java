package util;

public class HashUtil {

    private static final byte[] firstHalf = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77 }; 
    private static final byte[] secondHalf = new byte[] { 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90 }; 
    private static final char[] splChars = new char[] { '*', '&', '#', '@', '$', '!', '?', '>', '<', '.', '{', ']',')' };

    public static String makeHashedPassword(String password) {


        password = password.toUpperCase();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < password.length(); i++) {

            char currChar = password.charAt(i);

            if (currChar >= 'A' && currChar <= 'M') {

                int pos = currChar - 'A';
                sb.append((char) secondHalf[pos]);
                sb.append(splChars[pos % splChars.length]);

            } 
            else if (currChar >= 'N' && currChar <= 'Z') {
                
                int pos = currChar - 'N';
                sb.append((char) firstHalf[pos]);
                sb.append(splChars[pos % splChars.length]);
            } 
            else if(Character.isDigit(currChar)){
                sb.append(splChars[currChar - '0']);
            }
            else {
                sb.append(currChar);
            }
        }

        return sb.toString();
    }
}
