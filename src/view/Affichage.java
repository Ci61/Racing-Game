package view;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import model.*;

/**
 * Cette classe gère l'affichage de tous les éléments dans l'interface
 * Ainsi elle est "focus owner" du listener clavier
 * 
 * @author: Jing ZHANG & Liuyi CHEN
 * */
@SuppressWarnings("serial")
public class Affichage extends JPanel {
	
	//La taille de la fenêtre
	public static final int LARG=800;
	public static final int HAUT=600;
	/*L'horizon fixé se situe dans le quart supérieur de la fenêtre.*/
	public static final int horizon=HAUT/3;
	public final static int SOL = HAUT*9/10;	
	
	private Etat etat=new Etat();
	
	/**CONSTRUCTEUR
	 * Généralisation de la fenêtre, démarrage des threads pour réaliser de la programmation concurrente
	 * @param Etat : e
	 * */
	public Affichage(Etat e) {
		this.etat=e;
		setPreferredSize(new Dimension (LARG,HAUT));
		/*Définir que la fenêtre est "focus owner" de l'objet listener */
        this.setFocusable(true);
        this.requestFocusInWindow();
	}
	
	/**
	 * Méthode qui dessine tous les éléments sur l'interface :
	 * le décor, le véhicule, la piste
	 * @param : Graphics
	 */
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		super.paint(g2);

		/*Dessine la pelouse*/
		g.setColor(new Color(73, 186, 106));
		g.fillRect(0, HAUT/3, LARG, HAUT);
		
		/*La piste*/
		paintParcours(g2);
		
		/*La horizon et le ciel*/
		paintHorizon(g2);
		
		/*le véhicule*/
		paintMoto(g2);
		
		/*le décors*/
		paintDecors(g2);
		
		/*les obstacles éventuelles*/
		etat.parcours.paintObstacles(g2);
		
		/*les points de controle éventuelles*/
		etat.parcours.paintCheckPoint(g2);
		
		/*les oiseaux au ciel*/
		etat.birds.dessiner(g2);
		
		/*Des informations à afficher*/
		paintTexte(g2);
		
		/*L'interface de fin de jeu (l'utilisateur est perdu)*/
		paintPerdu(g2);
		
