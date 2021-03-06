package de.tgirobertosan.suiweed.spielwelt.trigger;

import java.io.IOException;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.GroupObject;

import de.tgirobertosan.suiweed.charakter.Charakter;

public class Dialogzeile extends Trigger {
	
	private int line;

	public Dialogzeile(TriggerEvent triggerEvent, Shape triggerArea,
			int interactionRange, int line) {
		super(triggerEvent, triggerArea, interactionRange);
		this.line = line;
	}

	@Override
	public void fireTrigger(Charakter charakter) {
		System.out.println("FIRE DIALOGZEILE");
		try {
			charakter.getDialogfenster().aktualisiereDialogfenster(line);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Trigger getFromGroupObject(GroupObject groupObject) {
		TriggerEvent triggerEvent = Trigger.getEventFromGroupObject(groupObject);
		Shape shape = groupObject.getShape();
		int range = Trigger.getRangeFromGroupObject(groupObject);
		int line = Integer.valueOf(groupObject.props.getProperty("Line", "-1"));
		if(line == -1) {
			System.out.println("Missing 'Line=INTEGER' Parameter in Custom Properties of "+groupObject.type+" "+groupObject.name);
			line = 0;
		}
		Dialogzeile dialogzeile = new Dialogzeile(triggerEvent, shape, range, line);
		return dialogzeile;
	}

}
