package control;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import model.*;
import view.*;

/**
 * Cette classe implémente l'interface KeyListener
 * Lorsque des évènements parviennent via l'Affichage, elle prévient l'objet Etat en conséquence.
 * 
 * @author: Jing ZHANG & Liuyi CHEN
 * */
public class ControlKey implements KeyListener{
	private Etat etat;
	private Affichage affichage;
	private JFrame fenetre;
	
	/**Associe le thread avec {@link Etat} et {@link Affichage}
	 * @param : Affichage_aff, Moto_mt
	 * */
	public ControlKey(Affichage aff, Etat e,JFrame f) {
		this.etat = e;
		this.affichage=aff;
		this.fenetre=f;
	}

	@Override
	public void keyTyped(KeyEvent e) {		
	}

	/**Cette méthode répond l'appui du clavier de l'utilisateur. Si le joueur n'a pas encore perdu son jeu, le déplacement de la moto se produira.<BR/>
	 * KeyEvent.VK_RIGHT -> la moto se déplace vers la droite et les décors vers la gauche pour créer une sensation de virage <BR/>
	 * KeyEvent.VK_LEFT -> contrairement à ce qui précède
	 * 
	 * @param KeyEvent
	 * */
	@Override
	public void keyPressed(KeyEvent e) {
		if(!etat.estPerdu()) {
			if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
				if(etat.getPause()) {
					//on parcour le menu en passant par l'element suivant
					etat.select(Etat.CLAVIER.DOWN);
				}else {
					//lorsque la moto se déplace vers la droite, son image changera
					etat.MotoRight();
					etat.moto.setImage("src/image/motoR.png");
				}
			}else if(e.getKeyCode()==KeyEvent.VK_LEFT) {
				if(etat.getPause()) {
					//on parcour le menu en passant par l'element precedent
					etat.select(Etat.CLAVIER.UP);
				}else {
					//lorsque la moto se déplace vers la gauche, son image changera
					etat.MotoLeft();
					etat.moto.setImage("src/image/motoL.png");
				}
			}else if(e.getKeyCode()==KeyEvent.VK_UP) {
				if(etat.getPause())
					//on parcour le menu en passant par l'element precedent
					etat.select(Etat.CLAVIER.UP);
				else
					//si le jeu n'est pas en pause, le bouton "UP" sert à accélerer la vitesse de la moto.
					etat.accelerMoto();
			}else if(e.getKeyCode()==KeyEvent.VK_DOWN) {
				if(etat.getPause())
					//on parcour le menu en passant par l'element suivant
					etat.select(Etat.CLAVIER.DOWN);
				//Si c'est Escape, on appelle
			} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				etat.paused();
				// Si c'est Entrer, on choisit dans le menu (pause si rien n'est affiché)
			} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				etat.choose();
			}
			affichage.repaint();
		}else {
			affichage.setFocusable(false);
		}
		
		//si le joueur veut quitter le jeu, la fenetre(JFrame) sera fermé
		if (etat.estQuit()) {
			fenetre.dispose();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.etat.moto.setImage("src/image/moto.png");
		//lorsque l'on lache le bouton "haut", l'acceleration sera terminée
		if(e.getKeyCode()==KeyEvent.VK_UP) {
			if(!etat.getPause())
				etat.noAccelerMoto();
		}
		
	}
	
	
	
}
