package model;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

import view.Affichage;

/**
 * Cette classe contient des caractéristques des décors qui seront affiché ensuite sur l'interface.
 * 
 * @author: Jing ZHANG & Liuyi CHEN
 * */
public class Decors {
	/*Des attributs de la decoration*/
	private int nbCloud=4;
	private int lenCloud=50;
	private BufferedImage imgCloud;
	/*Une liste des coordonnees des nuages*/
	private ArrayList<Point> posClouds=new ArrayList<Point>();
	
	/*La décoration déplace en fonction de la moto*/
	private int pos=Etat.vitesseMoto;
	private int posAvance=0;
	
	/**CONSTRUCTEUR
	 * Initialiser a décoration de manière arbitraire lors de l'instanciation
	 * */
	public Decors() {
		initClouds();
	}
	
	/*Initialisation des coordonn�es des nuages*/
	void initClouds() {
		try {
			Random rd=new Random();
			setImgCloud(ImageIO.read(new File("src/image/cloud.png")));
			int x=0;
			for (int i =0;i<nbCloud;i++) {
				x =x+300+rd.nextInt(80);
				int y=5+rd.nextInt(70);
				posClouds.add(new Point(x,y));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * getParcours retourne une copie de la ligne avec les Points calcules
	 * C'est-a-dire on renvoie que les points visibles,
	 *  dont les coordonn�es ont �t� calcul�es en retirant la valeur de position � leur valeur d�abscisse.
	 * */
	public Point[] 	getPosCloud(){
		/*On cr�e un tableau de Points pour ne pas �craser la ligne origine*/
		Point[] copie = new Point[posClouds.size()];
		Random rd=new Random();
		int i=0;
		for(Point pt: posClouds){
			copie[i]=new Point(pt.x-getAvance(),pt.y);
			i+=1;
		}
		/**Lorsque le deuxieme point sort de la zone visible,
		 * On retire le premier point de la ligne origine.
		 * On attend le deuxieme point pour retirer le premier parce que si on supprimer le premier point d�s qu'il sort de la zone,
		 * on va perdre la trace entre le premier point et le deuxieme point.
		 * */
		if(copie[1].x<0) {
			posClouds.remove(0);
		}
		
		/**Lorsque le dernier point rentre dans la fen�tre visible,
		 * On ajoute un point suppl�mentaire dans la ligne initiale (ArrayListe) pour que la ligne bris�e ne s�interrompe pas.*/
		if(copie[copie.length-1].x<=Affichage.LARG) {
			int nx =posClouds.get(posClouds.size()-1).x+300+rd.nextInt(100);
			int ny=5+rd.nextInt(70);
			posClouds.add(new Point(nx,ny));
		}
		return copie;
	}
	
	/**
	 * Retourner la valeur courante de la position
	 * */
	public int getAvance() {
		return posAvance;
	}
	
	/*faire incrementer la position de quelques pixels*/
	public void setAvance() {
		posAvance=posAvance+5;

	}
	
	/**
	 * Selon un appui de clavier, les décors va à droite.
	 * */
	public void goRight() {
		setPosCloud(pos);
	}
	
	/**
	 * Selon un appui de clavier, les décors va à gauche.
	 * */
	public void goLeft() {
		setPosCloud(-pos);
	}
	
	/**
	 * déplacement horizontal des nuages
	 * @param position : le décalage 
	 */
	public void setPosCloud(int position) {
		posClouds.forEach(e->e.x+=position);
	}
	
	/******************************************
	 * Getters & Setters
	 *************************************** */
	
	/**
	 * renvoie un objet d'image des nuages
	 * */
	public BufferedImage getImgCloud() {
		return imgCloud;
	}

	public void setImgCloud(BufferedImage imgCloud) {
		this.imgCloud=imgCloud;
	}

	public int getLenCloud() {
		return lenCloud;
	}


}
