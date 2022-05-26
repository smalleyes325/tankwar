package wang.smalleyes.tankwar.model;


import wang.smalleyes.tankwar.inter.Accessible;
import wang.smalleyes.tankwar.inter.Crashable;
import wang.smalleyes.tankwar.inter.Destroyable;

/**
 * 土墙类
 *
 * @author smalleyes
 */
public class Wall extends Element implements Crashable, Accessible, Destroyable {
    /**
     * 墙血量
     */
    private int blood = 3;

    /**
     * 构造一个墙
     *
     * @param x X坐标
     * @param y Y坐标
     */
    public Wall(int x, int y) {
        this.x = x;
        this.y = y;
        this.imagePath = "src/main/resources/img/wall.gif";
        this.getSize();
    }

    /**
     * 返回爆炸物
     */
    @Override
    public Blast showBlast() {
        blood--;
        return new Blast(this, this.blood <= 0);
    }

    /**
     * 判断墙是否可销毁
     *
     * @return
     */
    @Override
    public boolean isDestroy() {
        return blood <= 0;
    }
}
