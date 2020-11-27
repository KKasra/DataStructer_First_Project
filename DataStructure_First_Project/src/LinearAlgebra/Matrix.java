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

    public Matrix product(Matrix B) throws Exception {
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
            
            Node <Pair<Integer, Map <Integer, Double>>> pointerOnColumnsOfB = B.columns.getFirst();
            while(pointerOnColumnsOfB != null){
                int j = pointerOnColumnsOfB.getElement().getFirst();
                double sum = 0;
                
                Node<Pair<Integer, Double>> pointerOnRowsOfA = pointerOnColumnsOfA.getElement().getSecond().getFirst();
                Node<Pair<Integer, Double>> pointerOnRowsOfB = pointerOnColumnsOfB.getElement().getSecond().getFirst();
        
                while (pointerOnRowsOfA != null && pointerOnRowsOfB != null) {
                    while (pointerOnRowsOfA != null && pointerOnRowsOfA.getElement().getFirst() < pointerOnRowsOfB.getElement().getFirst())
                        pointerOnRowsOfA = pointerOnRowsOfA.getNext();
        
                    if (pointerOnRowsOfA == null) 
                        break;
        
                    while (pointerOnRowsOfB != null && pointerOnRowsOfB.getElement().getFirst() < pointerOnRowsOfA.getElement().getFirst())
                        pointerOnRowsOfB = pointerOnRowsOfB.getNext();
                    
                    if (pointerOnRowsOfB == null)
                        break;

                    sum += 
                        pointerOnRowsOfA.getElement().getSecond() * pointerOnRowsOfB.getElement().getSecond();
                    
                    pointerOnRowsOfA = pointerOnRowsOfA.getNext();
                    pointerOnRowsOfB = pointerOnRowsOfB.getNext();    
                    }

                res.columns.get(i).put(j, sum);
                pointerOnColumnsOfB = pointerOnColumnsOfB.getNext();  
            }
            pointerOnColumnsOfA = pointerOnColumnsOfA.getNext();
        }
        return res;
    }
    
    public double getValueOf(int i, int j) throws Exception {
        return columns.get(i).get(j);
    }
    public void setValue(int i, int j, double value) throws Exception {
        if (!columns.containsKey(i))
            columns.put(i, new Map<Integer, Double>());
            columns.get(i).put(j, value);
    }
    @Override
    public String toString() {
        return "Matrix [columns=" + columns + "]";
    }


    
}
