
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

public class Main {
    private static boolean playing = true;
    private final static double DT = 0.001;
    private static double time = 0;
    private static int mode = 0;
    private static PaintFrame frame;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Action> actions = new ArrayList<Action>();
        while(true){
            String[] arguments = br.readLine().split(" ");
            double time = Double.parseDouble(arguments[3]);

            double[] volumes = new double[3];
            for (int i = 0; i < 3; i++) {
                volumes[i] = Double.parseDouble(arguments[i])/1000;
            }

            if(time == 0 && volumes[0] == 0 && volumes[1] == 0 && volumes[2] == 0) break;

            Action ac = new Action(time, volumes);
            actions.add(ac);
        }
        Collections.sort(actions, new ActionComp());
        Robot r = new Robot();

        Measure measure = new Measure();
        frame = new PaintFrame(r);
        frame.setBounds(10,10,1000,1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setMeasure(measure);
        frame.addKeyListener(new SpaceListener());


        double[] a = {0, 0, 0};
        r.setVolumes(a);

       Point prev = new Point(0,0);

        for (int i = 0; ; i++) {
            if(playing) {
                time += DT;
                r.doStep(DT);
            }
            else i--;
            if(i%10000==0) {
                Point curse =MouseInfo.getPointerInfo().getLocation();
                Point translatedCurse = new Point(curse.x - frame.getX(), curse.y - frame.getY());
                measure.setCursor(translatedCurse);
                frame.paint(frame.getGraphics());
                if(actions.size()>0 && time>=actions.get(0).time){
                    r.setVolumes(actions.get(0).velocities);
                    actions.remove(0);
                    mode++;
                }

            }
            if(i%100000 == 0){
                Pair<Point, Point> p = new Pair<>(r.getXY(-1), prev);
                frame.addSegment(p);
            }else{
                if(i%50000 == 0){
                prev = r.getXY(-1);
            }
            }
        }
    }
    static double getTime(){
        return time;
    }

    public static int getMode() {
        return mode;
    }

    public static void changePlaying(){
        playing = !playing;
    }

    public static void exit(){
        System.exit(0);
    }
}
