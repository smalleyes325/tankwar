package wang.smalleyes.tankwar.model;

/**
 * 元素 草类
 *
 * @author smalleyes
 */
public class Grass extends Element {

    public Grass(int x, int y) {
        this.x = x;
        this.y = y;
        this.imagePath = "src/main/resources/img/grass.gif";
        this.getSize();
        this.floor = 5;
    }

}
