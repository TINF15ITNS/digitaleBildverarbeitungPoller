package Aufgabe3Fingerprint;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

public class Aufgabe3FingerPrintTest {

	public final static String fileLocationFirstPic = "C:\\Users\\Nikolai\\OneDrive\\DHBW\\6.Semester\\Digitale Bildverarbeitung\\BildWordNurAstloecher.bmp";
	public final static String fileLocationSecondPic = "C:\\Users\\Nikolai\\OneDrive\\DHBW\\6.Semester\\Digitale Bildverarbeitung\\BildWordNurAstloecherGedreht.bmp";

	
	public static void aufgabe3FingerprintTest() {
		
		//Lese Bilder ein		
		BufferedImage img1 = null;
		BufferedImage img2 = null;
		try {
			img1 = ImageIO.read(new File(fileLocationFirstPic));
			img2 = ImageIO.read(new File(fileLocationSecondPic));
		} catch (IOException e) {
		System.out.println(e.getMessage());
			throw new RuntimeException("Konnte Bild nicht einlesen");
		}
		
		List<Mittelpunkt> list1= Aufgabe3Fingerpint.aufgabe3Fingerprint(img1);
		List<Mittelpunkt> list2= Aufgabe3Fingerpint.aufgabe3Fingerprint(img2);
		if(Aufgabe3FingerPrintTest.vergleiche(list1, list2)) {
			System.out.println("Die beiden Bilder sind identisch");
		}
		
		//vergleiche Mittelpunkte

	}
	
	public static boolean vergleiche(List<Mittelpunkt> list1, List<Mittelpunkt> list2) {
		int count = 0;
		//Mittelpunkt
		for(int i = 0; i< list1.size(); i++) {
			Mittelpunkt m = list1.get(i);
			//alle Entfernungen zu anderen Mittelpunkten
			for(int k = 0; k< list2.size(); k++) {
				if(vergleicheMittelpunkte(m, list2.get(k))) {
					//wenn identischen Mittelpunkt gefunden, dann zähle mit
					count++;
					break;
				}
			}
			
		}
		//wenn genauso oft Mittelpunkt gefunden, wie Mittelpunkte in liste1, dann zu jedem mittelpunkt den passenden gefunden
		if(count == list1.size()) {
			return true;
		}
			
		return false;
	}
	
	/**
	 * gibt true zurück, wenn alle Entfernungen der beiden Punkte gleich sind
	 * @param m1
	 * @param m2
	 * @return
	 */
	private static boolean vergleicheMittelpunkte(Mittelpunkt m1, Mittelpunkt m2) {

		//iteriere über alle Entfernungen von m1
		for(int i = 0; i<m1.entfernungen.size(); i++) {
			double m1wert = m1.entfernungen.get(i);
			boolean gefunden = false;
			for(int k = 0; k < m2.entfernungen.size(); k++) {
				if(m1wert == m2.entfernungen.get(k)) {
					gefunden = true;
					break;
				}
			}
			if(gefunden == false) {
				return false;
			}
		}
		
		
		
		return true;
	}
}
