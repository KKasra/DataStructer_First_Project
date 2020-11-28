package LinearAlgebra;

import DataStructures.Map;
import DataStructures.Node;
import DataStructures.Pair;

public class Matrix {
    private Map<Integer, Map<Integer, Double>> columns;

    public Matrix() {
        columns = new Map<Integer, Map<Integer, Double>>();
    }

    public Matrix getTranspose() throws Exception {
        Matrix res = new Matrix();

        Node<Pair<Integer, Map<Integer, Double>>> pointer = columns.getFirst();
        while (pointer != null) {
            Map<Integer, Double> column = pointer.getElement().getSecond();

            int i = pointer.getElement().getFirst();

            Node<Pair<Integer, Double>> pointer1 = column.getFirst();
            while (pointer1 != null) {
                int j = pointer1.getElement().getFirst();
                double value = pointer1.getElement().getSecond();

                if (!res.columns.containsKey(j))
                    res.columns.put(j, new Map<Integer, Double>());
                if (!res.columns.get(j).containsKey(i))
                    res.columns.get(j).put(i, 0D);

                res.columns.get(j).put(i, res.columns.get(j).get(i) + value);

                pointer1 = pointer1.getNext();
            }
            pointer = pointer.getNext();
        }

        return res;
    }

    public Matrix product(Matrix B) {
       Matrix res = new Matrix();

       this.iterateOnMatrix((a_i,a_j,Aij) -> {
           B.iterateOnMatrix((b_i, b_j, Bij) ->{
               if (a_j == b_i)
                    res.setValue(a_i, b_j, Aij * Bij + res.getValueOf(a_i, b_j));
           });
       });
    //    Node<Pair<Integer, Map<Integer, Double>>> ACP = columns.getFirst();
    //    while(ACP != null) {

    //         int a_i = ACP.getElement().getFirst();
    //         Node<Pair<Integer, Double>> ARP = ACP.getElement().getSecond().getFirst();
    //         while (ARP != null) {
    //             int a_j = ARP.getElement().getFirst();
                
                

    //             ARP = ARP.getNext();
    //         }

    //        ACP = ACP.getNext();
    //    }

       return res;
    }

    public void iterateOnMatrix(Preformer preformer) {
        Node<Pair<Integer, Map<Integer, Double>>> ACP = columns.getFirst();
        while (ACP != null) {

            int i = ACP.getElement().getFirst();
            Node<Pair<Integer, Double>> ARP = ACP.getElement().getSecond().getFirst();
            while (ARP != null) {
                int j = ARP.getElement().getFirst();

                preformer.preform(i, j, ARP.getElement().getSecond());

                ARP = ARP.getNext();
            }

            ACP = ACP.getNext();
        }

    }

    public static interface Preformer {
        void preform(int i, int j, double value);
    }

    public Matrix optimizedProduct(Matrix B) {
        try {
            B = B.getTranspose();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Matrix res = new Matrix();

        Node<Pair<Integer, Map<Integer, Double>>> pointerOnColumnsOfA = columns.getFirst();
        while (pointerOnColumnsOfA != null) {
            int i = pointerOnColumnsOfA.getElement().getFirst();
            res.columns.put(i, new Map<Integer, Double>());

            Node<Pair<Integer, Map<Integer, Double>>> pointerOnColumnsOfB = B.columns.getFirst();
            while (pointerOnColumnsOfB != null) {
                int j = pointerOnColumnsOfB.getElement().getFirst();
                double sum = 0;

                Node<Pair<Integer, Double>> pointerOnRowsOfA = pointerOnColumnsOfA.getElement().getSecond().getFirst();
                Node<Pair<Integer, Double>> pointerOnRowsOfB = pointerOnColumnsOfB.getElement().getSecond().getFirst();

                while (pointerOnRowsOfA != null && pointerOnRowsOfB != null) {
                    while (pointerOnRowsOfA != null
                            && pointerOnRowsOfA.getElement().getFirst() < pointerOnRowsOfB.getElement().getFirst())
                        pointerOnRowsOfA = pointerOnRowsOfA.getNext();

                    if (pointerOnRowsOfA == null)
                        break;

                    while (pointerOnRowsOfB != null
                            && pointerOnRowsOfB.getElement().getFirst() < pointerOnRowsOfA.getElement().getFirst())
                        pointerOnRowsOfB = pointerOnRowsOfB.getNext();

                    if (pointerOnRowsOfB == null)
                        break;

                    sum += pointerOnRowsOfA.getElement().getSecond() * pointerOnRowsOfB.getElement().getSecond();

                    pointerOnRowsOfA = pointerOnRowsOfA.getNext();
                    pointerOnRowsOfB = pointerOnRowsOfB.getNext();
                }
                if (sum > 0)
                    try {
                        res.columns.get(i).put(j, sum);
                    } catch (Exception e) {
                    }
                pointerOnColumnsOfB = pointerOnColumnsOfB.getNext();
            }
            pointerOnColumnsOfA = pointerOnColumnsOfA.getNext();
        }
        return res;
    }

    public double getValueOf(int i, int j) {
        if (!columns.containsKey(i))
            return 0;
        try {
            if (!columns.get(i).containsKey(j))
                return 0;
            return columns.get(i).get(j);

        } catch (Exception e) {
            return 0;
        }
    }

    public void setValue(int i, int j, double value) {
        if (!columns.containsKey(i))
            columns.put(i, new Map<Integer, Double>());
        try {
            columns.get(i).put(j, value);
        } catch (Exception e) {
        }
    }

    @Override
    public String toString() {
        return "Matrix [columns=" + columns + "]";
    }

}
