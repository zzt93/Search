package synonym.bl;

import synonym.data.ChineseChar;
import synonym.data.SenStructure;
import synonym.data.PartOfSpeech;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zzt on 3/8/16.
 * <p>
 * Usage:
 *
 * Precondition:
 * Chinese sentence structure:
 * (1)(2)(3)...
 * (1)(3)...
 * (2)(4)...
 *
 * every type has some words
 *
 * Steps:
 * 1. input some character
 * 2. find type for this character
 * 3. guess structure
 * 4. determine its part of speech by structure
 * 5. find synonym of specific part of speech's character/word
 */
public class SentenceSplit {

    private HashMap<ChineseChar, ArrayList<PartOfSpeech>> map = new HashMap<>();
//    private ArrayList<SenStructure> allStructures;
}
