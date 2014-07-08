android-base-plugin
===================

This plugin makes creating android plugins easier by providing plugin developers a similar API as in iOS. Instead of having to
write logic like this:

```java
@Override
public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

    if (action.equals("greet")) {

        String name = data.getString(0);
        String message = "Hello, " + name;
        callbackContext.success(message);

        return true;

    } else {

        return false;

    }
}

```

You can just add this plugin as a dependency and extend it instead of CordovaPlugin. Then instead of needing to override execute
you can just implement a method that has the the format `action(params, callbackContext)` example:

```java
public boolean greet(String name, CallbackContext callbackContext) {
  String message = "Hello, " + name;
  callbackContext.success(message);

  return true;
}

```

Removing a lot of boilerplate that was needed before.