package wang.smalleyes.tankwar.model;

import wang.smalleyes.tankwar.util.DrawUtils;

import java.io.IOException;


/**
 * 游戏选项指针
 *
 * @author smalleyes
 */
public class ChoosePoint extends Element {
    /**
     * 方法上次执行时间
     */
    long lastTime;
    /**
     * 方法执行间隔
     */
    long interval = 0;

    /**
     * 创建游戏选项指针
     *
     * @param imagePath 图片路径
     * @param x X坐标
     * @param y Y坐标
     */
    public ChoosePoint(String imagePath, int x, int y) {
        this.imagePath = imagePath;
        this.x = x;
        this.y = y;
        this.floor = -1;
        this.getSize();
    }

    /**
     * 指针的移动
     *
     * @param valX X坐标偏移量
     * @param ValY Y坐标偏移量
     */
    public void move(int valX, int ValY) {
        this.x += valX;
        this.y += ValY;
    }

    /**
     * 指针的移动
     *
     * @param x
     * @param y
     */
    public void turn(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 重写的draw方法
     */
    @Override
    public void draw() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastTime) > interval) {
            lastTime = currentTime;
            try {
                DrawUtils.draw(imagePath, x, y);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
