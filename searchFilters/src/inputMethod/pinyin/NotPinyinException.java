package inputMethod.pinyin;

/**
 * Created by zzt on 1/27/16.
 * <p>
 * Usage:
 */
public class NotPinyinException extends Exception {

    public NotPinyinException(String message) {
        super("english or invalid pinyin" + message);
    }
}
