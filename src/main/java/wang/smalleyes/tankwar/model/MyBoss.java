package wang.smalleyes.tankwar.model;


import wang.smalleyes.tankwar.inter.Accessible;
import wang.smalleyes.tankwar.inter.Crashable;
import wang.smalleyes.tankwar.inter.Destroyable;

/**
 *
 * @author smalleyes
 */
public class MyBoss extends Element implements Accessible, Destroyable, Crashable
{
	/**
	 * boss血量
	 */
	private int blood=1;
	/**
	 * 创建boss
	 * @param x
	 * @param y
	 */
	public MyBoss(int x,int y)
	{
		this.x=x;
		this.y=y;
		this.imagePath="src/main/resources/img/boss.png";
		this.getSize();
	}

	@Override
	public Blast showBlast() {
		blood--;
		return new Blast(this,this.blood<=0);
	}
	@Override
	public boolean isDestroy() {
		return blood <= 0;
	}
}
