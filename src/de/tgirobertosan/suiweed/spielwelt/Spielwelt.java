package de.tgirobertosan.suiweed.spielwelt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.tiled.GlobalTile;
import org.newdawn.slick.tiled.GroupObject;
import org.newdawn.slick.tiled.ObjectGroup;
import org.newdawn.slick.tiled.TileOnLayer;
import org.newdawn.slick.tiled.TileSet;
import org.newdawn.slick.tiled.TiledMapPlus;

import de.tgirobertosan.suiweed.Kampfsystem;
import de.tgirobertosan.suiweed.SpielState;
import de.tgirobertosan.suiweed.charakter.Charakter;
import de.tgirobertosan.suiweed.items.Gegenstaende;
import de.tgirobertosan.suiweed.spielwelt.trigger.Dialogzeile;
import de.tgirobertosan.suiweed.spielwelt.trigger.LocatedSound;
import de.tgirobertosan.suiweed.spielwelt.trigger.Teleport;
import de.tgirobertosan.suiweed.spielwelt.trigger.Trigger;
import de.tgirobertosan.suiweed.spielwelt.trigger.Trigger.TriggerEvent;


public class Spielwelt extends TiledMapPlus {
	
	//Settings für Editor:
	
	private Music music = new Music("res/spielwelt/audio/theme.ogg");
	private Gegenstaende gegenstaende;
	private int layerIndexAbovePlayer = 2; //Layer >= layerIndexAbovePlayer werden nach dem Spieler gerendert
	private String collisionObjectLayerName = "block"; //Name des ObjectLayers mit dem Kollisionsobjekte auf Map erzeugt werden

	private String name;
	
	private SpielState spielState;
	
	private int absoluteX = 0;
	private int absoluteY = 0;
	
	private int transX = 0;
	private int transY = 0;
	private int xOffset, yOffset;
	private int mapWidth;
	private int mapHeight;
	
	private boolean checkInitTriggers = true;
	private HashSet<SoundOnSpielwelt> loopedSounds = new HashSet<SoundOnSpielwelt>();
	
	private ArrayList<Shape> collisionObjects = new ArrayList<Shape>();
	private ArrayList<Trigger> interactionTriggers = new ArrayList<Trigger>();
	private ArrayList<Trigger> locationTriggers = new ArrayList<Trigger>();
	private ArrayList<Trigger> initTriggers = new ArrayList<Trigger>();

	/**Der Charakter, falls er sich auf dieser Map befindet**/
	private Charakter charakter;
	/**Alle Gegner auf dieser Map**/
	private ArrayList<Gegner> gegner = new ArrayList<Gegner>();
	private Kampfsystem kampfsystem;
	
	//Testsound
	private SoundOnSpielwelt testSound = new SoundOnSpielwelt("res/spielwelt/audio/kebab.ogg", -1, -1, 1);

	public Spielwelt(String path) throws SlickException {
		super(path);
		this.name = path.substring(path.lastIndexOf("/")+1);
		this.mapWidth = getWidth()*getTileWidth();
		this.mapHeight = getHeight()*getTileHeight();
		
		
	}
	
