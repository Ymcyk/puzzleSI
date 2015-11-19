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
    public static void main(String[] args) {
       /* Sudoku s = new Sudoku();
		
		String txt = "003020600900305001001800400008102900700000000000008200002600500800203009005010300";
		//003020600900305001001806400008102900700000008006708200002609500800203009005010300
		s.fromStringN9(txt);
		System.out.println(s.isLegal());
		System.out.println(s.unknowns);
		
		GraphSearchConfigurator conf = new GraphSearchConfigurator();
		conf.setWantedNumberOfSolutions(Integer.MAX_VALUE);
		
		GraphSearchAlgorithm a = new BestFirstSearch(s, conf);
		a.execute();
		List<GraphState> sols = a.getSolutions();
		
		for (GraphState sol:sols) {
			System.out.println(sol);
			System.out.println("---");
		}
		
		System.out.println("Time: " + a.getDurationTime());
		System.out.println("Closed: " + a.getClosedSet().size());
		System.out.println("Open: " + a.getOpenSet().size());
		System.out.println("Solutions: " + a.getSolutions().size());
*/
       Puzzle p = new Puzzle();

        try {
            p.blendPuzzle(30);
            System.out.println("-------Puzzle niepoukładane-------");
            System.out.println(p);
            GraphSearchConfigurator conf = new GraphSearchConfigurator();
            conf.setWantedNumberOfSolutions(1);
		
            GraphSearchAlgorithm a = new AStar(p, conf);
            a.execute();
            GraphState sol = a.getBestSoFar();

            System.out.println("-------Puzzle rozwiązane-------");
            System.out.println(sol);

            
            System.out.println("Time: " + a.getDurationTime());
            System.out.println("Closed: " + a.getClosedSet().size());
            System.out.println("Open: " + a.getOpenSet().size());
            System.out.println("Solutions: " + a.getSolutions().size());
            
        } catch (InvalidDirection ex) {
            System.out.println(ex);
        }
        
    }
    
}
