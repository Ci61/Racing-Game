package control;

import model.*;

/**
 * Cette classe gère l'acheminement du parcours
 * 
 * @author: Jing ZHANG & Liuyi CHEN
 * */
public class Avancer extends Thread{
	  private Etat etat;
	  private SynchroAff affichage;
	  
	  /*La duree du blocage du thread actuel*/
	  private static final int DELAY = 50; 
	  
	  /**Associe le thread avec {@link Etat} et {@link SynchroAff}
	   * @param e Etat
	   * @param a SynchroAff
	   * */
	  public Avancer(Etat e,SynchroAff a) {
		  this.etat = e;
		  this.affichage=a;
	  }
	  
	  /**
	   * Définit la fonction run :
	   * Une boucle (s'arretant lors que le joueur est perdu) qui fait avancer la piste
	   */
	  @Override
	  public void run() {
	    while(!etat.estPerdu()) {
	    	if(!etat.getPause()) {
		    	etat.parcours.avancer();
		    	affichage.redraw();
	    	}
	    	try {
	            Thread.sleep(DELAY);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	   
	  }
}
