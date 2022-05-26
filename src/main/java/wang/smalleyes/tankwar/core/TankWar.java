package wang.smalleyes.tankwar.core;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import wang.smalleyes.tankwar.inter.Accessible;
import wang.smalleyes.tankwar.inter.Crashable;
import wang.smalleyes.tankwar.inter.Destroyable;
import wang.smalleyes.tankwar.model.BackGround;
import wang.smalleyes.tankwar.model.Blast;
import wang.smalleyes.tankwar.model.Bullet;
import wang.smalleyes.tankwar.model.ChoosePoint;

import wang.smalleyes.tankwar.common.Constant;
import wang.smalleyes.tankwar.common.Direction;
import wang.smalleyes.tankwar.model.Element;
import wang.smalleyes.tankwar.model.Enemy;
import wang.smalleyes.tankwar.model.EnemyLast;
import wang.smalleyes.tankwar.model.GameOver;
import wang.smalleyes.tankwar.model.Map;
import wang.smalleyes.tankwar.model.MyBoss;
import wang.smalleyes.tankwar.model.MyTank;
import wang.smalleyes.tankwar.model.MyTankLast;
import wang.smalleyes.tankwar.model.PointRecord;
import org.lwjgl.input.Keyboard;

public class TankWar extends Window {
    /**
     * 游戏关卡设置
     */
    int mapStage = 4;
    /**
     * 敌方可复活坦克数量
     */
    int enemyNum = 2;
    int size;
    /**
     * 控制开始时间
     */
    long startTime = 0;
    /**
     * 背景图片
     */
    BackGround bg;
    BackGround stage;
    /**
     * 选项指针
     */
    ChoosePoint cPoint;
    /**
     * 游戏总元素集合
     */
    List<Element> list;
    /**
     * 游戏地图元素集合
     */
    List<Element> mElementList;
    /**
     * cPoint位置
     */
    int cPointLoca = 0;
    /**
     * 地图对象创建地图
     */
    Map map;
    /**
     * 坦克1
     */
    MyTank mt1;
    /**
     * 坦克2
     */
    MyTank mt2;
    /**
     * 计分板
     */
    PointRecord pr;
    /**
     * 游戏借宿标语
     */
    GameOver go;
    /**
     * 坦克数量
     */
    int mapEnemyNum = 0;
    int startEnemyNum = 5;
    int startMapEnemyNum = 0;
    int myTankLife1 = 0;
    int myTankLife2 = 0;
    int drawNum = 0;
    /**
     * 是否还有敌人
     */
    boolean hasNoEnemy = true;

    List<Element> enemyLastList;
    /**
     * tanke1的生命值
     */
    List<Element> myTankList1;
    /**
     * tanke2的生命值
     */
    List<Element> myTankList2;
    private int currentX;
    private int currentY;

    /**
     * 游戏窗口
     *
     * @param title
     * @param width
     * @param height
     * @param fps
     */
    public TankWar(String title, int width, int height, int fps) {
        super(title, width, height, fps);
    }

    /**
     * 游戏初始化时
     */
    @Override
    protected void onCreate() {
        //初始化地图
        map = new Map();
        //始化总元素集合
        list = new CopyOnWriteArrayList<Element>();
        //初始化地图元素集合
        mElementList = new CopyOnWriteArrayList<Element>();
        //敌人余量图标
        enemyLastList = new CopyOnWriteArrayList<Element>();
        //mt1生命图标
        myTankList1 = new CopyOnWriteArrayList<Element>();
        myTankList2 = new CopyOnWriteArrayList<Element>();
        //初始化游戏背景图片
        bg = new BackGround("src/main/resources/img/startbackground.jpg", 0, 0);
        //初始化游戏选项指针图片
        cPoint = new ChoosePoint("src/main/resources/img/point.png", 634, 360);
        cPointLoca = 0;
        //list添加元素
        list.add(bg);
        list.add(cPoint);
    }

    /**
     * 鼠标点击时
     */
    @Override
    protected void onMouseEvent(int key, int x, int y) {
    }

