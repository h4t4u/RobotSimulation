import java.util.Comparator;

class Action {
    public double time;
    public double[] velocities;

    public Action(double[] velocities, double time) {
        this.time = time;
        this.velocities = velocities;
    }
}

class ActionComp implements Comparator<Action>{

    @Override
    public int compare(Action o1, Action o2) {
        return (int)(o1.time-o2.time);
    }
}
