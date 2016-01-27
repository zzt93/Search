package inputMethod;

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
            return "Utf8String{" +
                    "" + string +  "=" + Arrays.toString(string.getBytes(UTF_8)) +
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

    public static void main(String[] args) {
        System.out.println(new Utf8String("我"));
        System.out.println(new Utf8String("沃"));
        System.out.println(new Utf8String("瓦"));
    }
}
