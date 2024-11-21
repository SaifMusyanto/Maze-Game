package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class ObjectBoots extends SuperObject{
        public ObjectBoots(){
        name = "Boots";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/boots.png"));
        } catch (IOException e) {
            // TODO: handle exception
        }
    }
}
