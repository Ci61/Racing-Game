package model;

import view.Affichage;

/**
 * Cette classe se compose des attributs et des méthodes qui gere le déplacement, l'accélération et la vitesse de la moto.
 * Aussi, il contient la gestion du temps restant
 * 
 * @author: Jing ZHANG & Liuyi CHEN
 * */
public class Moto {

	//Les caractéristiques de la moto :
	/**la largeur */
	public final static int wid=70;
	/** la hauteur */
	public final static int len=70;
	/**l'abscisse*/
	private int x;
	/**l'ordonnée*/
	public final static int y=Affichage.HAUT-len;
	/**Le chemin où se trouve l'image de la moto*/
	private String srcMoto="src/image/moto.png";
	
	//vitesse & acceleration
	public int vitesse = 2;
	public int vitesseAux = 0;
	public int vitesse_max = 250;
	private int accelerationX = 1;
	private boolean accelerationY=false;
	
	//Temps restant et temps alloué
	private int ajoutTemps=10;
	private int creditTemps=30;

	
	/**
	 * CONSTRUCTEUR
	 * */
	public Moto() {
	}
	
	/**
	 * Indique à la moto qu'elle s'est crashée
	 */
	public void crash() {
		vitesse=2;
	}
	
	
	/**
	 * Déplace la moto vers la droite, et vérifier qu'il ne dépasse pas la fenêtre
	 */
	public void goRight() {
		if(x+wid+1<=Affichage.LARG) x=x+Etat.vitesseMoto;
	}

	/**
	 * Déplace la moto vers la gauche, et vérifier qu'il ne dépasse pas la fenêtre
	 */
	public void goLeft() {
		if(x>=0) x=x-Etat.vitesseMoto;
	}

	/**
	 * Calcule de la vitesse en fonction de l’accélération et de la position par rapport à la piste
	 * Si l'utilisateur accélère la moto en appuyant sur le bouton, l'accélération sera plus grande lorsque la moto est sur la route,
	 * et elle décélérera plus lentement lorsqu'elle est hors route.
	 */
	public void accelerer(Etat etat) {
		vitesseAux = (int)((1 - (float)etat.parcours.distancePiste(x)/Affichage.LARG) * vitesse_max);
		if (accelerationY) {
			vitesseAux = vitesse_max;
		}
		if (vitesse <= vitesseAux - accelerationX) {
			if(accelerationY) {
				vitesse += 4*accelerationX;
			}else
				vitesse += accelerationX;
		//Si il va atteindre sa vitesse max
		}else if (vitesse >= vitesseAux && vitesse > 0) {
			if(accelerationY)
				vitesse -=  accelerationX;
			else
				vitesse -=  2*accelerationX;
		}else {
			vitesse = vitesseAux;
		}
	}
	
	/**
	 * Fait perdre le temps restant
	 */
	public void perdCTemps() {
		creditTemps--;
	}
	
	/**
	 * À chaque point de contrôle, un temps supllémentaire sera alloué
	 * */
	public void reinitTemps() {
		creditTemps+=ajoutTemps;
	}
	
	/******************************************
	 * Setters & Getters
	 *************************************** */

	/**
	 * Initialiser l'abscisse de départ de la moto
	 * */
	public void setPosition(int x0) {
		this.x=x0-wid/2;
	}
	
	public int getTemps() {
		return creditTemps;
	}
	
	/**
	 * Déterminer s'il faut accélérer la moto
	 * */
	public void setAccelerY(boolean b) {
		this.accelerationY=b;
	}
	
	/**
	 * Retourne true s'il la moto est en mode accéléré
	 * */
	public boolean getAccelerY() {
		return accelerationY;
	}
	
	/**Déterminer l'image de la moto 
	 * */
	public void setImage(String img) {
		this.srcMoto=img;
	}
	
	/**
	 * Retourne la chemin où se trouve l'image de la moto
	 * @return String 
	 */
	public String getImage() {
		return this.srcMoto;
	} 
	
	/**
	 * Retourne l'ordonnée de la moto
	 * @return int
	 */
	public int getHauteur() {
		return y;
	}

	/**
	 * Retourne l'abscisse de la moto
	 * @return int
	 */
	public int getPosition() {
		return x;
	}
	
}
