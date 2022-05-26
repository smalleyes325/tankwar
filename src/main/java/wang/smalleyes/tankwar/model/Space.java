package wang.smalleyes.tankwar.model;

/**
 * 空元素
 * @author smalleyes
 */
public class Space extends Element {
    public Space(int x, int y) {
        this.x = x;
        this.y = y;
        this.imagePath = "src/main/resources/img/space.png";
    }
}
