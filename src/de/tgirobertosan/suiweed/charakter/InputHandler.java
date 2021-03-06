package de.tgirobertosan.suiweed.charakter;
import java.util.ArrayList;

import org.newdawn.slick.Input;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.ControllerButtonControl;
import org.newdawn.slick.command.ControllerDirectionControl;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.command.MouseButtonControl;

import de.tgirobertosan.suiweed.items.Gegenstaende;
import de.tgirobertosan.suiweed.items.Gegenstand;
import de.tgirobertosan.suiweed.spielwelt.trigger.Trigger;


public class InputHandler implements InputProviderListener {
	
	private Input input;
	private InputProvider provider;

	private Command interact = new BasicCommand("interact");
	private Command attack = new BasicCommand("attack");
	
	private Command up = new BasicCommand("up");
	private Command left = new BasicCommand("left");
	private Command right = new BasicCommand("right");
	private Command down = new BasicCommand("down");

	private Command leftClick = new BasicCommand("leftClick");

	
	private Charakter charakter;
	private Gegenstaende gegenstaende;
	
	public InputHandler(Input input) {
		this.input = input;
		this.provider = new InputProvider(input);
	}

	@Override
	public void controlPressed(Command cmd) {
		
	}

	@Override
	public void controlReleased(Command cmd) {
		
	}
	
	public void init() {
		provider.addListener(this);
		
		provider.bindCommand(new KeyControl(Input.KEY_LEFT), left);
		provider.bindCommand(new KeyControl(Input.KEY_A), left);
		provider.bindCommand(new KeyControl(Input.KEY_RIGHT), right);
		provider.bindCommand(new KeyControl(Input.KEY_D), right);
		provider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.LEFT), left);
		provider.bindCommand(new KeyControl(Input.KEY_UP), up);
		provider.bindCommand(new KeyControl(Input.KEY_W), up);
		provider.bindCommand(new KeyControl(Input.KEY_DOWN), down);
		provider.bindCommand(new KeyControl(Input.KEY_S), down);
		provider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.UP), up);

		provider.bindCommand(new KeyControl(Input.KEY_E), interact);
		provider.bindCommand(new MouseButtonControl(Input.MOUSE_RIGHT_BUTTON), attack);
		provider.bindCommand(new ControllerButtonControl(0, 1), attack);
		
		provider.bindCommand(new MouseButtonControl(Input.MOUSE_LEFT_BUTTON), leftClick);
	}
	
	
	public void handleInput() {
		
		if(charakter == null)
			return;
		handleMovement();
		handleAttacks();
		handleInteractions();
		//Testet kleinerwerdende Soundlautstärke bei zunehmender Distanz zum Charakter
		/*if(provider.isCommandControlDown(leftClick)) {
			charakter.getSpielwelt().testLocatedSound(input.getMouseX(), input.getMouseY());
		}*/
	}
	
	private void handleAttacks() {
		
		if(provider.isCommandControlPressed(attack))
			charakter.attack();
	}
	
	private void handleInteractions() {
		
		if(provider.isCommandControlPressed(interact)){
			checkCollision();
			for(Trigger trigger : charakter.getSpielwelt().getInteractionTriggers()){
				trigger.checkAndFire(charakter);
		  }
		}
	}
	
	private void handleMovement() {
		int xDir = 0, yDir = 0;
		
		if(provider.isCommandControlDown(left))
			xDir--;
		if(provider.isCommandControlDown(right))
			xDir++;
		if(provider.isCommandControlDown(up))
			yDir--;
		if(provider.isCommandControlDown(down))
			yDir++;
		charakter.move(xDir, yDir);
	}
	
	
	private void checkCollision(){
		
		for(Gegenstand gegenstand: this.gegenstaende.getSchwerter()){
			if(gegenstand.getColissionShape().contains(charakter.getCollisionShape())||
					charakter.getCollisionShape().intersects(gegenstand.getColissionShape())){
				charakter.takeItem(gegenstand);
				gegenstaende.deleteItem(gegenstand);
				break;
			}
		}
	}
	

	public void setCharakter(Charakter charakter) {
		this.charakter = charakter;
	}
	
	public void setGegenstaende(Gegenstaende gegenstaende){
		this.gegenstaende = gegenstaende;
			
	}
	
	public Charakter getCharakter() {
		return charakter;
	}

}
