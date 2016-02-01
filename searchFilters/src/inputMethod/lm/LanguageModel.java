package inputMethod.lm;

/**
 * Created by zzt on 1/29/16.
 * <p>
 * Usage:
 * mapping from chinese word to its possibilities
 * maybe implemented by DBMS in the future
 */
public interface LanguageModel {

    double UNIGRAM_POSSIBILITY_THRESHOLD = 1 * Math.pow(Math.E, -20);
    double BIGRAM_POSSIBILITY_THRESHOLD = 1 * Math.pow(Math.E, -30);

    double getUnigram(String syllable);
    double getBigram(String s1, String s2);
}
