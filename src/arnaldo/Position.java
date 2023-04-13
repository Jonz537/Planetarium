package arnaldo;

public class Position {
    private final double x;
    private final double y;

    /**
     *
     * @param x coordinata x
     * @param y coordinata y
     */
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }



    public boolean isSamePosition(Position position) {
        return (this.getX() == position.getX() && this.getY() == position.getY());
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
