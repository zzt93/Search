package inputMethod.lm;

/**
 * Created by zzt on 1/29/16.
 * <p>
 * Usage:
 */
public interface LanguageModel {

    double getUnigram(String syllable);
    double getBigram(String s1, String s2);
}
