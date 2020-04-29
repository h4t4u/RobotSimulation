import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class PaintFrame extends JFrame {
    private int WIDTH = getWidth(), HEIGHT = getHeight();
    private ArrayList<Pair<Point, Point>> qu = new ArrayList<>();
    Color bgColor = Color.decode("#1C2639");
    Color botColor = Color.BLUE;
    Color bordersColor = Color.decode("#D9E6F2");
    private Robot robot;
    private Measure measure = null;

    public PaintFrame(Robot robot) throws HeadlessException {
        this.robot = robot;
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        super.setBounds(x, y, width, height);
    }

    @Override
    public void paint(Graphics g) {
        Image img = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        Graphics imgGraphics = img.getGraphics();
        int[] xs =new int[3], ys = new int[3];
        for (int i = 0; i < 3; i++) {
            Point point = translateXY(robot.getXY(i));
            xs[i] = point.x; ys[i] = point.y;
        }
        imgGraphics.setColor(bgColor);
        imgGraphics.fillRect(0,0,WIDTH,HEIGHT);
        imgGraphics.setColor(bordersColor);
        imgGraphics.drawLine(0,HEIGHT/2,WIDTH,HEIGHT/2);
        imgGraphics.drawLine(WIDTH/2,0,WIDTH/2,HEIGHT);

        imgGraphics.drawString("" + HEIGHT/4,WIDTH/2, HEIGHT/4);
        imgGraphics.drawString("" + WIDTH/4,WIDTH*3/4, HEIGHT/2);
        imgGraphics.drawString("-" + HEIGHT/4,WIDTH/2, HEIGHT*3/4);
        imgGraphics.drawString("-" + WIDTH/4,WIDTH/4, HEIGHT/2);

        Iterator<Pair<Point, Point>> iter = qu.iterator();

        while (iter.hasNext()){
            Pair<Point, Point> seg = iter.next();
            Point point1 = translateXY(seg.getKey());
            Point point2 = translateXY(seg.getValue());
            imgGraphics.drawLine(point1.x, point1.y, point2.x, point2.y);
        }

        imgGraphics.setColor(botColor);
        imgGraphics.fillPolygon(xs, ys,3);
        imgGraphics.setColor(bordersColor);
        imgGraphics.drawPolygon(xs, ys,3);

        for (int i = 0; i < 3; i++) {
            imgGraphics.setColor(bgColor);
            imgGraphics.fillRect(xs[i], ys[i], 10,15);
            imgGraphics.setColor(bordersColor);
            imgGraphics.drawString(""+i, xs[i] + 2, ys[i] + 12);
            imgGraphics.drawRect(xs[i], ys[i], 10,15);
        }

        Pair<Point, Point> points = measure.getSegment();
        if(points != null) {
            int x1 = points.getKey().x, y1 = points.getKey().y;
            int x2 = points.getValue().x, y2 = points.getValue().y;

            imgGraphics.drawLine(x1,y1, x2, y2);
            double length = Point.distance(x1, y1, x2, y2);
            imgGraphics.setColor(bgColor);
            imgGraphics.fillRect((x1+x2)/2, (y1+y2)/2, 10*Double.toString(length).length(), 15);
            imgGraphics.setColor(bordersColor);
            imgGraphics.drawRect((x1+x2)/2, (y1+y2)/2, 10*Double.toString(length).length(), 15);
            imgGraphics.drawString(length+"", (x1+x2)/2 + 2, (y1+y2)/2 + 12);
        }

        imgGraphics.setFont(new Font("rubik", Font.PLAIN, 20));
        imgGraphics.drawString("Time: "+(int)Main.getTime()+"ms", 10,30);
        imgGraphics.drawString("Mode: "+Main.getMode(), 10,50);
        imgGraphics.drawString("x:"+robot.getXY(-1).x+"\t y:"+robot.getXY(-1).y*-1+"\t alpha:"+
                (int)robot.getAlpha(), 10,70);


        g.drawImage(img,0,0,null);
    }

    public void setMeasure(Measure measure) {
        this.addMouseListener(measure);
        this.measure = measure;
    }

    public void addSegment(Pair<Point, Point> segment){
        qu.add(0,segment);
        if(qu.size()>=100){
            qu.remove(qu.size()-1);
        }
    }
    private Point translateXY(Point point){
        return new Point(point.x+WIDTH/2, point.y+HEIGHT/2);
    }
}
/*
1 -1 0 100
0 3 -3 400
6 5 4 500
-4 -5 -6 800
10 10 10 1000
0 0 0 0
 */
