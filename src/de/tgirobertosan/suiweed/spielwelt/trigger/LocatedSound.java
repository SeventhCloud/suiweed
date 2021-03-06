package de.tgirobertosan.suiweed.spielwelt.trigger;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.GroupObject;

import de.tgirobertosan.suiweed.charakter.Charakter;
import de.tgirobertosan.suiweed.spielwelt.SoundOnSpielwelt;

public class LocatedSound extends Trigger {
	
	private SoundOnSpielwelt sound;
	private boolean loop;

	public static LocatedSound getFromGroupObject(GroupObject groupObject) {
		TriggerEvent triggerEvent = Trigger.getEventFromGroupObject(groupObject);
		Shape shape = groupObject.getShape();
		String soundPath = groupObject.props.getProperty("Sound", "res/spielwelt/audio/kebab.ogg");
		SoundOnSpielwelt sound = null;
		float volumeModifier = Float.valueOf(groupObject.props.getProperty("Volume", "1.0"));
		boolean loop = Boolean.valueOf(groupObject.props.getProperty("Loop", "false"));
		try {
			sound = new SoundOnSpielwelt(soundPath, shape.getCenterX(), shape.getCenterY(), volumeModifier);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return new LocatedSound(triggerEvent, shape, Trigger.getRangeFromGroupObject(groupObject), sound, loop);
	}
	
	public LocatedSound(TriggerEvent triggerEvent, Shape triggerArea,
			int interactionRange, SoundOnSpielwelt sound, boolean loop) {
		super(triggerEvent, triggerArea, interactionRange);
		this.sound = sound;
		this.loop = loop;
	}

	@Override
	public void fireTrigger(Charakter charakter) {
		if(!sound.playing())
			charakter.getSpielwelt().playSoundAt(sound, loop);
	}

}
