package base.ch02.example1;

public class Factorial {

    public static void main(String[] args) {
        // int n = Integer.parseInt(args[0]);
        int n = 30;
        System.out.print(n + "! is ");
        int fact = 1;
        while (n > 1) {
            fact *= n--;
        }
        System.out.println(fact);
    }

}
