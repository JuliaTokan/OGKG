import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class SimplePaint extends JApplet {

    public static void main(String[] args) {
        JFrame window = new JFrame("Triangulation");
        SimplePaintPanel content = new SimplePaintPanel();
        window.setContentPane(content);
        window.setSize(600,480);
        window.setLocation(100,100);
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.setVisible(true);
    }

    public void init() {
        setContentPane( new SimplePaintPanel() );
    }

    public static class SimplePaintPanel extends JPanel
            implements MouseListener, MouseMotionListener {

        private Graphics graphicsForDrawing;
        private int numPoint = 0;

        List<Point> points = new ArrayList<>();
        Triangulation delaunayTriangulator = new Triangulation(points);

        SimplePaintPanel() {
            setBackground(Color.WHITE);
            addMouseListener(this);
            addMouseMotionListener(this);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);  // Fill with background color (white).
        }

        public void mousePressed(MouseEvent evt) {
            repaint();
            graphicsForDrawing = getGraphics();
        }

        public void mouseReleased(MouseEvent evt) {
            int coord_x = evt.getX();   // x-coordinate of mouse.
            int coord_y = evt.getY();   // y-coordinate of mouse.
            Point p = new Point(coord_x, coord_y);
            points.add(new Point(p.getX(), p.getY()));
            if(points.size() >= 3) {
                delaunayTriangulator.triangulate();
            }
            List<Triangle> ts = delaunayTriangulator.getTriangles();

            ((Graphics2D) graphicsForDrawing).setStroke(new BasicStroke(3));
            graphicsForDrawing.setColor(Color.BLUE);
            for (Triangle t : ts) {
                graphicsForDrawing.drawLine(t.getA().getX().intValue(), t.getA().getY().intValue(), t.getB().getX().intValue(), t.getB().getY().intValue());
                graphicsForDrawing.drawLine(t.getA().getX().intValue(), t.getA().getY().intValue(), t.getC().getX().intValue(), t.getC().getY().intValue());
                graphicsForDrawing.drawLine(t.getC().getX().intValue(), t.getC().getY().intValue(), t.getB().getX().intValue(), t.getB().getY().intValue());
            }
            graphicsForDrawing.setColor(Color.RED);
            for (int i = 0; i < points.size(); i++) {
                graphicsForDrawing.drawOval(points.get(i).getX().intValue() - 1, points.get(i).getY().intValue() - 1, 3, 3);
            }
        }

        public void mouseDragged(MouseEvent evt) { }
        public void mouseEntered(MouseEvent evt) { }
        public void mouseExited(MouseEvent evt) { }
        public void mouseClicked(MouseEvent evt) { }
        public void mouseMoved(MouseEvent evt) { }
    }

}
