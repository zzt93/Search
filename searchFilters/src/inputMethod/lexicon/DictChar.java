package inputMethod.lexicon;

/**
 * Created by zzt on 1/29/16.
 * <p>
 * Usage:
 */
public class DictChar implements Comparable<DictChar> {

    private Character character;

    public DictChar(Character character) {
        this.character = character;
    }

    @Override
    public int compareTo(DictChar o) {
        return character.compareTo(o.character);
    }
}
