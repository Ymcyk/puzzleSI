/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import sac.State;
import sac.StateFunction;
import sac.examples.slidingpuzzle.SlidingPuzzle;
/**
 *
 * @author Mateusz
 */
public class HFunctionManhattan extends StateFunction {

    @Override
    public double calculate(State state) {
        double h = 0.0;
        Puzzle s = ( Puzzle ) state;
        
        for (int i = 0; i < s.board.length; i++) {
            for (int j=0; j < s.board.length; j++){
                if ((s.board[i][j] != 0))
                     h += manhattan (s , i, j);
            }
        }
        
        return h;
    }
    protected int manhattan ( Puzzle s , int index, int index2) {
        int n = s.n;
        int i1 = s.board[index][index2] / n;
        int j1 = s.board[index][index2] % n;
        //int i2 = index / n;
        int i2 = ((n * index) + index2) / n;
        int j2 = ((n * index) + index2) % n;
       
        return Math.abs(i1 - i2) + Math.abs(j1 - j2);
    }
}

     
             
