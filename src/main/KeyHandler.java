package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }// not used

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // get a number of the key that was pressed

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {// kalo mencet W / UP
            upPressed = true;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {// kalo mencet S / DOWN
            downPressed = true;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {// kalo mencet A / LEFT
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {// kalo mencet D / RIGHT
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {// kalo mencet W / UP
            upPressed = false;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {// kalo mencet S / DOWN
            downPressed = false;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {// kalo mencet A / LEFT
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {// kalo mencet D / RIGHT
            rightPressed = false;
        }
    }

}
