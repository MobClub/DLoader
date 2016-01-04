package m.dloader.demo;

import java.util.HashMap;

import m.dloader.impl.SMSSDK;
import m.dloader.impl.SMSSDK.EventHandler;
import m.dloader.impl.SMSSDK.RegisterPage;
import m.dloader.impl.ShareSDK;
import m.dloader.impl.ShareSDK.OnekeyShare;
import m.dloader.impl.ShareSDK.Platform;
import m.dloader.impl.ShareSDK.PlatformActionListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.btnShowUser).setOnClickListener(this);
		findViewById(R.id.btnShare).setOnClickListener(this);
		findViewById(R.id.btnVerify).setOnClickListener(this);
		ShareSDK.initSDK(this);
		SMSSDK.initSDK(this, "d73c397917b2", "cd963a9fcc26e89299dfb689839bd39d");
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnShowUser: {
				Platform plat = ShareSDK.getPlatform("SinaWeibo");
				plat.SSOSetting(true);
				plat.setPlatformActionListener(new PlatformActionListener() {
					public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
						Toast.makeText(MainActivity.this, res.toString(), Toast.LENGTH_SHORT).show();
					}
					
					public void onCancel(Platform platform, int action) {
						Toast.makeText(MainActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
					}
					
					public void onError(Platform platform, int action, Throwable t) {
						Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
					}
				});
				plat.showUser(null);
			} break;
			case R.id.btnShare: {
				OnekeyShare oks = new OnekeyShare();
				oks.disableSSOWhenAuthorize();
				oks.setText("Shared by dloader");
				oks.setImageUrl("http://start.firefoxchina.cn/img/worldindex/logo.png");
				oks.setCallback(new PlatformActionListener() {
					public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
						Toast.makeText(MainActivity.this, res.toString(), Toast.LENGTH_SHORT).show();
					}
					
					public void onCancel(Platform platform, int action) {
						Toast.makeText(MainActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
					}
					
					public void onError(Platform platform, int action, Throwable t) {
						Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
					}
				});
				oks.show(this);
			} break;
			case R.id.btnVerify: {
				RegisterPage page = new RegisterPage();
				page.setRegisterCallback(new EventHandler() {
					public void afterEvent(int event, int result, Object data) {
						String msg = "event: " + event + "\n";
						msg += "result: " + result + "\n";
						msg += "data: " + data;
						Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
					}
				});
				page.show(this);
			} break;
		}
	}
	
}
