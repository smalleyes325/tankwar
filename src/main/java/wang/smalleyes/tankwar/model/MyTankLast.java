package wang.smalleyes.tankwar.model;

/**
 * 我方坦克剩余
 * @author smalleyes
 */
public class MyTankLast extends Element
{
	public MyTankLast(int lastNum,int x,int y)
	{
		this.imagePath="src/main/resources/img/mytanklast_"+lastNum+".png";
		this.x=x;
		this.y=y;
		this.getSize();
		this.floor=10000;
	}
}
