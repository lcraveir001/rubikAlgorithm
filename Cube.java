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
        sides = new HashMap<>();
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
        sides = new HashMap<>();
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

    @Override
    public String toString() {
        String output = "";
        for (int[][] side : sides.values()) {
            output += sideToString(side);
        }
        return output;
    }

    private String sideToString(int[][] side) {
        String output = "";
        for(int i[] : side) {
            output += i.toString()+"\n";
        }

        return output;
    }



} 