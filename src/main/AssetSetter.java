package main;

import object.ObjectBoots;
import object.ObjectChest;
import object.ObjectDoor;
import object.ObjectKey;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new ObjectKey();
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 12 * gp.tileSize;

        gp.obj[1] = new ObjectKey();
        gp.obj[1].worldX = 29 * gp.tileSize;
        gp.obj[1].worldY = 34 * gp.tileSize;

        gp.obj[2] = new ObjectKey();
        gp.obj[2].worldX = 13 * gp.tileSize;
        gp.obj[2].worldY = 26 * gp.tileSize;

        gp.obj[3] = new ObjectChest();
        gp.obj[3].worldX = 25 * gp.tileSize;
        gp.obj[3].worldY = 42 * gp.tileSize;

        gp.obj[4] = new ObjectDoor();
        gp.obj[4].worldX = 13 * gp.tileSize;
        gp.obj[4].worldY = 15 * gp.tileSize;

        gp.obj[5] = new ObjectDoor();
        gp.obj[5].worldX = 12 * gp.tileSize;
        gp.obj[5].worldY = 24 * gp.tileSize;

        gp.obj[6] = new ObjectDoor();
        gp.obj[6].worldX = 25 * gp.tileSize;
        gp.obj[6].worldY = 39 * gp.tileSize;

        gp.obj[7] = new ObjectBoots();
        gp.obj[7].worldX = 10 * gp.tileSize;
        gp.obj[7].worldY = 6 * gp.tileSize;
    }
}
