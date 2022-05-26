package wang.smalleyes.tankwar.inter;


import wang.smalleyes.tankwar.model.Blast;

/**
 * 可被攻击的接口
 * @author smalleyes
 *
 */
public interface Accessible
{
	/**
	 * 显示爆炸效果
	 * @return 爆炸物效果
	 */
	public Blast showBlast();
}
