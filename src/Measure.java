
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Measure implements MouseListener {
    Pair<Point, Point> segment;
    boolean opened = false;

    public Pair<Point, Point> getSegment() {
        return segment;
    }

    public void setCursor(Point point){
        if(!opened || segment == null) return;
        segment.setValue(point);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        opened = true;
        segment = new Pair<>(e.getPoint(), e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        opened = false;
        segment.setValue(e.getPoint());
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}