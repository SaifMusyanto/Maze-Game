package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class ObjectChest extends SuperObject {
        public ObjectChest(){
        name = "Chest";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/chest.png"));
        } catch (IOException e) {
            // TODO: handle exception
        }
    }
}
