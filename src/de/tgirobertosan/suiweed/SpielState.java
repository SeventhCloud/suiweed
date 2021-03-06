package de.tgirobertosan.suiweed;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.tgirobertosan.suiweed.charakter.InputHandler;
import de.tgirobertosan.suiweed.gui.Button;
import de.tgirobertosan.suiweed.items.Gegenstaende;
import de.tgirobertosan.suiweed.spielwelt.Spielwelt;

public class SpielState extends BasicGameState {

	private int id = 1;

	private GameContainer container;
	private InputHandler inputHandler;
	private Spielwelt spielWelt = null;

	private Button button;
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.container = container;
		inputHandler = new InputHandler(container.getInput());
		inputHandler.init();
		// container.getInput().enableKeyRepeat();
		changeSpielwelt(new Spielwelt("/res/spielwelt/tilemaps/level0.tmx"));
		button = new Button(3, container.getWidth() - 150,0, 100, 150, "zurueck", "zurueckHover", "zurueckPressed");
		button.init(container);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		spielWelt.renderWithObjects(container, g);
		button.render(container, g);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		this.container = container;
		inputHandler.handleInput();
		spielWelt.focusCharakter(container.getWidth(), container.getHeight());
		spielWelt.checkTriggers();
		spielWelt.playLoopedSounds();
		spielWelt.updateGegner();

		if (button.update(container)) {
			game.init(container);
			game.enterState(0);

		}
		if(spielWelt.getCharakter().getLeben()<=0){
			game.init(container);
			game.enterState(0);
		}
	}

	@Override
	public int getID() {

		return id;
	}

	public void changeSpielwelt(Spielwelt spielwelt) {
		try {
			spielwelt.init(this, container);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.spielWelt = spielwelt;
	}

	public InputHandler getInputHandler() {
		return inputHandler;
	}

}
