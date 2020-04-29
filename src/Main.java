
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.zip.DataFormatException;

public class Main {
    private static boolean playing = true;
    private final static double DT = 0.001;
    private final static int STEPS = 10000;
    private static double time = 0, lastActionTime = 0;
    private static int mode = 0;
    private static PaintFrame frame;
    private static ArrayList<Action> actions = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Robot r = new Robot();
        Measure measure = new Measure();
        initializeFrame(r, measure);

        double[] a = {0, 0, 0};
        actions.add(new Action(a, 0));
        r.setVelocities(a);

       Point prev = new Point(0,0);

        int i = 0;
        while (true) {
            //Getting command from terminal
            if(br.ready()) try {
                Action ac = createAction(br.readLine());
                actions.add(ac);
            }catch (DataFormatException e){
                System.err.println(e.getMessage());
            }

            //Checking if it's time for new action
            if(actions.size()>=2  && time>=actions.get(1).time){
                r.setVelocities(actions.get(1).velocities);
                actions.remove(0);
                mode++;
            }

            //Repainting
            if(i%STEPS==0) {
                Point curse = MouseInfo.getPointerInfo().getLocation();
                Point translatedCurse = new Point(curse.x - frame.getX(), curse.y - frame.getY());
                measure.setCursor(translatedCurse);
                frame.paint(frame.getGraphics());
            }

            //Do step & refresh tracks in case moving
            if(isPlaying()){
                time += DT;
                r.doStep(DT);

                if(i%(STEPS*10) == 0){
                    Pair<Point, Point> p = new Pair<>(r.getXY(-1), prev);
                    frame.addSegment(p);
                    i=0;
                }
                else{
                    if(i%(STEPS*5) == 0){
                        prev = r.getXY(-1);
            }}}

            i++;
        }
    }
    static double getTime(){
        return time;
    }

    static int getMode() {
        return mode;
    }

    private static boolean isPlaying(){
        return playing && actions.size()>=2;
    }

    static void changePlaying(){
        playing = !playing;
    }

    static void exit(){
        System.exit(0);
    }

    private static Action createAction(String s) throws DataFormatException {
        String[] arguments = s.split(" ");
        double time = Double.parseDouble(arguments[3]);
        if(time<lastActionTime){
            throw new DataFormatException("Commands must be entered with ascending time");
        }

        double[] velocities = new double[3];
        for (int i = 0; i < 3; i++) {
            velocities[i] = Double.parseDouble(arguments[i])/1000;
        }
        return new Action(velocities, time);
    }

    private static void initializeFrame(Robot r, Measure measure){
        frame = new PaintFrame(r);
        frame.setBounds(10,10,1000,1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setMeasure(measure);
        frame.addKeyListener(new SpaceListener());
    }
}
