
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * IDA* pseudo code from: https://en.wikipedia.org/wiki/Iterative_deepening_A*
 * Algorithm to run our Rubik's Cube solver.
 */
public class Algorithm {

    static final HashMap<Integer, String> movementNotation = new HashMap<Integer, String>();
    static final int maxCost = 18; // all cubes can be solved with cost of this
    int costToCurrentNode;
    ArrayDeque<Integer> moves = new ArrayDeque<Integer>();
    ArrayDeque<Integer> finalMoves = new ArrayDeque<Integer>();


    /*
     * int row is which row that is being turned
     * 0: top row
     * 1: middle row
     * 2: bottom row
     * 
     * int col is which column that is being turned
     * 0: left col
     * 1: middle col
     * 2: right col
     * 
     * int face is which side's face that is being turned clockwise
     * 0: front
     * 1: back
     */



    public Algorithm() {
        this.costToCurrentNode = 0; // equal to pseduocode g
        this.moves = new ArrayDeque<>();
        this.finalMoves = new ArrayDeque<>();

        movementNotation.put(1, "top row right");
        movementNotation.put(2, "middle row right");
        movementNotation.put(3, "bottom row right");
        movementNotation.put(4, "top row left");
        movementNotation.put(5, "middle row left");
        movementNotation.put(6, "bottom row left");
        movementNotation.put(7, "first col up");
        movementNotation.put(8, "middle col up");
        movementNotation.put(9, "last col up");
        movementNotation.put(10, "first col down");
        movementNotation.put(11, "middle col down");
        movementNotation.put(12, "last col down");
        movementNotation.put(13, "turn face clockwise");
        movementNotation.put(14, "turn back clockwise");
        movementNotation.put(15, "turn face counterclockwise");
        movementNotation.put(16, "turn back counterclockwise");

    }

