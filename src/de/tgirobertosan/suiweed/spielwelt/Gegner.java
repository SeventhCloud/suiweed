package de.tgirobertosan.suiweed.spielwelt;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import de.tgirobertosan.suiweed.charakter.Charakter;

public class Gegner {



	private float x;
	private float y;
	private float startX;
	private Image gegner;
	private Charakter spieler;
	private boolean move;
	private boolean verfolgen;
	private float speed;
	private int gegnerLeben;
	private Circle sicht;
	private double start,weiter;
	private boolean attacke;
	private float zufall;
	private Shape collisionShape;

	public Gegner(float x, float y){
		this.x = x;
		this.y = y;
		speed = (float) (Math.random()+0.1);
		gegnerLeben = 20;
		try {
			gegner = new Image("res/gegner/image/gegner.png");
			sicht = new Circle(x + 20, y + 25, 150);
			collisionShape  = new Circle(x+25, y+25, 22);

		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		startX = x;
	}



	public void render(Graphics g){
		g.drawImage(gegner, x, y);
	}

	public void update(){
		if(!verfolgen)
			bewegen();
		else if(verfolgen && spieler != null){
			verfolgen();
			puefeAttacke();
		}
		collisionShape.setCenterX(x+20);
		collisionShape.setCenterY(y+25);
	}
	private void bewegen(){
		if(move){
			x = (float) (x + speed);
		}
		else if(!move){
			x = (float) (x - speed);
		}
		if(x >= startX + 15){
			move = false;
		}
		else if(x <= startX - 15){
			move = true;
		}	

	}
	private void puefeAttacke() {
		if(spieler.getCollisionShape().intersects(collisionShape)){
			if(!attacke){
				start = System.currentTimeMillis();
				attacke = true;
				zufall = (float) (Math.random()*2+1);
			}
			if((weiter-start)/1000>=zufall){
				start = System.currentTimeMillis();
				
				spieler.setLeben((int)(spieler.getLeben()-((Math.random()*4)+3)));
				System.out.println("Spielerleben: " +spieler.getLeben());
				zufall = (float) (Math.random()*2+1);

			}

		}
		else{
			attacke = false;
		}
		weiter = System.currentTimeMillis();
	}
	public void verfolgen(){
		if(x>=spieler.getX()){
			x = x - speed;
		}
		else if(x<spieler.getX()){
			x = x + speed;
		}
		if(y>=spieler.getY()){
			y = y - speed;
		}
		else if(y<spieler.getY()){
			y = y + speed;
		}
	}
	public void schaueNachSpieler(Charakter spieler){
		this.spieler = spieler;
		if(sicht.intersects(spieler.getCollisionShape())){
			verfolgen = true;
		}
	}
	public void setX(float x) {
		this.x = x;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public Circle getSicht() {
		return sicht;
	}

	public boolean isVerfolgen() {
		return verfolgen;
	}
	public void setVerfolgen(boolean verfolgen){
		this.verfolgen = verfolgen;
	}
	
	public float getGegnerX(){
		return(x);
	}
	
	public float getGegnerY(){
		return(y);
	}
	public int getGegnerLeben(){
		return gegnerLeben;
	}
	public void setLeben(int gegnerLeben){
		this.gegnerLeben = gegnerLeben;
	}
}
