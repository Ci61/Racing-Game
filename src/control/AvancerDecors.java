package control;

import model.Etat;

/**
 * Cette classe gère l'acheminement des nuages
 * 
 * @author: Jing ZHANG & Liuyi CHEN
 * */
public class AvancerDecors extends Thread{
	  private Etat etat ;
	  private SynchroAff affichage;
	  /*La duree du blocage du thread actuel*/
	  private static final int DELAY = 100; 
	  
	  /**Associe le thread avec {@link Etat} et {@link SynchroAff}
	   * */
	  public AvancerDecors(Etat e,SynchroAff a) {
		  this.etat = e;
		  this.affichage=a;
	  }
	  
	  @Override
	  public void run() {
		/**Une boucle infinie met a jour la valeur "pos" dans le parcours afin d'avancer le ligne */
	    while(!etat.estPerdu()) {
	    	if(!etat.getPause()) {
		    	etat.dcr.setAvance();
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