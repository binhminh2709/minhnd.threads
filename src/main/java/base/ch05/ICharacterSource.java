package base.ch05;

public interface ICharacterSource {
    public void addCharacterListener(ICharacterListener cl);

    public void removeCharacterListener(ICharacterListener cl);

    public void nextCharacter();
}
