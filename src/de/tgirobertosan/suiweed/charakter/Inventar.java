package de.tgirobertosan.suiweed.charakter;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import de.tgirobertosan.suiweed.items.Gegenstaende;
import de.tgirobertosan.suiweed.items.Gegenstand;
import de.tgirobertosan.suiweed.items.Waffe;


public class Inventar implements KeyListener, MouseListener {


	//Attribute

	private float x = 111;
	private float y = 109;
	private float slotX = x + 65;
	private float slotY = y + 473;
	private float slotBreite = 30;
	private float slotHoehe = 29;
	private float slotRandBreite = 7, slotRandHoehe = 9.9f;
	private Image inventar;
	private boolean sichtbarkeit = false; //true = sichtbar, false = unsichtbar
	private Shape inventarleiste;
	private ArrayList<Slot> slots = new ArrayList<>();
	private byte slotSpaltenAnzahl = 8;
	private byte slotZeilenAnzahl  = 4;
	private boolean waffeImInventar = false;
	private Input input;
	private ArrayList<Gegenstand> gegenstand;

	//Methoden

	public void init(GameContainer container) throws SlickException{

		gegenstand = new ArrayList<>();
		inventar = new Image("res/character/images/Inventar.png");
		inventarleiste = new Rectangle(x, y, 443, 175);

		for(byte spalte = 0, zeile = 0; spalte < slotSpaltenAnzahl; spalte++){
			slots.add(new Slot(new Rectangle((spalte *slotBreite) + slotX + (spalte * slotRandBreite), (zeile*slotHoehe) + slotY+ (zeile * slotRandHoehe), slotBreite, slotHoehe)));

			if(zeile  < slotZeilenAnzahl -1 && spalte == slotSpaltenAnzahl - 1){
				zeile++;
				spalte = -1;
			}
		}


		input = container.getInput();
		input.addKeyListener(this);
		input.addMouseListener(this);

	}

	public void update(GameContainer arg0, int arg1) throws SlickException {



	}	

	public void render(GameContainer arg0, Graphics g) throws SlickException {
		zeichne(x, y);
	}


	private void zeichne(float x , float y){

		if(sichtbarkeit){
			inventar.draw(x,y);
			for(int index = 0; index < slots.size(); index++){
				if(slots.get(index).getBelegt()){
					slots.get(index).getGegenstand().getPicture().draw(slots.get(index).getX(),slots.get(index).getY());
				}
			}
		}
	}


	public void stow(Gegenstand gegenstand){

		this.gegenstand.add(gegenstand);

		for(int index = 0; index < slots.size(); index++){
			if(!slots.get(index).getBelegt()){
				slots.get(index).takeItem(gegenstand);
				break;
			}
		}

	}




	@Override
	public void inputEnded() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAcceptingInput() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setInput(Input arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(int arg0, char arg1) {

		switch(arg0){
		case(Input.KEY_I): sichtbarkeit = !sichtbarkeit ; break;
		default:break;
		}
	}

	@Override
	public void keyReleased(int arg0, char arg1) {


	}

	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		if(sichtbarkeit && oldx >= inventarleiste.getX() && oldy >= inventarleiste.getY()){
			if(oldx <= inventarleiste.getX() + inventarleiste.getWidth() && oldy <= inventarleiste.getY()+ inventarleiste.getHeight()){

				x = x + (newx - oldx);
				y = y + (newy - oldy);
				slotX = slotX + (newx - oldx);
				slotY = slotY + (newy - oldy);
				inventarleiste.setLocation(x, y);

				for(byte spalte = 0, zeile = 0, i = 0; i < slots.size(); spalte++, i++){
					slots.get(i).setPosition((spalte *slotBreite) + slotX + (spalte*slotRandBreite),(zeile*slotHoehe) + slotY + (zeile * slotRandHoehe));
					slots.get(i).setLocation((spalte *slotBreite) + slotX + (spalte*slotRandBreite), (zeile*slotHoehe) + slotY + (zeile * slotRandHoehe));

					if(zeile  < slotZeilenAnzahl -1 && spalte == slotSpaltenAnzahl - 1){
						zeile++;
						spalte = -1;
					}
				}
			}
		}

	}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {


	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMoved(int arg0) {
		// TODO Auto-generated method stub

	}
}