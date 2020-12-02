package LinearAlgebra;

import DataStructures.List;
import DataStructures.Map;
import DataStructures.Node;
import DataStructures.Pair;

public class Matrix {
    private Map<Integer, Map<Integer, Double>> columns;

    public Matrix() {
        columns = new Map<Integer, Map<Integer, Double>>();
    }
    public Matrix(Map<Integer, Map<Integer, Double>> columns){
        this.columns = columns;
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

        // this.iterateOnMatrix((a_i, a_j, Aij) -> {
        // B.iterateOnMatrix((b_i, b_j, Bij) -> {
        // if (a_j == b_i)
        // res.setValue(a_i, b_j, Aij * Bij + res.getValueOf(a_i, b_j));
        // });
        // });
        res = optimizedProduct(B);
        return res;
    }

    public void iterateOnMatrix(Preformer preformer) {
        columns.iterateOnMap(node -> {
            node.getElement().getSecond().iterateOnMap(node1 -> {
                preformer.preform(node.getElement().getFirst(), node1.getElement().getFirst(),
                        node1.getElement().getSecond());
            });
        });

    }

    public static interface Preformer {
        void preform(int i, int j, double value);
    }

    public Matrix optimizedProduct(Matrix B) {
        Matrix res = new Matrix();
        Node<Pair<Integer, Map<Integer, Double>>> ACP = this.columns.getFirst();
        if (ACP == null)
            return res;
        Node<Pair<Integer, Map<Integer, Double>>> RCP = new Node<Pair<Integer, Map<Integer, Double>>>(
                new Pair<Integer, Map<Integer, Double>>(ACP.getElement().getFirst(), new Map<Integer, Double>()));

        while (ACP != null) {
            Node<Pair<Integer, Double>> AIJ = ACP.getElement().getSecond().getFirst();
            Node<Pair<Integer, Map<Integer, Double>>> BCP = B.columns.getFirst();
            while (AIJ != null && BCP != null) {
                // getting two pointers on same place
                while (AIJ != null && AIJ.getElement().getFirst().compareTo(BCP.getElement().getFirst()) < 0)
                    AIJ = AIJ.getNext();
                if (AIJ == null)
                    break;
                while (BCP != null && BCP.getElement().getFirst().compareTo(AIJ.getElement().getFirst()) < 0)
                    BCP = BCP.getNext();
                if (BCP == null)
                    break;
                // ---------------

                Node<Pair<Integer, Double>> BIJ = BCP.getElement().getSecond().getFirst();
                while (BIJ != null) {
                    int i = ACP.getElement().getFirst();
                    int j = BIJ.getElement().getFirst();
                    res.setValue(i, j,
                            res.getValueOf(i, j) + AIJ.getElement().getSecond() * BIJ.getElement().getSecond());
                    BIJ = BIJ.getNext();
                }

                // moving further
                AIJ = AIJ.getNext();
                BCP = BCP.getNext();
            }

            ACP = ACP.getNext();
            if (ACP != null)
                RCP.setNext(new Node<Pair<Integer, Map<Integer, Double>>>(new Pair<Integer, Map<Integer, Double>>(
                        ACP.getElement().getFirst(), new Map<Integer, Double>())));
            RCP = RCP.getNext();
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
