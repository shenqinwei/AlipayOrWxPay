package cn.lejiayuan.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：沈钦伟
 * 功能:alipay 支付工具类
 */

public class ALipayUtils {

    private static final int SDK_PAY_FLAG = 1;
    private Activity context;
    private ALiPayBuilder builder;

    private ALipayUtils(ALiPayBuilder builder) {
        this.builder = builder;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

//            返回码	含义
//            9000	订单支付成功
//            8000	正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
//            4000	订单支付失败
//            5000	重复请求
//            6001	用户中途取消
//            6002	网络连接出错
//            6004	支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
//            其它	其它支付错误
            AliPayResult payResult = new AliPayResult((Map<String, String>) msg.obj);
            switch (payResult.getResultStatus()) {
                case "9000":
                    Toast.makeText(context,"支付成功", Toast.LENGTH_SHORT).show();
                    break;
                case "8000":
                    Toast.makeText(context,"正在处理中", Toast.LENGTH_SHORT).show();
                    break;
                case "4000":
                    Toast.makeText(context,"订单支付失败", Toast.LENGTH_SHORT).show();
                    break;
                case "5000":
                    Toast.makeText(context,"重复请求", Toast.LENGTH_SHORT).show();
                    break;
                case "6001":
                    Toast.makeText(context,"已取消支付", Toast.LENGTH_SHORT).show();
                    break;
                case "6002":
                    Toast.makeText(context,"网络连接出错", Toast.LENGTH_SHORT).show();
                    break;
                case "6004":
                    Toast.makeText(context,"正在处理中", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(context,"支付失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };



    /**
     * 签名在服务端来做
     * @param context
     * @param orderInfo
     */
    public void toALiPay(final Activity context, final String orderInfo) {
        this.context = context;
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(context);
                Map<String, String> result = alipay.payV2
                        (orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }





    public static class ALiPayBuilder {
        public ALipayUtils build() {
            return new ALipayUtils(this);
        }
    }
}
