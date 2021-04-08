package model;

import java.awt.Point;
import java.util.Random;

import control.SynchroAff;


/**
 * Cette classe se compose des éléments dessinés dans l'interface .
 * Elle déclare également la vitesse de la moto et de la piste.
 * 
 * @author: Jing ZHANG & Liuyi CHEN
 * */
public class Etat {
	/**/
	public static enum CLAVIER {UP, DOWN};
	private SynchroAff affichage;
	Random rd=new Random();
	
	/*un objet Moto*/
	public Moto moto=new Moto();
	/*un objet Parcours*/
	public Parcours parcours=new Parcours(this);
	/*un objet Decors*/
	public Decors dcr=new Decors();
	/*defilement des oiseaux*/
	public Birds birds;
	public Menu menu = new Menu();
	
	/*une constante désigne le pixel déplacé lorsque que l'utilisateur appuie une touche*/
	public static int vitesseMoto=10;
	
	//Des flags
	private boolean pause = false;
	private boolean quit = false;
	
	/**CONSTRUCTEUR
	 * */
	public Etat() {
		
	}

	/**
	 * Initialise l'affichage pour l'état
	 * @param a : le thread d'affichage
	 */
	public void setAff(SynchroAff a) {
		birds=new Birds(this,a);
		affichage=a;
	}
	
	public void MotoRight() {
		moto.goRight();
		dcr.goLeft();
	}

	public void MotoLeft() {
		moto.goLeft();
		dcr.goRight(); 
	}
	
	/**
	 * Dès que la vitesse d'avancement (la vitesse du joueur) atteint 0 ou le compteur de temps arrive à 0 , le joueur est perdu. Donc le jeu se termine.
	 * @return boolean pour indiquer si le joueur est perdu ou non
	 * */
	public boolean estPerdu() {
		return (moto.getTemps()<=0)|| (moto.vitesse<=0) || quit;
	}
	
	/**
	 * Retourne la variable qui indique si le jeu est en pause ou non
	 * @return true si le jeu est en pause
	 */
	public boolean getPause() {
		return pause;
	}

	/**
	 * Change d'etat le jeu (pause devient reprise, jeu normal devient pause)
	 */
	public void paused() {
		if (!pause) {
			pause = true;
			affichage.redraw();
		} else {
			pause = false;
			affichage.redraw();
		}
	}
	
	/**
	 * Transmet la selection (enter) au {@link Menu}
	 */
	public void choose() {
		menu.choose(this);
		affichage.redraw();
	}
	
	
	/**
	 * Line Intersection Formula :
	 * x = ((x1*y2-y1*x2)*(x3-x4)-(x1-x2)*(x3*y4-x4*y3))/((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4))
	 * y = ((x1*y2-y1*x2)*(y3-y4)-(y1-y2)*(x3*y4-x4*y3))/((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4))
	 * where
	 * @param (x1,y1) starting point of segment 1
	 * @param  (x2,y2) ending point of segment 1
	 * @param  (x3,y3) starting point of segment 2
	 * @param  (x4,y4) ending point of segment 2
	 * */
	public static Point pntInter(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		int x = 1000;
		int y = 1000;
		
		if (((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4)) != 0) {
			x = ((x1*y2-y1*x2)*(x3-x4)-(x1-x2)*(x3*y4-y3*x4))/
					((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4));
		}
		if (((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4)) != 0)
			y = ((x1*y2-y1*x2)*(y3-y4)-(y1-y2)*(x3*y4-y3*x4))/
			((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4));
		return new Point(x,y);
	}
	
	/**
	 * Enregistre que le jeu se finit et qu'il faut fermer la fenetre
	 */
	public void quit() {
		quit = true;
	}

	/**
	 * Getter de quit
	 * @return quit
	 */
	public boolean estQuit() {
		return quit;
	}

	/**
	 * 
	 * */
	public void select(CLAVIER d) {
		if (pause) {
			menu.parcourir(d);
			affichage.redraw();
		} else {
			affichage.redraw();
		}	
	}
	
	/**L'utilisateur accélère la vitesse de {@link Moto}
	 * */
	public void accelerMoto() {
		if (!estPerdu()) {
			moto.setAccelerY(true);
		}
	}

	/**L'utilisateur a arrêté d'accélérer la vitesse de {@link Moto}
	 * */
	public void noAccelerMoto() {
		moto.setAccelerY(false);
	}

	/**
	 * Le jeu recommence 
	 * */
	public void reinit() {
		moto=new Moto();
		parcours=new Parcours(this);
		pause = false;
		affichage.redraw();		
	}
	

}
