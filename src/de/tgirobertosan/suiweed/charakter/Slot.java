package de.tgirobertosan.suiweed.charakter;

import org.newdawn.slick.geom.Rectangle;


import de.tgirobertosan.suiweed.items.Gegenstand;

public class Slot extends Rectangle{

	private int id;
	
	private float x;
	private float y;
	
	private float height = 29;
	private float width = 30;
	
	private boolean belegt = false;
	
	private Gegenstand gegenstand;
	
	public Slot(Rectangle rectangle){
		super(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
		
		x = rectangle.getX();
		y = rectangle.getY();
		
	}

	
	public void takeItem(Gegenstand gegenstand){
		
		this.gegenstand = gegenstand;
		belegt = true;
	}
	
	
	public Gegenstand getGegenstand(){
		return gegenstand;
	}
	
	public void setPosition(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public int getId() {
		return id;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getHeight() {
		return height;
	}

	public float getWidth() {
		return width;
	}

	public boolean getBelegt() {
		return belegt;
	}
	
	
}
