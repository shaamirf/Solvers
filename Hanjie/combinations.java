package Hanjie;
import java.util.*;
public class combinations {
    public static void main(String[] args) {
        int[][]rows = {{1,4,1},{1,4,1},{1,2,1},{0,0,2},{0,0,2},{0,0,0},{0,0,4},{0,6,1},{0,0,8},{0,0,10}};
        ArrayList<String[]> combos = getcombinations(rows[4]);
        for(String[]s:combos){
            for(String str:s) System.out.print(str+",");
            System.out.println();
        }
    }
    public static ArrayList<String[]> getcombinations(int[]x){
        ArrayList<String[]> combinations = new ArrayList<String[]>();
        int sum = getSum(x);
        int gaps= getGaps(x);
        int vals = getVals(x);
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
//generates a row, used in method above
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
    public static int getGaps(int[]x){
        int values = 0;
        for(int i:x){
            if(i!=0) values++;
        }
        return values-1;
    }
    public static int getVals(int[]x){
        int values = 0;
        for(int i:x){
            if(i!=0) values++;
        }
        return values;
    }
    public static int getSum(int[]x){
        int sum = 0;
        for(int i:x) sum+=i;
        return sum;
    }
    public static int getMax(int[]x){
        int max = 0;
        for(int i:x) if(i>max) max = i;
        return max;
    }
}
