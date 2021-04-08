package model;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import view.Affichage;

/**
 * Cette classe contient des méthodes concernant les fonctionnalités sur le parcours.
 * 
 * @author: Jing ZHANG & Liuyi CHEN
 * */
public class Parcours {
	//Liste des points qui constituent la route
	private ArrayList<Point> ligne = new ArrayList<Point>();
	//Liste des obstacles sur la route
	private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	//Liste des points de controle
	private ArrayList<CheckPoint> check_points = new ArrayList<CheckPoint>();
	private Etat etat;
	private static final Random rd = new Random();

	//Position de la pointe de la piste
	private int x0=Affichage.LARG/2;
	private int y0=Affichage.HAUT;
	
	//Avancement
	private int position = 0;
	
	//Caractéristiques de la route
	private int segment = 300;
	private int variant = 300;
	private Point precedent = new Point(0,0);
	
	//Plus la valeur est petite, plus il y a de décors
	private static final int TauxObstacles = 70;
	
	/**
	 * Initialisation de la ligne aléatoirement
	 */
	public Parcours(Etat e) {
		this.etat=e;
		initLigne();
	}	
	
	/**
	 * Ajoute un point au parcours
	 */
	private void initLigne() {
		ligne.add(new Point(x0,y0));
		//Définie la longueur du prochain segment
		y0 -= rd.nextInt(segment/3) + segment;
		etat.moto.setPosition(x0);
	}
	
	public void addLigne() {
		x0 += rd.nextInt(variant)-variant/2;
		if (x0 < 250) {
			x0=250;
		} else if (x0> Affichage.LARG*3/4) {
			x0= 500 + rd.nextInt(150);
		}
		ligne.add(new Point(x0,y0));
		//Définie la longueur du prochain segment
		y0 -= rd.nextInt(segment/3) + segment;
	}
	
	/**
	 * Retourne une copie de la ligne avec les points visibles dans la fenetre.</BR>
	 * S'il n'y a plus assez de points, on en rajoute un. Par contre, si un point est loin de la zone visible, on va le supprimer
	 * @return une copie de points
	 */
	public ArrayList<Point> getParcours() {
		ArrayList<Point> copie = new ArrayList<Point>();
		for (int i=0; i<ligne.size(); i++) {
			if (ligne.get(i).y + position < Affichage.HAUT*3) {
				copie.add(new Point(ligne.get(i).x, ligne.get(i).y + position));
				if (ligne.get(i).y + position < 0) {
					return copie;
				}
				if (i==ligne.size()-1) {
					addLigne();
				}
			} else {
				ligne.remove(i);
			}
		}
		return copie;
	}
	
	/**
	 * Renvoie le kilometrage de la piste
	 * @return position
	 */
	public int getPosition() {
		return position;
	}
	
	/**
	 * Avance la postion du parcours. Lors de l'acheminement, on ajoute des obstacles arbitrairement. 
	 * De plus, 
	 * @param etat 
	 */
	public void avancer() {
		this.position += etat.moto.vitesse/10;
		//Créer des obstacles arbitrairement 
		if (rd.nextInt(TauxObstacles) == 0) {
			addObstacles();
		}
		testObstacles();
		testCheckPnts();
	}
	
	/**Test collision des obstacles avec la moto
	 * */
	void testObstacles() {
		for (Obstacle ob : obstacles) {
			if (!ob.estHeurter()) {
				//Test de collision en hauteur
				if (position - ob.y >= Affichage.SOL - ob.getLen() && position - ob.y <= Affichage.HAUT && etat.moto.getHauteur() >= Affichage.SOL - ob.getLen()) {
					//Test de collision en largeur
					if (etat.moto.getPosition() < ob.getX(position) + ob.getWid()  && etat.moto.getPosition() + Moto.wid > ob.getX(position)) {
						etat.moto.crash();
						ob.setHeurter();
					}
				}
			}
		}
	}
	
	/**Test collision des points de controles avec la moto
	 * */
	void testCheckPnts() {
		for (CheckPoint p : check_points) {
			if (!p.estCheck()) {
				if (position - p.y >= Moto.y && position - p.y <= Affichage.HAUT ) {
					etat.moto.reinitTemps();	
					p.setCheck();
				}
			}
		}
	}

	/**
	 * Calcule la distance de la piste au point
	 * @param x le x du point
	 * @param y le y du point
	 * @return la distance entre les deux (en pixel)
	 */
	public int distancePiste(int x) {
		return Math.abs((x+Moto.wid/2) - positionPiste(Affichage.HAUT));
	}
	
	/**
	 * Calcule la position de la piste au y donné
	 * @param y l'endoit où regarder la position de la piste
	 * @return la position x de la piste
	 */
	public int positionPiste(int y) {
		ArrayList<Point> parcours = getParcours();
		for (int i=1; i < parcours.size()-1; i++) {
			if (parcours.get(i).y <= y) {
				Point pos = Etat.pntInter(0, y, 100, y, parcours.get(i-1).x,parcours.get(i-1).y, parcours.get(i).x,parcours.get(i).y);
				if (y == Affichage.horizon) {
					precedent = pos;
				}
				int res = pos.x;
				return res;
			}
		}
		
		int res = precedent.x;
		return res;
	}
	
	/**
	 * Ajoute un obctacle avec une image aléatoire.
	 */
	public void addObstacles() {
		int nbImg=rd.nextInt(4);
		obstacles.add(new Obstacle(position - Affichage.HAUT/3,nbImg));
	}
	
	/**
	 * Dessine les obstacles
	 * @param g {@link Graphics2D}
	 */
	public void paintObstacles(Graphics2D g) {	
		//Dessine les decors
		for (int i = 0; i < obstacles.size(); i++) {
			//Si l'obstacle est hors de la fenêtre, on le supprime
			if (position - obstacles.get(i).y > Affichage.HAUT * 2) {
				obstacles.remove(i);
			} else {
				obstacles.get(i).draw(g, position);;
			}
		}
	}
	
	/**
	 * Ajoute un concurrent
	 */
	public void addCheckPoint() {
		check_points.add(new CheckPoint(position - Affichage.HAUT/2));
	}

	
	/**
	 * Dessine les checkpoints
	 * @param g {@link Graphics2D}
	 */
	public void paintCheckPoint(Graphics2D g) {	
		for (int i = 0; i < check_points.size(); i++) {
			if (check_points.get(i).estCheck()&&getPosition() - check_points.get(i).y > Affichage.HAUT*2 ) {
				check_points.remove(i);
			} else {
				int y =check_points.get(i).getY(position);
				int x=positionPiste(y);
				/*La largeur de la piste actuelle*/
				int taille = (Affichage.LARG/20 + (y - 300)/4);
				g.setStroke(new BasicStroke(5f));
				g.setPaint(new Color(246, 36, 64));
				int a0=y-70;
				int b0=y-50;
				int[] a = {x-taille-20, x+taille+20, x+taille+20, x-taille-20};
				int[] b = {a0, a0, b0, b0};
				g.fillPolygon(a, b, 4);
				g.setPaint(new Color(119, 47, 0));
				g.drawLine(x-taille-20, a0, x-taille-20, y);
				g.drawLine(x+taille+20, a0, x+taille+20, y);

			}
		}
	}
	
}
