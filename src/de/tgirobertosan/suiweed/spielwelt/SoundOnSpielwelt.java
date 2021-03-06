package de.tgirobertosan.suiweed.spielwelt;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import de.tgirobertosan.suiweed.charakter.Charakter;

public class SoundOnSpielwelt extends Sound {

	private static int meterInPixel = 32;
	
	private float volumeModifier;
	private float x;
	private float y;
	
	public SoundOnSpielwelt(String ref, float x, float y, float volumeModifier) throws SlickException {
		super(ref);
		this.x = x;
		this.y = y;
		this.volumeModifier = volumeModifier;
	}
	
	public void playSoundFor(Charakter charakter) {
		float aQuadrat = (float) (x-charakter.getX())/meterInPixel*(x-charakter.getX())/meterInPixel;
		float bQuadrat = (float) (y-charakter.getY())/meterInPixel*(y-charakter.getY())/meterInPixel;
		float distance = (float) Math.sqrt(aQuadrat+bQuadrat);
		if(distance<1)
			distance = 1;
		this.playAt(1, 1/distance*volumeModifier, 0, 0, 1);
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}

}
