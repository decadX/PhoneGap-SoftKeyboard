package de.phonostar;

import org.json.JSONArray;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

public class SoftKeyboard extends CordovaPlugin {

    public SoftKeyboard() {
    }

    public void showKeyBoard() {
      InputMethodManager mgr = (InputMethodManager) cordova.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
      mgr.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    public void hideKeyBoard() {
      InputMethodManager mgr = (InputMethodManager) cordova.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
      mgr.hideSoftInputFromWindow(((View) webView).getWindowToken(), 0);
      
    }

    public boolean isKeyBoardShowing() {
      final View rootView = cordova.getActivity().getWindow().getDecorView().findViewById(android.R.id.content).getRootView();
      Rect r = new Rect();
      rootView.getWindowVisibleDisplayFrame(r);
      int heightDiff = rootView.getRootView().getHeight() - (r.bottom);
      DisplayMetrics dm = new DisplayMetrics();
      cordova.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
      final float density = dm.density;
      int pixelHeightDiff = (int)(heightDiff / density);
      
      return (100 < pixelHeightDiff); // if more than 100 pixels, its probably a keyboard...
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
    if (action.equals("show")) {
      this.showKeyBoard();
      callbackContext.success("done");
      return true;
    }
    else if (action.equals("hide")) {
      this.hideKeyBoard();
      callbackContext.success();
      return true;
    }
    else if (action.equals("isShowing")) {
      callbackContext.success(Boolean.toString(this.isKeyBoardShowing()));
      return true;
    }
    else {
      return false;
    }
  }
}

