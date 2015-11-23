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
    
    public static void Manhattan(Puzzle p, int numbersOfBlend, int numbersOfProblems) throws InvalidDirection {
        float czas = 0;
        double droga = 0;
        int open = 0;
        int closed = 0;
        
        
        for (int i = 0; i < numbersOfProblems;i++){
                p=new Puzzle();
                p.blendPuzzle(numbersOfBlend);
                
                Puzzle.setHFunction(new HFunctionManhattan());
                GraphSearchConfigurator conf = new GraphSearchConfigurator();
                conf.setWantedNumberOfSolutions(1);
                GraphSearchAlgorithm a = new AStar(p, conf);
                a.execute();
                GraphState sol = a.getBestSoFar();

                czas += a.getDurationTime();
                closed += a.getClosedSet().size();
                open += a.getOpenSet().size();
                droga += sol.getPath().size();
                
            }
            czas = czas / numbersOfProblems;
            closed = closed / numbersOfProblems;
            open = open / numbersOfProblems;
            droga = droga / numbersOfProblems;
            
            System.out.println("Heurystyka Manhattan:");
            System.out.println("Czas: " + czas);
            System.out.println("Closed: " + closed);
            System.out.println("Open: " + open);
            System.out.println("Droga: " + droga);
        
    }
    
    public static void MisplacedTiles(Puzzle p, int numberOfBlend, int numbersOfProblems) throws InvalidDirection {
        float czas = 0;
        double droga = 0;
        int open = 0;
        int closed = 0;
        
        
        for (int i = 0; i < numbersOfProblems;i++){
                p=new Puzzle();
            
                p.blendPuzzle(numberOfBlend);
                Puzzle.setHFunction(new HFunctionMisplacedTiles());
                GraphSearchConfigurator conf = new GraphSearchConfigurator();
                conf.setWantedNumberOfSolutions(1);
                GraphSearchAlgorithm a = new AStar(p, conf);
                a.execute();
                GraphState sol = a.getBestSoFar();

                czas += a.getDurationTime();
                closed += a.getClosedSet().size();
                open += a.getOpenSet().size();
                droga += sol.getPath().size();
                
            }
            czas = czas / numbersOfProblems;
            closed = closed / numbersOfProblems;
            open = open / numbersOfProblems;
            droga = droga / numbersOfProblems;
            
            System.out.println("Heurystyka Manhattan:");
            System.out.println("Czas: " + czas);
            System.out.println("Closed: " + closed);
            System.out.println("Open: " + open);
            System.out.println("Droga: " + droga);
    }
    
    public static void main(String[] args) throws InvalidDirection {
        
        Puzzle.n = 4;
        int numbersOfProblems = 10;
        int numberOfBlend = 30;
               
        
        Puzzle p = null;

        try {
            
            //--------Heurystyka Manhattan-------
            //-----------------------------------
            Manhattan(p, numberOfBlend, numbersOfProblems);
            
            //--------Heurystyka MisplacedTiles-------
            //----------------------------------------
            MisplacedTiles(p, numberOfBlend, numbersOfProblems);
            
            
            
            
            /*for (int i = 0; i < numbersOfProblems;i++){
                p=new Puzzle();
            
                p.blendPuzzle(numberOfBlend);
                
                
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
                
                
            }
            czas=czas/100;
            closed=closed/100;
            open=open/100;
            droga=droga/100;
            
            System.out.println("Heurystyka Manhattan:");
            System.out.println("Czas: " + czas);
            System.out.println("Closed: " + closed);
            System.out.println("Open: " + open);
            System.out.println("Droga: " + droga);
            
            //----------------------------------------
            //--------Heurystyka MisplacedTiles-------
            //----------------------------------------
            czas = 0;
            closed = 0;
            open = 0;
            droga = 0;
            
            for (int i = 0; i < numbersOfProblems;i++){
                p=new Puzzle();
                p.blendPuzzle(numberOfBlend);
                Puzzle.setHFunction(new HFunctionMisplacedTiles());
                GraphSearchConfigurator conf = new GraphSearchConfigurator();
                conf.setWantedNumberOfSolutions(1);
                GraphSearchAlgorithm a = new AStar(p, conf);
                a.execute();
                GraphState sol = a.getBestSoFar();

                czas += a.getDurationTime();
                closed += a.getClosedSet().size();
                open += a.getOpenSet().size();
                droga += sol.getPath().size();
                
                
            }
            czas=czas/100;
            closed=closed/100;
            open=open/100;
            droga=droga/100;
            
            System.out.println("Heurystyka MisplacedTiles:");
            System.out.println("Czas: " + czas);
            System.out.println("Closed: " + closed);
            System.out.println("Open: " + open);
            System.out.println("Droga: " + droga);
            */
            
        } catch (InvalidDirection ex) {
            System.out.println(ex);
        }
        
    }
    
}
