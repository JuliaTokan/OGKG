import java.util.List;

public class Triangulation {

    private List<Point> inputPoints;
    private TriangleSet outputTriangles;

    public Triangulation(List<Point> inputPoints){
        this.inputPoints = inputPoints;
        this.outputTriangles = new TriangleSet();
    }

    public void triangulate(){

        outputTriangles = new TriangleSet();

        double maxOfAllCoordinates = 0;

        for(Point p : inputPoints){
            maxOfAllCoordinates = Math.max(p.getX(), Math.max(p.getY(), maxOfAllCoordinates));
        }

        maxOfAllCoordinates *= 20;

        Point p1 = new Point(0, 1 * maxOfAllCoordinates);
        Point p2 = new Point(1 * maxOfAllCoordinates, 0);
        Point p3 = new Point(-1 * maxOfAllCoordinates, -1 * maxOfAllCoordinates);

        Triangle superTriangle = new Triangle(p1, p2, p3);

        outputTriangles.add(superTriangle);


        for(int i=0; i<inputPoints.size(); i++){
            Triangle triangle = outputTriangles.findTriangleContainingPoint(inputPoints.get(i));

            if(triangle == null){
                Edge edge = outputTriangles.getNearestEdge(inputPoints.get(i));

                Triangle first = outputTriangles.findTriangleWithEdge(edge);
                Triangle second = outputTriangles.findTriangleSharingEdge(first, edge);

                Point externalPointOfFirstTriangle = first.getVertexNotOnEdge(edge);
                Point externalPointOfSecondTriangle = second.getVertexNotOnEdge(edge);

                outputTriangles.remove(first);
                outputTriangles.remove(second);

                Triangle t1 = new Triangle(edge.getA(), externalPointOfFirstTriangle, inputPoints.get(i));
                Triangle t2 = new Triangle(edge.getB(), externalPointOfFirstTriangle, inputPoints.get(i));
                Triangle t3 = new Triangle(edge.getA(), externalPointOfSecondTriangle, inputPoints.get(i));
                Triangle t4 = new Triangle(edge.getB(), externalPointOfSecondTriangle, inputPoints.get(i));

                outputTriangles.add(t1);
                outputTriangles.add(t2);
                outputTriangles.add(t3);
                outputTriangles.add(t4);

                performFlip(t1, new Edge(edge.getA(), externalPointOfFirstTriangle), inputPoints.get(i));
                performFlip(t2, new Edge(edge.getB(), externalPointOfFirstTriangle), inputPoints.get(i));
                performFlip(t3, new Edge(edge.getA(), externalPointOfSecondTriangle), inputPoints.get(i));
                performFlip(t4, new Edge(edge.getB(), externalPointOfSecondTriangle), inputPoints.get(i));
            }
            else{
                Point a = triangle.getA();
                Point b = triangle.getB();
                Point c = triangle.getC();

                outputTriangles.remove(triangle);

                Triangle first = new Triangle(a, b, inputPoints.get(i));
                Triangle second = new Triangle(b, c, inputPoints.get(i));
                Triangle third = new Triangle(c, a, inputPoints.get(i));

                outputTriangles.add(first);
                outputTriangles.add(second);
                outputTriangles.add(third);

                performFlip(first, new Edge(a, b), inputPoints.get(i));
                performFlip(second, new Edge(b, c), inputPoints.get(i));
                performFlip(third, new Edge(c, a), inputPoints.get(i));
            }
        }

        outputTriangles.removeAllTrianglesWithPoint(p1);
        outputTriangles.removeAllTrianglesWithPoint(p2);
        outputTriangles.removeAllTrianglesWithPoint(p3);

    }

    public void performFlip(Triangle triangle, Edge edge, Point point){

        Triangle triangleSharingEdge = outputTriangles.findTriangleSharingEdge(triangle, edge);

        if(triangleSharingEdge == null) return;

        if(triangleSharingEdge.isPointInCircumcircle(point)){
            outputTriangles.remove(triangle);
            outputTriangles.remove(triangleSharingEdge);

            Point commonVertexOfNewTriangles = triangleSharingEdge.getVertexNotOnEdge(edge);

            Triangle firstTriangle = new Triangle(commonVertexOfNewTriangles, edge.getA(), point);
            Triangle secondTriangle = new Triangle(commonVertexOfNewTriangles, edge.getB(), point);

            outputTriangles.add(firstTriangle);
            outputTriangles.add(secondTriangle);

            performFlip(firstTriangle, new Edge(commonVertexOfNewTriangles, edge.getA()), point);
            performFlip(secondTriangle, new Edge(commonVertexOfNewTriangles, edge.getB()), point);
        }

    }

    public List<Triangle> getTriangles(){
        return this.outputTriangles.getTriangleList();
    }
}
