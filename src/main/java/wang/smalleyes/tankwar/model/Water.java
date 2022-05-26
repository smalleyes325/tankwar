package wang.smalleyes.tankwar.model;


import wang.smalleyes.tankwar.inter.Crashable;

/**
 * Water类
 *
 * @author smalleyes
 */
public class Water extends Element implements Crashable {

    public Water(int x, int y) {
        this.x = x;
        this.y = y;
        this.imagePath = "src/main/resources/img/water.gif";
        this.getSize();
    }

}
