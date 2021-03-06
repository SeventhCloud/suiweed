package de.tgirobertosan.suiweed.gui;

import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.tgirobertosan.suiweed.spielwelt.Spielwelt;



public class MenuState extends BasicGameState{


	private int id = 0;

	private Image hintergrundbild;
	
	private Sound hintergrundmusik;

	private ArrayList<Button> button = new ArrayList<Button>();


	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {


		button.add(new Button(0, container.getWidth() / 2 - 100/2,100, 100 , 150, "start", "startHover", "startPressed"));
		button.add(new Button(1,container.getWidth() / 2 - 100/2, 300, 100, 150, "ende", "endeHover", "endePressed"));
		
		for (int i = 0; i < button.size(); i++) {

			button.get(i).init(container);

		}

		hintergrundbild = new Image("res/gui/images/background.jpg");
		hintergrundbild = hintergrundbild.getScaledCopy(container.getWidth(), container.getHeight() ); //breite ergibt sich aus dem Seitenverh�ltniss

		//hintergrundmusik = new Sound("res/gui/audio/hintergrundmusik.ogg");

		//click = new Sound("res/gui/audio/husten_6.ogg");


	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

		hintergrundbild.drawCentered(container.getWidth()/2, container.getHeight()/2);


		for (int i = 0; i < button.size(); i++) {

			button.get(i).render(container, g);

		}



	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

		if (button.get(0).update(container)) {
			game.enterState(1);
			
		}
		if (button.get(1).update(container)) {
			container.exit();
		}

	}


	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}


}