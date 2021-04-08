package control;

import model.*;

/**
 * Cette classe fait diminuer le temps restant chaque seconde, et puis la fenêtre va être redessinée.
 * 
 * @author: Jing ZHANG & Liuyi CHEN
 * */
public class DiminuerTime extends Thread{
	private Etat etat;
	private SynchroAff affichage;
	private static final int DELAY=1000;
	
	/**
	 * Associe le thread avec {@link Etat} et {@link SynchroAff}
	 * @param e l'etat
	 * @param aff le SynchroAff responsable de l'affichage
	 */
	public DiminuerTime(Etat e,SynchroAff aff) {
		this.etat = e;
		this.affichage = aff;
	}
	
	/**
	 * Une boucle qui fait diminuer le temps de 1 seconde, tant que le jeu n'est pas en pause et que le joueur ne perd pas le jeu
	 */
	@Override
	public void run() {
		while (!etat.estPerdu()) {
			if(!etat.getPause()) {
				etat.moto.perdCTemps();
				affichage.redraw();
			}
			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
