package main.java.solutions;

public class Tuple<X,Y> {
    public final X first;
    public final Y second;

    public Tuple(X first, Y second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "(" + first + "," + second + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DataTuple)){
            return false;
        }

        Tuple<X,Y> other_ = (Tuple<X,Y>) other;
        return other_.first.equals(first) && other_.second.equals(second);
    }

    @Override
    //borrowed from effective java
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + first.hashCode();
        result = prime * result + second.hashCode();
        return result;
    }
}
