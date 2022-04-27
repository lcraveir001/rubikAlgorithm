
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

//hello every one 
//this is superior to code together

/**
 * rubiksCube
 *  IDA* pseudo code from: https://en.wikipedia.org/wiki/Iterative_deepening_A*
 */
public class Algorithm {
    
    static final HashMap<Integer, String> movementNotation =  new HashMap<Integer, String>();;
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





    /**
     * 
     * Hello evryone
     * I just wanted to write a reminder here for myself for when 
     * I need to mention something about cost to current
     * I think the int can just be local per method cuz it was
     * never really used
     */



    public Cube turnRight(Cube cube, int row){
        
        int[] left = cube.getRow("left", row);
        int[] face = cube.getRow("face", row);
        int[] right = cube.getRow("right", row);
        int[] back = cube.getRow("back", row);
        
        cube.setRow("left", row, face);
        cube.setRow("back", row, left);
        cube.setRow("right", row, back);
        cube.setRow("face", row, right);
        if (row == 0){
            int[] top1 = cube.getRow("top", 0);
            int[] top2 = cube.getRow("top", 1);
            int[] top3 = cube.getRow("top", 2);

            cube.setColumn("top", 2, top1);
            cube.setColumn("top", 1, top2);
            cube.setColumn("top", 0, top3);
        }
        if (row == 2){
            int[] bottom1 = cube.getRow("bottom", 0);
            int[] bottom2 = cube.getRow("bottom", 1);
            int[] bottom3 = cube.getRow("bottom", 2);

            cube.setColumn("bottom", 2, bottom1);
            cube.setColumn("bottom", 1, bottom2);
            cube.setColumn("bottom", 0, bottom3);
        }
        return cube;
    }

    /**
    
     */
    public Cube turnLeft(Cube cube, int row){
        // needs to be able to store the color values as a 
        int[] left = cube.getRow("left", row);
        int[] face = cube.getRow("face", row);
        int[] right = cube.getRow("right", row);
        int[] back = cube.getRow("back", row);
        
        cube.setRow("face", row, left);
        cube.setRow("right", row, face);
        cube.setRow("back", row, right);
        cube.setRow("left", row, back);
        if (row == 0){
            int[] top1 = cube.getRow("top", 0);
            int[] top2 = cube.getRow("top", 1);
            int[] top3 = cube.getRow("top", 2);

            cube.setColumn("top", 0, top1);
            cube.setColumn("top", 1, top2);
            cube.setColumn("top", 2, top3);
        }
        if (row == 2){
            int[] bottom1 = cube.getRow("bottom", 0);
            int[] bottom2 = cube.getRow("bottom", 1);
            int[] bottom3 = cube.getRow("bottom", 2);

            cube.setColumn("bottom", 0, bottom1);
            cube.setColumn("bottom", 1, bottom2);
            cube.setColumn("bottom", 2, bottom3);
        }
        
        return cube;
    }

    public Cube turnUp(Cube cube, int col){
        int[] left = cube.getColumn("left", col);
        int[] face = cube.getRow("face", col);
        int[] right = cube.getRow("right", col);
        int[] back = cube.getRow("back", col);
        
        cube.setRow("left", col, face);
        cube.setRow("back", col, left);
        cube.setRow("right", col, back);
        cube.setRow("face", col, right);
        if (col == 0){
            int[] left1 = cube.getColumn("left", 0);
            int[] left2 = cube.getColumn("left", 1);
            int[] left3 = cube.getColumn("left", 2);

            cube.setRow("left", 2, left1);
            cube.setRow("left", 1, left2);
            cube.setRow("left", 0, left3);
        }
        if (col == 2){
            int[] right1 = cube.getRow("right", 0);
            int[] right2 = cube.getRow("right", 1);
            int[] right3 = cube.getRow("right", 2);

            cube.setColumn("right", 2, right1);
            cube.setColumn("right", 1, right2);
            cube.setColumn("right", 0, right3);
        }
        return cube;
    }  

