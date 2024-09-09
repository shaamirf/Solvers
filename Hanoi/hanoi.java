package Hanoi;
import java.util.*;
public class hanoi {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Number: ");
        towerOfHanoi(in.nextInt(), "A", "B", "C");
        in.close();
    }
    public static void towerOfHanoi(int n, String a, String b, String c){
        if(n>0){
            towerOfHanoi(n-1,a,c,b);
            System.out.println("Move disk "+n+" from "+a+" to "+c);
            towerOfHanoi(n-1,b,a,c);
        }
    }
}
