package sudoku;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

import sac.State;
import sac.StateFunction;
import sac.graph.BestFirstSearch;
import sac.graph.GraphSearchAlgorithm;
import sac.graph.GraphSearchConfigurator;
import sac.graph.GraphState;
import sac.graph.GraphStateImpl;

public class Sudoku extends GraphStateImpl {
	public static final int n = 3;
	public static final int N = n*n;
	
	public byte[][] board = null;
	public int unknowns = N*N;
	
	static {
		setHFunction(new StateFunction() {
			@Override
			public double calculate(State state) {
				Sudoku s = (Sudoku)state;
				return s.unknowns;
			}
		});
	}
	
	public Sudoku() {
		this.board = new byte[N][N];
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				board[i][j] = 0;
			}
		}
	}
	
	public Sudoku(Sudoku parent) {
		board = new byte[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				board[i][j] = parent.board[i][j];
			}
		}
		unknowns = parent.unknowns;
	}
	
	public boolean isLegal() {
		byte[] group = new byte[N];
				
		//check rows
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++)
				group[j] = board[i][j];
			
			if (!isGroupLegal(group))
				return false;
		}
		
		//check cols
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++)
				group[j] = board[j][i];
			
			if (!isGroupLegal(group))
				return false;
		}
		
		//check quads
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int z = 0;
				for (int k = 0; k < n; k++) {
					for (int l = 0; l < n; l++) {
						group[z++] = board[i*n+k][j*n+l];
					}
				}
				if (!isGroupLegal(group))
					return false;
			}
		}
		return true;
	}
	
	private boolean isGroupLegal(byte[] group) {
		boolean[] visited = new boolean[N];
		for (int i = 0; i < N; i++) {
			visited[i] = false;
		}
		
		for (int i = 0; i < N; i++) {
			if (group[i] == 0)
				continue;
			
			if (visited[group[i] - 1])
				return false;
			else
				visited[group[i] - 1] = true;
			}
		
		return true;
	}
	
	private void countUnknowns() {
		unknowns = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++){
				if (board[i][j] == 0)
					unknowns++;
			}
		}
	}
	
	public void fromStringN9(String txt) {
		int k = 0;
		
		for(int i = 0; i < N; i++)
			for(int j = 0; j < N; j++) {
				board[i][j] = Byte.valueOf(txt.substring(k, k+1));
				k++;
			}
		
		countUnknowns();
	}

	/*@Override
	public String toString() {
		String tmp = "";
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				tmp += board[i][j] + ",";
			}
			tmp += "\n";
		}
		
		return tmp;
	}*/
	
	@Override
	public String toString() {
		StringBuilder tmp = new StringBuilder();
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				tmp.append(board[i][j]);
				tmp.append(",");
			}
			tmp.append("\n");
		}
		
		return tmp.toString();
	}

	@Override
	public List<GraphState> generateChildren() {
		List<GraphState> children = new ArrayList<GraphState>();
		int i = 0;
		int j = 0;
		for (int k = 0; k < N*N; k++) {
			i = k/N;
			j = k%N;
			if (board[i][j] == 0)
				break;
		}
		
		for (int k = 0; k < N; k++) {
			Sudoku child = new Sudoku(this);
			child.board[i][j] = (byte)(k+1);
			child.unknowns--;
			if (child.isLegal()) 
				children.add(child);
		}
		
		return children;
	}

	@Override
	public int hashCode() {
		//return toString().hashCode();
		byte[] copy=new byte[N*N];
		int k=0;
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				copy[k++]=board[i][j];
			}
		}
		
		return Arrays.hashCode(copy);
	}

	@Override
	public boolean isSolution() {
		return ((unknowns == 0) && (isLegal()));
	}
}
