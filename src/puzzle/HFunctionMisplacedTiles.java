/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import static puzzle.Puzzle.n;
import sac.State;
import sac.StateFunction;
import sac.examples.slidingpuzzle.SlidingPuzzle;
/**
 *
 * @author Mateusz
 */
public class HFunctionMisplacedTiles extends StateFunction {

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
    
}
