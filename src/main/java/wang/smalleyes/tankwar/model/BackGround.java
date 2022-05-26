package wang.smalleyes.tankwar.model;
/**
 * 游戏背景元素
 * @author smalleyes
 *
 */
public class BackGround extends Element 
{
	/**
	 * 创建背景图片
	 * @param imagePath 图片路径
	 * @param x X坐标
	 * @param y Y坐标
	 */
	public BackGround(String imagePath, int x, int y)
	{
		this.imagePath=imagePath;
		this.x=x;
		this.y=y;
		this.getSize();
		this.floor=-1;
	}

}
