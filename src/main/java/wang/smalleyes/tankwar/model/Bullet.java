package wang.smalleyes.tankwar.model;

import java.io.IOException;


import wang.smalleyes.tankwar.common.Constant;
import wang.smalleyes.tankwar.common.Direction;
import wang.smalleyes.tankwar.inter.Accessible;
import wang.smalleyes.tankwar.inter.Crashable;
import wang.smalleyes.tankwar.inter.Destroyable;
import wang.smalleyes.tankwar.inter.Fireable;
import wang.smalleyes.tankwar.util.CollisionUtils;
import wang.smalleyes.tankwar.util.DrawUtils;


/**
 * 子弹对象
 *
 * @author smalleyes
 */
public class Bullet extends Element implements Destroyable, Crashable {
    /**
     * 子弹方向
     */
    Direction direction;
    /**
     * 子弹速度
     */
    int speed = 10;
    /**
     * 所归元素
     */
    public Element fireFrom;

    /**
     * 创建一个子弹类
     *
     * @param x 子弹X坐标
     * @param y 子弹Y坐标
     */
    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        this.imagePath = "src/main/resources/img/bullet.gif";
        this.floor = 2;
        this.getSize();
    }

    /**
     * 根据调用者数据创建子弹对象
     *
     * @param enemy 调用者坦克
     */
    public Bullet(Fireable f) {
        Element e = (Element) f;

        this.fireFrom = e;

        this.direction = e.direction;
        int tX = e.x;
        int tY = e.y;
        int tW = e.width;
        int tH = e.height;
        int bW;
        int bH;

        switch (e.direction) {
            case DOWN:
                this.imagePath = "src/main/resources/img/bullet_d.gif";
                this.getSize();
                bW = this.width;
                bH = this.height;
                this.x = (tW - bW) / 2 + tX;
                this.y = tY + tH;
                break;
            case LEFT:
                this.imagePath = "src/main/resources/img/bullet_l.gif";
                this.getSize();
                bW = this.width;
                bH = this.height;
                this.x = tX - bW;
                this.y = (tH - bH) / 2 + tY;
                break;
            case RIGHT:
                this.imagePath = "src/main/resources/img/bullet_r.gif";
                this.getSize();
                bW = this.width;
                bH = this.height;
                this.x = tX + tW;
                this.y = (tH - bH) / 2 + tY;
                break;
            case UP:
                this.imagePath = "src/main/resources/img/bullet_u.gif";
                this.getSize();
                bW = this.width;
                bH = this.height;
                this.x = (tW - bW) / 2 + tX;
                this.y = tY - bH;
                break;
            default:
                break;
        }
    }

    /**
     * 重写draw方法
     */
    @Override
    public void draw() {
        //判断方向,移动
        switch (this.direction) {
            case DOWN:
                y += speed;
                break;
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
            case UP:
                y -= speed;
                break;
            default:
                break;
        }
        try {
            DrawUtils.draw(imagePath, x, y);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * 判断子弹是否可销毁
     */
    @Override
    public boolean isDestroy() {
        this.getSize();
        if (this.x < 0 || this.y < 0 || this.x > 21 * 64 - this.width || this.y > Constant.GAME_HEIGHT) {
            return true;
        }
        return false;
    }
    //判断子弹是否打中目标

    /**
     *
     */
    public boolean isHit(Accessible a) {
        //拿到子弹自己的坐标和宽高
        int bX = this.x;
        int bY = this.y;
        int bW = this.width;
        int bH = this.height;

        //转换成Element类型
        Element e = (Element) a;

        //拿到元素的坐标和宽高
        int eX = e.x;
        int eY = e.y;
        int eW = e.width;
        int eH = e.height;

        //判断两个有没有碰撞
        boolean isHit = CollisionUtils.isCollisionWithRect(bX, bY, bW, bH, eX, eY, eW, eH);

        //返回结果
        return isHit;
    }

    public boolean isHit(Crashable c) {
        int bX = this.x;
        int bY = this.y;
        int bW = this.width;
        int bH = this.height;

        //转换成Element类型
        Element e = (Element) c;

        //拿到元素的坐标和宽高
        int eX = e.x;
        int eY = e.y;
        int eW = e.width;
        int eH = e.height;

        //判断两个有没有碰撞
        boolean isHit = CollisionUtils.isCollisionWithRect(bX, bY, bW, bH, eX, eY, eW, eH);

        //返回结果
        return isHit;
    }

}
