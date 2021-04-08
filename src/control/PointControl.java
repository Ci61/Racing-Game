package control;

import model.Etat;
import view.Affichage;

/*
 * Un thread qui ajoute un point de control à un intervalle arbitraire
 * */
public class PointControl extends Thread{
	  private Etat etat;
	  private SynchroAff affichage;
	  
	  /*La duree du blocage du thread actuel*/
	  private static int distanceCheckpoint = 5000;
	  private static int tempsAjout=1000;
	  
	  /**Associe le thread avec {@link Etat} et {@link Affichage}
	   * @param p un objet Parcours
	   * @param a un objet affichageAffichage_a
	   * */
	  public PointControl(Etat e,SynchroAff a) {
		  this.etat = e;
		  this.affichage=a;
	  }
	  
	  /**
	   * Si la moto accélère, l'intervalle entre chaque point de contrôle est plus court que l'intervalle sans accélération
	   * */
	  public void incrDistance() {
		  if(!etat.moto.getAccelerY())
			  tempsAjout=tempsAjout*2;
		  distanceCheckpoint += tempsAjout; 
	  }
	  
	  /**
	   * Définit la fonction run :
	   * Une boucle (s'arretant lors que le joueur est perdu) qui fait avancer la piste
	   */
	  @Override
	  public void run() {
	    while(!etat.estPerdu()) {
	    	try {
	            Thread.sleep(distanceCheckpoint);
		    	if(!etat.getPause()) {
			    	etat.parcours.addCheckPoint();
			    	affichage.redraw();
			    	incrDistance();
		    	}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	   
	  }
}
