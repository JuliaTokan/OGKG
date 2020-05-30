// Represents an edge formed by two points
public class Edge {

    private Point a;
    private Point b;

    public Edge(Point a, Point b){
        this.a = a;
        this.b = b;
    }

    public Point getA() {
        return a;
    }

    public void setA(Point a) {
        this.a = a;
    }

    public Point getB() {
        return b;
    }

    public void setB(Point b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }
}
