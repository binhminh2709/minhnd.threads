package com.minhnd.ch02;

public class CharacterEvent {
	public CharacterSource source;
	public int character;
	
	public CharacterEvent(CharacterSource cs, int c) {
		source = cs;
		character = c;
	}
}
