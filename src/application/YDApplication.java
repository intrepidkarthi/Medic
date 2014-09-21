package application;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class YDApplication extends android.app.Application {

    private static YDApplication context;



	public YDApplication() {
        context = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //initGlobalModelStore();
        //setupImageDownloader();
    }

//    public void setupImageDownloader() {
//        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//        //.cacheInMemory()
//        .cacheOnDisc()
//        .build();
//
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
//        .defaultDisplayImageOptions(defaultOptions)
//        .build();
//
//        ImageLoader.getInstance().init(config);
//    }

    public void initGlobalModelStore() {
        //initiate controllers
    }

    public static Context getContext() {
        return context;
    }

    public static YDApplication getApplicationObject() {
        return (YDApplication)context;
    }



    public static String getAppKey() {
        // Add code to print out the key hash
        try {
            PackageInfo info = YDApplication.getApplicationObject().getPackageManager().getPackageInfo(
                    "com.droidboosters.yourdeals",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d("KeyHash:", keyHash);
                return keyHash;
            }
        } catch (NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        return null;
    }




}
