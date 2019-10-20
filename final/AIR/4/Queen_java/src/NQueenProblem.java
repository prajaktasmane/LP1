//https://www.geeksforgeeks.org/n-queen-problem-backtracking-3/
public class NQueenProblem {

	public static int N =4;
	static boolean isSafe(int [][]board,int row,int col)
	{
		
		//check row on left side
		for(int i=0;i<col;i++)
			if(board[row][i]==1)
				return false;
		//check upper diag
		for(int i=row,j=col;i>=0 && j>=0;i--,j--)
			if(board[i][j]==1)
				return false;
		//check lower diag
		for(int i=row,j=col;i<N && j>=0;i++,j--)
			if(board[i][j]==1)
				return false;
		return true;
		
	}
	
	
	static boolean NQUtil(int[][] board,int col)
	{
		if(col>=N)
			return true;
		//check all rows in this col
		for(int i=0;i<N;i++)
		{
			if(isSafe(board,i,col))
			{
				board[i][col]=1;
				if(NQUtil(board,col+1))
					return true;
				board[i][col]=0;
			}
		}
		return false;
	}
	
	static boolean solveNQ()
	{
		
		int[][] board = new int[N][N];
		if(NQUtil(board,0)==false)
		{
			System.out.println("Solution does not exist");
			return false;
		}
		else
			printSol(board);
		return true;
	}
	
	static void printSol(int[][] board)
	{
		for(int i=0;i<N;i++)
		{
			for(int j=0;j<N;j++)
			{
				System.out.print(board[i][j]+"  ");
			}
			System.out.println();
		}
		
	}
	
	public static void main(String[] args) {
		solveNQ();

	}

}
