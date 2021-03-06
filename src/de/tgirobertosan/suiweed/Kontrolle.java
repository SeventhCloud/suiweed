package de.tgirobertosan.suiweed;


import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import de.tgirobertosan.suiweed.gui.MenuState;
import de.tgirobertosan.suiweed.items.Gegenstaende;

public class Kontrolle extends StateBasedGame{
	
	private AppGameContainer spiel = null;
	
	private int breite = 1920;
	private int hoehe = 1080;

	public Kontrolle(String name) {
		super(name);

	}
	
	public void spielErstellen(){
		try {
			spiel = new AppGameContainer(this);
			spiel.setDisplayMode(breite, hoehe, false);//Breite,Hoehe,Fullscreen
			spiel.setTargetFrameRate(60);
			spiel.start();

		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		

	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new MenuState());
		this.addState(new SpielState());
		
		
	}
	
	
	
	

}