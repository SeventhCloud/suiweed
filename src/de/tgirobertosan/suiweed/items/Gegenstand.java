package de.tgirobertosan.suiweed.items;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Gegenstand {

	private int id;
	private float x;
	private float y;
	private int width = 32;
	private int height = 32;
	private Image image;
	private boolean dropped = true;
	private boolean destroy = false;
	private Shape collisionShape;
	
	public Gegenstand(Image image, int id, int x, int y){
		
		this.image = image;
		this.id = id;
		
		this.x = x;
		this.y = y;
		
		collisionShape = new Rectangle(x, y, width, height);
		
	}
	
	
	
	
	public void zeichne(Graphics g){
		image.draw(x, y);
		// g.draw(collisionShape);
	}
	
	
	public Image getPicture(){
		return image;
	}


	public Shape getColissionShape(){
		return collisionShape;
	}



	public void setDestroy(boolean destroy){
		this.destroy = destroy;
	}
	
	public boolean getDestroy(){
		return destroy;
	}
	
	public int getId(){
		return id;
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}	
}
