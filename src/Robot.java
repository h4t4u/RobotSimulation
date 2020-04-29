import java.awt.*;
import java.util.zip.DataFormatException;

public class Robot {
    private double x = 0,y = 0;
    private double alpha = 0;
    private static final int RADIUS = 52;
    private double[] volumes = new double[3];

    public void doStep(double dt){
        //System.out.println(x + " " + y + " " + alpha);
        double firstAlpha = alpha;
        double secondAlpha = alpha + 2*Math.PI/3;
        double thirdAlpha = alpha - 2*Math.PI/3;

        double firstDelta = dt*volumes[0];
        double secondDelta = dt*volumes[1];
        double thirdDelta = dt*volumes[2];

        x += (firstDelta*2/3)*Math.cos(firstAlpha); y -= (firstDelta*2/3)*Math.sin(firstAlpha);
        x += (secondDelta*2/3)*Math.cos(secondAlpha); y -= (secondDelta*2/3)*Math.sin(secondAlpha);
        x += (thirdDelta*2/3)*Math.cos(thirdAlpha); y -= (thirdDelta*2/3)*Math.sin(thirdAlpha);

        for (int i = 0; i < 3; i++) {
            alpha += (dt*volumes[i])/(3*RADIUS);
        }
        refreshAlpha();
    }
    public Point getXY(int num){
        if(num == -1){
            return  new Point((int)x, (int)-y);
        }
        if(num>=3 || num<0) throw new IllegalArgumentException("Number must be between 0 and 2");
        double alphaPoint = alpha + num * Math.PI*2/3;
        double xp = x + RADIUS * Math.sin(alphaPoint);
        double yp = y + RADIUS * Math.cos(alphaPoint);
        return new Point((int)xp, (int)-yp);
    }

    public void refreshAlpha(){
        if(alpha>2*Math.PI)alpha-=2*Math.PI;
        if(alpha<=0)alpha+=2*Math.PI;
    }

    public void setVolumes(double[] a){
        if(a.length!=3) throw new IllegalArgumentException("a must be 3 length");
        volumes = a;
    }

    public double getAlpha() {
        return alpha;
    }
    
    public int getRadius(){
    return RADIUS;
    }
}
