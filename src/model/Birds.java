package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import control.Bird;
import control.SynchroAff;
import view.Affichage;

/**
 * Cette classe se compose d'une liste d'oiseaux, et qui gère à ajouter et afficher un oiseau généré arbitrairement dans l'interface 
 * 
 * @author: Jing ZHANG & Liuyi CHEN
 * */
public class Birds {
	/*Une liste des oiseaus*/
	private ArrayList<Bird> list_bird=new ArrayList<Bird>();
	private Etat etat;
	private SynchroAff aff;
	
	/**CONSTRUCTEUR
	 * Ajout d'un oiseau dans la liste des oiseaus au depart
	 * */
	public Birds(Etat e,SynchroAff a) {
		this.etat=e;
		this.aff=a;
		this.list_bird.add(new Bird(e));
	}
	
	/**
	 * Cette méthode place dans un objet Graphics l'image correspondant à l'état de l'oiseau
	 * Lorsqu'un oiseau est hors de la zone visible, il sera être supprimé dans la liste et un nouvel aura été rajouté dans la liste
	 * @param: Graphics_g
	 * */
	public void dessiner(Graphics g){
		if(list_bird.size()>0) {
			for(Bird b: list_bird) {
				if(b.getPosition()<Affichage.LARG+50) {
					BufferedImage img;
					try {
						/*On charge l'image dans un buffer pour la dessiner*/
						img = ImageIO.read(new File("src/image/bird/"+b.getNum()+".png"));
						g.drawImage(img, b.getPosition(), b.getHauteur(),90,90, null);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else {
					this.list_bird.add(new Bird(etat));
					list_bird.remove(b);
				}
			}
			aff.redraw();
		}

	}
	
}
