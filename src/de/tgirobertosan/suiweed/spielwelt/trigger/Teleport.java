package de.tgirobertosan.suiweed.spielwelt.trigger;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.GroupObject;

import de.tgirobertosan.suiweed.SpielState;
import de.tgirobertosan.suiweed.charakter.Charakter;
import de.tgirobertosan.suiweed.spielwelt.Location;
import de.tgirobertosan.suiweed.spielwelt.Spielwelt;

public class Teleport extends Trigger {

	private Location destination;
	
	public static Teleport getFromGroupObject(GroupObject groupObject, Spielwelt[] spielwelten, SpielState spielState) {
		TriggerEvent triggerEvent = Trigger.getEventFromGroupObject(groupObject);
		Shape triggerArea = groupObject.getShape();
		Location destination = Location.getFromGroupObject(groupObject, spielwelten);
		if(triggerEvent == TriggerEvent.INTERACT) {
			return new Teleport(triggerEvent, triggerArea, destination, Trigger.getRangeFromGroupObject(groupObject));
		}
		return new Teleport(triggerEvent, triggerArea, destination);
	}
	
	public Teleport(TriggerEvent triggerEvent, Shape triggerArea, Location destination) {
		super(triggerEvent, triggerArea);
		this.destination = destination;
	}

	public Teleport(TriggerEvent triggerEvent, Shape triggerArea, Location destination, int interactionRange) {
		super(triggerEvent, triggerArea, interactionRange);
		this.destination = destination;
	}

	@Override
	public void fireTrigger(Charakter charakter) {
		charakter.setLocation(destination);
		System.out.println("TELEPORT!");
	}
	
	public Location getDestination() {
		return destination;
	}

}
