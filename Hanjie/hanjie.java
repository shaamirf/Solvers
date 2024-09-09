package Hanjie;
import java.util.*;
public class hanjie{
    public static void main(String[] args) {
        
        int[][]rows = new int[10][3];
        int[][]columns = new int[10][3];
        
        Scanner in = new Scanner(System.in);
// program first takes in column input values in the form of "a b c"
        System.out.println("Column Inputs:");
        for(int i=0;i<10;i++){
            for(int j=0;j<3;j++){
                columns[i][j] = in.nextInt();
            }
            in.nextLine();
// program takes in row input values in the form of "a b c"
        }
        System.out.println("Row Inputs:");
        for(int i=0;i<10;i++){
            for(int j=0;j<3;j++){
                rows[i][j] = in.nextInt();
            }
            in.nextLine();
        }
        in.close();
//creates the 10 by 10 board
        String[][]board = new String[10][10];
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                board[i][j]=".";
            }
        }

//runs the hanjie method with all created arrays and index values of 0
//hanjie is a recursive method that applies dfs
//searches for the one combination of rows that complies all constraints
        ArrayList<String[]>s = getcombinations(rows[0]);
        boolean run = hanjie(rows,columns,board,0,0,s);
        printboard(board);
        
    }
//prints the board
    public static void printboard(String[][]b){
        for(int i = 0;i<10;i++){
            System.out.println("+---+---+---+---+---+---+---+---+---+---+");
            for(int j = 0;j<10;j++){
                System.out.print("| "+b[i][j]+" ");
            }
            System.out.println("|");
        }
        System.out.println("+---+---+---+---+---+---+---+---+---+---+");
    }
    public static void setRow(String[]s,String[][]b,int row){
        for(int i=0;i<10;i++){
            b[row][i] = s[i];
        }
    }
    public static void clearRow(String[][]b,int row){
        for(int i=0;i<10;i++){
            b[row][i] = ".";
        }
    }
//returns total number of gaps in row/col
    public static int getGaps(int[]x){
        int values = 0;
        for(int i:x){
            if(i!=0) values++;
        }
        return values-1;
    }
//returns total number of nonzero values in row/col
    public static int getVals(int[]x){
        int values = 0;
        for(int i:x){
            if(i!=0) values++;
        }
        return values;
    }
//returns sum of values in row/col
    public static int getSum(int[]x){
        int sum = 0;
        for(int i:x) sum+=i;
        return sum;
    }
//returns largest group size in row/col
    public static int getMax(int[]x){
        int max = 0;
        for(int i:x) if(i>max) max = i;
        return max;
    }
//gets all possible combinations for a given row, provided the constraints
    public static ArrayList<String[]> getcombinations(int[]x){
        ArrayList<String[]> combinations = new ArrayList<String[]>();
        int sum = getSum(x);
        int gaps= getGaps(x);
        int vals = getVals(x);
    //different loops based on number of non zero values in the row constraints
        if(vals==3){
            for(int i=0;i<10-(sum+gaps)+1;i++){
                for(int j=i+x[0]+1;j<10-(sum+gaps)+x[0]+2;j++){
                    for(int k=j+x[1]+1;k<10-x[2]+1;k++){
                        combinations.add(makeList(i,j,k,x[0],x[1],x[2]));
                    }
                }
            }
        }
        if(vals==2){
            for(int i=0;i<10-(sum+gaps)+1;i++){
                for(int j=i+x[1]+1;j<10-x[2]+1;j++){
                    combinations.add(makeList(i,j,0,x[1],x[2],0));
                }
            }
        }
        if(vals==1){
            for(int i=0;i<10-x[2]+1;i++){
                combinations.add(makeList(i,0,0,x[2],0,0));
            }
        }
        return combinations;
    }
//generates a list of 10 values, used in method above
//parameters determine which values are labeled "x" and which are left as "."
    public static String[] makeList(int i, int j, int k, int a, int b, int c){
        String[]s = new String[10];
        for(int y = 0;y<10;y++)
            s[y]=".";
        for(int y = 0;y<a;y++){
            s[i+y]="x";
        }
        for(int y = 0;y<b;y++){
            s[j+y]="x";
        }
        for(int y = 0;y<c;y++){
            s[k+y]="x";
        }
        return s;
    }
//checks if the board complies with column constraints
    public static boolean validBoard(String[][]b, int[][]c){
    //first for loop to check all 10 columns
        for(int i=0;i<10;i++){
            int streak = 0;
            int gaps = -1;
            if(b[9][i].equals("x")) gaps = 0;
            int sum = 0;
            int max = getMax(c[i]);
            int actualGaps = getGaps(c[i]);
            int actualSum = getSum(c[i]);
            String[]col = new String[10];
    //second for loop to check all the 10 values in the column
    //3 conditions to return true
    //a. counted number of gaps does not exceed the number its supposed to be for that column
    //b. total number of counted "x"s does not exceed the number its supposed to be for that column
    //c. largest chunk should not be lartger than the largest number in the column constraints
            for(int j=0;j<10;j++){
                col[j] = b[j][i];
                if(col[j].equals("x")){
                    streak++;
                    if(streak>max) return false;
                    sum++;
                    if(sum>actualSum) return false;
                }
                if(col[j].equals(".")){
                    if(streak>0) gaps++;
                    if(gaps>actualGaps) return false;
                    streak = 0;
                }
            }
        }
        return true;
    }
//main backtracking code
    //logic behind the code
    //1. recursive loop starts at first row
    //2. for each row, test all possible combinations
    //2. if a combination satisfies the column constraints, then move to the next row
    //3. if combination does not satisfy the constraints, check the next combination
    //4. if out of combinations, go back to previous row
    //5. if on row 9 and combination satisfies the constraints, return true and exit the recursive loop
    //6. if no combinations are given for a row (0 0 0), skip the row
    public static boolean hanjie(int[][]r,int[][]c,String[][]b,int row, int n, ArrayList<String[]>combos){
        boolean works = true;
        if(combos.size()>0){
            if(row==9){
                setRow(combos.get(n), b, row);
                if(validBoard(b, c)) return true;
                else{
                    if(n+1<combos.size()) return hanjie(r, c, b, row, n+1, combos);
                    else{
                        clearRow(b, row);
                        return false;
                    }
                }
            }
            else{
                setRow(combos.get(n), b, row);
                if(validBoard(b, c)) works = hanjie(r, c, b, row+1, 0, getcombinations(r[row+1]));
                else works = false;
                if(!works){
                    if(n+1<combos.size()) works = hanjie(r, c, b, row, n+1, combos);
                    else{
                        clearRow(b, row);
                        works = false;
                    }
                }
                if(!works) return false;
            }  
        }
        else{
            if(row==9 && validBoard(b, c)) return true;
            else if(validBoard(b, c)){
                works = hanjie(r, c, b, row+1, 0, getcombinations(r[row+1]));
            }
            else works = false;
            if(!works) return false;
        }
        return true;
    }
}