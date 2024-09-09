package mowing;
import java.util.*;
public class mowing {

//1
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(); // number of lawns
        in.nextLine();
        /* input example:
        2 (number of rows)
        12 11 (dimensions) 5 6 (starting point)
        11 13 9 1
        */

        int[][]lawns = new int[n][4]; //2d array that stores input data for each lawn
        //each i array holds values for a separate lawn
        for(int i = 0;i<n;i++){
            for(int j = 0; j<4; j++){
                lawns[i][j] = in.nextInt();
            }
            in.nextLine();
        }
        in.close();

        for(int i=0;i<n;i++){
            
            int vertical = lawns[i][0];
            int horizontal = lawns[i][1];
            int y = lawns[i][2];
            int x = lawns[i][3];
            //values taken from each line and printed out
            System.out.println(vertical + " " + horizontal + " " + y + " " + x);

            String[][]lawn = generateLawn(vertical,horizontal,y,x); //creates a randomized lawn of given size

            //creating a copy of the original lawn that will be edited
            String[][]mowedLawn = new String[lawn.length][lawn[0].length];
            for(int j = 0; j<lawn.length; j++){
                for(int k = 0; k<lawn[0].length; k++){
                    mowedLawn[j][k] = lawn[j][k];
                }
            }

            mow(mowedLawn,vertical,horizontal,y,x); // method to mow the copied lawn

            printLawns(lawn, mowedLawn);//prints out the mowed lawn in comparison to the original
            System.out.println();
        }

    }
//2
    public static void printLawns(String[][]l, String[][]m) //purpose is to print original and mowed lawn side by side
    {
        int y = l.length;
        int x = l[0].length;
        for(int i = 0; i<y; i++)
        {
            for(int j = 0; j<x; j++)//prints row of original lawn
            {
                System.out.print(l[i][j]+" ");
            }

            System.out.print(" |  "); //for spacing

            for(int j = 0; j<x; j++)//prints row of mowed lawn
            {
                System.out.print(m[i][j]+" ");
            }
            System.out.println();
        }
    }
//3
    public static void setValues(String[][]l, int y, int x, String s)
    {
        //sets values around and including the specified point.
        /*s s s
          s s s
          s s s*/
        l[y-1][x-1] = s;
        l[y-1][x] = s;
        l[y-1][x+1] = s;
        l[y][x-1] = s;
        l[y][x] = s;
        l[y][x+1] = s;
        l[y+1][x-1] = s;
        l[y+1][x] = s;
        l[y+1][x+1] = s;
    }
//4
    public static boolean checkValue(String[][]l, int y, int x, String s)
    { 
        //in this code, it checks for a "." , "C", or "T" around and including the specified point l[y][x]
        if(l[y-1][x-1].equals(s)) return true;
        if(l[y-1][x].equals(s)) return true;
        if(l[y-1][x+1].equals(s)) return true;
        if(l[y][x-1].equals(s)) return true;
        if(l[y][x].equals(s)) return true;
        if(l[y][x+1].equals(s)) return true;
        if(l[y+1][x-1].equals(s)) return true;
        if(l[y+1][x].equals(s)) return true;
        if(l[y+1][x+1].equals(s)) return true;

        return false;
    }
//5
    public static boolean validMove(String[][]l, int l1, int l2, int y, int x)
    // will be used to check whether the lawnmover's new position will be in bounds and not hitting a tree
    {
        //checks if movement will be in bounds
        if(y-1<0) return false;
        if(y+1==l1) return false;
        if(x-1<0) return false;
        if(x+1==l2) return false;
        
        //checks all 8 positions around given point; if "T" is around, then the move is not valid
        if(checkValue(l,y,x,"T"))
            return false;

        return true; //if all clear
    }
//6
    public static String[][] generateLawn(int l1, int l2, int y, int x) //creates a lawn with randomized tree spots
    {
        String[][]lawn = new String[l1][l2];
        for(int i = 0; i<l1; i++){
            for(int j = 0; j<l2; j++){
                int r = (int)(Math.random()*100) + 1; // between 1-100 #RNG
                if(r<=8) lawn[i][j] = "T"; //basicaly an 8% chance for a tree to spawn at any given coordinate
                else lawn[i][j] = ".";
            }
        }

        //to ensure that the lawnmover's starting point does not overlap with a tree
        setValues(lawn,y,x,".");
        
        return lawn;
    }
//7
    public static void mow(String[][]l, int l1, int l2, int y, int x) //the most important method
    //using recursion, the mow method will call itself with an adjacent coordinate, and mow the uncut grass
    //the method keeps calling itself, scanning for empty grass and cutting it, until it reaches a dead end or all the grass around has been cut
    //after which, the lawnmover jumps back to the previous coordinate and scans around in a different direction for empty grass
    //eventually the method reaches a final dead end, after which the lawn is completely mowed
    {
        setValues(l,y,x,"C"); //cut grass marked by setting values as "C".

        //checkValue metd ensures that the lawnmover will only move if the movement will have more grass around to cut.
        if(validMove(l,l1,l2,y-1,x))  //moves up by 1
        {     
            if(checkValue(l,y-1,x,"."))
                mow(l,l1,l2,y-1,x);
        }
        if(validMove(l,l1,l2,y,x+1)) //moves right by 1
        {
            if(checkValue(l,y,x+1,"."))
                mow(l,l1,l2,y,x+1);
        }
        if(validMove(l,l1,l2,y+1,x)) //moves down by 1
        {
            if(checkValue(l,y+1,x,"."))
                mow(l,l1,l2,y+1,x);
        }
        if(validMove(l,l1,l2,y,x-1)) //moves left by 1
        {
            if(checkValue(l,y,x-1,"."))
                mow(l,l1,l2,y,x-1);
        }
    }
}