	public void init(SpielState spielState, GameContainer container) throws SlickException {
		this.spielState = spielState;
		this.kampfsystem = new Kampfsystem();
		addObjects(container);
		addTileCollisions();
		if(container.getWidth() > mapWidth)
			xOffset = (container.getWidth()-mapWidth)/2;
		if(container.getHeight() > mapHeight)
			yOffset = (container.getHeight()-mapHeight)/2;
		
		
		try {
			gegenstaende = new Gegenstaende();
			this.spielState.getInputHandler().setGegenstaende(gegenstaende);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		music.play();
	}

	/**
	 * @param animated Ob animierte Tiles der Spiewelt animiert werden sollten
	 */
	public void setTileAnimations(boolean animated) {
		for(TileSet tileset : getTilesets()) {
			for(GlobalTile globaltile : tileset.getGlobalTiles())
				globaltile.setAnimated(animated);
		}
	}
	
	public void renderCollisionObjects(Graphics g) {
		for(Shape shape : collisionObjects)
			g.fill(shape);
	}
	public void renderTriggerShapes(Graphics g) {
		for(Trigger trigger : locationTriggers) {
			g.fill(trigger.getTriggerArea());
		}
	}
	
	/**
	 * @param screenWidth Width of the Window
	 * @param screenHeight Height of the Window
	 */
	public void focusCharakter(int screenWidth, int screenHeight) {
		focusPoint(screenWidth, screenHeight, (int)charakter.getX()+charakter.getBreite()/2, (int)charakter.getY()+charakter.getHoehe()/2);
	}
	/**
	 * @author Shockper (from http://slick.ninjacave.com/forum/viewtopic.php?t=4713)
	 */
	public void focusPoint(int screenWidth, int screenHeight, int focusX, int focusY) {
		if(focusX-screenWidth/2 < 0)
			transX = xOffset;
		else if(focusX+screenWidth/2 > mapWidth)
			transX = -mapWidth+screenWidth-xOffset;
		else
			transX = (int)-focusX+screenWidth/2;

		if(focusY-screenHeight/2 < 0)
			transY = yOffset;
		else if(focusY+screenHeight/2 > mapHeight)
			transY = -mapHeight+screenHeight-yOffset;
		else
			transY = (int)-focusY+screenHeight/2;
	}
	
	public void playLoopedSounds() {
		for(SoundOnSpielwelt sound : loopedSounds) {
			if(!sound.playing())
				sound.playSoundFor(charakter);
		}
		
	}
	
	/**Rendert die Map komplett mit Spieler**/
	public void renderWithObjects(GameContainer container, Graphics g) throws SlickException {
		g.translate(transX, transY);
		renderGroundLayers();
		
		gegenstaende.render(g);
		
		for(Gegner gegner: gegner){
			gegner.render(g);
		}
		if(charakter != null) {
			charakter.renderCharakter();
			//g.draw(getCharakter().getCollisionShape());
		}
		
		
		renderTopLayers();

		//renderTriggerShapes(g);
		//renderCollisionObjects(g);
		if(charakter != null)
			charakter.zeichneNamen(g);
		
		g.translate(-transX, -transY);
		if(charakter != null)
			charakter.renderInventar(container, g);
		
	}
	
	public void renderGroundLayers() {
		for(int i = 0; i < layerIndexAbovePlayer && i < getLayerCount(); i++) {
			render(absoluteX, absoluteY, i);
		}
	}
	
	public void renderTopLayers() {
		for(int i = layerIndexAbovePlayer;i < getLayerCount(); i++) {
			render(absoluteX, absoluteY, i);
		}
	}
	
	private void addTileCollisions() {
		for(TileSet tileSet : getTilesets())
			for(GlobalTile globalTile : tileSet.getGlobalTiles()) {
				Shape shape = globalTile.getCollisionShape();
				if(shape != null) {
					for(TileOnLayer tileOnLayer : getAllTilesFromAllLayers(tileSet.name)) {
						if(tileOnLayer.getGid() == globalTile.getGid()) {
							collisionObjects.add(shape.transform(Transform.createTranslateTransform(tileOnLayer.x*tileSet.getTileWidth()+absoluteX, tileOnLayer.y*tileSet.getTileHeight()+absoluteY)));
						}
					}
				}
			}
	}
	
	private void addObjects(GameContainer container) throws SlickException {
		for(ObjectGroup objectGroup : getObjectGroups()) {
			if(objectGroup.name.equalsIgnoreCase(collisionObjectLayerName))
				for(GroupObject blockObject : objectGroup.getObjects()) {
					collisionObjects.add(blockObject.getShape());
				}
			else {
				for(GroupObject groupObject : objectGroup.getObjects()) {
					Trigger trigger = null;
					System.out.println("Loading: "+groupObject.type);
					if(groupObject.type.equalsIgnoreCase("charakter") && charakter == null) {
						charakter = new Charakter(groupObject.name, groupObject.x, groupObject.y, this, kampfsystem);
						charakter.init(container);
						spielState.getInputHandler().setCharakter(charakter);
						
					} else if(groupObject.type.equalsIgnoreCase("Teleport")) {
						trigger = Teleport.getFromGroupObject(groupObject, null, spielState);
					} else if(groupObject.type.equalsIgnoreCase("Gegner")) {
						gegner.add(new Gegner(groupObject.x, groupObject.y));
					}else if(groupObject.type.equalsIgnoreCase("LocatedSound")) {
						trigger = LocatedSound.getFromGroupObject(groupObject);
					} else if(groupObject.type.equalsIgnoreCase("Dialogzeile")) {
						trigger = Dialogzeile.getFromGroupObject(groupObject);
					}
					if(trigger == null)
						continue;
					if(trigger.getTriggerEvent() == TriggerEvent.INTERACT)
						interactionTriggers.add(trigger);
					else if(trigger.getTriggerEvent() == TriggerEvent.INIT) {
						//NOTE: INIT-Triggers werden erst in checkTriggers() gefeuert, Map wäre hier noch nicht gerendert.
						initTriggers.add(trigger);
					}
					else
						locationTriggers.add(trigger);
				}
				
			}
		}
	}
	
	public void testLocatedSound(int x, int y) {
		//Methode ist nur da um zu testen ob Sound Placement (Mausklick aus InputHandler) funktioniert
		if(!testSound.playing()) {
			testSound.setX(-transX+x);
			testSound.setY(-transY+y);
			playSoundAt(testSound);
		}
	}
	
	public void updateGegner(){
		for(Gegner gegner: gegner){
			gegner.update();
			gegner.schaueNachSpieler(getCharakter());
		}
		//Die Methode sortiert die Gegner in der ArrayList nach der Y-Position
		Collections.sort(gegner, new Comparator<Gegner>() {

			@Override
			public int compare(Gegner o1, Gegner o2) {
				// TODO Auto-generated method stub
				return Float.compare(o1.getY(), o2.getY());
			}
		});
		
	}
	
	public void playSoundAt(SoundOnSpielwelt sound) {
		playSoundAt(sound, false);
	}

	public void playSoundAt(SoundOnSpielwelt sound, boolean loop) {
		sound.playSoundFor(charakter);
		if(loop)
			loopedSounds.add(sound);
	}
	
	public void checkTriggers() {
		if(checkInitTriggers) {
			for(Trigger trigger : initTriggers)
				trigger.checkAndFire(charakter);
			checkInitTriggers = false;
		}
		for(Trigger trigger : locationTriggers) {
			trigger.checkAndFire(charakter);
		}
	}
	

	public boolean checkCollision(Shape shape) {
		for(Shape collisionObject : getCollisionObjects()) {
			if(shape.intersects(collisionObject))
				return true;
		}
		return false;
	}
	
	public Charakter getCharakter() {
		return charakter;
	}
	
	public void setCharakter(Charakter charakter) {
		this.charakter = charakter;
	}
	
	public ArrayList<Shape> getCollisionObjects() {
		return collisionObjects;
	}
	
	public String getName() {
		return name;
	}

	public ArrayList<Trigger> getInteractionTriggers() {
		return interactionTriggers;
	}
	
	public void changeSpielwelt(Spielwelt neueSpielwelt) {
		if(neueSpielwelt != null) {
			spielState.changeSpielwelt(neueSpielwelt);
		}
	}
	
	public ArrayList<Gegner> getGegner(){
		
		return(gegner);
		
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}
	
	public Music getMusic(){
		return music;
	}
}
