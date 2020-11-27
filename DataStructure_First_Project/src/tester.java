import java.util.Scanner;

import DataStructures.Map;

public class tester {
    public static void main(String[] args) {
        Map<Integer, Integer> map = new Map<Integer, Integer>();
        Scanner scanner = new Scanner(System.in);
        int n = 10;
        while(n-- > 0) {
            map.put(scanner.nextInt(), scanner.nextInt());
            map.iterateOnMap(node -> {
                System.out.println(">" + node.getElement().getFirst() + " " + node.getElement().getSecond());
            });
        }
        scanner.close();
    }
    
}
