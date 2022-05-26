package wang.smalleyes.tankwar.model;


import wang.smalleyes.tankwar.inter.Accessible;
import wang.smalleyes.tankwar.inter.Crashable;

/**
 * 铁墙类
 *
 * @author smalleyes
 */
public class Steel extends Element implements Crashable, Accessible {
    /**
     * 创造铁墙
     *
     * @param x
     * @param y
     */
    public Steel(int x, int y) {
        this.x = x;
        this.y = y;
        this.imagePath = "src/main/resources/img/steel.gif";
        this.getSize();
    }

    /**
     * 创造爆炸物
     */
    @Override
    public Blast showBlast() {
        return new Blast(this, false);
    }
}
