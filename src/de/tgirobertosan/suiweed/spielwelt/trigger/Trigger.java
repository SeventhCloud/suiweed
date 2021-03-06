package de.tgirobertosan.suiweed.spielwelt.trigger;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.GroupObject;

import de.tgirobertosan.suiweed.charakter.Charakter;

public abstract class Trigger {
	
	public enum TriggerEvent {
		ENTER, LEAVE, TOUCH, INTERACT, INIT
	}
	
	protected TriggerEvent triggerEvent;
	protected boolean active;
	
	/**The Shape in which the player gets checked for the TriggerEvent**/
	protected Shape triggerArea;
	protected int interactionRange;
	
	public static TriggerEvent getEventFromGroupObject(GroupObject groupObject) {
		return TriggerEvent.valueOf(groupObject.props.getProperty("TriggerEvent", "ENTER"));
	}
	
	public Trigger(TriggerEvent triggerEvent, Shape triggerArea) {
		this(triggerEvent, triggerArea, 50);
	}

	public Trigger(TriggerEvent triggerEvent, Shape triggerArea, int interactionRange) {
		this.triggerEvent = triggerEvent;
		this.triggerArea = triggerArea;
		this.interactionRange = interactionRange;
		if(triggerEvent != TriggerEvent.LEAVE)
			active = true;
	}
	
	/**Triggers with TriggerEvent.INTERACT should NOT be checked in update() they are checked by InputHandler**/
	public void checkAndFire(Charakter charakter) {
		if(!active && triggerEvent == TriggerEvent.LEAVE) {
			if(triggerArea.intersects(charakter.getCollisionShape()) || triggerArea.contains(charakter.getCollisionShape()))
				active = true;
			return;
		} else if (!active && triggerEvent == TriggerEvent.ENTER) {
			if(!triggerArea.contains(charakter.getCollisionShape()))
				active = true;
			return;
		} else if (!active)
			return;
		
		switch(triggerEvent) {
		case ENTER:
			if(triggerArea.contains(charakter.getCollisionShape())) {
				fireTrigger(charakter);
				active = false;
			}
			break;
		case LEAVE:
			if(!triggerArea.contains(charakter.getCollisionShape()) && !triggerArea.intersects(charakter.getCollisionShape())) {
				fireTrigger(charakter);
				active = false;
			}
			break;
		case TOUCH:
			if(triggerArea.intersects(charakter.getCollisionShape()))
				fireTrigger(charakter);
			break;
		case INTERACT:
			if(triggerArea.contains(charakter.getCollisionShape()) || charakter.getCollisionShape().contains(triggerArea) || 
					(charakter.isLookingInDirectionOf(triggerArea) && charakter.isInNearOf(triggerArea, interactionRange)))
				fireTrigger(charakter);
			break;
		case INIT:
			active = false;
			return;
		default:
			break;
		}
	}
	
	public abstract void fireTrigger(Charakter charakter);

	public TriggerEvent getTriggerEvent() {
		return triggerEvent;
	}
	
	public Shape getTriggerArea() {
		return triggerArea;
	}

	public boolean isActive() {
		return active;
	}

	public void setTriggerEvent(TriggerEvent triggerEvent) {
		this.triggerEvent = triggerEvent;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public static int getRangeFromGroupObject(GroupObject groupObject) {
		return Integer.valueOf(groupObject.props.getProperty("Range", "50"));
	}

}