    /**
     * 键盘按下时
     */
    @Override
    protected void onKeyEvent(int key) {
        switch (key) {
            //当按下空格时
            case Keyboard.KEY_SPACE:
                //判断是否存在开始背景和选项指针
                if (list.contains(bg) && list.contains(cPoint)) {
                    if (cPointLoca == 3) {
                        cPointLoca = 0;
                        cPoint.move(0, -261);
                        break;
                    } else {
                        cPoint.move(0, 87);
                        cPointLoca++;
                        break;
                    }
                } else {
                    if (cPointLoca == 0) {
                        if (list.contains(mt1)) {
                            Bullet b = mt1.fire();
                            if (b != null) {
                                list.add(b);
                            }
                            break;
                        }
                    }
                    if (cPointLoca == 1) {
                        if (list.contains(mt2)) {

                            Bullet b = mt2.fire();
                            if (b != null) {
                                list.add(b);
                            }
                            break;
                        }
                    } else {
                        try {
                            mt1.drawMap(map, drawNum);
                            if (mt1.x == currentX && currentY == mt1.y) {
                                drawNum++;
                                if (drawNum > 6) {
                                    drawNum = 0;
                                }
                            } else {
                                drawNum = 0;
                                currentX = mt1.x;
                                currentY = mt1.y;
                            }
                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                        list.removeAll(mElementList);
                        mElementList = map.createMap(0);
                        list.addAll(mElementList);
                        System.out.println(drawNum);
                        break;
                    }

                }
                //当按下回车键时
            case Keyboard.KEY_RETURN:
                //判断是否是在开始界面
                if (list.contains(cPoint) && list.contains(bg)) {
                    //判断指针所在
                    switch (cPointLoca) {
                        //单人游戏
                        case 0:
                            mt1 = new MyTank(Constant.TANK1_X, Constant.TANK1_Y, 0, 3);
                            //获取生命值
                            myTankLife1 = mt1.lifeTime;
                            //添加图片

                            myTankList1.add(new MyTankLast(myTankLife1, 1500, 600));

                            list.addAll(myTankList1);
                            pr = new PointRecord();
                            list.remove(cPoint);
                            list.remove(bg);
                            mElementList = map.createMap(mapStage);
                            list.add(mt1);
                            list.add(pr);
                            //获取map地图初始敌坦值
                            mapEnemyNum = 0;
                            for (Element element : mElementList) {
                                if (element instanceof Enemy) {
                                    mapEnemyNum++;
                                    startMapEnemyNum++;
                                }
                            }
                            System.out.println(enemyLastList.size());
                            for (int x = 1; x <= (enemyNum + mapEnemyNum); x++) {
                                enemyLastList.add(new EnemyLast(x));
                            }
                            list.addAll(enemyLastList);
                            list.addAll(mElementList);
                            System.out.println(enemyLastList.size());
                            break;

                        //多人游戏
                        case 1:
                            mt1 = new MyTank(Constant.TANK1_X, Constant.TANK1_Y, 0, 3);
                            mt2 = new MyTank(Constant.TANK2_X, Constant.TANK2_Y, 1, 3);
                            //获取生命值
                            myTankLife1 = mt1.lifeTime;
                            myTankLife2 = mt2.lifeTime;
                            //添加图片
                            myTankList1.add(new MyTankLast(myTankLife1, 1500, 600));
                            myTankList2.add(new MyTankLast(myTankLife2, 1500, 750));
                            list.addAll(myTankList1);
                            list.addAll(myTankList2);
                            pr = new PointRecord();
                            list.remove(cPoint);
                            list.remove(bg);
                            mElementList = map.createMap(mapStage);
                            list.add(mt1);
                            list.add(mt2);
                            list.add(pr);
                            mapEnemyNum = 0;
                            for (Element element : mElementList) {
                                if (element instanceof Enemy) {
                                    mapEnemyNum++;
                                    startMapEnemyNum++;
                                }
                            }
                            System.out.println(enemyLastList.size());
                            for (int x = 1; x <= (enemyNum + mapEnemyNum); x++) {
                                enemyLastList.add(new EnemyLast(x));
                            }
                            list.addAll(enemyLastList);
                            list.addAll(mElementList);
                            System.out.println(enemyLastList.size());
                            break;
                        //自定义地图
                        case 2:
                            mElementList = map.createMap(0);
                            list.addAll(mElementList);
                            mt1 = new MyTank(Constant.TANK1_X, Constant.TANK1_Y, 0, 3);
                            mt1.speed = 64;
                            list.remove(cPoint);
                            list.remove(bg);
                            list.add(mt1);
                            list.add(pr);
                            break;
                        //退出游戏
                        case 3:
                            System.exit(0);
                            break;
                    }
                }
                break;
            //当按下esc键时
            case Keyboard.KEY_ESCAPE:
                if (list.contains(cPoint) && list.contains(bg)) {
                    break;
                } else {
                    //清空所有集合
                    myTankList1.removeAll(myTankList1);
                    myTankList2.removeAll(myTankList2);
                    enemyLastList.removeAll(enemyLastList);
                    list.removeAll(enemyLastList);
                    list.removeAll(list);
                    list.add(bg);
                    list.add(cPoint);
                    mElementList.removeAll(mElementList);
                    enemyNum = startEnemyNum;
                    mapEnemyNum = startMapEnemyNum;

                    break;
                }

            case Keyboard.KEY_UP:
                if (list.contains(cPoint) && list.contains(bg)) {
                    break;
                }
                mt1.move(Direction.UP);
                break;
            case Keyboard.KEY_DOWN:
                if (list.contains(cPoint) && list.contains(bg)) {
                    break;
                }

                mt1.move(Direction.DOWN);
                break;

            case Keyboard.KEY_LEFT:
                if (list.contains(cPoint) && list.contains(bg)) {
                    break;
                }

                mt1.move(Direction.LEFT);
                break;

            case Keyboard.KEY_RIGHT:
                if (list.contains(cPoint) && list.contains(bg)) {
                    break;
                }

                mt1.move(Direction.RIGHT);
                break;

            case Keyboard.KEY_W:
                if (list.contains(cPoint) && list.contains(bg)) {
                    break;
                }
                if (cPointLoca == 0 || cPointLoca == 1) {
                    mt2.move(Direction.UP);
                    break;
                }
            case Keyboard.KEY_S:
                if (list.contains(cPoint) && list.contains(bg)) {
                    break;
                }
                if (cPointLoca == 0 || cPointLoca == 1) {
                    mt2.move(Direction.DOWN);
                    break;
                }
            case Keyboard.KEY_A:
                if (list.contains(cPoint) && list.contains(bg)) {
                    break;
                }
                if (cPointLoca == 0 || cPointLoca == 1) {
                    mt2.move(Direction.LEFT);
                    break;
                }
            case Keyboard.KEY_D:
                if (list.contains(cPoint) && list.contains(bg)) {
                    break;
                }
                if (cPointLoca == 0 || cPointLoca == 1) {
                    mt2.move(Direction.RIGHT);
                    break;
                }
            case Keyboard.KEY_X:
                //判断是否存在开始背景和选项指针
                if (list.contains(bg) && list.contains(cPoint)) {
                    if (cPointLoca == 3) {
                        cPointLoca = 0;
                        cPoint.move(0, -261);
                        break;
                    } else {
                        cPoint.move(0, 87);
                        cPointLoca++;
                        break;
                    }
                } else {
                    if (list.contains(mt2)) {
                        Bullet b = mt2.fire();
                        if (b != null) {
                            list.add(b);
                        }
                        break;
                    }
                }
                break;
            case Keyboard.KEY_NUMPAD0:
                //判断是否存在开始背景和选项指针
                if (list.contains(bg) && list.contains(cPoint)) {
                    if (cPointLoca == 3) {
                        cPointLoca = 0;
                        cPoint.move(0, -261);
                        break;
                    } else {
                        cPoint.move(0, 87);
                        cPointLoca++;
                        break;
                    }
                } else {
                    if (list.contains(mt2)) {
                        Bullet b = mt1.fire();
                        if (b != null) {
                            list.add(b);
                        }
                        break;
                    }
                }
                break;
            case Keyboard.KEY_1:
                cPoint.turn(634, 360);
                cPointLoca = 0;
                break;
            case Keyboard.KEY_2:
                cPoint.turn(634, 360 + 87);
                cPointLoca = 1;
                break;
            case Keyboard.KEY_3:
                cPoint.turn(634, 360 + 87 * 2);
                cPointLoca = 2;
                break;
            case Keyboard.KEY_4:
                cPoint.turn(634, 360 + 87 * 3);
                cPointLoca = 3;
                break;

            default:
                break;
        }

    }


    /**
     * 游戏画面更新:帧率
     */
    @Override
    protected void onDisplayUpdate() {
        /**
         * 判断是否还有敌人
         */
        for (Element element : list) {
            if (element instanceof Enemy) {
                hasNoEnemy = false;
                break;
            }
        }
        hasNoEnemy = true;
        list.sort(new Comparator<Element>() {
            @Override
            public int compare(Element e1, Element e2) {
                if (list.size() >= 2) {
                    return e1.floor - e2.floor;
                }
                return 0;
            }

        });

        //判断是否可销毁
        for (Element element : list) {
            //判断可摧毁的
            if (element instanceof Destroyable) {
                Destroyable d = (Destroyable) element;
                if (d.isDestroy()) {
                    //敌方坦克被打死
                    if (d instanceof Enemy) {
                        int x = 0;
                        int y = 0;
                        Random r1 = new Random();
                        if (enemyNum > 0) {
                            while (true) {
                                x = r1.nextInt(13);
                                y = r1.nextInt(20);
                                if (map.isEmpty(x, y) && x == 0 && (y == 0 || y == 10 || y == 19)) {
                                    break;
                                }

                            }
                            list.add(new Enemy(x * 64, y * 64));
                            enemyNum--;
                            list.removeAll(enemyLastList);
                            enemyLastList.remove(enemyLastList.size() - 1);
                            list.addAll(enemyLastList);
                        } else if (enemyNum == 0) {
                            list.removeAll(enemyLastList);
                            enemyLastList.remove(enemyLastList.size() - 1);
                            list.addAll(enemyLastList);
                            mapEnemyNum--;
                            if (mapEnemyNum == 0) {
                                list.removeAll(list);
                                mElementList.removeAll(mElementList);
                                enemyLastList.removeAll(enemyLastList);

                                System.out.println("you win!");
                                //继续下一关
                                mapStage++;
                                enemyNum = startEnemyNum;
                                mt1 = new MyTank(Constant.TANK1_X, Constant.TANK1_Y, 0, 3);
                                mElementList = map.createMap(mapStage);
                                list.add(mt1);
                                list.add(pr);
                                list.removeAll(myTankList1);
                                myTankList1.add(new MyTankLast(myTankLife1, 1500, 600));
                                list.addAll(myTankList1);
                                //获取map地图初始敌坦值
                                mapEnemyNum = 0;
                                for (Element element1 : mElementList) {
                                    if (element1 instanceof Enemy) {
                                        mapEnemyNum++;
                                        startMapEnemyNum++;
                                    }
                                }
                                System.out.println(enemyLastList.size());
                                for (int x1 = 1; x1 <= (enemyNum + mapEnemyNum); x1++) {
                                    enemyLastList.add(new EnemyLast(x1));
                                }
                                list.addAll(enemyLastList);
                                list.addAll(mElementList);

                                break;
                            }
                        }
                        System.out.println(enemyNum);
                        System.out.println(hasNoEnemy);
						/*if(enemyNum==0)
						{
							enemyNum=10;
							for(Element e:list)
							{
								if(e instanceof Enemy)
								{
									hasEnemy=true;
									break;
								}
							}
							if(!hasEnemy)
							{								
								list.removeAll(list);
								System.out.println("you win!");
							}
						}*/
                    }
                    list.remove(d);

                }
                //boss被打死游戏结束
                if (d instanceof MyBoss && d.isDestroy()) {

                    GameOver go = new GameOver();
                    list.add(go);
                    list.remove(mt1);
                    list.remove(mt2);
                    size = list.size();
                    for (int x = size - 1; x >= 0; x--) {
                        list.remove(x);
                    }
                    list.add(bg);
                    list.add(cPoint);
                    //清空所有集合
                    enemyLastList.removeAll(enemyLastList);
                    list.removeAll(enemyLastList);
                    list.removeAll(list);
                    list.add(bg);
                    list.add(cPoint);
                    mElementList.removeAll(mElementList);
                    enemyNum = startEnemyNum;
                    mapEnemyNum = startMapEnemyNum;
                }
                //我的坦克被打死
                if (d instanceof MyTank && d.isDestroy()) {
                    MyTank mt = (MyTank) d;
                    //如果mt是mt1
                    if (mt == mt1) {
                        mt.lifeTime--;
                        myTankLife1 = mt.lifeTime;
                        list.removeAll(myTankList1);
                        myTankList1.removeAll(myTankList1);
                        myTankList1.add(new MyTankLast(myTankLife1, 1500, 600));
                        list.addAll(myTankList1);
                        //如果mt1坦克生命值为0
                        if (mt.lifeTime == 0) {
                            //如果mt2的生命值也为0
                            if (mt2.lifeTime == 0) {
                                list.remove(mt);
                                GameOver go = new GameOver();
                                list.add(go);
                                //清空所有集合
                                myTankList1.removeAll(myTankList1);
                                myTankList2.removeAll(myTankList2);
                                enemyLastList.removeAll(enemyLastList);
                                list.removeAll(enemyLastList);
                                list.removeAll(list);
                                list.add(bg);
                                list.add(cPoint);
                                mElementList.removeAll(mElementList);
                                //重新初始化参数
                                enemyNum = startEnemyNum;
                                mapEnemyNum = startMapEnemyNum;
                            }
                            //如果mt2的生命值还有
                            else {
                                //只移除mt1
                                list.remove(mt);
                                myTankList1.removeAll(myTankList1);
                            }
                        } else {
                            //如果mt1生命值不为0复活
                            mt1 = new MyTank(Constant.TANK1_X, Constant.TANK1_Y, 0, mt.lifeTime);
                            list.add(mt1);
                        }
                    }
                    //如果mt是mt2的话
                    else if (mt == mt2) {
                        mt.lifeTime--;
                        System.out.println(mt.lifeTime);
                        myTankLife2 = mt.lifeTime;
                        list.removeAll(myTankList2);
                        myTankList2.removeAll(myTankList2);
                        myTankList2.add(new MyTankLast(myTankLife2, 1500, 750));
                        list.addAll(myTankList2);
                        //如果mt2的生命值为0
                        if (mt.lifeTime == 0) {
                            //如果mt1的生命值为0
                            if (mt1.lifeTime == 0) {
                                list.remove(mt);
                                GameOver go = new GameOver();
                                list.add(go);
                                //清空所有集合
                                myTankList2.removeAll(myTankList2);
                                myTankList1.removeAll(myTankList1);
                                enemyLastList.removeAll(enemyLastList);
                                list.removeAll(enemyLastList);
                                list.removeAll(list);
                                list.add(bg);
                                list.add(cPoint);
                                mElementList.removeAll(mElementList);
                                enemyNum = startEnemyNum;
                                mapEnemyNum = startMapEnemyNum;
                            } else {
                                //清空mt2
                                myTankList2.removeAll(myTankList2);
                                list.remove(mt);
                            }
                        } else {
                            //如果mt2还有生命,复活
                            mt2 = new MyTank(Constant.TANK2_X, Constant.TANK2_Y, 1, mt.lifeTime);
                            list.add(mt2);
                        }
                    }
                }
            }
        }
        //遍历集合,判断子弹是否打中
        for (Element element : list) {
            if (element instanceof Bullet) {
                Bullet b = (Bullet) element;
                for (Element element1 : list) {
                    if (element1 instanceof Accessible) {
                        Accessible a = (Accessible) element1;
                        //获取类型
                        if (b.isHit(a)) {
                            if (!(a.getClass() == b.fireFrom.getClass())) {
                                list.remove(b);
                                Blast blast = a.showBlast();
                                if (blast != null) {
                                    list.add(blast);
                                }
                            } else {
                                list.remove(b);
                            }
                        }

                    }
                    //判断是否是两个不同的子弹互撞
                    if (element1 instanceof Bullet) {
                        Bullet b1 = (Bullet) element1;
                        //获取类型如果子弹不是同一个就抵消
                        if (b.isHit(b1) && b1 != b) {
                            list.remove(b);
                            list.remove(b1);
                        }

                    }
                }
            }
        }
        //判断坦克是否碰撞墙或另一个坦克
        for (Element element : list) {

            if (element instanceof MyTank) {
                MyTank mt = (MyTank) element;
                for (Element element1 : list) {
                    //如果不是同一个对象就crash
                    if (element1 instanceof Crashable && element1 != mt) {
                        Crashable h = (Crashable) element1;
                        boolean isHit = mt.isHit(h);
                        if (isHit && cPointLoca != 2) {
                            break;
                        }
                    }
                }
            }
            //如果是敌机
            if (element instanceof Enemy) {
                Enemy e = (Enemy) element;
                for (Element element1 : list) {
                    //如果相撞的是来自同类发射的子弹就不受伤
                    if (element1 instanceof Crashable && element1 != e && !(element1 instanceof Bullet)) {
                        Crashable h = (Crashable) element1;
                        boolean isHit = e.isHit(h);
                        if (isHit) {
                            break;
                        }
                    }
                }
            }
            //如果是子弹
            if (element instanceof Bullet) {
                Bullet b = (Bullet) element;
                for (Element element1 : list) {
                    //如果两个子弹相撞
                    if (element1 instanceof Crashable && element1 instanceof Bullet) {
                        Crashable h = (Crashable) element1;
                        boolean isHit = b.isHit(h);
                        if (isHit) {
                            break;
                        }
                    }
                }
            }
        }
        //遍历集合
        for (Element element : list) {
            if (element instanceof Enemy) {
                if (cPointLoca != 2) {
                    Enemy e = (Enemy) element;
                    e.draw();
                    Bullet b = e.fire();
                    if (b != null) {
                        list.add(b);

                    }
                }
            }
        }
        //遍历集合
        for (Element element : list) {
            if (element instanceof Element) {

                element.draw();
            }
        }

    }

}
