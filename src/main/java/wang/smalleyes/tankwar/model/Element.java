package wang.smalleyes.tankwar.model;

import wang.smalleyes.tankwar.common.Direction;
import wang.smalleyes.tankwar.util.DrawUtils;

import java.io.IOException;


/**
 * 元素超类
 * @author smalleyes
 *
 */
public class Element 
{
	/**
	 * 元素X坐标
	 */
	public int x;
	/**
	 * 元素Y坐标
	 */
	public int y;
	/**
	 * 元素宽度
	 */
	int width;
	/**
	 * 元素高度 
	 */
	int height;	
	/**
	 * 元素资源路径
	 */
	String imagePath;
	/**
	 * 元素层次等级
	 */
	public int floor=0;
	public Direction direction=Direction.UP;
	/**
	 * 元素添加到画板
	 */
	public void draw()
	{
		try {
			DrawUtils.draw(imagePath, x, y);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	/**
	 * 获取元素大小
	 */
	public void getSize()
	{ 
		int[] size=new int[2];
		try {
			size = DrawUtils.getSize(imagePath);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		this.width=size[0];
		this.height=size[1];
	}
	
}
