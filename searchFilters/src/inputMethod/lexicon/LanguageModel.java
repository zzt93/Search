package inputMethod.lexicon;

/**
 * Created by zzt on 1/29/16.
 * <p>
 * Usage:
 */
public class LanguageModel {
    private static LanguageModel ourInstance = new LanguageModel();

    public static LanguageModel getInstance() {
        return ourInstance;
    }

    private LanguageModel() {
    }

    public double getRadio(String syllable) {
        return 0;
    }
}
