import java.util.Scanner;
import java.util.Random;

public class ChapmanPuzzle {
	public static final int MAX = 4;
	public static void main (String [] args){
		try{
			boolean endGame = false;
			Scanner scanner = new Scanner(System.in);
			String[][]board = createBoard();
			if(!isSolvable(board)){
				shuffle(board);
			}
			else if (isSolvable(board)){
				System.out.println("Your board is:\n");
				printBoard(board);
				do{
					System.out.print("You may move the blank '0' up, down, left or right\n"
							+ "What move do you wish to take ?\n"
							+ "(up 'i', down 'm', left 'k' or right 'j')\n"
							+ "To quit the game press 'q'.\n ");
					
					if(!scanner.hasNext("i") && !scanner.hasNext("m") && !scanner.hasNext("k") &&
						!scanner.hasNext("j") && !scanner.hasNext("q")){
						System.out.print(" Invalid input. Please press i,m,k,j or q");
						break;
					}
					else if(scanner.hasNext("q")){
						endGame = true;
					}
					else{
						char move = scanner.next().charAt(0);
						makeMove(move,board);
						if(isSolved(board)){
							System.out.print("Congratulations! You solved the puzzle!\n");
							endGame = true;
						}
					}
				}
				while(!endGame);
				if(endGame == true){
					System.out.print("Thank you for playing! See you soon.\n");
				}
				scanner.close();
			}
		}
		catch (NullPointerException exception){
		}
		catch (java.util.NoSuchElementException exception){
			System.out.print("No input entered.");
		}		
	}
	
	/** Create board - creates a 2D array with numbers in a random order
	 * @return: 2D array with numbers randomly arranged
	 */
	public static String[][] createBoard(){
		int number = 0;
		String temp;
		int [][] array = new int[MAX][MAX];					// create a 2D array
		String[][] board = new String [MAX][MAX];
		if( number < MAX*MAX){								// while the number is less than the max number of tiles
			for(int row = 0; row < MAX ; row++){
				for(int column = 0; column < MAX; column++){
					array[row][column] = number;			// set the position in array to a number
					number++;								// number = number + 1
				}
			}
			for(int i =0; i < MAX; i++){
				for( int j =0; j < MAX; j++){
					temp = String.valueOf(array[i][j]);		// convert the value at that position into string
					board[i][j] = temp;						// place it in the same position on the board 
					if(array[i][j] == 0){					// if the value is 0
						board[i][j] = "BB";					// replace it with string 'BB'
					}
				}
			}
			shuffle(board);									// shuffle the board to randomize it.
			return board;
		}
		return null;
	}
	
	/** Shuffle - rearrange numbers in the 2D array so they are at random 
	 * @param board- 2D array 
	 */
	public static void shuffle(String[][] board){
		int number = 0;
		if(number < MAX*MAX){								// while the number is less than the max number of tiles
			String temp;
			String value;
			int row;
			int column;
			Random random = new Random();
			for(int i = 0; i < MAX ; i++){
				for(int j = 0; j < MAX; j++){
					row = random.nextInt(3);				// create random row index
					column = random.nextInt(3);				// create random column index
					temp = board[row][column];				// get the value at the position of randomly generated coordinates
					value = board[i][j];					// get the value at the current position
					board[i][j] = temp;						// at the current position, place the random value
					board[row][column] = value;				// at the random coordinates, place the value from current position
					number++;								// number = number + 1
				}
			}
		}
		if(!isSolvable(board)){								// shuffle the board until it is solvable
			shuffle(board);
		}
	}
	/** Print board - create a visual representation of 2D array by displaying it on the console
	 * 
	 * @param board - 2D array
	 */
	public static void printBoard(String[][]board){
		 for (int row = 0; row < MAX; ++row) {			
	           System.out.print("| ");						// Print '|' at the start of each row
	           for (int col = 0; col < MAX; ++col)
	               System.out.print(board[row][col] + "| ");// Print a value followed by '|'
	           System.out.println();
	     }
	     for (int col = 0; col < MAX; ++col){
	          System.out.print("---");						// Print bottom line
	     }
	     System.out.println();
	}
	
	/** is Solved - checks whether the current state of the board is the same as the end result
	 * @param board - 2D array
	 * @return boolean
	 */
	public static boolean isSolved(String[][]board){
		int count = 0;
		String solvedBoard[][] = {{"1","2","3","4"},{"5","6","7","8"},{"9","10","11","12"},{"13","14","15","BB"}};
		for(int i = 0; i< MAX; i++){
			for(int j = 0; j < MAX ; j++){
				if(board[i][j] == solvedBoard[i][j]){		// check whether the current value on the board is the same as the one on solved board 
					count++;								// count the number of elements that are the same
				}
			}
		}
		if(count == MAX*MAX){								// check whether the count is the same as the number of elements
			return true;
		}
		return false;
	}
	
