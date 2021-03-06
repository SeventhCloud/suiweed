package de.tgirobertosan.suiweed.charakter;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class TestBot {

	private float x,y,breite,hoehe;
	private SpriteSheet testBot;
	
	public TestBot(float x, float y, float breite, float hoehe){
		
		this.x = x;
		this.y = y;
		this.breite = breite;
		this.hoehe = hoehe;
		
		try {
			testBot = new SpriteSheet("res/character/sprites/char.png", 100, 100);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	public void zeichne(){
		testBot.draw(x, y);
		
	}
	
}
