/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.util.List;
import java.util.Random;
import sac.graph.GraphState;
import sac.graph.GraphStateImpl;

/**
 *
 * @author Piotr
 */
class InvalidDirection extends Exception{
    public InvalidDirection(String msg){
        super(msg);
    }
}

public class Puzzle extends GraphStateImpl {
    public static final int n = 3;
    //public static final int N = n*n;
    public byte [][] board = null;
    
    private Random rand = new Random(System.currentTimeMillis());
    
    // pozycja puzzla przesuwnego
    private int x = 0;  // wiersze tablicy
    private int y = 0;  // kolumny tablicy
    
    public Puzzle() {
        this.board = new byte[n][n];
        
        for( int i = 0; i < n; i++)
            for( int j = 0; j < n; j++)
                this.board[i][j] = (byte)((n*i)+j); 
    }
    
    public Puzzle( Puzzle copy){
        this.board = new byte[n][n];
        
        for( int i = 0; i < n; i++)
            for( int j = 0; j < n; j++)
                this.board[i][j] = copy.board[i][j];
        
        this.x = copy.x;
        this.y = copy.y;
    }
    
    public void blendPuzzle( int movesNumber) throws InvalidDirection{
        while(movesNumber != 0){
            if (!makeMove(rand.nextInt(4)))
                continue;
            --movesNumber;
        }
    }

    public boolean makeMove( int direction) throws InvalidDirection{
        byte temp;
        int tmpX = x, tmpY = y;
        
        switch (direction) {
            case 0:
                --tmpX;
                break;
            case 1:
                ++tmpY;
                break;
            case 2:
                ++tmpX;
                break;
            case 3:
                --tmpY;
                break;
            default:
                throw new InvalidDirection("Kierunki w przedziale 0-3");
        }
            
        if( tmpX < 0 || tmpX >= n || tmpY < 0 || tmpY >= n){
            System.out.println("Zle: " + tmpX + " " + tmpY);
            tmpX = x;
            tmpY = y;
            return false;
        }
            
        System.out.println(x + " " + y + " do " + tmpX + " " + tmpY);
        temp = board[tmpX][tmpY];
        board[tmpX][tmpY] = 0;
        board[x][y] = temp;
        x = tmpX;
        y = tmpY;
        return true;
    }
    
    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                tmp.append(board[i][j]);
                tmp.append(",");
            }
            tmp.append('\n');
        }
        return tmp.toString();
    }

    @Override
    public List<GraphState> generateChildren() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isSolution() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

