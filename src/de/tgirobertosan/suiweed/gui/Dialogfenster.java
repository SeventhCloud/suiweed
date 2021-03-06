package de.tgirobertosan.suiweed.gui;

import java.awt.Font;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import org.newdawn.slick.*;


public class Dialogfenster {

	private Timer timer = new Timer();
	private TimerTask task;

	private String text = "";
	private String aktuellerText = "";

	private Image fenster;
	private String path = "res/gui/images/dialogfenster.png";

	private int fensterHoehe;
	private int fensterBreite;
	private int x;
	private int y;
	private Color textColor = new Color(54, 59, 54);

	private boolean sichtbarkeit = false;
	private boolean bereitsGesprochen = false;
	private int counter = 0;

	private GameContainer container;

	private SimpleFont simplefont;

	private int line = 1;

	public Dialogfenster(GameContainer container, int line) throws SlickException, IOException {

		super();
		this.container = container;
		this.line = line;

		fenster = new Image(path);

		fensterHoehe = container.getHeight()/7;
		fensterBreite = container.getWidth();
		x = 0;
		y = container.getHeight() - fensterHoehe;

		simplefont = new SimpleFont( "Times New Roman", Font.BOLD, 20);

		fenster.setAlpha(0.5f);

	}

	public void render(Graphics g) throws SlickException{

		if (sichtbarkeit) {
			fenster.draw(x, y, fensterBreite, fensterHoehe);

			g.setFont(simplefont.get());
			g.setColor(textColor);
			g.drawString(aktuellerText, x, y);
		}




	}

	public void aktualisiereDialogfenster(int line) throws IOException{

		
		if(task != null)
			return;
		
		
		if(this.line != line){
			bereitsGesprochen = false;
		}


		text = Files.readAllLines(Paths.get("res/gui/dialoge.txt")).get(line);
		this.line = line;

		sichtbarkeit = true;

	
		task = new TimerTask() {

			@Override
			public void run() {

				if(!bereitsGesprochen && counter <= text.length() ){

					aktuellerText = text.substring(0, counter);
					counter++;
				}
				else{
					bereitsGesprochen = true;
					counter = 0;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					sichtbarkeit = false;
					this.cancel();
					task = null;
								
				}
			}
		};
		timer.purge();
		timer.schedule(task, 0, 40);
	}

	


}
