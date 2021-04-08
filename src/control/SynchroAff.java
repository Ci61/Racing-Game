package control;

import model.Etat;
import view.Affichage;

/**
 * Définit un thread qui redessine la {@link JPanel} régulierement, afin de ne pas avoir une surcharge de au niveau de l'affichage
 * @author Jing ZHANG & Liuyi CHEN
 */
public class SynchroAff extends Thread {
	private Boolean draw = true;
	private Affichage affichage;
	private Etat etat;
	
	/**
	 * Associe le thread avec {@link Etat} et {@link Affichage}
	 * @param aff l'affichage
	 * @param et l'etat
	 */
	public SynchroAff(Affichage aff, Etat et) {
		affichage = aff;
		etat = et;
	}
	
	/**
	 * Redessiner la fenêtre panel
	 */
	public void redraw() {
		draw = true;
	}
	
	/**
	 * Mise à jour la fenêtre régulierement (tous les 1/24 seconde)
	 */
	@Override
	public void run() {
		while (!etat.estPerdu()) {
			if (draw) {
				affichage.repaint();
				draw = false;
			}
			try {
				Thread.sleep(1000/24);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		affichage.repaint();
		affichage.revalidate();
	}

}