	/** Make Move - moves the blank BB in the direction entered by user
	 * @param move - direction entered by user
	 * @param board - 2D array
	 */
	public static void makeMove(char move, String[][]board){
		String temp;
		for(int i = 0; i<MAX; i++){
			for(int j = 0; j<MAX;j++){
				if(board[i][j] == "BB"){					// check the 2D array until we land on blank tile
					if(move == 'i'){						// if the user chooses to go up
						if(i >= 0){
							temp = board[i-1][j];			// saved the value thats on top of the blank BB
							board[i-1][j] = "BB";			// set the position one higher to BB
							board[i][j] = temp;				// set the current position to the value saved
							break;
						}
						else{
							System.out.println("Error! The blank tile cannot go upwards. Please pick"
									+ " a different move!");
						}
					}
					else if (move == 'j'){					// if the user chooses to go right
						if(j < MAX-1){
							temp = board[i][j+1];			// save the value thats one space to the right of BB
							board[i][j+1] = "BB";			// set the position to the right to be BB
							board[i][j] = temp;				// set the current position to be the saved value
							break;
						}
						else{
							System.out.println("Error! The blank tile cannot go right. Please pick"
									+ " a different move!");
						}
					}
					else if(move == 'k'){					// if the user chooses to go left
						if(j >= 0){
							temp = board[i][j-1];			// save the value to the left of BB
							board[i][j-1] = "BB"; 			// set the position to the left to be BB
							board[i][j] = temp;				// set the current position to be the value saved
							break;
						}
						else{
							System.out.println("Error! The blank tile cannot go left. Please pick"
									+ " a different move!");
						}
					}
					else if(move == 'm'){					// if the user chooses to go down
						if(i < MAX-1){
							temp = board[i+1][j];			// save the value below the blank BB
							board[i+1][j] = "BB";			// set the position below to be BB
							board[i][j] = temp;				// set the current position to be the value saved
							break;
						}
						else{
							System.out.println("Error! The blank tile cannot go down. Please pick"
									+ " a different move!");
						}
					}
					else{
						System.out.println("Invalid move!");
					}
				}
			}
		}
		printBoard(board);
	}
	
	/** is Solvable - check whether the combination of the board is solvable
	 * @param board - 2D array
	 * @return boolean
	 */
	public static boolean isSolvable(String[][] board){
		int[][] arrayBoard = convertToInt(board);				// convert the board into an integer 2D array
		int[] array = convertTo1D(arrayBoard);					// convert 2D array to 1D array
		if(even_perm(array)){									// if the permutation is even, the board is solvable
			return true;
		}
		return false;
	}
	
	/** convert to Int - converts array from string to integer
	 * @param array - 2D String array
	 * @return board - 2D Integer array
	 */
	public static int[][] convertToInt(String[][]array){
		int temp;
		int board[][] = new int[MAX][MAX];
		for(int i = 0; i < MAX; i++){
			for(int j = 0; j < MAX; j++){
				if(array[i][j]== "BB"){							// if the value at current position is BB, replace it with 0
					board[i][j] = 0;
				}
				else{
					temp = Integer.valueOf(array[i][j]);		// get the int value from the string
					board[i][j] = temp;							// place it in the new array at the same position
				}
			}
		}
		return board;
	}
	
	/** convert to 1D - converts a 2D array into a 1D array
	 * @param array - 2D array
	 * @return board - 1D array
	 */
	public static int[] convertTo1D(int[][]array){
	     int board[] = new int[array.length*array[0].length];	// create 1D array with length set to width*height of the 2D array
	     for(int i = 0; i < array.length; i++) {
	         int[] row = array[i];
	         for(int j = 0; j < row.length; j++) {
	             int number = array[i][j];						// get the number from the current position
	             board[i*row.length+j] = number;				// place it in the next spot of the 1D array
	         }
	     }
	     return board;
	}
	
	/** inversions - finds the number of inversions in a board
	 * @param p - 1D array 
	 * @param low
	 * @param high
	 * @return result - the number of inversion
	 */
	public static int inversions(int[] p,int low, int high){ 
		 int result = 0;
		 for (int i = low; i < high; i = i+1)					
		 for (int j = i+1; j < high; j = j+1)					 
		 if ( p[i] > p[j] )									
		 result = result+1;
		 return result;
	}
	
	/** Even Perm - checks whether permutation is even or odd
	 * @param p - 1D array
	 * @return boolean
	 */
	public static boolean even_perm(int[] p){
		 int n = inversions(p, 0, p.length);
		 return n%2 == 0;
	}
}
