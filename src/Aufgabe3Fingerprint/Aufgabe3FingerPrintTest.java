package Aufgabe3Fingerprint;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

public class Aufgabe3FingerPrintTest {

	public final static String fileLocationBasicPicture = "src\\pictures\\BildWordNurAstloecher.bmp";
	public final static String fileLocationRotatedPicture = "src\\pictures\\BildWordNurAstloecherGedreht.bmp";
	public final static String fileLocationSegmentPicture = "src\\pictures\\BildWordNurAstloecherAusschnitt.bmp";
	
	public static void aufgabe3FingerprintTest() {
		
		//Lese Bilder ein		
		BufferedImage basis = null;
		BufferedImage rotated = null;
		BufferedImage segment = null;
		try {
			basis = ImageIO.read(new File(fileLocationBasicPicture));
			rotated = ImageIO.read(new File(fileLocationRotatedPicture));
			segment = ImageIO.read(new File(fileLocationSegmentPicture));
		} catch (IOException e) {
		System.out.println(e.getMessage());
			throw new RuntimeException("Konnte Bild nicht einlesen");
		}
		
		List<Mittelpunkt> listBasis= Aufgabe3Fingerpint.aufgabe3Fingerprint(basis);
		List<Mittelpunkt> listRotated= Aufgabe3Fingerpint.aufgabe3Fingerprint(rotated);
		if(Aufgabe3FingerPrintTest.vergleiche(listBasis, listRotated)) {
			System.out.println("Die beiden Bilder sind identisch oder ein Ausschnitt des anderen");
		}else {
			System.out.println("Es konnte keine Übereinstimmung gefunden werden");
		}
		
		
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		System.out.print("\n\n\n\n\n");
		List<Mittelpunkt> listSegment = Aufgabe3Fingerpint.aufgabe3Fingerprint(segment);
		if(Aufgabe3FingerPrintTest.vergleiche(listSegment, listBasis)) {
			System.out.println("Die beiden Bilder sind identisch oder ein Ausschnitt des anderen");
		}else {
			System.out.println("Es konnte keine Übereinstimmung gefunden werden");
		}
	}
	
	/**
	 * 
	 * @param listImage Liste der Mittelpunkte, die mit dem Basis Bild verglichen werden sollen
	 * @param listBasis Liste der Mittelpunkte des Basisimage
	 * @return true, wenn identisch/enthalten
	 */
	public static boolean vergleiche(List<Mittelpunkt> listImage, List<Mittelpunkt> listBasis) {
		int count = 0;
		//Mittelpunkt
		for(int i = 0; i< listImage.size(); i++) {
			Mittelpunkt m = listImage.get(i);
			//alle Entfernungen zu anderen Mittelpunkten
			for(int k = 0; k< listBasis.size(); k++) {
				if(vergleicheMittelpunkte(m, listBasis.get(k))) {
					//wenn identischen Mittelpunkt gefunden, dann zähle mit
					count++;
					System.out.println("Übereinstimmenden Mittelpunkt für Mittelpunkt " + i + " gefunden");
					break;
				}
			}
			
		}
		//wenn genauso oft Mittelpunkt gefunden, wie Mittelpunkte in liste1, dann zu jedem mittelpunkt den passenden gefunden
		if(count == listImage.size()) {
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
	private static boolean vergleicheMittelpunkte(Mittelpunkt punktImage, Mittelpunkt punktBasis) {

		//iteriere über alle Entfernungen von m1
		for(int i = 0; i<punktImage.entfernungen.size(); i++) {
			double imageWert = punktImage.entfernungen.get(i);
			boolean gefunden = false;
			for(int k = 0; k < punktBasis.entfernungen.size(); k++) {
				if(imageWert == punktBasis.entfernungen.get(k)) {
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
