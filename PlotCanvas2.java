
import java.awt.*;
import java.util.*;

// an interface for all objects to be plotted
interface Plotable {

    public void plot(Graphics g, int xoffset, int yoffset);
}

// Canvas for plotting graph
class PlotCanvas2 extends Canvas {

    // size of plot area
    int width, height;
    // Axes and objects to be plotted
    Axis x_axis, y_axis;
    Vector<Plotable> objects;
    LineSegment red, green, blue;
    ArrayList<LineSegment> redList = new ArrayList();
    ArrayList<LineSegment> greenList = new ArrayList();
    ArrayList<LineSegment> blueList = new ArrayList();
    boolean showMean = false;
    boolean showHistogram = false;

    public PlotCanvas2(int wid, int hgt) {
        width = wid;
        height = hgt;
        x_axis = new Axis(true, width);
        y_axis = new Axis(false, height);
        objects = new Vector<Plotable>();
        
        

    }

    // add objects to plot
    public void addObject(Plotable obj) {
        objects.add(obj);
        repaint();
    }

    public void setMeanColor(Color clr) {
        red = new LineSegment(Color.RED, clr.getRed(), 0, clr.getRed(), 100);
        green = new LineSegment(Color.GREEN, clr.getGreen(), 0, clr.getGreen(), 100);
        blue = new LineSegment(Color.BLUE, clr.getBlue(), 0, clr.getBlue(), 100);
        showMean = true;
        repaint();
    }

    public void clearObjects() {
        objects.clear();
        repaint();
    }

    // redraw the canvas
    public void paint(Graphics g) {
        // draw axis
        int xoffset = (getWidth() - width) / 2;
        int yoffset = (getHeight() + height) / 2;
        x_axis.plot(g, xoffset, yoffset);
        y_axis.plot(g, xoffset, yoffset);

        // plot each object
        Iterator<Plotable> itr = objects.iterator();
        while (itr.hasNext()) {
            itr.next().plot(g, xoffset, yoffset);
        }
        
        if (showHistogram) {
             for (int i = 0; i < redList.size(); i++) {
                LineSegment line = redList.get(i);
                line.draw(g, xoffset, yoffset, getHeight());
            }
            for (int i = 0; i < greenList.size(); i++) {
                LineSegment line = greenList.get(i);
                line.draw(g, xoffset, yoffset, getHeight());
            }

            for (int i = 0; i < blueList.size(); i++) {
                LineSegment line = blueList.get(i);
                line.draw(g, xoffset, yoffset, getHeight());
            }
        }
        
        
        
    }

    public void drawLineSegment(Color clr, int x0, int y0, int x1, int y1) {
        if (clr.equals(Color.RED)) {
            redList.add(new LineSegment(clr, x0, y0, x1, y1));
        }
        if (clr.equals(Color.BLUE)) {
            blueList.add(new LineSegment(clr, x0, y0, x1, y1));
        }
        if (clr.equals(Color.GREEN)) {
            greenList.add(new LineSegment(clr, x0, y0, x1, y1));
        }
        showHistogram = true;
        repaint();
    }

}

// Axis class for plotting X or Y axis
class Axis implements Plotable {

    // type and length of the axis
    boolean xAxis;
    int length, size = 15;

    // Constructor
    public Axis(boolean isX, int len) {
        xAxis = isX;
        length = len;
    }

    // plot axis with arrow
    public void plot(Graphics g, int xoffset, int yoffset) {
        g.setColor(Color.BLACK);
        if (xAxis) {
            g.drawLine(xoffset - size, yoffset, xoffset + length + size, yoffset);
            g.fillArc(xoffset + length, yoffset - size, size * 2, size * 2, 160, 40);
        } else {
            g.drawLine(xoffset, yoffset + size, xoffset, yoffset - length - size);
            g.fillArc(xoffset - size, yoffset - length - size * 2, size * 2, size * 2, 250, 40);
        }
    }
}

// Bar class defines for ploting a vertical line
class VerticalBar implements Plotable {

    // color, location, and length of the line
    Color color;
    int pos, length;

    // Constructor
    public VerticalBar(Color clr, int p, int len) {
        color = clr;
        pos = p;
        length = len;
    }

    public void plot(Graphics g, int xoffset, int yoffset) {
        g.setColor(color);
        g.drawLine(xoffset + pos, yoffset, xoffset + pos, yoffset - length);
    }

}

class LineSegment {
    // location and color of the line segment

    int x0, y0, x1, y1;
    Color color;
    // Constructor

    public LineSegment(Color clr, int x0, int y0, int x1, int y1) {
        color = clr;
        this.x0 = x0;
        this.x1 = x1;
        this.y0 = y0;
        this.y1 = y1;
    }

    public void draw(Graphics g, int xoffset, int yoffset, int height) {
        g.setColor(color);
        g.drawLine(x0 + xoffset, height - y0 - yoffset+200, x1 + xoffset, height - y1 - yoffset+200);
    }
}
