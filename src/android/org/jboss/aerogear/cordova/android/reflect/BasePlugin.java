/*
 * JBoss, Home of Professional Open Source.
 * Copyright Red Hat, Inc., and individual contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.aerogear.cordova.android.reflect;

import android.util.Log;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

public class BasePlugin extends CordovaPlugin {

  private static final String TAG = BasePlugin.class.getSimpleName();

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    try {
      final int length = args.length();
      Class[] classArgs = new Class[length + 1];
      Object[] values = new Object[length + 1];
      for (int i = 0; i < length; i++) {
        classArgs[i] = args.get(i).getClass();
        values[i] = args.get(i);
      }
      classArgs[length] = CallbackContext.class;
      values[length] = callbackContext;
      final Method method = getClass().getMethod(action, classArgs);
      final Object invoke = method.invoke(this, values);
      if (invoke instanceof Boolean) {
        return (Boolean)invoke;
      }
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(String.format("Unable to invoke the method with name '%s' check permissions", action), e);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(String.format("Plugin '%s' does not have action '%s' method defined", getClass().getSimpleName(), action), e);
    }
    return false;
  }

  public <T> T convert(JSONObject object, Class<T> type) {
    return convert(object, type, false);
  }

  public <T> T convert(JSONObject object, Class<T> type, boolean lenient) {
    try {
      T result = type.newInstance();
      final Iterator iterator = object.keys();
      while (iterator.hasNext()) {
        String key = (String) iterator.next();
        try {
          Field field = type.getDeclaredField(key);
          field.setAccessible(true);
          field.set(result, object.get(key));
        } catch (NoSuchFieldException e) {
          if (!lenient) {
            throw new RuntimeException(e);
          }
        }
      }
      return result;
    } catch (InstantiationException e) {
      throw new RuntimeException(e);
    } catch (JSONException e) {
      Log.e(TAG, "The key was here a moment ago");
    } catch (IllegalAccessException e) {
      Log.e(TAG, String.format("Unable to invoke the instantiate object of type '%s' check permissions", type));
    }

    return null;
  }
}