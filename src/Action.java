import java.util.Comparator;

public class Action {
    public double time;
    public double[] velocities;

    public Action(double time, double[] velocities) {
        this.time = time;
        this.velocities = velocities;
    }
    public boolean isBigger(Action a, Action b){
        return a.time>b.time;
    }
}

class ActionComp implements Comparator<Action>{

    @Override
    public int compare(Action o1, Action o2) {
        return (int)(o1.time-o2.time);
    }
}
