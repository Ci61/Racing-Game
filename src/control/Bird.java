package control;

import java.util.Random;

import model.Etat;
import view.Affichage;

/**
 * Cette classe faire afficher un oiseau qui se déplace en battant des ailes dans le décors.
 * 
 * @author: Jing ZHANG & Liuyi CHEN
 * */
public class Bird extends Thread{
	/*la numéro correspond à l'image de l'Bird dans la répertoire image*/
	private static int num=0;
	/*l'ordonnée*/
	private int hauteur;
	/*l'abscisse*/
	private static int position;
	/*la vitesse d'avance de l'Bird*/
	private int avance=5;
	/*cette variable indique le temps (en millisecondes) entre chaque mise à jour de différents images*/
	private int delai; 
	private Etat etat;
	
	/**CONSTRUCTEUR
	 * Initialiser le delai et la hauteur au hasard
	 * La position est fixée de manière ce que l'oiseau soit completement a gauche au démarrage du jeu
	 * */
	public Bird(Etat e) {
		Random r= new Random();
		this.delai=50+r.nextInt(50);
		this.hauteur=50+r.nextInt(200);
		Bird.position=-10;
		this.etat=e;
	}
	  
	
	/**
	 * Une boucle pour modifier l'abscisse et l'état de l'oiseau, la boucle s'arrete lorsqu'il est complètement sorti de la zone visible
	 * */
	@Override
	public void run() {
		/*une variable boulean controle la boucle*/
		boolean exit=false;   
	    while(!exit) {
  	    	if(getPosition()>=Affichage.LARG+50) {
				exit=true;
			}
  	    	if(!etat.getPause()) {
  	  	    	/*Mettre à jour l'abscisse de l'oiseau de sorte qu'il vole de gauche à droite*/
  				setPosition();
  				/*Mettre à jour l'état pour passer à l'image suivante de l'oiseau*/
  				setNum();
  	    	}
	    	try {
                Thread.sleep(delai);
            } catch (Exception e) {
            	Thread.currentThread().interrupt();
                e.printStackTrace();
            }
	    }
	   
	  }
	  
	  /**Renvoyer l'ordonnée de l'Bird*/
	  public int getHauteur() {
		  return this.hauteur;
	  }
	  
	  /**Faire voler l'Bird vers la droite*/
	  public void setPosition() {
		  position=position+avance;
	  }
	  
	  /**Renvoie l'abscisse de l'oiseau*/
	  public int getPosition() {
		  return Bird.position;
	  }
	  
	  /**
	   * Mettre à jour la valeur de l'état.
	   * Nous avons que 18 images (numéroté de 0 à 17) pour faire l'animation de l'Bird, donc on vérifie la valeur de l'état avant de l'incrémenter
	   * */
	  public void setNum() {
		  if(num>=17) {
			  Bird.num=0;
		  }else{
			  Bird.num=num+1;
		  }
	  }
	  
	  /**Renvoyer l'état de l'oiseau*/
	  public int getNum() {
		  return Bird.num;
	  }
	  
}
