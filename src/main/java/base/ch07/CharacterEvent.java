package base.ch07;

public class CharacterEvent {
    public ICharacterSource source;
    public int character;

    public CharacterEvent(ICharacterSource cs, int c) {
        source = cs;
        character = c;
    }
}
