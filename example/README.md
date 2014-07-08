# Cordova Hello World Plugin

This is a clone of the https://github.com/don/cordova-plugin-hello repo to demonstrate the use of the android-reflect plugin

##Test
1. `cordova create test`
2.  Added the following to the index.js file to invoke the plugin (in the onDeviceReady function):
  ```js
      var win = function (result) {
          alert(result);
      },
      fail = function (error) {
          alert("ERROR " + error);
      };

      hello.greet("World", win, fail);
  ```

Compare original [plugin implementation](https://github.com/edewit/cordova-plugin-hello/blob/master/src/android/Hello.java)
with the [new one](https://github.com/edewit/aerogear-reflect-cordova/blob/master/example/src/android/Hello.java)

##Use
* Declare aerogear-reflect-cordova as a dependency in your plugin.xml
* Extend BasePlugin instead of CordovaPlugin