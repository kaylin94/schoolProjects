/**
 *  @author   J. Hollingsworth, Kaylin Hunter, and Alex Simoneaux
 *  
 *  Divide-and-Conquer approach to placing tromino
 *  tiles on a 2^k x 2^k board.
 */
import java.awt.Color;

public class Tiling {

	private static Color randColor() {
		int r = (int)(Math.random() * 256);
		int g = (int)(Math.random() * 256);
		int b = (int)(Math.random() * 256);

		return new Color(r, g, b);
	}

	public static void tile(DeficientBoard db, int row, int col, int n) {
		/**base case:
		 * if k = 1 (ie - you have a 2x2 board)
		 * 		if the deficient tile is in top left
		 * 			place a LR tile
		 * 
		 * 		if the deficient tile is in top right
		 * 			place a LL tile
		 * 
		 * 		if the deficient tile is in lower left
		 * 			place a UR tile
		 * 
		 * 		if the deficient tile is in lower right
		 * 			place a UL tile
		 * 
		 * find deficient column
		 * find deficient row
		 * determine which quadrant it is in
		 * 
		 * if its in UL, place a LR tile
		 * if its in UR, place a LL tile
		 * if its in LL, place a UR tile
		 * it its in LR, place a UL tile
		 * 
		 * Split the board into 4 quadrants and recurse on each
		 * 
		 **/

		//finding the deficient row and column
		int dR = db.getDeficientRow(row, col, n);
		int dC = db.getDeficientCol(row, col, n);

		//base case of n = 2 (k = 1)
		if(n == 2){
			//if it is the upper half
			if(dR < row +n/2){
				//and left quadrant
				if(dC < col +n/2){
					//place a lower right tromino
					db.placeTromino(row + (n/2)-1, col + (n/2)-1, db.LR, randColor());
				}
				//and right quadrant
				if(dC >= col +n/2){
					//place a lower left tromino
					db.placeTromino(row +(n/2)-1, col + (n/2)-1, db.LL, randColor());
				}
			}
			//else if it is in the lower half
			else{
				//and left quadrant
				if(dC < col +n/2){
					//place an upper right tromino
					db.placeTromino(row +(n/2)-1, col + (n/2)-1, db.UR, randColor());
				}
				//and right quadrant
				if(dC >= col +n/2){
					//place an upper left tromino
					db.placeTromino(row +(n/2)-1, col + (n/2)-1, db.UL, randColor());
				}
			}
		}

		//in any other case but the base case, do the same thing
		else{
			if(dR < row + n/2){
				if(dC < col +n/2){
					db.placeTromino(row +(n/2)-1,col +  (n/2)-1, db.LR, randColor());
				}
				if(dC >= col + n/2){
					db.placeTromino(row +(n/2)-1,col +  (n/2)-1, db.LL, randColor());
				}
			}
			else{
				if(dC < col + n/2){
					db.placeTromino(row +(n/2)-1, col + (n/2)-1, db.UR, randColor());
				}
				if(dC >=  col +n/2){
					db.placeTromino(row +(n/2)-1, col + (n/2)-1, db.UL, randColor());
				}
			}

			//however recurse on:
			
			//top left quadrant
			tile(db,row ,col ,n/2);
			//top right quadrant
			tile(db,row, col + n/2,n/2);
			//lower left quadrant
			tile(db,row + n/2,col,n/2);
			//lower right quadrant
			tile(db, row + n/2, col + n/2,n/2);
			
			//until it reaches the base case
		}
	}

	public static void main(String[] args) {

		DeficientBoard db = new DeficientBoard(4);
		System.out.println(db);

		tile(db, 0, 0, db.getSize());

		BoardViewer bv = new BoardViewer(db);
		bv.setVisible(true);

	}

}
