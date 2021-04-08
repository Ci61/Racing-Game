package appli;

import javax.swing.JFrame;
import control.*;
import model.*;
import view.*;

/**
 * En respectant le motif MVC, on a besoin de cette classe "Main" pour lancer le programme.
 * Cette classe consiste en des objets et le lancement des threads pour réaliser différentes fonctionnalités du jeu
 * 
 * @author Jing ZHANG & Liuyi CHEN
 * */
public class Main {
	public static void main(String[] args) {
		
		/*************************************************
		 * Création d'une fenêtre centrée à l'écran
		 * ***********************************************/
		JFrame fenetre = new JFrame("JOUER!!");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		/************************************************
		 * Création des différentes classes du modèle MVC
		 * ***********************************************/
		
		/*Model*/
		Etat etat=new Etat();
		/*View*/
		Affichage affichage=new Affichage(etat);
		/*Control*/
		ControlKey ctrlKey=new ControlKey(affichage,etat,fenetre);	
		/*Ajout d'un KeyListener pour que notre interface puisse réagir aux evenements clavier*/
		affichage.addKeyListener(ctrlKey);
		/**un thread d’affichage qui s’exécute tous les 1/24 seconde*/
		SynchroAff affi = new SynchroAff(affichage, etat);
		etat.setAff(affi);

		/**Ajout le JPanel dans la fenêtre et des Settings*/
		//affichage.btnPause.addActionListener(new ControlMouse(affi,etat,affichage.btnPause));
		fenetre.add(affichage);
		fenetre.pack();
		fenetre.setVisible(true);
		fenetre.setLocationRelativeTo(null);
		
		/*************************************************
		 * Lancement des threads
		 * ***********************************************/
		affi.start();
		(new Avancer(etat,affi)).start();
		(new Accelerer(etat,affi)).start();
		(new Thread(new AvancerDecors(etat,affi))).start();
		(new DiminuerTime(etat,affi)).start();
		(new Thread(new Bird(etat))).start();
		(new PointControl(etat,affi)).start();
	}
}
