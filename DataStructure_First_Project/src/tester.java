import java.util.Scanner;

import DataStructures.Map;
import LinearAlgebra.Matrix;

public class tester {
    public static void main(String[] args) throws Exception {
        Matrix a = new Matrix();
        Matrix b = new Matrix();
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int m = scanner.nextInt();
        while (n-- > 0) {
            int i = scanner.nextInt();
            int j = scanner.nextInt();
            double value = scanner.nextDouble();
            a.setValue(i, j, value);

        }

        while (m-- > 0) {
            int i = scanner.nextInt();
            int j = scanner.nextInt();
            double value = scanner.nextDouble();
            b.setValue(i, j, value);
        }

        System.out.println(b.product(a));
    }
    
}
