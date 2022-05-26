package wang.smalleyes.tankwar.model;

import wang.smalleyes.tankwar.common.Constant;
import wang.smalleyes.tankwar.common.Direction;
import wang.smalleyes.tankwar.inter.Accessible;
import wang.smalleyes.tankwar.inter.Crashable;
import wang.smalleyes.tankwar.inter.Destroyable;
import wang.smalleyes.tankwar.inter.Fireable;
import wang.smalleyes.tankwar.util.CollisionUtils;
import wang.smalleyes.tankwar.util.SoundUtils;

import java.io.IOException;


/**
 * 主角坦克类
 *
 * @author smalleyes
 */
public class MyTank extends Element implements Crashable, Destroyable, Accessible, Fireable {
    //字段==================================================================================================
    /**
     * 上次开火时间
     */
    long lastFireTime = 0;
    /**
     * 开火间隔
     */
    long interval = 300;
    /**
     * 坦克移动速度
     */
    public int speed = 32;
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
    int canMoveDistance;
    /**
     * 坦克颜色类型
     */
    private int num;
    public int lifeTime = 3;

    //构造方法================================================================================================

    /**
     * 创建我的坦克类
     *
     * @param x X坐标
     * @param y Y坐标
     * @param num 坦克号码:0和1,代表不同颜色
     * @return
     */
    public MyTank(int x, int y, int num, int lifeTime) {
        this.x = x;
        this.y = y;
        this.num = num;
        this.lifeTime = lifeTime;
        this.floor = 1;
        this.direction = Direction.UP;
        this.imagePath = switchImage(this.direction, this.num);
        this.getSize();

    }



    //成员方法================================================================================================

    /**
     * 获取坦克图片地址
     */
    private String switchImage(Direction direction, int num) {
        String switchImagePath = null;
        switch (num) {
            case 0:
                switch (direction) {
                    case DOWN:
                        switchImagePath = "src/main/resources/img/tank_d.gif";
                        break;
                    case LEFT:
                        switchImagePath = "src/main/resources/img/tank_l.gif";
                        break;
                    case RIGHT:
                        switchImagePath = "src/main/resources/img/tank_r.gif";
                        break;
                    case UP:
                        switchImagePath = "src/main/resources/img/tank_u.gif";
                        break;
                    default:
                        break;
                }
            case 1:
                switch (direction) {
                    case DOWN:
                        switchImagePath = "src/main/resources/img/tank_d.gif";
                        break;
                    case LEFT:
                        switchImagePath = "src/main/resources/img/tank_l.gif";
                        break;
                    case RIGHT:
                        switchImagePath = "src/main/resources/img/tank_r.gif";
                        break;
                    case UP:
                        switchImagePath = "src/main/resources/img/tank_u.gif";
                        break;
                    default:
                        break;
                }
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
        if (this.badDirection != null && this.badDirection == direction) {
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
            }
            return;
        }
        if (this.direction != direction) {
            //判断方向并转换图
            this.imagePath = switchImage(direction, this.num);
            this.direction = direction;
        } else {
            //判断方向并移动
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
            }
        }
        //判断是否出边界
        if (x < 0) {
            this.x = 0;
        }
        if (x > 21 * 64 - this.width) {
            x = 21 * 64 - this.width;
        }
        if (y < 0) {
            this.y = 0;
        }
        if (y > Constant.GAME_HEIGHT - this.height) {
            this.y = Constant.GAME_HEIGHT - this.height;
        }
    }

    /**
     * 边缘检测
     *
     * @param element
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
                tY += speed;
                break;
            case LEFT:
                tX -= speed;
                break;
            case RIGHT:
                tX += speed;
                break;
            case UP:
                tY -= speed;
                break;
            default:
                break;
        }
        //判断下一步是否碰撞
        isHit = CollisionUtils.isCollisionWithRect(tX, tY, tW, tH, eX, eY, eW, eH);
        if (isHit)//计算下一步可移动距离
        {
            this.badDirection = direction;
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
                default:
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
        if (currentTime - lastFireTime < interval) {
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
     * 画地图
     *
     * @param map
     * @param drawNum
     * @throws IOException
     */
    public void drawMap(Map map, int drawNum) throws IOException {
        int x1 = this.x / 64;
        int y1 = this.y / 64;
        switch (drawNum) {
            case 0:
                map.setMap(x1, y1, 0);
                break;
            case 1:
                map.setMap(x1, y1, 1);
                break;
            case 2:
                map.setMap(x1, y1, 2);
                break;
            case 3:
                map.setMap(x1, y1, 3);
                break;
            case 4:
                map.setMap(x1, y1, 4);
                break;
            case 5:
                map.setMap(x1, y1, 0);
                break;
            case 6:
                map.setMap(x1, y1, 6);
                break;
            default:
                break;
        }
    }
}
