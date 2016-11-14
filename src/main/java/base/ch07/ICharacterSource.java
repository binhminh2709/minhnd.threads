package base.ch07;

public interface ICharacterSource {
    public void addCharacterListener(ICharacterListener cl);

    public void removeCharacterListener(ICharacterListener cl);

    public void nextCharacter();
}
