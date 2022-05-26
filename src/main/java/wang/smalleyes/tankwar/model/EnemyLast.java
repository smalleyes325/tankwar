package wang.smalleyes.tankwar.model;

/**
 * 敌人数量
 *
 * @author smalleyes
 */
public class EnemyLast extends Element {
    int var = 0;

    public EnemyLast(int last) {
        this.getEnemyLast(last);
    }

    public void getEnemyLast(int last) {
        this.imagePath = "src/main/resources/img/enemylast.gif";
        this.getSize();
        int varX = last % 2 == 1 ? 0 : 1;
        int varY = last % 2 == 1 ? (last + 1) / 2 - 1 : last / 2 - 1;
        this.x = 22 * 64 + varX * (32 + this.width);
        this.y = 100 + varY * 64;
    }
}
