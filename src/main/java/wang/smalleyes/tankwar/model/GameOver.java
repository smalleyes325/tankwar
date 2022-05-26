package wang.smalleyes.tankwar.model;

import wang.smalleyes.tankwar.common.Constant;
import wang.smalleyes.tankwar.util.DrawUtils;

import java.io.IOException;


/**
 * @author smalleyes
 */
public class GameOver extends Element {
    int floor = 1000;
    private int speed = 5;

    public GameOver() {
        this.imagePath = "src/main/resources/img/gameover.png";
        this.getSize();
        this.x = (Constant.GAME_WIDTH - this.width) / 2;
        this.y = Constant.GAME_HEIGHT;
    }

    @Override
    public void draw() {
        if (this.y > (Constant.GAME_HEIGHT - this.height) / 2) {
            this.y -= speed;
        } else {
            this.y = (Constant.GAME_HEIGHT - this.height) / 2;
        }
        try {
            DrawUtils.draw(imagePath, x, y);
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}
