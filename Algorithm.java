
import java.util.ArrayList;
import java.util.HashMap;

//hello every one 
//this is superior to code together

/**
 * rubiksCube
 */
public class Algorithm {
    
    static final HashMap<Integer, String> movementNotation = new HashMap<Integer, String>();
    static final int maxCost = 18; // all cubes can be solved with cost of this
    
    int costToCurrentNode;

    /*
        int row is which row that is being turned
            0: top row
            1: middle row
            2: bottom row
        
        int col is which column that is being turned
            0: left col
            1: middle col
            2: right col

        int face is which side's face that is being turned clockwise
            0: front
            1: back
    */

    public Algorithm() {
        this.costToCurrentNode = 0; // equal to pseduocode g
        movementNotation.put(1,"top row right");
        movementNotation.put(2, "middle row right");
        movementNotation.put(3, "bottom row right");
        movementNotation.put(4,"top row left");
        movementNotation.put(5, "middle row left");
        movementNotation.put(6, "bottom row left");
        movementNotation.put(7,"first col up");
        movementNotation.put(8, "middle col up");
        movementNotation.put(9, "last col up");
        movementNotation.put(10,"first col down");
        movementNotation.put(11, "middle col down");
        movementNotation.put(12, "last col down");

    }
    public void turnRight(Cube cube, int row){
        int[][] left = cube.getSide("left");
        int[][] face = cube.getSide("face");
        int[][] right = cube.getSide("right");
        int[][] back = cube.getSide("back");
        
        for (int index = 0; index < 3; index++) {
            
        }
        



    }

    /**
    
     */
    public void turnLeft(Cube cube, int row){
        // needs to be able to store the color values as a 
    }

    public void turnUp(Cube cube, int col){

    }

    public void turnDown(Cube cube, int col){

    }

    public void turnFace(Cube cube, int face){

    }

    public void idaStar(Cube startCube){

    }

    public void search() {
        // f = costToCurrentNode + costToEndGoal()

    }

    public int cost() {
        // equal to pseduo h(node)
        return maxCost - costToCurrentNode; 
       
    }

    public int costToEndGoal() {
        // equal to pseduo code cost(node, succ) 
        return 0;
    }
    public ArrayList<String> getNextMoves() {
        // equal to pseduo successors(node)
        return null;
    }



    public static void main(String[] args) {
        Cube cube = new Cube();
        Algorithm algo = new Algorithm();
        algo.idaStar(cube);

    }
} 