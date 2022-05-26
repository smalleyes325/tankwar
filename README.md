# tankwar
很久以前利用轻量级游戏库lwjgl开发的坦克大战，支持自定义地图。后期有空再打磨一下，算是回味下童年了。

游戏界面:

![游戏菜单界面](https://qncloud.smalleyes.wang/tankwar/tankwar_bg.png)

### 功能介绍

- 孤胆英雄：单人游戏

- 双龙出海：双人游戏

- 自拟江湖：在游戏界面自定义地图。目前游戏地图在resources/map文件夹下，直接对txt文件进行修改也可，自定义的游戏地图是map_stage_0.txt这个文件。

  ![自定义地图](https://qncloud.smalleyes.wang/tankwar/tankwar_map.png)

- 退隐山林：退出游戏，目前退出游戏不会保留任何游戏进度~

### 操作方式

- 游戏菜单界面：空格键 切换选项，回车键 确定

- 游戏内：
  - 单人模式：**方向键** 移动，**空格键** 攻击；
  - 双人模式：1P **WASD** 移动，2P **方向键** 移动 有:bug:

- 自定义地图：**方向键** 移动，**空格键** 切换选择需要设置的地图元素。

### 运行方式

**运行启动类wang.smalleyes.tankwar.tankwar，**

启动前需要将resources/libs/jar里面的两个jar包添加到类库

同时需要添加下启动参数：-Djava.library.path=src/main/resources/libs/natives/macosx

这个是不同操作系统需要依赖的动态库，按照自己的操作系统选择不同路径即可

![path设置](https://qncloud.smalleyes.wang/tankwar/tankwar_conf.png)
