package main.java.solutions;

public class DataTuple<X,Y,Z> {
    public final X first;
    public final Y second;
    public Z third;

    public DataTuple(X first, Y second, Z third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public String toString() {
        return "(" + first + "," + second + "," + third + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DataTuple)){
            return false;
        }

        DataTuple<X,Y,Z> other_ = (DataTuple<X,Y,Z>) other;
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