    /**
    Helper method to swap the first and third (index 0 and 2) of a given array.
     */
    public int[] swap(int[] arr) {
        int a = arr[0];
        int b = arr[2];
        arr[0] = b;
        arr[2] = a;

        return arr;
    }
    /**
    Turns an input row of an input cube to the left. 
     */
    public Cube turnLeft(Cube cube, int row) {

        int[] left = cube.getRow("left", row);
        int[] face = cube.getRow("face", row);
        int[] right = cube.getRow("right", row);
        int[] back = cube.getRow("back", row);

        cube.setRow("left", row, face);
        cube.setRow("back", row, left);
        cube.setRow("right", row, back);
        cube.setRow("face", row, right);
        if (row == 0) {
            int[] top1 = cube.getRow("top", 0);
            int[] top2 = cube.getRow("top", 1);
            int[] top3 = cube.getRow("top", 2);

            cube.setColumn("top", 2, top1);
            cube.setColumn("top", 1, top2);
            cube.setColumn("top", 0, top3);
        }
        if (row == 2) {
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
    Turns an input row of an input cube to the right. 
     */
    public Cube turnRight(Cube cube, int row) {
        // needs to be able to store the color values as a
        int[] left = cube.getRow("left", row);
        int[] face = cube.getRow("face", row);
        int[] right = cube.getRow("right", row);
        int[] back = cube.getRow("back", row);

        cube.setRow("face", row, left);
        cube.setRow("right", row, face);
        cube.setRow("back", row, right);
        cube.setRow("left", row, back);
        if (row == 0) {
            int[] top1 = cube.getColumn("top", 0);
            int[] top2 = cube.getColumn("top", 1);
            int[] top3 = cube.getColumn("top", 2);

            cube.setRow("top", 2, top1);
            cube.setRow("top", 1, top2);
            cube.setRow("top", 0, top3);
        }
        if (row == 2) {
            int[] bottom1 = cube.getColumn("bottom", 0);
            int[] bottom2 = cube.getColumn("bottom", 1);
            int[] bottom3 = cube.getColumn("bottom", 2);

            cube.setRow("bottom", 2, bottom1);
            cube.setRow("bottom", 1, bottom2);
            cube.setRow("bottom", 0, bottom3);
        }

        return cube;
    }
    /**
    Turns an input column of the face of an input cube up. 
     */
    public Cube turnUp(Cube cube, int col) {
        int[] bottom = cube.getColumn("bottom", col);
        bottom = swap(bottom);
        int[] face = cube.getColumn("face", col);
        int[] top = cube.getColumn("top", col);
        int[] back = cube.getColumn("back", col);

        cube.setColumn("bottom", col, back);
        cube.setColumn("face", col, bottom);
        cube.setColumn("top", col, face);
        cube.setColumn("back", col, top);
        if (col == 0) {
            int[] left1 = cube.getColumn("left", 0);
            int[] left2 = cube.getColumn("left", 1);
            int[] left3 = cube.getColumn("left", 2);

            cube.setRow("left", 2, left1);
            cube.setRow("left", 1, left2);
            cube.setRow("left", 0, left3);
        }
        if (col == 2) {
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
    Turns an input column of the face of an input cube down. 
     */
    public Cube turnDownForWhat(Cube cube, int col) {
        int[] bottom = cube.getColumn("bottom", col);
        int[] face = cube.getColumn("face", col);
        face = swap(face);

        int[] top = cube.getColumn("top", col);
        int[] back = cube.getColumn("back", col);

        cube.setColumn("bottom", col, face);
        cube.setColumn("face", col, top);
        cube.setColumn("top", col, back);
        cube.setColumn("back", col, bottom);
        if (col == 0) {
            int[] left1 = cube.getRow("left", 0);
            int[] left2 = cube.getRow("left", 1);
            int[] left3 = cube.getRow("left", 2);

            cube.setColumn("left", 2, left1);
            cube.setColumn("left", 1, left2);
            cube.setColumn("left", 0, left3);
        }
        if (col == 2) {
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
    Turns the an input side(0 = face 1 = back) of an input cube clockwise. 
     */
    public Cube turnFaceClockwise(Cube cube, int face) {
        // 0 = front and 1 = back for the "face" int
        int[] front0 = cube.getRow("face", 0);
        int[] front1 = cube.getRow("face", 1);
        int[] front2 = cube.getRow("face", 2);

        int[] back0 = cube.getRow("back", 0);
        int[] back1 = cube.getRow("back", 1);
        int[] back2 = cube.getRow("back", 2);

        if (face == 0) {
            cube.setColumn("face", 2, front0);
            cube.setColumn("face", 1, front1);
            cube.setColumn("face", 0, front2);

            int[] left = cube.getColumn("left", 2);
            int[] top = cube.getRow("top", 2);
            int[] right = cube.getColumn("right", 0);
            right = swap(right);
            int[] bottom = cube.getRow("bottom", 2);

            cube.setRow("top", 2, left);
            cube.setColumn("right", 0, top);
            cube.setRow("bottom", 2, right);
            cube.setColumn("left", 2, bottom);

        }

        if (face == 1) {
            cube.setColumn("back", 2, back0);
            cube.setColumn("back", 1, back1);
            cube.setColumn("back", 0, back2);

            int[] left = cube.getColumn("left", 0);
            int[] top = cube.getRow("top", 0);
            int[] right = cube.getColumn("right", 2);
            right = swap(right);
            int[] bottom = cube.getRow("bottom", 0);

            cube.setRow("top", 0, left);
            cube.setColumn("right", 2, top);
            cube.setRow("bottom", 0, right);
            cube.setColumn("left", 0, bottom);
        }

        return cube;
    }

    /**
    Turns the an input side(0 = face 1 = back) of an input cube counterclockwise. 
     */
    public Cube turnFaceCounterClockwise(Cube cube, int face) {
        int[] front0 = cube.getColumn("face", 0);
        int[] front1 = cube.getColumn("face", 1);
        int[] front2 = cube.getColumn("face", 2);

        int[] back0 = cube.getRow("back", 0);
        int[] back1 = cube.getRow("back", 1);
        int[] back2 = cube.getRow("back", 2);

        if (face == 0) {
            cube.setRow("face", 2, front0);
            cube.setRow("face", 1, front1);
            cube.setRow("face", 0, front2);

            int[] left = cube.getColumn("left", 2);
            int[] top = cube.getRow("top", 2);
            int[] right = cube.getColumn("right", 0);
            int[] bottom = cube.getRow("bottom", 2);
            bottom = swap(bottom);

            cube.setColumn("left", 2, top);
            cube.setRow("top", 2, right);
            cube.setColumn("right", 0, bottom);
            cube.setRow("bottom", 2, left);

        }

        if (face == 1) {
            cube.setColumn("back", 2, back0);
            cube.setColumn("back", 1, back1);
            cube.setColumn("back", 0, back2);

            int[] left = cube.getColumn("left", 0);
            int[] top = cube.getRow("top", 0);
            int[] right = cube.getColumn("right", 2);
            int[] bottom = cube.getRow("bottom", 0);
            bottom = swap(bottom);

            cube.setColumn("left", 0, top);
            cube.setRow("top", 0, right);
            cube.setColumn("right", 2, bottom);
            cube.setRow("bottom", 0, left);
        }

        return cube;
    }

    /**
     * Preforms IDA* algorithm on a cube
     */
    public ArrayDeque<Cube> idaStar(Cube startCube) {
        // bound = maxCost
        int bound = maxCost;
        ArrayDeque<Cube> path = new ArrayDeque<Cube>();
        path.push(startCube);
        Boolean found = false;
        while (!found) {
            int t = search(path, 0, bound);
            if (t == -1) {
                found = true;
                System.out.println("Found solution, victory!");
                return path;
            } else if (t == Integer.MAX_VALUE) {
                System.out.println("No solution found, failed.");
                return path;
            }
            bound = t;
        }
        return null;
    }

    /**
     * Recursively called search method for IDA*
     */
    public int search(ArrayDeque<Cube> path, int costToCurrent, int bound) {
        Integer copyCostToCurrent = costToCurrent;

        System.out.println("\nSearching..."); 
        int t = 0;

        Cube node = path.peek();
        int costToCheapest = costToCheapest(copyCostToCurrent);
        int f = copyCostToCurrent + costToCheapest; // f = g + h

        if (f > bound) {
            return f;
        }
        if (node.checkSolve()) {
            return -1; // FOUND
        }
        int min = Integer.MAX_VALUE;
        ArrayList<Cube> successors = getNextMoves(node);
        for (Cube succ : successors) {
            if (!path.contains(succ)) {
                finalMoves.push(moves.removeLast());
                path.push(succ);
                t = search(path, copyCostToCurrent + 1, bound);
                if (t == -1) { // t equals found
                    return -1;
                } else if (t < min) {
                    min = t;
                }
                path.pop();
                finalMoves.pop();
            }
        }
        return min;
    }


    /**
     * Finds the cost to to cheapest solution
     */
    public int costToCheapest(int costToCurrent) {
        Integer copyCostToCurrent = costToCurrent;
        if (copyCostToCurrent > maxCost) {
            return maxCost+1;
        } else {
            return maxCost - copyCostToCurrent;
        }
    }

    /**
     * Gets all the next possible moves for a given cube
     */
    public ArrayList<Cube> getNextMoves(Cube node) {
        ArrayList<Cube> successors = new ArrayList<Cube>();
        moves = new ArrayDeque<>();
        Cube nodeCopy = new Cube(node);
        for (int i = 0; i <= 2; i++) {
            if (turnRight(nodeCopy, i) != node)
                successors.add(nodeCopy);
            nodeCopy = new Cube(node);
            if (turnDownForWhat(nodeCopy, i) != node)
                successors.add(nodeCopy);
            nodeCopy = new Cube(node);
            if (turnUp(nodeCopy, i) != node)
                successors.add(nodeCopy);
            nodeCopy = new Cube(node);
            if (turnLeft(nodeCopy, i) != node)
                successors.add(nodeCopy);
            nodeCopy = new Cube(node);
        }
        for (int j = 0; j <= 1; j++) {
            nodeCopy = new Cube(node);
            if (turnFaceClockwise(nodeCopy, j) != node)
                successors.add(nodeCopy);
            nodeCopy = new Cube(node);
            if (turnFaceCounterClockwise(nodeCopy, j) != node)
                successors.add(nodeCopy);
        }

        moves.push(1);
        moves.push(10);
        moves.push(7);
        moves.push(4);
        moves.push(2);
        moves.push(11);
        moves.push(8);
        moves.push(5);
        moves.push(3);
        moves.push(12);
        moves.push(9);
        moves.push(6);
        moves.push(13);
        moves.push(15);
        moves.push(14);
        moves.push(16);
        
        return successors;
    }

    /**
     * Prints out the moves to get to the solved cube.
     */
    public void printMoves() {
        System.out.println("Path to solve:");
        while(finalMoves.size() > 0) {
            int move = finalMoves.pop();
            System.out.println(movementNotation.get(move));
        }
    }

    public static void main(String[] args) {
        int[][] face = { { 5, 5, 5 }, { 0, 0, 0 }, {  0, 0, 0 } };
        int[][] top = { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } };
        int[][] bottom = { { 2, 2, 2 }, { 2, 2, 2 }, { 2, 2, 2 } };
        int[][] left = { { 4, 4, 4}, { 3, 3, 3 }, {  3, 3, 3 } };
        int[][] right = { {  3, 3, 3 }, { 4, 4, 4 }, { 4, 4, 4 } };
        int[][] back = { { 0, 0, 0 }, { 5, 5, 5 }, {  5, 5, 5  } };

        Cube cube = new Cube(face, top, bottom, left, right, back);

        // Cube cube = new Cube();
        Algorithm algo = new Algorithm();
        
        algo.idaStar(cube);
        algo.printMoves();
        System.out.println("success");

    }
}