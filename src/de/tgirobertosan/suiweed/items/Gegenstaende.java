package de.tgirobertosan.suiweed.items;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.tgirobertosan.suiweed.charakter.Charakter;
import de.tgirobertosan.suiweed.spielwelt.trigger.Trigger;

public class Gegenstaende{
	
	private java.util.List<String> text;
	private int line = 1;
	private String path = "";
	private int anzahlSchwerter = 5;
	
	private ArrayList<Gegenstand> schwerter = new ArrayList<>();
	private ArrayList<Waffe> aexte = new ArrayList<>();
	private ArrayList<Waffe> dolch = new ArrayList<>();
	
	public Gegenstaende() throws SlickException, IOException{
		
		
	
		int abstand = 0;
		text = Files.readAllLines(Paths.get("res/Gegenstaende/WaffenSchaden.txt"));
		
		for(int index = 0; index < anzahlSchwerter; index++){
			path = "res/Gegenstaende/Waffen/Schwerter/Schwert" + index + ".png";
			abstand += 45;
			schwerter.add(new Schwert(new Image(path), index, 1315 + abstand, 1700, text));	
		}
		
	}
	
	
	public void render(Graphics g){
		
		for(int index = 0; index < schwerter.size(); index++){
			schwerter.get(index).zeichne(g);
		}
		
	}
	
	public void update(){
		
	}
	
	public void deleteItem(Gegenstand gegenstand){
	
		schwerter.remove(gegenstand);
		
	}
	
	
	
	public ArrayList<Gegenstand> getSchwerter(){
		return schwerter;
	}
}

