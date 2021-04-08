package control;

import model.*;
import view.Affichage;

/**
 * Cette classe gère l'accélération de la moto
 * 
 * @author: Jing ZHANG & Liuyi CHEN
 * */
public class Accelerer extends Thread{
	  private Etat etat;
	  private SynchroAff affichage;
	  /*La duree du blocage du thread actuel*/
	  private static final int DELAY = 60; 
	  
	  /**Associe le thread avec {@link Etat} et {@link Affichage}
	   * @param : Parcours_p, Affichage_a
	   * */
	  public Accelerer(Etat e,SynchroAff a) {
		  this.etat = e;
		  this.affichage=a;
	  }
	  
	  /**
	   * Définit la fonction run :
	   * Une boucle qui fait accélérer la moto. <BR/>
	   * Si le joueur est perdu, alors un message s’affiche avec le score de l'utilisateur.
	   */
	  @Override
	  public void run() { 
	    while(!etat.estPerdu()) {
	    	if(!etat.getPause()) {
		    	etat.moto.accelerer(etat);
		    	affichage.redraw();
	    	}

	    	try {
	    		Thread.sleep(DELAY);
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	    }
	    //le score de l'utilisateur est calculé en fonction du kilométrage
	    //JOptionPane.showMessageDialog(null, "PERDU !! Votre score final est: "+etat.parcours.getPosition()/100,"",JOptionPane.PLAIN_MESSAGE);
	   
	  }
}
