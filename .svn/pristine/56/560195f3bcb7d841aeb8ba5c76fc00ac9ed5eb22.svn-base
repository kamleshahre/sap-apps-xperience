package com.sap.appsexperience.util;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.sap.appsexperience.AlertasActivity;
import com.sap.appsexperience.ConfigActivity;
import com.sap.appsexperience.R;

public class MenuHandler {
	public static boolean onCreateOptionsMenu(MenuInflater inflater, Menu menu) {
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
	
    public static boolean onOptionsItemSelected(MenuItem item, Activity parent) {
    	
        try {
			switch (item.getItemId()) {
			/*
			case R.id.alertas:
				Intent alertasActivity = new Intent(parent.getApplicationContext(), AlertasActivity.class);
				parent.startActivity(alertasActivity);
				return true;
				*/
			case R.id.config:
				Intent settingsActivity = new Intent(parent.getApplicationContext(), ConfigActivity.class);
				parent.startActivity(settingsActivity);
			    return true;
			}
		} 
        catch (Exception e) {
			System.out.println(">>>OndeAbastecerActivity.onOptionItemSelected(). " + e.getMessage());
			e.printStackTrace();
			return false;
		}
        
        return false;
    }
	
	

}