		/*Si le jeu est en pause, un menu sera dessine*/
		if (etat.getPause()) {
			etat.menu.draw(g2);
		}
		
	}
	
	/**
	 * Dessine de l'horizon fixé
	 * @param g {@link Graphics2D}
	 */
	public void paintHorizon(Graphics2D g) {
		g.setColor(new Color(255, 254, 200));
	    g.fillRect(0,0,LARG, horizon);   
	}
	
	/**
	 * Affichage de la moto avec une image personnalisée à l'aide de la classe BufferedImage
	 * @param g {@link Graphics2D}
	 */
	public void paintMoto(Graphics2D g) {
		BufferedImage img;
		Moto moto=etat.moto;
		try {
			img=ImageIO.read(new File(moto.getImage()));
			g.drawImage(img, moto.getPosition(), moto.getHauteur(),Moto.wid,Moto.len, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Dessine le parcours
	 * @param g {@link Graphics2D}
	 */
	public void paintParcours(Graphics2D g) {
		ArrayList<Point> parcours = etat.parcours.getParcours();
		for (int i=0; i < parcours.size()-1; i++) {
			g.setStroke(new BasicStroke(4));
			Point x0 = new Point(parcours.get(i).x, parcours.get(i).y);
			Point x1 = new Point(parcours.get(i+1).x, parcours.get(i+1).y);
			
			//Caclule la partie gauche du parcours afficher
			int xR_haut = x0.x - (Affichage.LARG/20 + (x0.y - 300)/4);
			int xR_bas = x1.x - (Affichage.LARG/20 + (x1.y - 300)/4);
			
			//Caclule la partie droite du parcours à afficher
			int xL_haut = x0.x + (Affichage.LARG/20 + (x0.y - 300)/4);
			int xL_bas = x1.x + (Affichage.LARG/20 + (x1.y - 300)/4);
			
			//Trace les pav�s
			g.setStroke(new BasicStroke(1));
			g.setPaint(new Color(111, 112, 112));
			int[] x = {xR_haut, xR_bas, xL_bas, xL_haut};
			int[] y = {x0.y, x1.y, x1.y, x0.y};
			g.fillPolygon(x, y, 4);
			
			
			//Trace les bordures de la route
			g.setStroke(new BasicStroke(3f));
			g.setPaint(Color.WHITE);
			g.drawLine(xR_haut, x0.y , (xR_bas+xR_haut)/2, (x0.y+x1.y)/2);
			g.drawLine(xL_haut, x0.y,(xL_bas+xL_haut)/2, (x0.y+x1.y)/2);
			g.setPaint(new Color(236, 52, 15 ));
			g.drawLine((xR_bas+xR_haut)/2, (x0.y+x1.y)/2,xR_bas, x1.y);
			g.drawLine((xL_bas+xL_haut)/2, (x0.y+x1.y)/2,xL_bas, x1.y);
			
			//Trace la ligne au milieu
			g.setStroke(new BasicStroke(2));
			g.setColor(new Color(193, 193, 193));
			Point haut = Etat.pntInter(0, x0.y - 60, 100, x0.y - 60,x0.x,x0.y,x1.x,x1.y);
			Point bas = Etat.pntInter(0, x1.y + 100, 100, x1.y + 100,x0.x,x0.y,x1.x,x1.y);
			g.drawLine(haut.x, haut.y, bas.x, bas.y);
		}
	}
	
	/**
	 * Dessine le texte
	 * @param g {@link Graphics2D}
	 */
	public void paintTexte(Graphics2D g) {		
		/*afficher le kilométrage*/
		if(etat.moto.getAccelerY()) {
			g.setPaint(new Color(12, 58, 151));
		}else
			g.setPaint(Color.BLACK);
		
		g.setFont(new Font("Arial", Font.BOLD, 16)); 
		g.drawString("Kilomètre\n: "+Integer.toString(etat.parcours.getPosition()), LARG/100, 40);
		g.drawString("Temps restant : "+Integer.toString(etat.moto.getTemps()), LARG - (LARG+HAUT)/9, 40);
		g.drawString(Integer.toString(etat.moto.vitesse)+" km/h", 20, HAUT - (LARG+HAUT)/30);
	}
	
	
	/**
	 * Affichage du décor:
	 * des montagnes, des nuages au ciel et le kilométrage
	 * @param Graphics2D
	 * */
	public void paintDecors(Graphics2D g) {
		
		/*dessiner Les Montagnes*/
		g.setColor(new Color(102, 136, 177)); 
		int[] x5 = {100,200,400};
		int[] y5 = {horizon, 100,horizon};
		g.fillPolygon(x5, y5, 3);
		
		g.setColor(new Color(102, 136, 177)); 
		int[] x4 = {400,600,800};
		int[] y4 = {horizon, 120,horizon};
		g.fillPolygon(x4, y4, 3);
		
		g.setColor(new Color(30, 55, 153)); 
		int[] x3 = {100, 350,600};
		int[] y3 = {horizon, 90,horizon};
		g.fillPolygon(x3, y3, 3);
		
		g.setColor(new Color(0, 28, 75)); 
		int[] x2 = {-70, 125,230};
		int[] y2 = {horizon, 50,horizon};
		g.fillPolygon(x2, y2, 3);
		
		g.setColor(new Color(0, 28, 75)); 
		int[] x1 = {690,800,800};
		int[] y1 = {horizon, 50,horizon};
		g.fillPolygon(x1, y1, 3);

		/*dessiner les nuages*/
		Decors dcr=etat.dcr;
		BufferedImage img=dcr.getImgCloud();
		Point[] clouds = dcr.getPosCloud();
		for(Point c:clouds) {
			g.drawImage(img,c.x ,c.y ,dcr.getLenCloud(),dcr.getLenCloud(), null);
		}

	}

	/**
	 * Afficahge si l'utilisateur perd le jeu
	 * @param g {@link Graphics2D}
	 */
	public void paintPerdu(Graphics2D g) {
		if(etat.estPerdu()) {
			g.setColor(Color.WHITE);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			int[] x = {0, LARG,LARG, 0};
			int[] y = {0,0, HAUT, HAUT};
			g.fillPolygon(x, y, 4);
			g.setColor(Color.BLACK);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
			g.drawString("GAME OVER", LARG/2-(LARG+HAUT)/10, HAUT/2);
			g.setFont(new Font("TimesRoman", Font.PLAIN, (LARG+HAUT)/45));
			g.drawString("Score : "+Integer.toString(etat.parcours.getPosition()/100), LARG/2-60, HAUT/2+50);
		}
	}
	
	public static void paintObstacle(Graphics2D g, int x, int y,float opacity,BufferedImage imgObsta) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		g.drawImage(imgObsta,x ,y ,75,75, null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
	}
	
}
