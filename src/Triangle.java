import java.util.Arrays;

public class Triangle {

    private Point a;
    private Point b;
    private Point c;

    public Triangle(Point a, Point b, Point c){
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public boolean doesContainPoint(Point p){
        double signPAB = p.subtract(a).crossProduct(b.subtract(a));
        double signPBC = p.subtract(b).crossProduct(c.subtract(b));
        double signPCA = p.subtract(c).crossProduct(a.subtract(c));

        if(hasSameSign(signPAB, signPBC) && hasSameSign(signPBC, signPCA)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean hasPoint(Point p){
        if(this.a == p || this.b == p || this.c == p){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isPointInCircumcircle(Point p){
        // Check if point lies in circumcircle by calculating determinant
        double a11 = a.getX() - p.getX();
        double a21 = b.getX() - p.getX();
        double a31 = c.getX() - p.getX();

        double a12 = a.getY() - p.getY();
        double a22 = b.getY() - p.getY();
        double a32 = c.getY() - p.getY();

        double a13 = (a.getX() - p.getX()) * (a.getX() - p.getX()) + (a.getY() - p.getY()) * (a.getY() - p.getY());
        double a23 = (b.getX() - p.getX()) * (b.getX() - p.getX()) + (b.getY() - p.getY()) * (b.getY() - p.getY());
        double a33 = (c.getX() - p.getX()) * (c.getX() - p.getX()) + (c.getY() - p.getY()) * (c.getY() - p.getY());

        double det = a11 * a22 * a33 + a12 * a23 * a31 + a13 * a21 * a32 - a13 * a22 * a31 - a12 * a21 * a33 - a11 * a23 * a32;

        // Check orientation of the triangle to determine sign of determinant
        if(isClockWise() && det > 0){
            return true;
        }
        if(!isClockWise() && det < 0){
            return true;
        }

        return false;
    }

    public EdgeDistanceMap getNearestEdge(Point point){
        EdgeDistanceMap[] edges = new EdgeDistanceMap[3];

        edges[0] = new EdgeDistanceMap(new Edge(a, b), closestPoint(new Edge(a, b), point).subtract(point).mag());
        edges[1] = new EdgeDistanceMap(new Edge(b, c), closestPoint(new Edge(b, c), point).subtract(point).mag());
        edges[2] = new EdgeDistanceMap(new Edge(c, a), closestPoint(new Edge(c, a), point).subtract(point).mag());

        Arrays.sort(edges);

        return edges[0];
    }

    public Point getVertexNotOnEdge(Edge edge){
        if(this.a != edge.getA() && this.a != edge.getB()){
            return this.a;
        }
        if(this.b != edge.getA() && this.b != edge.getB()){
            return this.b;
        }
        if(this.c != edge.getA() && this.c != edge.getB()){
            return this.c;
        }
        return null;
    }

    // Checks if the triangle has an edge
    public boolean hasEdge(Edge edge){
        if((this.a == edge.getA() || this.b == edge.getA() || this.c == edge.getA()) && (this.a == edge.getB() || this.b == edge.getB() || this.c == edge.getB())){
            return true;
        }
        return false;
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

    public Point getC() {
        return c;
    }

    public void setC(Point c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                '}';
    }

    // private helper methods for class
    private boolean hasSameSign(double d1, double d2){
        if(d1 > 0 && d2 > 0){
            return true;
        }
        if(d1 < 0 && d2 < 0){
            return true;
        }

        return false;
    }

    // Checks if orientation of triangle is clockwise or anticlockwise
    private boolean isClockWise(){
        double a11 = a.getX() - c.getX();
        double a21 = b.getX() - c.getX();

        double a12 = a.getY() - c.getY();
        double a22 = b.getY() - c.getY();

        double det = a11 * a22 - a12 * a21;

        if(det > 0){
            return true;
        }
        else{
            return false;
        }
    }

    private Point closestPoint(Edge e, Point p){
        Point ab = e.getB().subtract(e.getA());

        double distFromA = p.subtract(e.getA()).dot(ab) / ab.dot(ab);

        if(distFromA < 0){
            distFromA = 0;
        }
        else if(distFromA > 1){
            distFromA = 1;
        }
        return e.getA().add(ab.multiplyWithScalar(distFromA));
    }

}
