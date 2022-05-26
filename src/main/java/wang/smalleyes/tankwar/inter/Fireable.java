package wang.smalleyes.tankwar.inter;


import wang.smalleyes.tankwar.model.Bullet;


/**
 * @author smalleyes
 */
public interface Fireable {

    /**
     * 可攻击的
     * @return 子弹对象
     */
    public Bullet fire();

}
