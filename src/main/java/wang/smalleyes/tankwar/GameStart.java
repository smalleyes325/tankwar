package wang.smalleyes.tankwar;


import wang.smalleyes.tankwar.core.TankWar;
import wang.smalleyes.tankwar.common.Constant;

/**
 * 启动类
 * @author smalleyes
 */
public class GameStart {
    public static void main(String[] args) {
        TankWar w5 = new TankWar(Constant.GAME_TITLE, Constant.GAME_WIDTH, Constant.GAME_HEIGHT, Constant.GAME_FPS);
        w5.start();
    }

}
