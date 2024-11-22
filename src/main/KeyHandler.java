package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }// not used

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // get a number of the key that was pressed

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            if (gp.ui.commandNum == 0) { // Ketika berada di bagian "Select Character"
                if (code == KeyEvent.VK_D) { // Tombol D untuk menambah
                    gp.ui.charSelection++;
                    if (gp.ui.charSelection > gp.player.characterCount - 1) {
                        gp.ui.charSelection = 0; // Reset ke karakter pertama
                    }
                    System.out.println("charSelection: " + gp.ui.charSelection);
                    gp.repaint(); // Perbarui UI
                }
                if (code == KeyEvent.VK_A) { // Tombol A untuk mengurangi
                    gp.ui.charSelection--;
                    if (gp.ui.charSelection < 0) {
                        gp.ui.charSelection = gp.player.characterCount - 1; // Loop ke karakter terakhir
                    }
                    System.out.println("charSelection: " + gp.ui.charSelection);
                    gp.repaint(); // Perbarui UI
                }
            }
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) { // Navigasi ke atas
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2; // Loop ke opsi terakhir
                }
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) { // Navigasi ke bawah
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0; // Loop ke opsi pertama
                }
            }
            if (code == KeyEvent.VK_ENTER) { // Pilih opsi dengan ENTER
                if (gp.ui.commandNum == 1) { // Start game
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
                if (gp.ui.commandNum == 2) { // Quit game
                    System.exit(0);
                }
            }
        }

        // PLAY STATE
        if (gp.gameState == gp.playState) {
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
            if (code == KeyEvent.VK_ESCAPE) {// kalo mencet esc
                if (gp.gameState == gp.playState) {
                    gp.gameState = gp.pauseState;
                } else if (gp.gameState == gp.pauseState) {
                    gp.gameState = gp.playState;
                }
            }
        } else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_ESCAPE) {// kalo mencet esc
                if (gp.gameState == gp.playState) {
                    gp.gameState = gp.pauseState;
                } else if (gp.gameState == gp.pauseState) {
                    gp.gameState = gp.playState;
                }
            }
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
