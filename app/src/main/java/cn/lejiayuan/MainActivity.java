package cn.lejiayuan;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.lejiayuan.alipay.ALipayUtils;


public class MainActivity extends AppCompatActivity {

    private IWXAPI iwxapi; //微信支付api
    private String APP_ID="wx86c19a9478abc351";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iwxapi = WXAPIFactory.createWXAPI(MainActivity.this, "wx86c19a9478abc351",false); //初始化微信api
        iwxapi.registerApp("wx86c19a9478abc351"); //注册appid  appid可以在开发平台获取

        //微信支付
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestCemera();
                //假装请求了服务器 获取到了所有的数据,注意参数不能少
                WXPayUtils.WXPayBuilder builder = new WXPayUtils.WXPayBuilder();
                builder.setAppId("wx86c19a9478abc351")
                        .setPartnerId("1349457101")
                        .setPackageValue("Sign=WXPay")
                        .setPrepayId("wx021756462978283e0fa10cba3327370131")
                        .setNonceStr("BfqDn48a54UcFXsX")
                        .setTimeStamp(1541152255+"")
                        .setSign("5FF922943813F1B38856F3212A6EEFA6ADC94F596729B79C6C0B9B40C636809D")
                        .build().toWXPayAndSign(MainActivity.this, APP_ID,"da87a03389d0f45f95dcfca34ea91dc0");

            }
        }); //支付宝支付
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestCemera();
                ALipayUtils.ALiPayBuilder aLiPayBuilder=new ALipayUtils.ALiPayBuilder();
                aLiPayBuilder.build().toALiPay(MainActivity.this,"");
            }
        });

    }

    private void requestCemera() {
        if (PermissionsUtil.hasPermission(this, Manifest.permission.READ_PHONE_STATE)) {
            //有访问摄像头的权限
        } else {
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permissions) {

                }


                @Override
                public void permissionDenied(@NonNull String[] permissions) {
                    //用户拒绝了访问摄像头的申请
                }
            }, new String[]{Manifest.permission.READ_PHONE_STATE});
        } if (PermissionsUtil.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //有访问摄像头的权限
        } else {
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permissions) {
                    //用户授予了访问摄像头的权限

                }


                @Override
                public void permissionDenied(@NonNull String[] permissions) {
                    //用户拒绝了访问摄像头的申请
                }
            }, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
        }
    }




}
