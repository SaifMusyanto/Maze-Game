package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.ObjectKey;

public class UI {

    GamePanel gp;
    Font arial40, arial65;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    double playTime;// game play timer
    DecimalFormat dFormat = new DecimalFormat("#0.00");// format the timer

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

        if (gameFinished) {

            g2.setFont(arial40); // set the font
            g2.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            text = "You found the treasure!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // get the length of the text

            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * 3);
            g2.drawString(text, x, y); // print the text

            text = "Your time is: " + dFormat.format(playTime) + "!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // get the length of the text

            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 4);
            g2.drawString(text, x, y); // print the text

            g2.setFont(arial65);
            g2.setColor(Color.yellow);
            text = "CONGRATULATIONS!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // get the length of the text

            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 2);
            g2.drawString(text, x, y); // print the text

            gp.gameThread = null; //// stop the frame game

        } else {
            g2.setFont(arial40); // set the font
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
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
}
