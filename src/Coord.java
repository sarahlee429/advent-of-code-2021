public class Coord<X, Y> {
    public final X row;
    public final Y column;
    public Coord(X row, Y y) {
        this.row = row;
        this.column = y;
    }

    @Override
    public String toString() {
        return "(" + row + "," + column + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Coord)){
            return false;
        }

        Coord<X,Y> other_ = (Coord<X,Y>) other;
        return other_.row.equals(this.row) && other_.column.equals(this.column);
    }

    @Override
    //borrowed
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((row == null) ? 0 : row.hashCode());
        result = prime * result + ((column == null) ? 0 : column.hashCode());
        return result;
    }
}