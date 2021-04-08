package model;

/**
 * Cette classe sert à enregistrer un point de controle et detecter si la moto a déjà dépassé ce point. 
 * @author: Jing ZHANG & Liuyi CHEN
 * */
public class CheckPoint{
	public int y =0;
	private boolean check = false;
	
	/**CONSTRUCTEUR
	 * enregistre l'ordonnee d'un point de controle
	 * */
	public CheckPoint(int yy) {
		this.y = yy;
	}
	
	public int getY(int pos) {
		return pos-y;
	}
	
	/**
	 * Lorsque la moto rencontre un obstacle, heurter est égale à true
	 * */
	public void setCheck() {
		check = true;
	}
	
	/**
	 * Renvoie si la moto a rencontré l'obstacle
	 * */
	public boolean estCheck() {
		return check;
	}
}
