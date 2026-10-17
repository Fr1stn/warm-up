import  java.util.Scanner;
import  java.math.BigInteger;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Scanner scannerS = new Scanner(System.in);
        System.out.println("введите первый мнжетель 2 раза");
        int a = scanner.nextInt();
        String aa = scannerS.nextLine();
        System.out.println("введите второй множитель 2 раза ");
        int b = scanner.nextInt();
        String bb = scannerS.nextLine();
        int c = 0;
        f(a ,b, c);
        System.out.println(fa(a,b));
        BigInteger aabig =new  BigInteger(aa);
        BigInteger bbbig = new BigInteger(bb);
        BigInteger cc = aabig.multiply(bbbig);
        System.out.println(cc);
        System.out.println(faf(a , b));

    }
    static void f (int a , int b, int c){
        if (a >= 0 && b >= 0 ) {
            for (int i = 1; i <= Math.abs(a); i++) {
                c += b;
            }
            System.out.println(c);
        }
        else  if (a < 0 || b <0  ){
            for (int i = 1; i <= Math.abs(a); i++) {
                c -= b;
            }
            System.out.println(c);
        }
        else {
            for (int i = 1; i <= Math.abs(a); i++) {
                c += b;
            }
            System.out.println(c);

        }
    }
    static int fa (int a, int b){
        if ( b == 0 ){
            return 0;
        }
        return a + fa(a , b - 1);

    }
    static  int faf(int a , int b){
        int result = 0 ;
        while (b != 0){
            if ((b & 1) != 0 ){result += a;}
            a <<= 1;
            b >>=1;
        }
        return  result;

    }


}