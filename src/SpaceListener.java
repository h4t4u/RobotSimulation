import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SpaceListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == ' '){
            Main.changePlaying();
        }
        if(e.getKeyChar() == 27){
            Main.exit();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
