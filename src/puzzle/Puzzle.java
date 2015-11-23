/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import sac.State;
import sac.examples.slidingpuzzle.HFunctionMisplacedTiles;
import sac.graph.GraphState;
import sac.graph.GraphStateImpl;

/**
 *
 * @author Piotr
 */
class InvalidDirection extends Exception{
    // Gdy zostanie podany niepoprawny kierunek, to jest podnoszony ten wyjątek
    public InvalidDirection(String msg){
        super(msg);
    }
}

public class Puzzle extends GraphStateImpl {
    public static int n = 4;
    //public static final int N = n*n;
    public byte [][] board = null;
    
    private Random rand = new Random(System.currentTimeMillis());
    
    // pozycja puzzla przesuwnego
    private int x = 0;  // wiersze tablicy
    private int y = 0;  // kolumny tablicy
    
    //static { 
      //  setHFunction(new HFunctionMisplacedTiles());
        //        };
    
    /*
    static {
        setHFunction(new HFunctionMisplacedTiles() {
            @Override
            public double calculate(State state) {
                double h = 0.0;
                byte value = 0;
                Puzzle s = (Puzzle)state;
                for( int i = 0; i < n; i++){
                    for( int j = 0; j < n; j++){
                        if( (s.board[i][j] != 0) && (s.board[i][j] != value))
                            h += 1.0;
                        
                        value++;
                    }
                }
                return h;
            }
        });
    }*/
    
    // Do zrobienia jeszcze heurestyka Manhattan
    // Sposób jej liczenia jest w dokumentacji sac (na stronie z labkami)
    // Na zajęciach będziemy musieli obie porównać, więc jedna będzie zakomentowana
 /*   
    static {
        setHFunction(new HFunctionManhattan() {
            @Override
            public double calculate(State state) {

                return ;
            }
        });
    }
*/
    
    public Puzzle() {
        this.board = new byte[n][n];
        byte value = 0;
        
        for( int i = 0; i < n; i++)
            for( int j = 0; j < n; j++)
                this.board[i][j] = value++;
                //this.board[i][j] = (byte)((n*i)+j); 
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
            //System.out.println("Zle: " + tmpX + " " + tmpY);
            return false;
        }
            
        //System.out.println(x + " " + y + " do " + tmpX + " " + tmpY);
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
    public List<GraphState> generateChildren(){
        List<GraphState> children= new ArrayList<GraphState>();
        
        for( int i = 0; i < 4; i++){
            Puzzle child = new Puzzle(this);
            try{
                
                if(child.makeMove(i))
                    children.add(child);
                
            } catch (InvalidDirection e) {
                System.out.println(e.getStackTrace());
            }
        }
        
        return children;
    }

    @Override
    public boolean isSolution() {
        byte value = 0;
        
        for( int i = 0; i < n; i++)
            for( int j = 0; j < n; j++){
                if(this.board[i][j] != value++)
                return false;
            }

        return true;
    }

    @Override
    public int hashCode() {
        byte[] copy = new byte[n*n];
        int k = 0;
        for( int i = 0; i < n; i++){
            for( int j = 0; j < n; j++){
                copy[k++] = board[i][j];
            }
        }   
        
        return Arrays.hashCode(copy);
    }
}

