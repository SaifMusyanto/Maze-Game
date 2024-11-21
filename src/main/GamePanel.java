package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile size
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow;// 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldCol;

    // FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(); // variable to receive key input
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread; // to start/stop the game time looping, so the image r always generated
    
    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH); // set player entity
    public SuperObject obj[] = new SuperObject[10]; // menampilkan maximal 10 object


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);// get better render performance
        this.addKeyListener(keyH); // to recognize key input from keyH
        this.setFocusable(true); // allowed to listen key input
    }

    public void setupGame(){// 
        aSetter.setObject();

        playMusic(0);// play BlueBoyAdventure song
    }

    public void startGameThread() {
        gameThread = new Thread(this);// start this game
        gameThread.start(); // this method calls run the method run()
    }

    @Override
    public void run() {// method by Runnable. it used to run the thread
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        // int drawCount = 0;

        while (gameThread != null) {// as long as game thread exists, it repeats the process that is written inside
                                    // this brackets

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            // delta adalah satuan waktu untuk update frame, setiap 1 delta akan mengupdate
            // frame 1x

            if (delta >= 1) {
                // 1 UPDATE : update information such as character position
                update();
                // 2 DRAW: draw the screen with the updated information
                repaint();
                delta--;
                // drawCount++;
            }

            if (timer >= 1000000000) {
                // System.out.println("FPS: " + drawCount);
                // drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {// paint the component
        super.paintComponent(g); // this is Jpanel method

        Graphics2D g2 = (Graphics2D) g;// casting menjadi instance ofGraphics2D

        tileM.draw(g2);// draw the map first

        for (int i = 0; i < obj.length; i++) { //draw object
            if(obj[i] != null){
                obj[i].draw(g2, this);
            }
        } // draw an object

        player.draw(g2);// draw the character

        ui.draw(g2);// draw the text

        g2.dispose();
    }

    public void playMusic(int i){ // play and loop music
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){ //stop music
        music.stop();
    }

    public void playSE(int i){// play sound effect
        soundEffect.setFile(i);
        soundEffect.play();
    }

}
