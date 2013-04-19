package tests;

import util.Secretary;


public class SecretaryTestOne {

    private Secretary mySecretary = new Secretary("src/tests/", "Example1.txt");

    public void permutation (String str) {
        permutation("", str);
    }

    private void permutation (String prefix, String str) {
        int n = str.length();

        if (n == 0) {
            mySecretary.write(prefix);
        }

        else {
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n));
        }
    }

    private void saveSession () {
        mySecretary.saveSession("Example1.txt");
    }

    private void loadSession () {
        try {
            @SuppressWarnings("rawtypes")
            Class[] parameterTypes = {String.class};
            mySecretary.loadSession("Example1.txt", this.getClass().getMethod("printLine", parameterTypes), this);
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    
    
    public void printLine (String string) {
        System.out.println(string);
    }
    
    

    public static void main (String[] args) {
        SecretaryTestOne test = new SecretaryTestOne();
        test.permutation("hello");
//        test.saveSession();
        test.loadSession();
    }
}
