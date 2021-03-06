package de.tgirobertosan.suiweed.spielwelt;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.GroupObject;


public class Location {
	
	private Spielwelt spielwelt;
	private int x;
	private int y;

	public static Location getFromGroupObject(GroupObject groupObject) {
		return getFromGroupObject(groupObject, null);
	}

	public static Location getFromGroupObject(GroupObject groupObject, Spielwelt[] spielwelten) {
		String map = groupObject.props.getProperty("Map");
		int x = Integer.valueOf(groupObject.props.getProperty("X", "0"));
		int y = Integer.valueOf(groupObject.props.getProperty("Y", "0"));

		Spielwelt destinationWelt = null;
		if(spielwelten != null && map != null) {
			for(Spielwelt spielwelt : spielwelten) {
				if(spielwelt.getName().equals(map)) {
					destinationWelt = spielwelt;
					break;
				}
			}
		}
		if(destinationWelt == null && map != null)
			try {
				destinationWelt = new Spielwelt("/res/spielwelt/tilemaps/"+map);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		return new Location(destinationWelt, x, y);
	}
	
	public Location(Spielwelt spielwelt, int x, int y) {
		this.spielwelt = spielwelt;
		this.x = x;
		this.y = y;
	}

	public Spielwelt getSpielwelt() {
		return spielwelt;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setSpielwelt(Spielwelt spielwelt) {
		this.spielwelt = spielwelt;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	

}
