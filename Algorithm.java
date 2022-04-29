
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

//hello every one 
//this is superior to code together

/**
 * rubiksCube
 * IDA* pseudo code from: https://en.wikipedia.org/wiki/Iterative_deepening_A*
 */
public class Algorithm {

    static final HashMap<Integer, String> movementNotation = new HashMap<Integer, String>();;
    static final int maxCost = 18; // all cubes can be solved with cost of this

    int costToCurrentNode;

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

    }

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
     * currently turns to the right
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
            int[] top1 = cube.getRow("top", 0);
            int[] top2 = cube.getRow("top", 1);
            int[] top3 = cube.getRow("top", 2);

            cube.setColumn("top", 0, top1);
            cube.setColumn("top", 1, top2);
            cube.setColumn("top", 2, top3);
        }
        if (row == 2) {
            int[] bottom1 = cube.getRow("bottom", 0);
            int[] bottom2 = cube.getRow("bottom", 1);
            int[] bottom3 = cube.getRow("bottom", 2);

            cube.setColumn("bottom", 0, bottom1);
            cube.setColumn("bottom", 1, bottom2);
            cube.setColumn("bottom", 2, bottom3);
        }

        return cube;
    }

    public Cube turnUp(Cube cube, int col) {
        int[] bottom = cube.getColumn("bottom", col);
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
     * possibly turning more than just the face??
     */
    public Cube turnDownForWhat(Cube cube, int col) {
        int[] bottom = cube.getColumn("bottom", col);
        int[] face = cube.getColumn("face", col);
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
     * assuming we are turning the face clockwise
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
            int[] bottom = cube.getRow("bottom", 0);

            cube.setRow("top", 0, left);
            cube.setColumn("right", 2, top);
            cube.setRow("bottom", 0, right);
            cube.setColumn("left", 0, bottom);
        }

        return cube;

    }

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

            cube.setRow("left", 2, top);
            cube.setColumn("top", 0, right);
            cube.setRow("right", 2, bottom);
            cube.setColumn("bottom", 2, left);

        }

        if (face == 1) {
            cube.setColumn("back", 2, back0);
            cube.setColumn("back", 1, back1);
            cube.setColumn("back", 0, back2);

            int[] left = cube.getColumn("left", 0);
            int[] top = cube.getRow("top", 0);
            int[] right = cube.getColumn("right", 2);
            int[] bottom = cube.getRow("bottom", 0);

            cube.setRow("left", 0, top);
            cube.setColumn("top", 2, right);
            cube.setRow("right", 0, bottom);
            cube.setColumn("bottom", 0, left);
        }

        return cube;
    }

    /**
     * LILA
     * THIS IS WHERE IDA STAR IS
     */

    public ArrayDeque<Cube> idaStar(Cube startCube) {
        // bound = maxCost
        int bound = maxCost;
        ArrayDeque<Cube> path = new ArrayDeque<Cube>();
        path.push(startCube);
        Boolean found = false;
        System.out.println("Searching...");
        int timer = 0;
        while (!found) {
            timer++;
            int t = search(path, 0, bound);
            if (t == -1) {
                found = true;
                return path;
            }
            System.out.println("b before: " + bound);
            bound = t;
            System.out.println("b after: " + bound);

            // NOTE
            // Bound is really big
            // always turns out to be 2,147,483,647

            if (timer == 100000000) {
                System.out.println("you got a problem here");
                break;
            }
        }
        return null;
    }

    public int search(ArrayDeque<Cube> path, int costToCurrent, int bound) {
        // f = costToCurrentNode + costToEndGoal()
        // NOTE: do we even need cost() if we are passing in the cost to the current
        // node
        // and cost() just finds the maxCost - the cost of the current node
        // canit we just do maxCost - cost of current?

        // we can do if returns -1, then it is found
        int t = 0;

        // should t be a global variable or should we keep it local?
        Cube node = path.peekLast();
        int f = costToCurrent + costToCheapest(costToCurrent); // f = g + h
        System.out.println("f: " + f);
        System.out.println("bound here is: " + bound);
        if (f > bound) {
            return f;
        }
        if (node.checkSolve()) {
            return -1; // FOUND

        }
        int min = Integer.MAX_VALUE;
        System.out.println("Path length before search: " + path.size());
        // System.out.println("Length of sucessors: " + getNextMoves(node));
        for (Cube succ : getNextMoves(node)) {
            if (!path.contains(succ)) {
                path.push(succ);
                t = search(path, costToCurrent + 1, bound);
                if (t == -1) { // t equals found
                    System.out.println("found");
                    return -1;
                } else if (t < min) {
                    System.out.println("t is less than min");
                    min = t;
                } else {
                    System.out.println("min is not modified.");
                }
                path.pop();
            } else {
                System.out.println("successor found in path");
            }
        }
        return min;
    }

    public int costToCheapest(int costToCurrent) {
        // equal to pseduo h(node)
        // maybe change fx name to costToCheapest
        if (costToCurrent > maxCost) {
            return maxCost;
        } else {
            return maxCost - costToCurrent;
        }
        // return maxCost - costToCurrent;
    }

    // public int updateCost() {
    // // equal to pseduo code cost(node, succ)
    // //should change fx name to be cost
    // //wouldn't this just be equal to 1?
    // //from current to successor is just one more move
    // //should always be one, may not need
    // // costToCurrentNode+=1;
    // return costToCurrentNode+1;
    // // return currentCost+=1;
    // }
    public ArrayList<Cube> getNextMoves(Cube node) {
        // this has an issue with sucessors being in the path already for some reason

        // equal to pseduo successors(node)
        // I changed this to output an array of Cubes since the
        // successors have to be the next closest cube states
        // i changed the turn functions to return the output cube
        ArrayList<Cube> successors = new ArrayList<Cube>();
        for (int i = 0; i <= 2; i++) {
            if (turnDownForWhat(node, i) != node)
                successors.add(turnDownForWhat(node, i));
            if (turnUp(node, i) != node)
                successors.add(turnUp(node, i));
            if (turnLeft(node, i) != node)
                successors.add(turnLeft(node, i));
            if (turnRight(node, i) != node)
                successors.add(turnRight(node, i));
        }
        // for(int i = 0; i < successors.size(); i++) {
        // if (successors.get(i) == node) {
        // successors.remove(i);
        // }
        // }
        // successors.add(new Cube());
        return successors;
    }

    public static void main(String[] args) {
        Cube cube = new Cube();
        Algorithm algo = new Algorithm();
        System.out.println(cube.toString());
        System.out.println(algo.turnRight(cube, 0));
        // System.out.println(cube.toString());
        // System.out.println("new cube");
        // System.out.println(cube.toString());
        // algo.turnLeft(cube, 2);
        // System.out.println("new cube 2");
        // System.out.println(cube.toString());
        // algo.turnUp(cube, 2);
        // System.out.println("new cube 3");
        // System.out.println(cube.toString());
        // algo.turnDownForWhat(cube, 2);
        // System.out.println("new cube 4");
        // System.out.println(cube.toString());
        // algo.turnFaceClockwise(cube, 1);
        // System.out.println("new cube 5");
        // System.out.println(cube.toString());
        // algo.turnFaceCounterClockwise(cube, 0);
        // System.out.println("new cube 6");
        // System.out.println(cube.toString());

        // ArrayDeque<Cube> finalPath = algo.idaStar(cube);
        // System.out.println("success?????");
        // for (Cube c : finalPath) {
        // System.out.println(c.toString());
        // }
        // System.out.println("Cube: ");
        // System.out.println(cube.toString());
        // ArrayList<Cube> test = algo.getNextMoves(cube);
        // for(Cube c : test){
        // // System.out.println(c.toString());
        // if(c == cube) {
        // System.out.println("Cube:\n" + cube.toString());

        // System.out.println("Succ:\n" + c.toString());
        // }

        // }
        // if (test.contains(cube)) {
        // System.out.println("ohhhh no");
        // } else {
        // System.out.println("victory");
        // }
        // ^this is not the way to go about it, but is temporary

        // hello everyone
        // the output of ida* is a stack of cubes
        // how do we want to output the answers?
        // for every push and pop, should we have another stack
        // that uses the entries from the movement map?

        // I think returning a set of moves from the stack of the cubes

    }
}