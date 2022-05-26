package wang.smalleyes.tankwar.model;

import wang.smalleyes.tankwar.common.Constant;
import wang.smalleyes.tankwar.common.Direction;
import wang.smalleyes.tankwar.inter.Accessible;
import wang.smalleyes.tankwar.inter.Crashable;
import wang.smalleyes.tankwar.inter.Destroyable;
import wang.smalleyes.tankwar.inter.Fireable;
import wang.smalleyes.tankwar.util.CollisionUtils;
import wang.smalleyes.tankwar.util.DrawUtils;
import wang.smalleyes.tankwar.util.SoundUtils;

import java.io.IOException;
import java.util.Random;


/**
 * 主角坦克类
 *
 * @author smalleyes
 */
public class Enemy extends Element implements Crashable, Destroyable, Accessible, Fireable {
    //字段==================================================================================================
    /**
     * 上次开火时间
     */
    long lastFireTime = 0;
    /**
     * 开火间隔
     */
    long fireInterval = 600;
    /**
     * 移动间隔
     */
    long moveInterval = 200;
    /**
     * 坦克移动速度
     */
    int speed = 16;
    /**
     * 坦克的血量
     */
    int blood = 3;
    /**
     * 坦克碰撞方向
     */
    Direction badDirection;
    /**
     * 坦克下一次碰撞前可移动距离
     */
    Direction[] dirs = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

    int canMoveDistance;

    private long lastTime = 0;
    ;
    /**
     * 坦克颜色类型
     */

    //构造方法================================================================================================

    /**
     * 创建我的坦克类
     *
     * @param x X坐标
     * @param y Y坐标
     * @return
     */
    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        this.floor = 0;
        this.direction = Direction.UP;
        this.imagePath = switchImage(this.direction);
        this.getSize();

    }



    //成员方法================================================================================================

    /**
     * 获取坦克图片地址
     */
    private String switchImage(Direction direction) {
        String switchImagePath = null;
        switch (direction) {
            case DOWN:
                switchImagePath = "src/main/resources/img/enemy_1_d.gif";
                break;
            case LEFT:
                switchImagePath = "src/main/resources/img/enemy_1_l.gif";
                break;
            case RIGHT:
                switchImagePath = "src/main/resources/img/enemy_1_r.gif";
                break;
            case UP:
                switchImagePath = "src/main/resources/img/enemy_1_u.gif";
                break;
            default:
                break;
        }
        return switchImagePath;
    }

    /**
     * 坦克移动方法
     *
     * @param up
     */
    public void move(Direction direction) {

        long currentTime = System.currentTimeMillis();
        int index = 0;
        Random r = new Random();
        index = r.nextInt(4);
        if (currentTime - lastTime < moveInterval) {
            return;
        }
        lastTime = currentTime;

        if (this.badDirection != null && this.badDirection == this.direction) {
            switch (direction) {
                case UP:
                    this.y -= canMoveDistance;
                    break;
                case DOWN:
                    this.y += canMoveDistance;
                    break;
                case LEFT:
                    this.x -= canMoveDistance;
                    break;
                case RIGHT:
                    this.x += canMoveDistance;
                    break;
                default:
                    break;
            }
            this.direction = dirs[index];
            this.imagePath = this.switchImage(direction);
            return;
        }

        switch (direction) {
            case UP:
                this.y -= speed;
                break;
            case DOWN:
                this.y += speed;
                break;
            case LEFT:
                this.x -= speed;
                break;
            case RIGHT:
                this.x += speed;
                break;
            default:
                break;
        }
        /**
         * 边界
         */
        if (x < 0) {
            this.x = 0;
            this.direction = dirs[index];
            this.imagePath = this.switchImage(direction);
            return;
        }
        if (x > 21 * 64 - this.width) {
            x = 21 * 64 - this.width;
            this.direction = dirs[index];
            this.imagePath = this.switchImage(direction);
            return;
        }
        if (y < 0) {
            this.y = 0;
            this.direction = dirs[index];
            this.imagePath = this.switchImage(direction);
            return;
        }
        if (y > Constant.GAME_HEIGHT - this.height) {
            this.y = Constant.GAME_HEIGHT - this.height;
            this.direction = dirs[index];
            this.imagePath = this.switchImage(direction);
            return;
        }


    }

    /**
     * 边缘检测
     *
     * @param h h
     * @return
     */
    public boolean isHit(Crashable h) {
        boolean isHit = false;
        Element e = (Element) h;
        int tX = this.x;
        int tY = this.y;
        int tW = this.width;
        int tH = this.height;

        int eX = e.x;
        int eY = e.y;
        int eW = e.width;
        int eH = e.height;

        //预测下一步要走得距离
        switch (direction) {
            case DOWN:
                this.imagePath = this.switchImage(direction);
                tY += speed;
                break;
            case LEFT:
                this.imagePath = this.switchImage(direction);
                tX -= speed;
                break;
            case RIGHT:
                this.imagePath = this.switchImage(direction);
                tX += speed;
                break;
            case UP:
                this.imagePath = this.switchImage(direction);
                tY -= speed;
                break;
            default:
                break;
        }
        //判断下一步是否碰撞
        isHit = CollisionUtils.isCollisionWithRect(tX, tY, tW, tH, eX, eY, eW, eH);
        if (isHit)//计算下一步可移动距离
        {
            this.badDirection = this.direction;
            tX = this.x;
            tY = this.y;
            switch (direction) {
                case DOWN:
                    this.canMoveDistance = eY - tY - tH;
                    break;
                case LEFT:
                    this.canMoveDistance = tX - eX - eW;
                    break;
                case RIGHT:
                    this.canMoveDistance = eX - tX - tW;
                    break;
                case UP:
                    this.canMoveDistance = tY - eY - eH;
                    break;
            }
        } else {
            this.badDirection = null;
        }
        return isHit;
    }

    /**
     * 开火,生成子弹
     *
     * @return
     */
    @Override
    public Bullet fire() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFireTime < fireInterval) {
            return null;
        } else {
            lastFireTime = currentTime;
            try {
                SoundUtils.play("src/main/resources/snd/fire.wav");
            } catch (IOException e) {

                e.printStackTrace();
            }
            return new Bullet(this);
        }
    }

    @Override
    public Blast showBlast() {
        blood--;
        return new Blast(this, this.blood <= 0);
    }

    @Override
    public boolean isDestroy() {
        if (this.blood <= 0) {
            return true;
        }
        return false;
    }

    /**
     * 重写draw方法
     */
    @Override
    public void draw() {
        this.move(direction);
        try {
            DrawUtils.draw(imagePath, x, y);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
