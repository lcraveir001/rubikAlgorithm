import java.util.*;

/*
    colors on the faces of the cube will be represented by ints
    0: green
    1: white
    2: yellow
    3: red
    4: blue
    5: orange
    Consider not just the outer colors, but also the inner colors as well.
*/

/**
Class to hold a 6 2D array side version of a Rubik's cube.
 */
public class Cube {
    // instance variables for the Rubik's Cube faces and the hash map for the sides' colors
    private HashMap<String,int[][]> sides;
    private int[][] face;
    private int[][] top;
    private int[][] bottom;
    private int[][] left;
    private int[][] right;
    private int[][] back;
    
    /**
    Initializes and randomises a Rubik's cube.
     */
    public Cube(){
        this.face = randomize();
        this.top = randomize();
        this.bottom = randomize();
        this.left = randomize();
        this.right = randomize();
        this.back = randomize(); 
        this.sides = new HashMap<>();
        sides.put("face", face);
        sides.put("top", top);
        sides.put("bottom", bottom);
        sides.put("left", left);
        sides.put("right", right);
        sides.put("back", back);

        
    }

    /**
    Initializes a Rubik's cube with input sides.
     */
    public Cube(int[][] face, int[][]top, int[][] bottom, int[][]left, int[][] right, int[][] back){
        this.face = face;
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        this.back = back; 
        this.sides = new HashMap<>();
        sides.put("face", face);
        sides.put("top", top);
        sides.put("bottom", bottom);
        sides.put("left", left);
        sides.put("right", right);
        sides.put("back", back);
    }

    /**
     * Deep copy constructer 
     * @param cube cube to make copy of
     */
    public Cube(Cube cube) {
        this.face = new int[3][3];
        this.top = new int[3][3];
        this.bottom = new int[3][3];
        this.left = new int[3][3];
        this.right = new int[3][3];
        this.back = new int[3][3];

        for(int i =0; i<3; i++){
            for(int j =0; j<3; j++){
                this.face[i][j]=cube.getSide("face")[i][j];
            }
        }
        for(int i =0; i<3; i++){
            for(int j =0; j<3; j++){
                this.top[i][j]=cube.getSide("top")[i][j];
            }
        }
        for(int i =0; i<3; i++){
            for(int j =0; j<3; j++){
                this.bottom[i][j]=cube.getSide("bottom")[i][j];
            }
        }
        for(int i =0; i<3; i++){
            for(int j =0; j<3; j++){
                this.left[i][j]=cube.getSide("left")[i][j];
            }
        }
        for(int i =0; i<3; i++){
            for(int j =0; j<3; j++){
                this.right[i][j]=cube.getSide("right")[i][j];
            }
        }
        for(int i =0; i<3; i++){
            for(int j =0; j<3; j++){
                this.back[i][j]=cube.getSide("back")[i][j];
            }
        }
        this.sides = new HashMap<>(cube.sides);
        sides.put("face", face);
        sides.put("top", top);
        sides.put("bottom", bottom);
        sides.put("left", left);
        sides.put("right", right);
        sides.put("back", back);
    }

    /**
     * This method initializes a side (2D array).
     */
    private int[][] randomize() {
        Random rand = new Random();

        HashMap<Integer, Integer> counts = new HashMap<>();
        ArrayList<Integer> possibleColors = new ArrayList<>();
        possibleColors.addAll(List.of(0,1,2,3,4,5));
        for(int x : possibleColors) {
            counts.put(x,0);
        }
        
        int[][] side = new int[3][3];
        for (int i = 0; i < side.length; i++) {
            for(int j = 0; j<side.length; j++){
                int color = rand.nextInt(possibleColors.size()-1);
                counts.put(Integer.valueOf(color), counts.get(Integer.valueOf(color))+1);
                side[i][j] = color;
                if(counts.get(color) >= 6) {
                    possibleColors.remove(Integer.valueOf(color));
                }
            }
        }
        return side;
    }

    /**
    Returns a given side from the cube.
     */
    public int[][] getSide(String sideName){
       return sides.get(sideName);
    }
    
    /**
    Returns a given row from the cube.
     */
    public int[] getRow(String sideName, int row){
        int[][] side = getSide(sideName);
        int[] returnRow = new int[3];
        for (int index = 0; index < 3; index++) {
            returnRow[index] = side[row][index];
        }
        return returnRow;
    }

    /**
    Sets a given row from a side on the cube.
     */
    public void setRow(String sideName, int row, int[] newVals){
        int[][] side = getSide(sideName);
        for (int index = 0; index < 3; index++) {
            side[row][index] = newVals[index];
        }
    }

    /**
    Sets a given column from a side on the cube.
     */
    public void setColumn(String sideName, int col, int[] newVals){
        int[][] side = getSide(sideName);
        for (int index = 0; index < 3; index++) {
            side[index][col] = newVals[index];
        }
    }

    /**
    Returns a given column from a side on the cube.
     */
    public int[] getColumn(String sideName, int col){
        int [][] side = getSide(sideName);
        int[] returnCol = new int[3];
        for (int index = 0; index < 3; index++) {
                returnCol[index] = side[index][col];
            }
        return returnCol;
    }
    
    /**
    Sets the values for a given side on the cube.
     */
    public void setSide(String sideName, int[][] newSide){        
        if(sideName.equals("face")){
            this.face = newSide;
            sides.replace(sideName, newSide);
        }
        else if(sideName.equals("back")){
            this.back = newSide;
            sides.replace(sideName, newSide);
        }
        else if(sideName.equals("top")){
            this.top = newSide;
            sides.replace(sideName, newSide);
        }
        else if(sideName.equals("bottom")){
            this.bottom = newSide;
            sides.replace(sideName, newSide);
        }
        else if(sideName.equals("left")){
            this.left = newSide;
            sides.replace(sideName, newSide);
        }
        else{
            this.right = newSide;
            sides.replace(sideName, newSide);
        }
     }

    /**
    Method to check if the Rubik's cube is solved.
     */
    public Boolean checkSolve() {
        // equal to pseduo is_goal(node)
        ArrayList<Integer> colors = new ArrayList<>();
        colors.addAll(List.of(0,1,2,3,4,5));
        for (int[][] side : sides.values()) {

            int color = side[0][0];
            if (!colors.contains(color)){
                return false;
            } else {
                colors.remove(Integer.valueOf(color));
                for (int[] i : side ) {
                    for (int j : i) {
                        if (j != color) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
     }

    @Override
    public String toString() {
        String output = "";
        for (String sideName : sides.keySet()) {
            output += sideToString(sideName);
        }
        return output;
    }

    /**
    Prints out a side of the cube in a readable manner.
     */
    private String sideToString(String name) {
        String output = "";
        int[][] side = sides.get(name);

        output += "\n"+name + ": \n";
        for(int i[] : side) {
            for (int j : i) {
                output += j + " ";
            }
            output += "\n";
        }

        return output;
    }





} 