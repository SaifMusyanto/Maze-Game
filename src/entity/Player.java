package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2); // player position on the screen
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 24;
        solidArea.height = 24;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 25; // default starting position on the world
        worldY = gp.tileSize * 7;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() { // mengambil resource
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_down_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_right_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_left_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        // update lokasi karakter
        if (keyH.upPressed == true) {
            direction = "up";// mengganti asset karakter sesuai direction
        } else if (keyH.downPressed == true) {
            direction = "down";
        } else if (keyH.leftPressed == true) {
            direction = "left";
        } else if (keyH.rightPressed == true) {
            direction = "right";
        }

        // CHECK TILE COLLISION
        collisionOn = false;
        gp.cChecker.checkTile(this);

        // CHECK OBJECT COLLISION
        int objIndex = gp.cChecker.checkObject(this, true); // return integer
        pickUpObject(objIndex);

        // IF COLLISION IS FALSE, PLAYER CAN MOVE
        if (collisionOn == false) {
            switch (direction) {
                case "up":
                    if (keyH.upPressed == true) {
                        worldY -= speed;
                    }
                    break;
                case "down":
                    if (keyH.downPressed == true) {
                        worldY += speed;
                    }
                    break;
                case "left":
                    if (keyH.leftPressed == true) {
                        worldX -= speed;
                    }
                    break;
                case "right":
                    if (keyH.rightPressed == true) {
                        worldX += speed;
                    }
                    break;
            }
        }

        spriteCounter++;
        if (spriteCounter > 12) {// frames updated every 12 frame
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {// if it's not an object
            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Key":
                    gp.playSE(1);// play coin SE
                    hasKey++;
                    gp.obj[i] = null; // has key increased and the key disappears
                    gp.ui.showMessage("Key + 1!");

                    break;
                case "Door":
                    if (hasKey > 0) {
                        gp.playSE(3);// play door sound effect
                        gp.obj[i] = null;
                        hasKey--;// used the key, hasKey decreased
                        gp.ui.showMessage("Opened!");
                    } else
                        gp.ui.showMessage("You need a key to open the door!");
                    break;
                case "Boots":
                    gp.playSE(2); // play power up Sound effect
                    gp.obj[i] = null;
                    this.speed += 3;
                    gp.ui.showMessage("Speed Up!");
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4); // play fanfare song
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {
        // g2.setColor(Color.white);/// set screen color
        // g2.fillRect(x, y, gp.tileSize, gp.tileSize); // set the position and size of
        // character

        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                } else if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                } else if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                } else if (spriteNum == 2) {
                    image = right2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                } else if (spriteNum == 2) {
                    image = left2;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);// player position is fixed at the center
                                                                              // of the screen
    }
}
