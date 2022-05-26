package wang.smalleyes.tankwar.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 游戏地图类
 *
 * @author smalleyes
 */
public class Map {
    /**
     * 存储地图数据的二维数组
     */
    public int[][] mapElement = new int[14][21];

    /**
     * 获取地图文件
     *
     * @param stage 地图等级
     * @return
     */
    public File getMapFile(int stage) {
        String fileName = null;
        StringBuffer sb = new StringBuffer();
        sb.append("src/main/resources/map/map_stage_");
        sb.append(stage);
        sb.append(".txt");
        fileName = sb.toString();
        return new File(fileName);
    }

    /**
     * 创造设定等级的地图
     *
     * @param stage 地图等级
     * @return
     */
    public List<Element> createMap(int stage) {
        List<Element> elementList = new CopyOnWriteArrayList<Element>();
        //获取地图文件:
        File mapFile = getMapFile(stage);
        //读取地图文件到map数组
        try {
            readMap(mapFile);
        } catch (IOException e) {

            e.printStackTrace();
        }
        //创造地图
        elementList = createMap();

        return elementList;

    }

    /**
     * 创建地图
     *
     * @return
     */
    private List<Element> createMap() {
        List<Element> list = new CopyOnWriteArrayList<Element>();
        for (int x = 0; x < 14; x++) {
            for (int y = 0; y < 21; y++) {
                //空白 0
                if (mapElement[x][y] == 0) {
                    list.add(new Space(y * 64, x * 64));
                }
                //墙 1
                if (mapElement[x][y] == 1) {
                    list.add(new Wall(y * 64, x * 64));
                }
                //铁墙 2
                if (mapElement[x][y] == 2) {
                    list.add(new Steel(y * 64, x * 64));
                }
                //水 3
                if (mapElement[x][y] == 3) {
                    list.add(new Water(y * 64, x * 64));
                }
                //草 4
                if (mapElement[x][y] == 4) {
                    list.add(new Grass(y * 64, x * 64));
                }
                //敌人 5
                if (mapElement[x][y] == 5) {
                    list.add(new Enemy(y * 64, x * 64));
                }
                //boss 6
                if (mapElement[x][y] == 6) {
                    list.add(new MyBoss(y * 64, x * 64));
                }
            }
        }
        return list;
    }

    /**
     * 读取地图文件到二维数组中
     *
     * @throws IOException
     */
    public void readMap(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        int x = 0;
        //存储文件到数组中
        while ((line = br.readLine()) != null) {
            for (int y = 0; y < 21; y++) {
                mapElement[x][y] = Integer.parseInt(line.substring(y, y + 1));
            }
            x++;
        }
        br.close();
    }

    /**
     * 改变map
     *
     * @param b 第几行
     * @param a 第几列
     * @throws IOException
     */
    public void setMap(int a, int b, int drawNum) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File("src/main/resources/map/map_stage_0.txt")));
        ArrayList<String> list = new ArrayList<String>();
        String line = null;
        while ((line = br.readLine()) != null) {
            list.add(line);
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("src/main/resources/map/map_stage_0.txt")));
        for (int i = 0; i < list.size(); i++) {
            //System.out.println(list.get(i));
        }
        String changeString = list.get(b);
        //System.out.println(changeString);
        char[] c = changeString.toCharArray();
        for (int i = 0; i < c.length; i++) {
            c[i] = changeString.charAt(i);
            c[a] = new String(drawNum + "").charAt(0);
        }
        String resultString = new String(c);
        list.remove(b);
        list.add(b, resultString);

        for (int i = 0; i < list.size(); i++) {
            bw.write(list.get(i));
            bw.newLine();
        }
        br.close();
        bw.close();
    }

    /**
     * 判断map元素集合中的xy是否为空元素
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isEmpty(int x, int y) {
        return mapElement[x][y] == 0;
    }
}