    /**
    possibly turning more than just the face??
     */
    public Cube turnDownForWhat(Cube cube, int col){
        int[] left = cube.getColumn("left", col);
        int[] face = cube.getRow("face", col);
        int[] right = cube.getRow("right", col);
        int[] back = cube.getRow("back", col);
        
        cube.setRow("left", col, face);
        cube.setRow("back", col, left);
        cube.setRow("right", col, back);
        cube.setRow("face", col, right);
        if (col == 0){
            int[] left1 = cube.getColumn("left", 0);
            int[] left2 = cube.getColumn("left", 1);
            int[] left3 = cube.getColumn("left", 2);

            cube.setRow("left", 2, left1);
            cube.setRow("left", 1, left2);
            cube.setRow("left", 0, left3);
        }
        if (col == 2){
            int[] right1 = cube.getRow("right", 0);
            int[] right2 = cube.getRow("right", 1);
            int[] right3 = cube.getRow("right", 2);

            cube.setColumn("right", 0, right1);
            cube.setColumn("right", 1, right2);
            cube.setColumn("right", 2, right3);
        }
        return cube;

    }

    /**
    assuming we are turning the face clockwise
     */
    public Cube turnFaceClockwise(Cube cube, int face){
        // 0 = front and 1 = back for the "face" int
        int[] front0 = cube.getRow("front", 0);
        int[] front1 = cube.getRow("front", 1);
        int[] front2 = cube.getRow("front", 2);

        int[] back0 = cube.getRow("back", 0);
        int[] back1 = cube.getRow("back", 1);
        int[] back2 = cube.getRow("back", 2);

        if (face == 0) {
            cube.setColumn("front", 2, front0);
            cube.setColumn("front", 1, front0);
            cube.setColumn("front", 0, front0);
        }
        
        if (face == 1) {
            
        }

        return null;
        
    }

    public Cube turnFaceCounterClockwise(Cube cube, int face){
        int[] front0 = cube.getRow("front", 0);
        int[] front1 = cube.getRow("front", 1);
        int[] front2 = cube.getRow("front", 2);
        return null;
    }

    public ArrayDeque<Cube> idaStar(Cube startCube){
        //bound = maxCost
        int bound = maxCost;
        ArrayDeque<Cube> path = new ArrayDeque<Cube>();
        path.push(startCube);
        Boolean found = false;
        System.out.println("Searching...");
        int timer = 0;
        while(!found){
            timer++;
            int t = search(path, 0, bound);
            if(t==-1) {
                return path;
            }
            bound = t;
            if(timer == 100000000){
                System.out.println("you got a problem here");
                break;
            }
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
        Cube node = path.peekLast();
        int f = costToCurrent + costToCheapest(costToCurrent);
        if(f>bound){
            return f;
        }
        if(node.checkSolve()) {
            return -1; //FOUND
            
        }
        int min = Integer.MAX_VALUE;
        for(Cube succ : getNextMoves(node)){
            if(!path.contains(succ)){
                path.push(succ);
                t = search(path, costToCurrent + updateCost(costToCurrent), bound);
                if (t == -1) { // t equals found
                    return -1;
                }
                System.out.println("hi");
                if(t<min) {
                    min = t;
                    System.out.println("min to: " + min);
                }
                path.pop();
            }
        }
        return min;

    }

    public int costToCheapest(int costToCurrent) {
        // equal to pseduo h(node)
        //maybe change fx name to costToCheapest
        // return maxCost - costToCurrentNode; 
        return maxCost - costToCurrent;
    }

    public int updateCost(int currentCost) {
        // equal to pseduo code cost(node, succ) 
        //should change fx name to be cost
        //wouldn't this just be equal to 1?
        //from current to successor is just one more move
        //should always be one, may not need
        // costToCurrentNode+=1;
        // return costToCurrentNode;
        return currentCost+=1;
    }
    public ArrayList<Cube> getNextMoves(Cube node) {
        // equal to pseduo successors(node)
        //I changed this to output an array of Cubes since the
        //successors have to be the next closest cube states
        //i changed the turn functions to return the output cube
        ArrayList<Cube> successors = new ArrayList<Cube>();
        for(int i = 0; i<=2; i++){
            successors.add(turnDownForWhat(node, i));
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