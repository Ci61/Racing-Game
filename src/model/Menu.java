package model;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import model.Etat.CLAVIER;
import view.Affichage;

/**
 * Cette classe permet le joueur à faire des choix lors que le jeu est en pause
 * 
 * @author: Jing ZHANG & Liuyi CHEN
 * */
public class Menu {
	private int choix_menu = 0;
	private String[] listMenu={"Reprendre", "Recommence","Quitter"};
	
	/**CONSTRUCTEUR
	 * On va créer un menu avec des choix : reprendre, recommence et quitter
	 * */
	public Menu() {
	}
	
	/**
	 * C'est pour que l'utilisateur puisse parcourir le menu.
	 * En d'autres termes, le premier élément et le dernier élément du menu sont également adjacents.
	 * @param dir
	 */
	public void parcourir(CLAVIER dir) {
		if (dir == CLAVIER.UP) {
			//Lorsque l'on descend, des que i on est tout en haut, on redescend tout en bas
			if (choix_menu > 0) {
				choix_menu--;
			} else {
				choix_menu = listMenu.length - 1;
			}
		} else if (dir == CLAVIER.DOWN) {
			if (choix_menu < listMenu.length - 1) {
				choix_menu++;
				
			} else {
				//Si on est a la fin de la liste, on revient tout en haut
				choix_menu = 0;
			}
		}
	}
	
	/**
	 * Permet de sélectionner un element du menu.</BR>
	 * Si le joueur choisit "Reprendre", le jeu continuera là où il a été mis en pause. Alors que s'il choisit "Quitter", le jeu se terminera et la fenêtre sera fermée.
	 * @param etat 
	 */
	public void choose(Etat etat) {
		String selection = listMenu[choix_menu];
		if (selection == "Reprendre") {
			etat.paused();
		}else if(selection == "Recommence") {
			etat.reinit();
		}
		else if (selection == "Quitter") {
			etat.quit();
		}
	}
	/**
	 * Dessine le menu
	 * @param g Graphics2D
	 */
	public void draw(Graphics2D g) {
		g.setFont(new Font("Arial", Font.PLAIN, 40));
		int resize = 55;
		int x = Affichage.LARG/2-resize*2;
		int y = Affichage.HAUT/3+(Affichage.LARG+Affichage.HAUT)/20;

		//le fond
		g.setColor(Color.WHITE);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		int[] m = {0, Affichage.LARG,Affichage.LARG, 0};
		int[] n = {0,0,Affichage.HAUT, Affichage.HAUT};
		g.fillPolygon(m, n, 4);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		//On dessine l'effet de "selectionner"
		for (int i = 0; i < listMenu.length; i++) {
			if (i == choix_menu) {
				g.setColor(new Color(55,142,80));
				g.fillRoundRect(x-5, y - resize + (i * Affichage.HAUT / 10) + resize/5, resize*listMenu[i].length()/2, resize, 7, 7);
				g.setColor(Color.WHITE);
				g.drawString(listMenu[i], x, y + (i * Affichage.HAUT / 10));
			}else {
				g.setColor(new Color(40,142,70));
				g.drawString(listMenu[i], x, y + (i * Affichage.HAUT / 10));
			}
		}

	}
}
