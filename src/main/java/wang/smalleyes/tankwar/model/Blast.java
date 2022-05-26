package wang.smalleyes.tankwar.model;

import java.io.IOException;

import wang.smalleyes.tankwar.inter.Accessible;
import wang.smalleyes.tankwar.inter.Destroyable;
import wang.smalleyes.tankwar.util.DrawUtils;

/**
 * 爆炸效果对象
 *
 * @author smalleyes
 */
public class Blast extends Element implements Destroyable {
    static String[] paths =
            new String[] {"src/main/resources/img/blast_1.gif", "src/main/resources/img/blast_2.gif", "src/main/resources/img/blast_3.gif", "src/main/resources/img/blast_4.gif", "src/main/resources/img/blast_5.gif",
                    "src/main/resources/img/blast_6.gif", "src/main/resources/img/blast_7.gif", "src/main/resources/img/blast_8.gif",};
    //最大遍历索引
    private int maxLength;
    /**
     * 爆炸物是否可销毁
     */
    private boolean isDestroy;
    /**
     * 遍历的索引
     */
    private int index;

    /**
     * 创建最大遍历值的爆炸物
     *
     * @param a
     * @param isDestroy
     */
    public Blast(Accessible a, boolean isDestroy) {
        Element e = (Element) a;

        int eX = e.x;
        int eY = e.y;
        int eW = e.width;
        int eH = e.height;

        this.imagePath = paths[0];
        this.getSize();
        this.x = eX - (this.width - eW) / 2;
        this.y = eY - (this.height - eH) / 2;
        //如果没到被摧毁的时候
        if (!isDestroy) {
            this.maxLength = paths.length / 2 - 1;
        } else
            maxLength = paths.length - 1;
        this.floor = 15;
    }

    /**
     * 重写的draw方法
     */
    @Override
    public void draw() {
        this.imagePath = paths[index];
        index++;
        if (index > maxLength) {
            index = 0;
            this.isDestroy = true;
        } else {
            try {
                DrawUtils.draw(imagePath, x, y);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    /**
     * 判断是否可销毁
     *
     * @return
     */
    @Override
    public boolean isDestroy() {
        return this.isDestroy;
    }
}
