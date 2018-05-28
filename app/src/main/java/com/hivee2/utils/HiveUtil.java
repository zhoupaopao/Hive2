package com.hivee2.utils;

import android.content.Context;
import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;

import org.json.JSONException;
import org.json.JSONObject;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;

public class HiveUtil {

	
	
	private static long lastClickTime;  
	//防用户的快速点击
    public static boolean isFastDoubleClick() {  
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;  
        Log.e("fastclick", timeD + "");
        if ( 0 < timeD && timeD < 800) {     
            return true;     
        }     
        lastClickTime = time;     
        return false;     
    } 
	
	//坐标转换
	public static LatLng latlngChange(double lat,double lon){
		LatLng ll=new LatLng(lat, lon);
		System.out.println("util latlngchange  before-->"+ll.latitude+","+ll.longitude);
		CoordinateConverter c=new CoordinateConverter();
		c.from(CoordinateConverter.CoordType.GPS);//设置源坐标数据来源类型  为GPS
		c.coord(ll);//设置源坐标数据
		ll=c.convert();//执行坐标转换操作
		System.out.println("util latlngchange  later-->"+ll.latitude+","+ll.longitude);
		return ll;
	}
	public void onPausePage(Context context, String  pageName){
		JAnalyticsInterface.onPageEnd(context,pageName);
	}
	public void onResumePage(Context context, String  pageName){
		JAnalyticsInterface.onPageStart(context,pageName);
	}

	
	//封装try catch
		public static String jsonCheckNull(JSONObject so,String propertyName,String inits){
					String s=inits;
					//Log.e("check null", "so-->"+so.toString()+"\n"+"propertyName-->"+propertyName);
					try{
					
						s=so.getString(propertyName);
						
						Log.e("check null", "so get-->" + s);
					}catch(JSONException npe){
						npe.printStackTrace();
						s=inits;
						
					}catch(RuntimeException e){
						e.printStackTrace();
						s=inits;
					}
					System.out.println(propertyName+"hiveUtil check null-->"+s);
					return s;
				}
	
	//计算两个时间的间隔为多少分
	public static int timeJudge(String start,String end){
		java.util.Date startDate;
		java.util.Date endDate;
		int min=0;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			startDate=formatter.parse(start);
		} catch (ParseException e) {
			e.printStackTrace();
			Log.e("start", min + "");
			return min;
		}
		try {
			endDate=formatter.parse(end);
		} catch (ParseException e) {
			e.printStackTrace();
			Log.e("stop", min + "");
			return min;
		}
		min=(int) ((endDate.getTime()-startDate.getTime())/1000/60);
		System.out.println(min);
		Log.e("min", min + "");
		return min;
	}


	
}
