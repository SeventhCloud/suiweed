package de.tgirobertosan.suiweed.items;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;

public class Schwert extends Waffe{
	
	public Schwert(Image image, int id, int x, int y, List<String> text) {
		super(image, id, x, y);
		
		lineLenght = text.get(id + 1).length();
		schadenString = text.get(id + 1).substring(0, lineLenght);
		schaden = Integer.valueOf(schadenString);
	}
}
