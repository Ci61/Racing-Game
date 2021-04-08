package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import view.Affichage;


/**Cette calsse est un obstacle généré arbitrairement par la classe Parcours
 * 
 *  @author Jing ZHANG & Liuyi CHEN*/
public class Obstacle {
	
	private Random rand = new Random();
	private BufferedImage imgObsta;
	public int x = 0;
	public int y = 0;
	private final int wid = 60+rand.nextInt(20);
	private final int len = 60+rand.nextInt(20);
	private boolean heurter = false;
	
	/**
	 * Initialisation du décors
	 * @param yy la position initiale en y du décors
	 * @param pos_piste la position de la piste sur le y initial
	 */
	public Obstacle(int avance,int nbImg) {
		try {
			setImgObsta(ImageIO.read(new File("src/image/obstacle"+nbImg+".png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.y = avance;
		this.x = rand.nextInt(Affichage.LARG * 5) - Affichage.LARG*2;
		
	}
	
	public void draw(Graphics2D g, int pos) {
		int position_y = pos - y;
		int position_x = 0;
		position_x = Etat.pntInter(0, position_y, 100, position_y, Affichage.LARG/2, Affichage.horizon, x, Affichage.HAUT*2).x;
		//Si le vehicule entre en collision avec un obstacle, cela va rendre l'obstacle un peu transparent
		float opacity= heurter ? 0.5f : 1f;
		Affichage.paintObstacle(g, position_x, position_y,opacity,getImgObsta());
	}
	
	/**
	 * Renvoi la position en x de l'obstacle
	 * @param pos l'avancement actuel du parcours
	 * @return l'abscisse de l'obstacle
	 */
	public int getX(int pos) {
		int position_y = pos - y;
		return Etat.pntInter(0, position_y, 100, position_y, Affichage.LARG/2, Affichage.horizon, x, Affichage.HAUT*2).x;
	}
	
	/**Chargez l'image correspondant à l'obstacle
	 * */
	private void setImgObsta(BufferedImage read) {
		this.imgObsta=read;
	}
	
	/**L'image correspondante à l'obstacle
	 * */
	public BufferedImage getImgObsta() {
		return imgObsta;
	}
	
	/**
	 * Lorsque la moto rencontre un obstacle, heurter est égale à true
	 * */
	public void setHeurter() {
		heurter = true;
	}
	
	/**
	 * Renvoie si la moto a rencontré l'obstacle
	 * */
	public boolean estHeurter() {
		return heurter;
	}

	/**La largeur de l'obstacle
	 * */
	public int getWid() {
		return wid;
	}
	
	/**La longeur de l'obstacle
	 * */
	public int getLen() {
		return len;
	}
}
