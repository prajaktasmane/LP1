//https://www.geeksforgeeks.org/n-queen-problem-using-branch-and-bound/
public class NQueenB_B {
	
	public static int N =8;
	
	static boolean isSafe(int row,int col,int[][] slashCode,int[][] backslashcode,boolean[] rowlookup,boolean[] sclp,boolean[] backsclp)
	{
		
		if(sclp[slashCode[row][col]]|| backsclp[backslashcode[row][col]] || rowlookup[row])
			return false;
		return true;
		
	}
	
	static boolean solveNQueenUtil(int[][] board,int col,int[][] slash_code,int[][] back_slash_code,boolean[] rowlookup,boolean[] slash_lp,boolean[] backslash_lp)
	{
		if(col>=N)
			return true;
		for(int i=0;i<N;i++)
		{
			if(isSafe(i,col,slash_code,back_slash_code,rowlookup,slash_lp,backslash_lp))
			{
			board[i][col]=1;
			rowlookup[i]=true;
			slash_lp[slash_code[i][col]]=true;
			backslash_lp[back_slash_code[i][col]]=true;
			
			if(solveNQueenUtil(board,col+1,slash_code,back_slash_code,rowlookup,slash_lp,backslash_lp))
				return true;
			
			board[i][col]=0;
			rowlookup[i]=false;
			slash_lp[slash_code[i][col]]=false;
			backslash_lp[back_slash_code[i][col]]=false;
			}	
		}
		
		return false;
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
	static boolean solveNQueens()
	{
		int[][]  board = new int[N][N];
		int[][]  slash_code = new int[N][N];
		int[][]  back_slash_code = new int[N][N];
		
		boolean[] rowlookup = new boolean[N];
		boolean[] slash_code_lp = new boolean[2*N-1];
		boolean[] backslash_code_lp = new boolean[2*N-1];
		
		for(int r=0;r<N;r++)
		{
			for(int c=0;c<N;c++)
			{
				slash_code[r][c]=r+c;
				back_slash_code[r][c]= r-c+(N-1);
				
			}
		}
		
		if(solveNQueenUtil(board,0,slash_code,back_slash_code,rowlookup,slash_code_lp,backslash_code_lp)==false)
		{
			System.out.println("Solution does not exist");
			return false;
		}
		    printSol(board);
			return true;
		
	}
	
	public static void main(String[] args)
	{
		solveNQueens();
	}

}