/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import sac.graph.AStar;
import sac.graph.GraphSearchAlgorithm;
import sac.graph.GraphSearchConfigurator;
import sac.graph.GraphState;

/**
 *
 * @author Piotr
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InvalidDirection {
       
        float czas = 0;
        double droga = 0;
        int open = 0;
        int closed = 0;
       
        
        Puzzle.n = 3;
        Puzzle p = new Puzzle();

        try {
            for (int i=0;i<100;i++){
                p=new Puzzle();
            
                p.blendPuzzle(1000);
                
                
                Puzzle.setHFunction(new HFunctionManhattan());
                //Puzzle.setHFunction(new HFunctionMisplacedTiles());
                GraphSearchConfigurator conf = new GraphSearchConfigurator();
                conf.setWantedNumberOfSolutions(1);
                GraphSearchAlgorithm a = new AStar(p, conf);
                a.execute();
                GraphState sol = a.getBestSoFar();

                czas += a.getDurationTime();
                closed += a.getClosedSet().size();
                open += a.getOpenSet().size();
                droga += sol.getPath().size();
                
                //System.out.println("Czas: " + czas + "petla: "+ i);
            }
            czas=czas/100;
            closed=closed/100;
            open=open/100;
            droga=droga/100;
            
            System.out.println("Czas: " + czas);
            System.out.println("Closed: "+ closed);
            System.out.println("Open: "+ open);
            System.out.println("Droga: "+ droga);
            
            
        } catch (InvalidDirection ex) {
            System.out.println(ex);
        }
        
    }
    
}
