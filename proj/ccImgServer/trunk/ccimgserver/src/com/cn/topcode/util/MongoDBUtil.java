package com.cn.topcode.util;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;



/**
 * 
 * @Title:  数据库 CRUD
 * @Description: 实现TODO
 * @Copyright:Copyright (c) 2014
 * @Company:上海亨码信息科技有限公司
 * @Date:Mar 21, 2014
 * @author:xiezhongyong
 * @version 1.0
 */

public final class MongoDBUtil {
	private static List<ServerAddress> replicaSetSeeds;
	private static String dbName;
	private static String user = null;
	private static String password = null;
	private static Mongo mongo = null;

	private static DB db;

	static {
		try {
			dbName = Config.MONGODB_DBNAME;
			user = Config.MONGODB_USER;
			password = Config.MONGODB_PASSWORD;
			replicaSetSeeds = Config.MONGODB_HOSTS;
			if (replicaSetSeeds.size() > 0) {
				mongo = new Mongo(replicaSetSeeds);
			} else {
				mongo = new Mongo();
			}
			db = mongo.getDB(dbName);
			if (null != user && null != password) {
				db.authenticate(user, password.toCharArray());
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private MongoDBUtil() {
	}

	/**
	 *  检查id 是否合法.过期
	 * @param type
	 * @param id
	 * @return 0:合法，1:过期，2:不存在,-1:系统错误
	 */
	public static int query(String type,Long id) {
		try {
			DBCollection cn = db.getCollection(type);
			BasicDBObject query = new BasicDBObject();
			query.put("start", new BasicDBObject("$lte", id));
			query.put("end", new BasicDBObject("$gte", id));
			DBCursor cursor = cn.find(query);
			while(cursor.hasNext()) {
				DBObject data = cursor.next();
				Object obj = data.get("overTime");
				if(null != obj){
					Date overTime = DateUtil.string2Date(obj+"", "yyyy-MM-dd HH:mm:ss");
					if(overTime.getTime()>(new Date().getTime())){
						return 0;
					}else{
						return 1;
					}
				
				}else{
					return 2;
				}
			}
			
			 
			 return 2;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static void closeMongo() {
		try {
			if (null != mongo)
				mongo.close();
		} catch (Exception e) {
			throw new RuntimeException(" mongo close error..... "+e);
		}
	}
}
