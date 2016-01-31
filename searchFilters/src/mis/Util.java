package mis;

/**
 * Created by zzt on 1/31/16.
 * <p>
 * Usage:
 */
public class Util {
    public static int getHashCapacity(String num) {
        return (int) (Integer.parseInt(num) * 1.3);
    }
}
