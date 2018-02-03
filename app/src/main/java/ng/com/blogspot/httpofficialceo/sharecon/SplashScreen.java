package ng.com.blogspot.httpofficialceo.sharecon;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.lusfold.spinnerloading.SpinnerLoading;

public class SplashScreen extends AppCompatActivity {

    private SpinnerLoading spinnerLoading;

    private final int SPLASH_DISPLAY_LENGTH = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        spinnerLoading = (SpinnerLoading) findViewById(R.id.spinner_loading);
        spinnerLoading.setPaintMode(1);
        spinnerLoading.setCircleRadius(20);
        spinnerLoading.setItemCount(8);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreen.this,InitialActivity.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}





