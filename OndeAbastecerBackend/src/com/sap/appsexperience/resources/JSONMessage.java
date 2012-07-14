package com.sap.appsexperience.resources;

import java.lang.reflect.Field;
import java.util.List;

import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;

public class JSONMessage {
	public JSONObject getJSON() {
		JSONObject obj = new JSONObject();
		
		Field[] fields = this.getClass().getDeclaredFields();
		
		for (Field field : fields) {             
			if (field.isAnnotationPresent(JSONParameter.class)) {
				field.setAccessible(true);
				
				try {
					obj.put(field.getName(), field.get(this));
					
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return obj;
	}
	
	public void fromJSON(String anJsonObject) {
		JSONObject obj = null;
		try {
			obj = new JSONObject(anJsonObject);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		if(obj == null)
			return;
		
		Field[] fields = this.getClass().getDeclaredFields();
		
		for (Field field : fields) {             
			if (field.isAnnotationPresent(JSONParameter.class)) {
				field.setAccessible(true);
				
				try {
					field.set(this, obj.get(field.getName()));
					
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		}
	}
		

}
