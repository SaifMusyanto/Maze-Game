package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import object.ObjectKey;

public class UI {

    GamePanel gp;
    Font arial40, arial65;
    BufferedImage keyImage, rightArrow, rightArrowHover, leftArrow, leftArrowHover;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public int commandNum = 0;
    public int charSelection = 0;

    double playTime;// game play timer
    DecimalFormat dFormat = new DecimalFormat("#0.00");// format the timer
    Graphics g2;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial65 = new Font("Arial", Font.BOLD, 65);
        arial40 = new Font("Arial", Font.BOLD, 40);
        ObjectKey key = new ObjectKey();// create key object
        keyImage = key.image; // get the key image
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        g2.setFont(arial40);
        g2.setColor(Color.white);

        if (gp.gameState == gp.playState) {
            if (gameFinished) {

                g2.setFont(arial40); // set the font
                g2.setColor(Color.white);

                String text;
                int textLength;
                int x;
                int y;

                text = "You found the treasure!";
                textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
                // get the length of the text

                x = gp.screenWidth / 2 - textLength / 2;
                y = gp.screenHeight / 2 - (gp.tileSize * 3);
                g2.drawString(text, x, y); // print the text

                text = "Your time is: " + dFormat.format(playTime) + "!";
                textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
                // get the length of the text

                x = gp.screenWidth / 2 - textLength / 2;
                y = gp.screenHeight / 2 + (gp.tileSize * 4);
                g2.drawString(text, x, y); // print the text

                g2.setFont(arial65);
                g2.setColor(Color.yellow);
                text = "CONGRATULATIONS!";
                textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
                // get the length of the text

                x = gp.screenWidth / 2 - textLength / 2;
                y = gp.screenHeight / 2 + (gp.tileSize * 2);
                g2.drawString(text, x, y); // print the text

                gp.gameThread = null; //// stop the frame game

            } else {
                g2.setFont(arial40); // set the font
                g2.setColor(Color.white);
                g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize,
                        gp.tileSize, null);
                g2.drawString("x " + gp.player.hasKey, 74, 65);

                // TIME
                playTime += (double) 1 / 60;
                g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize * 11, 65);

                // MESSAGE
                if (messageOn) {

                    g2.setFont(g2.getFont().deriveFont(30F)); // SET FONT SIZE
                    g2.drawString(message, gp.tileSize / 2, gp.tileSize * 6);

                    messageCounter++;

                    if (messageCounter > 120) { // after 2 seconds it will disappear
                        messageCounter = 0;
                        messageOn = false;
                    }
                }
            }
        }
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));

        String text = "PAUSED";
        int y = gp.screenHeight / 2;
        int x = getXforCenteredText(text);

        /// shadow
        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);

        g2.drawString(text, x, y);
    }

    public void drawTitleScreen() {
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        /// TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 86F));
        String text = "Maze Tale";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;

        // SHADOW
        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);

        /// MAIN COLOR
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 36F));

        // CHOOSE CHARACTER
        text = "-select character-";
        x = getXforCenteredText(text);
        y += gp.tileSize * 1.2;
        g2.drawString(text, x, y);

        // CHARACTER IMAGE
        x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
        y += gp.tileSize * 1;
        g2.drawImage(gp.player.playerImages[charSelection][2], x, y, gp.tileSize * 2, gp.tileSize * 2, null);

        // ARROW BUTTONS
        try {
            leftArrow = ImageIO.read(getClass().getResourceAsStream("/res/ui/leftArrow.png"));
            leftArrowHover = ImageIO.read(getClass().getResourceAsStream("/res/ui/leftArrowHover.png"));
            rightArrow = ImageIO.read(getClass().getResourceAsStream("/res/ui/rightArrow.png"));
            rightArrowHover = ImageIO.read(getClass().getResourceAsStream("/res/ui/rightArrowHover.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        x = (gp.screenWidth / 2 - (gp.tileSize * 2) / 2) - gp.tileSize * 2;
        y += gp.tileSize * 0.5;
        int leftArrowX = x;
        int arrowY = y;
        g2.drawImage(leftArrow, leftArrowX, arrowY, gp.tileSize, gp.tileSize, null);

        x = (gp.screenWidth / 2 - (gp.tileSize * 2) / 2) + gp.tileSize * 3;
        int rightArrowX = x;
        g2.drawImage(rightArrow, rightArrowX, arrowY, gp.tileSize, gp.tileSize, null);

        if (commandNum == 0) {
            g2.drawImage(rightArrowHover, rightArrowX, arrowY, gp.tileSize, gp.tileSize, null);
            g2.drawImage(leftArrowHover, leftArrowX, arrowY, gp.tileSize, gp.tileSize, null);

        }

        /// MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 56F));

        text = "START";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3.5;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize * 1.5;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }

    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
}
