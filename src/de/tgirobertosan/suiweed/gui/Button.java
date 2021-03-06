package de.tgirobertosan.suiweed.gui;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.MouseOverArea;

public class Button {

	private int id;

	private int xButton;
	private int yButton;
	private int hoeheButton = 100;
	private int breiteButton;
	private String dateiname;
	private String dateinameOverImage;
	private String dateinameDownImage;
	private MouseOverArea mouseOverArea;

	private boolean mouseClicked = false;

	private boolean mouseReleased = false;

	public Button(int id, int xButton, int yButton, int hoeheButton, int breiteButton, String dateiname) {
		super();
		this.id = id;
		this.hoeheButton = hoeheButton;
		this.breiteButton = breiteButton;
		this.yButton = yButton;
		this.xButton = xButton;
		this.dateiname = dateiname;
	}



	public Button(int id, int xButton,int yButton, int hoeheButton, int breiteButton, String dateiname,
			String dateinameOverImage, String dateinameDownImage) {
		super();
		this.id = id;
		this.yButton = yButton;
		this.xButton = xButton;
		this.hoeheButton = hoeheButton;
		this.breiteButton = breiteButton;
		this.dateiname = dateiname;
		this.dateinameOverImage = dateinameOverImage;
		this.dateinameDownImage = dateinameDownImage;
	}



	public void init(GameContainer container) throws SlickException{


		mouseOverArea = new MouseOverArea(container, new Image("res/gui/images/" + dateiname +".png").getScaledCopy(breiteButton, hoeheButton), new Rectangle(xButton, yButton, breiteButton, hoeheButton));

		if(dateinameDownImage != null && dateinameOverImage != null){
			mouseOverArea.setMouseOverImage(new Image("res/gui/images/" + dateinameOverImage +".png").getScaledCopy(breiteButton, hoeheButton));
			mouseOverArea.setMouseDownImage(new Image("res/gui/images/" + dateinameDownImage +".png").getScaledCopy(breiteButton, hoeheButton));
		}

	}
	public void render(GameContainer container, Graphics g) {

		mouseOverArea.render(container, g);

	}	


	public boolean update(GameContainer container){

		if(container.getInput().isMouseButtonDown(0) && mouseOverArea.isMouseOver()) {
			mouseClicked = true;
		}

		if(!container.getInput().isMouseButtonDown(0) && mouseClicked && mouseOverArea.isMouseOver()){

			mouseClicked = false;
			mouseReleased = true;

		}
		else {
			mouseReleased = false;
		}

		if(!mouseOverArea.isMouseOver()){
			mouseClicked = false;
		}


		return mouseReleased;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}


	public String getDateiname() {
		return dateiname;
	}



	public void setDateiname(String dateiname) {
		this.dateiname = dateiname;
	}



	public String getDateinameOverImage() {
		return dateinameOverImage;
	}



	public void setDateinameOverImage(String dateinameOverImage) {
		this.dateinameOverImage = dateinameOverImage;
	}



	public String getDateinameDownImage() {
		return dateinameDownImage;
	}



	public void setDateinameDownImage(String dateinameDownImage) {
		this.dateinameDownImage = dateinameDownImage;
	}


	public boolean isMouseClicked() {
		return mouseClicked;
	}



	public void setMouseClicked(boolean mouseClicked) {
		this.mouseClicked = mouseClicked;
	}



	public boolean isMouseReleased() {
		return mouseReleased;
	}



	public void setMouseReleased(boolean mouseReleased) {
		this.mouseReleased = mouseReleased;
	}


}
