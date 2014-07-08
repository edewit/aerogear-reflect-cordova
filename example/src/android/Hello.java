package com.example.plugin;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.jboss.aerogear.cordova.android.reflect.BasePlugin;

public class Hello extends BasePlugin {

  public boolean greet(String name, CallbackContext callbackContext) {
    String message = "Hello, " + name;
    callbackContext.success(message);

    return true;
  }
}
