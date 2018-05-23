package Aufgabe3Fingerprint;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;


public class Aufgabe3Fingerpint {
	
	private static int tiefpassGrenze=300;

	public static List<Mittelpunkt> aufgabe3Fingerprint(BufferedImage img) {
		System.out.println("Höhe: " + img.getHeight() + " Breite: " + img.getWidth());
		
		System.out.println("Anzahl Pixel: " + img.getHeight()*img.getWidth());
		//Berechne einen diskreten Wert für jeden Pixel anhand dessen nachher verglichen werden kann
		int[][] pixelDiscretValues = new int[img.getHeight()][img.getWidth()];
		int minValue = Integer.MAX_VALUE;
		for (int h = 0; h<img.getHeight(); h++)
    	{
        	for (int w = 0; w<img.getWidth(); w++)
        	{
        	
        	int value = createDiscretValueForPixel(img.getRGB(w, h));
        	if(value < minValue) minValue = value;
        	
        	pixelDiscretValues[h][w] = value;
        	//rgb = img.getRGB(w, h);
        	//red = (rgb >> 16 ) & 0x000000FF;
            //green = (rgb >> 8 ) & 0x000000FF;
        	//blue = (rgb) & 0x000000FF;
        	
        	
        	}
    	}
    
    	//Lasse einen Tiefpassfilter drüber laufen, damit nur Astlöcher über bleiben
		tiefpassFilter(pixelDiscretValues);
		
		
		//Berechne Mittelpunkte von Astlöchern
		List<Mittelpunkt> list = berechneMittelpunkte(pixelDiscretValues);
		
		
		//Berechne Entfernung von jedem mittelpunkt zu jedem anderen (und Winkel relativ zu Norden = 0°)=steht aus
		berechneEntfernung(list);
		
		return list;
   
	}
	
	private static void berechneEntfernung(List<Mittelpunkt> list){
		for(int i = 0; i<list.size(); i++) {
			System.out.println("Berechne Entfernungen für Mittelpunkt " + i + " / " + list.size());
			Mittelpunkt m = list.get(i);
			for(int k = 0; k<list.size(); k++) {
				Mittelpunkt andere = list.get(k);
				if(andere.equals(m)) {
					continue;
				}
				m.entfernungen.add(Math.sqrt(((m.posx-andere.posx)*(m.posx-andere.posx))+((m.posy-andere.posy)*(m.posy-andere.posy))));			
			}
			
		}
	}
	
	
	
	
	private static List<Mittelpunkt> berechneMittelpunkte(int[][] array) {

		//Problem:
		//000100
		//000100
		//wird nicht als mittelpunkt erkannt
		
		List<Mittelpunkt> list= new LinkedList<Mittelpunkt>();
		
		boolean fertig = true;
		int durchlaeufe = 0;
		do {
			int[][] newarray = new int[array.length][array[0].length];
			fertig = true;
			durchlaeufe++;
			//System.out.println(ausgabeArray(array, 300, 400, 0, 300));
			System.out.println("Durchlauf: " + durchlaeufe + " ");

			
			int anzMittelpunkte = 0;
			int anzRandpunkte = 0;
		
			for (int i = 0; i<array.length; i++)
			{
				for (int j = 0; j<array[i].length; j++)
				{
					
					if(array[i][j] == 1) {
						int count = 0;
						if(i-1 < 0 || i+1 >= array.length || j-1 < 0 || j+1 >= array[i].length) {
							//falls Randpunkt des Bildes automatisch auf 0 da kein Mittelpunkt sein kann -> per Definition (von mir ;) )
							newarray[i][j] = 0;
							continue;
						}
						
						
						
						//Zähle Nachbarn mit 1, die den Tiefpass erfolgreich ( -> ==1) durchlaufen haben
						if(array[i-1][j-1] == 1) {count++;}
						if(array[i-1][j] == 1) {count++;}
						if(array[i-1][j+1] == 1) {count++;}
						if(array[i][j-1] == 1) {count++;}
						if(array[i][j+1] == 1) {count++;}
						if(array[i+1][j-1] == 1) {count++;}
						if(array[i+1][j] == 1) {count++;}
						if(array[i+1][j+1] == 1) {count++;}
        	
						/*if(i>300 && i<400 && j<300) {
							System.out.print(count);
						}*/
							
						//Vier Arten von Pixel nun:
						//Mittelpunkt: count == 0
						if(count == 0) {
							anzMittelpunkte++;
							newarray[i][j] = 1;
							list.add(new Mittelpunkt(i, j));
							continue;
						}else if (count <= 4) {
						//Randpunkt: Nachbarn mit 1 <=Nachbarn mit 0 -> count <= 4
							anzRandpunkte++;
							newarray[i][j] = 0;
							
							//Abbruchbedingung, wenn im ganzen Durchlauf kein Randpunkt mehr gefunden wurde, dann nur noch Mittelpunkte über
							fertig = false;
						}else {
						//NichtRandpunkt: count >4, aber unbekannt ob Mittelpunkt -> wiederholen bis nur noch Mittelpunkte
							newarray[i][j] = 1;
						}
						//PunktamRandDesBildes: durch if-Anweisung automatisch entfernt
					}else {
						newarray[i][j] = 0;
						/*if(i>300 && i<400 && j<300) {
							System.out.print("0");
						}*/
					}
				}
				/*if(i>300 && i<400) {
					System.out.println("");
				}*/
				
			}
			System.out.print(" Anzahl Mittelpunkte: " + anzMittelpunkte);
			System.out.println(" Anzahl Randpunkte: " + anzRandpunkte);
			array = newarray;
		} while(fertig == false);
		
		return list;
	}

	private static int createDiscretValueForPixel(int rgb) {
		int red = (rgb >> 16 ) & 0x000000FF;
        int green = (rgb >> 8 ) & 0x000000FF;
    	int blue = (rgb) & 0x000000FF;
    	
    	int value = red + green + blue;
		
		return value;
	}
	
	private static void tiefpassFilter(int[][] array){
		int anzErgebnisse = 0;
		//Grenze = 150
		for (int i = 0; i<array.length; i++)
    	{
        	for (int j = 0; j<array[i].length; j++)
        	{
        		if(array[i][j] < tiefpassGrenze) {
        			array[i][j] = 1;
        			anzErgebnisse++;
        		}else {
        			array[i][j] = 0;
        		}
        	}
    	}
		System.out.println("Anzahl durch Tiefpass: " + anzErgebnisse);
	}
	private static String ausgabeArray(int[][] array, int startlaenge, int laenge, int startbereite, int breite) {
		StringBuilder sb = new StringBuilder();
		for (int i = startlaenge; i<laenge; i++)
    	{
        	for (int j = startbereite; j<breite; j++)
        	{
        		sb.append(array[i][j]);
        	}
        	sb.append("\n");
        }
		return sb.toString();
	}
}


class Mittelpunkt{
	int posx, posy;
	
	//Integer ist ID des Mittelpunktes
	//Double die Entfernung des eigenen Punktes zu diesem
	LinkedList<Double> entfernungen;
	
	public Mittelpunkt(int x, int y) {
		posx = x;
		posy = y;
		entfernungen = new LinkedList<Double>();
	}
}
