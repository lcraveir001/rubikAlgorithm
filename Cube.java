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

public class Cube {
    // instance variables for the Rubik's Cube faces and the hash map for the sides' colors
    private HashMap<String,int[][]> sides;
    private int[][] face;
    private int[][] top;
    private int[][] bottom;
    private int[][] left;
    private int[][] right;
    private int[][] back;
    
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

    private int[][] randomize() {
        Random rand = new Random();
        int[][] side = new int[3][3];
        for (int i = 0; i < side.length; i++) {
            for(int j = 0; j<side.length; j++){
                side[i][j] = rand.nextInt(6);
            }
        }
        return side;
    }

    public int[][] getSide(String sideName){
       return sides.get(sideName);
    }
    
    public int[] getRow(String sideName, int row){
        int[][] side = getSide(sideName);
        int[] returnRow = new int[3];
        for (int index = 0; index < 3; index++) {
            returnRow[index] = side[row][index];
        }
        return returnRow;
    }

    public void setRow(String sideName, int row, int[] newVals){
        int[][] side = getSide(sideName);
        for (int index = 0; index < 3; index++) {
            side[row][index] = newVals[index];
        }
    }

    public void setColumn(String sideName, int col, int[] newVals){
        int[][] side = getSide(sideName);
        for (int index = 0; index < 3; index++) {
            side[index][col] = newVals[index];
        }
    }

    public int[] getColumn(String sideName, int col){
        int [][] side = getSide(sideName);
        int[] returnCol = new int[3];
        for (int index = 0; index < 3; index++) {
                returnCol[index] = side[index][col];
            }
        return returnCol;
    }

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

     //can be more efficient, look into eliminating j value
     public Boolean checkSolve() {
        // equal to pseduo is_goal(node)
        for (int[][] side : sides.values()) {
            int color = side[0][0];
            for (int[] i : side ) {
                for (int j : i) {
                    if (j != color) {
                        return false;
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