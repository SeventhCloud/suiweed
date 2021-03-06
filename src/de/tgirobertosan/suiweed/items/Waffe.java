package de.tgirobertosan.suiweed.items;
import java.util.List;
import org.newdawn.slick.Image;



public class Waffe extends Gegenstand {
	
	protected int lineLenght;
	protected String schadenString = "";
	protected int schaden = 0;
	
	public Waffe(Image image, int id, int x, int y) {
		
		super(image, id, x, y);
		
	}
}
