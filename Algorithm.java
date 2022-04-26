
import java.util.ArrayDeque;
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
    public Cube turnRight(Cube cube, int row){
        int[][] left = cube.getSide("left");
        int[][] face = cube.getSide("face");
        int[][] right = cube.getSide("right");
        int[][] back = cube.getSide("back");
        
        for (int index = 0; index < 3; index++) {
            
        }
        
        return null;


    }

    /**
    
     */
    public Cube turnLeft(Cube cube, int row){
        // needs to be able to store the color values as a 
        return null;
    }

    public Cube turnUp(Cube cube, int col){
        return null;
    }

    public Cube turnDown(Cube cube, int col){
        return null;
    }

    public Cube turnFace(Cube cube, int face){
        return null;
    }

    public ArrayDeque<Cube> idaStar(Cube startCube){
        //bound = maxCost
        int bound = maxCost;
        ArrayDeque<Cube> path = new ArrayDeque<Cube>();
        Boolean found = false;
        while(!found){
            int t = search(path, 0, bound);
            if(t==-1)return path;
            bound = t;
        }
        return null;
    }

    public int search(ArrayDeque<Cube> path, int costToCurrent, int bound) {
        // f = costToCurrentNode + costToEndGoal()
        //NOTE: do we even need cost() if we are passing in the cost to the current node
        // and cost() just finds the maxCost - the cost of the current node
        //canit we just do maxCost - cost of current?

        //we can do if returns -1, then it is found
        int t = 0;

        //should t be a global variable or should we keep it local?

        Cube node = path.pop();
        int f = costToCurrent + cost();
        if (f>bound)return f;
        if(node.checkSolve())return -1; //FOUND
        int min = 100;
        for(Cube succ : getNextMoves(node)){
            if(!path.contains(succ)){
                path.push(succ);
                t = search(path, costToCurrent + costTo(node, succ), bound);
                if (t == -1) return -1;
                if(t<min)min = t;
                path.pop();
            }
        }
        return min;

    }

    public int cost() {
        // equal to pseduo h(node)
        //maybe change fx name to costToCheapest
        return maxCost - costToCurrentNode; 
    }

    public int costTo(Cube node, Cube succ) {
        // equal to pseduo code cost(node, succ) 
        //should change fx name to be cost
        //wouldn't this just be equal to 1?
        //from current to successor is just one more move
        //should always be one, may not need
        return 0;
    }
    public ArrayList<Cube> getNextMoves(Cube node) {
        // equal to pseduo successors(node)
        //I changed this to output an array of Cubes since the
        //successors have to be the next closest cube states
        //i changed the turn functions to return the output cube
        ArrayList<Cube> successors = new ArrayList<Cube>();
        for(int i = 0; i<=2; i++){
            successors.add(turnDown(node, i));
            successors.add(turnUp(node, i));
            successors.add(turnLeft(node, i));
            successors.add(turnRight(node, i));
        }

        return successors;
    }



    public static void main(String[] args) {
        Cube cube = new Cube();
        Algorithm algo = new Algorithm();
        algo.idaStar(cube);

    }
} 