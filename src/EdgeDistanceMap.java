public class EdgeDistanceMap implements Comparable<EdgeDistanceMap> {

    private Edge edge;
    private double distance;

    public Edge getEdge() {
        return edge;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public EdgeDistanceMap(Edge edge, double distance) {
        this.edge = edge;
        this.distance = distance;
    }

    @Override
    public int compareTo(EdgeDistanceMap e){
        return Double.compare(this.distance, e.distance);
    }
}
