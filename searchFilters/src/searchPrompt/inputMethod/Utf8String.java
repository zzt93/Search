package searchPrompt.inputMethod;

import mis.MyIn;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by zzt on 1/26/16.
 * <p>
 * Usage:
 */
public class Utf8String {
    public static final String UTF_8 = "utf8";
    private String string;

    public Utf8String(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        try {
            return "Utf8String(len=" + string.length() +
                    "){" +
                    "" + string +
                    "=" + Arrays.toString(string.getBytes(UTF_8)) +
                    "=" + Arrays.toString(string.getBytes()) +
                    "=" + toHex(string) +
                    "}";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String toHex(String s) throws UnsupportedEncodingException {
        byte[] bytes = s.getBytes(UTF_8);
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
    }

    public void testChar() {
        for (char c : string.toCharArray()) {
            System.out.print(c + " ");
            System.out.print((int)c + " ");
            System.out.println(c < 127);
        }
    }

    public static void main(String[] args) {
        String original = "A" + "\uD840" + "\u00f1" + "\u00fc" + "C";
        testUTFString(original);
        MyIn in = new MyIn(System.in);
        while (in.hasNextLine()) {
            testUTFString(in.nextLine());
        }
    }

    private static void testUTFString(String in) {
        Utf8String x = new Utf8String(in);
        System.out.println(x);
        x.testChar();
    }
}
