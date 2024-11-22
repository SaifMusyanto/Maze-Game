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
    public BufferedImage[][] playerImages;
    public int characterCount = 2; // Jumlah desain karakter

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
        loadPlayerImages();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 25; // default starting position on the world
        worldY = gp.tileSize * 7;
        speed = 4;
        direction = "down";
    }

    public void loadPlayerImages() {
        try {
            playerImages = new BufferedImage[characterCount][8]; // [desain][arah]

            // Load desain pertama
            playerImages[0][0] = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_up_1.png"));
            playerImages[0][1] = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_up_2.png"));
            playerImages[0][2] = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_down_1.png"));
            playerImages[0][3] = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_down_2.png"));
            playerImages[0][4] = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_left_1.png"));
            playerImages[0][5] = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_left_2.png"));
            playerImages[0][6] = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_right_1.png"));
            playerImages[0][7] = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_right_2.png"));

            // Load desain kedua
            playerImages[1][0] = ImageIO.read(getClass().getResourceAsStream("/res/player/pokemon_up_1.png"));
            playerImages[1][1] = ImageIO.read(getClass().getResourceAsStream("/res/player/pokemon_up_2.png"));
            playerImages[1][2] = ImageIO.read(getClass().getResourceAsStream("/res/player/pokemon_down_1.png"));
            playerImages[1][3] = ImageIO.read(getClass().getResourceAsStream("/res/player/pokemon_down_2.png"));
            playerImages[1][4] = ImageIO.read(getClass().getResourceAsStream("/res/player/pokemon_left_1.png"));
            playerImages[1][5] = ImageIO.read(getClass().getResourceAsStream("/res/player/pokemon_left_2.png"));
            playerImages[1][6] = ImageIO.read(getClass().getResourceAsStream("/res/player/pokemon_right_1.png"));
            playerImages[1][7] = ImageIO.read(getClass().getResourceAsStream("/res/player/pokemon_right_2.png"));
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
        BufferedImage image = null;

        int charIndex = gp.ui.charSelection; // Indeks desain karakter berdasarkan gp.ui.charSelection
        if (charIndex >= playerImages.length) {
            charIndex = 0; // Default ke desain pertama jika indeks invalid
        }

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = playerImages[charIndex][0];
                } else if (spriteNum == 2) {
                    image = playerImages[charIndex][1];
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = playerImages[charIndex][2];
                } else if (spriteNum == 2) {
                    image = playerImages[charIndex][3];
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = playerImages[charIndex][4];
                } else if (spriteNum == 2) {
                    image = playerImages[charIndex][5];
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = playerImages[charIndex][6];
                } else if (spriteNum == 2) {
                    image = playerImages[charIndex][7];
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
