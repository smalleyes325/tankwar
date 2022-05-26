/*
 * Copyright (C) 2014-2017 LS Information Technology Co. Ltd.
 *
 * All right reserved.
 *
 * This software is the confidential and proprietary information of LS Company of China.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the contract agreement you entered into with LS inc.
 *
 * $Id: SceneChannelMapping.java  2015年2月11日  ljn $
 *
 * Create on 2015年2月11日 上午11:10:02
 *
 * Description:
 *
 */
package wang.smalleyes.tankwar.util;

import com.sun.nio.file.SensitivityWatchEventModifier;
import org.newdawn.slick.util.Log;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Objects;
import java.util.Properties;

/**
 * 配置参数，启动时自动加载
 * @author smalleyes
 * @since 2016.8.8
 */
public class ConfigMap {
	public static void main(String[] args) {

	}

	//换行符，linux与windows不一样
	public static final String NEXT_LINE_CHAR = System.getProperty("line.separator", "\n");

	//监听目录(项目resource/config目录)，即运行时classes/config目录
//	public static final String baseDir = Thread.currentThread().getContextClassLoader().getResource("config").getPath();
	public static final String baseDir = Objects.requireNonNull(ConfigMap.class.getClassLoader().getResource("config")).getPath();
	//监听文件
	private static final String target_file = "config.properties";
	
	private ConfigMap(){}

    private static Properties prop ;
    
    static{
    	init();

		try {
			addWatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    private static void init() {
    	try {
			prop = new Properties();
			//该方式只能读取类路径下的配置文件
			// 使用ClassLoader加载properties配置文件生成对应的输入流
			InputStream in = ConfigMap.class.getClassLoader().getResourceAsStream("config/config.properties");
			// 使用properties对象加载输入流
			prop.load(in);

    		 StringBuffer buf = new StringBuffer("config.properties 参数 ").append(NEXT_LINE_CHAR);
    		for (Object key : prop.keySet()) {
				buf.append("\t").append(key).append("=").append(prop.get(key)).append(NEXT_LINE_CHAR);
			}
    		Log.info( buf.toString());
		} catch (Exception e) {
			Log.error("configMap init error!,"+e.getMessage(),e);
			throw new RuntimeException("init ConfigMap error");
		}
    }

    public static String getValue(String key) {
        return prop.getProperty(key);
    }
    
    public static int getAsInteger(String key) {
        return Integer.parseInt(prop.getProperty(key));
    }
    
    public static void setValue(String key,String value){
    	if(prop.containsKey(key)){
    		prop.setProperty(key, value);
    	}
    }

    private static void addWatch() throws Exception {
		//构造监听服务
		WatchService watcher = FileSystems.getDefault().newWatchService();
		//监听注册，监听实体的创建、修改、删除事件，并以高频率(每隔2秒一次，默认是10秒)监听
		Paths.get(baseDir).register(watcher,
				new WatchEvent.Kind[]{StandardWatchEventKinds.ENTRY_CREATE,
						StandardWatchEventKinds.ENTRY_MODIFY,
						StandardWatchEventKinds.ENTRY_DELETE},
				SensitivityWatchEventModifier.HIGH);

		//TODO 修改为周期线程池
		while (true) {
			//每隔3秒拉取监听key
			WatchKey key = watcher.take();  //等待，超时就返回
			//监听key为null,则跳过
			if (key == null) {
				continue;
			}
			//获取监听事件
			for (WatchEvent<?> event : key.pollEvents()) {

				//获取监听事件类型
				WatchEvent.Kind kind = event.kind();
				System.err.println(kind.type());
				//异常事件跳过
				if (kind == StandardWatchEventKinds.OVERFLOW) {
					continue;
				}
				//获取监听Path
//				Object context = event.context();
				Path path = Paths.get("");
				//只关注目标文件
				if (!target_file.equals(path.toString())) {
					continue;
				}
				//文件删除
				if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
					System.out.printf("file delete, type:%s  path:%s \n", kind.name(), path);
					continue;
				}
				//构造完整路径
				Path fullPath = Paths.get(baseDir, path.toString());
				//读取文件内容
				String content = new String(Files.readAllBytes(fullPath), StandardCharsets.UTF_8);
				//按行读取文件内容
				//                List<String> lineList = Files.readAllLines(fullPath);
				//输出事件类型、文件路径及内容
				System.out.printf("type:%s  path:%s  content:%s\n", kind.name(), path, content);
			}
			//处理监听key后(即处理监听事件后)，监听key需要复位，便于下次监听
			key.reset();
		}
	}
}

