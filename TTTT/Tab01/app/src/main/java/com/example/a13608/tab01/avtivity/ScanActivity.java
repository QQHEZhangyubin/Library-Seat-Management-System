package com.example.a13608.tab01.avtivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.a13608.tab01.R;
import com.example.a13608.tab01.utils.ToastHelper;

import java.io.IOException;

import cn.simonlee.xcodescanner.core.CameraScanner;
import cn.simonlee.xcodescanner.core.GraphicDecoder;
import cn.simonlee.xcodescanner.core.NewCameraScanner;
import cn.simonlee.xcodescanner.core.OldCameraScanner;
import cn.simonlee.xcodescanner.core.ZBarDecoder;
import cn.simonlee.xcodescanner.view.AdjustTextureView;
import cn.simonlee.xcodescanner.view.ScannerFrameView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ScanActivity extends AppCompatActivity implements CameraScanner.CameraListener,TextureView.SurfaceTextureListener,
GraphicDecoder.DecodeListener,View.OnClickListener{
    private AdjustTextureView mTextureView;
    private CameraScanner mCameraScanner;
    private String TAG="XCodeScanner";
    private ScannerFrameView mScannerFrameView;
    protected GraphicDecoder mGraphicDecoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        mTextureView = findViewById(R.id.textureview);
        mScannerFrameView = findViewById(R.id.scannerframe);

        findViewById(R.id.btn_flash).setOnClickListener(this);

        /*
        * 注意，SDK21的设备是可以使用NewCameraScanner的，但是可能存在对新API支持不够的情况，比如红米Note3（双网通Android5.0.2）
        * 开发者可自行配置使用规则，比如针对某设备型号过滤，或者针对某SDK版本过滤
        * */
        if ( Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            mCameraScanner = NewCameraScanner.getInstance();
        } else {
            mCameraScanner = OldCameraScanner.getInstance();
        }
        mCameraScanner.setCameraListener(this);
        mTextureView.setSurfaceTextureListener(this);
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, getClass().getName() + ".onRestart()");
        if (mTextureView.isAvailable()) {
            //部分机型转到后台不会走onSurfaceTextureDestroyed()，因此isAvailable()一直为true，转到前台后不会再调用onSurfaceTextureAvailable()
            //因此需要手动开启相机
            mCameraScanner.setSurfaceTexture(mTextureView.getSurfaceTexture());
            mCameraScanner.setPreviewSize(mTextureView.getWidth(), mTextureView.getHeight());
            mCameraScanner.openCamera(this.getApplicationContext());
        }
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, getClass().getName() + ".onPause()");
        mCameraScanner.closeCamera();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, getClass().getName() + ".onDestroy()");
        mCameraScanner.setGraphicDecoder(null);
        if (mGraphicDecoder != null) {
            mGraphicDecoder.setDecodeListener(null);
            mGraphicDecoder.detach();
        }
        mCameraScanner.detach();
        super.onDestroy();
    }
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Log.e(TAG, getClass().getName() + ".onSurfaceTextureAvailable() width = " + width + " , height = " + height);
        mCameraScanner.setSurfaceTexture(surface);
        mCameraScanner.setPreviewSize(width, height);
        mCameraScanner.openCamera(this.getApplicationContext());
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        Log.e(TAG, getClass().getName() + ".onSurfaceTextureSizeChanged() width = " + width + " , height = " + height);
        // TODO 当View大小发生变化时，要进行调整。
//        mTextureView.setImageFrameMatrix();
//        mCameraScanner.setPreviewSize(width, height);
//        mCameraScanner.setFrameRect(mScannerFrameView.getLeft(), mScannerFrameView.getTop(), mScannerFrameView.getRight(), mScannerFrameView.getBottom());
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        Log.e(TAG, getClass().getName() + ".onSurfaceTextureDestroyed()");
        return true;
    }

    @Override// 每有一帧画面，都会回调一次此方法
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    @Override
    public void openCameraSuccess(int frameWidth, int frameHeight, int frameDegree) {
        Log.e(TAG, getClass().getName() + ".openCameraSuccess() frameWidth = " + frameWidth + " , frameHeight = " + frameHeight + " , frameDegree = " + frameDegree);
        mTextureView.setImageFrameMatrix(frameWidth, frameHeight, frameDegree);
        if (mGraphicDecoder == null) {
            mGraphicDecoder = new ZBarDecoder();//使用带参构造方法可指定条码识别的格式
            mGraphicDecoder.setDecodeListener(this);
        }
        //该区域坐标为相对于父容器的左上角顶点。
        //TODO 应考虑TextureView与ScannerFrameView的Margin与padding的情况
        mCameraScanner.setFrameRect(mScannerFrameView.getLeft(), mScannerFrameView.getTop(), mScannerFrameView.getRight(), mScannerFrameView.getBottom());
        mCameraScanner.setGraphicDecoder(mGraphicDecoder);
    }
    @Override
    public void openCameraError() {
        ToastHelper.showToast("出错了", ToastHelper.LENGTH_SHORT);
    }

    @Override
    public void noCameraPermission() {
       // ToastHelper.showToast("没有权限", ToastHelper.LENGTH_SHORT);
    }

    @Override
    public void cameraDisconnected() {
        ToastHelper.showToast("断开了连接", ToastHelper.LENGTH_SHORT);
    }

    int mCount = 0;
    String mResult = null;

    @Override
    public void decodeComplete(String result, int type, int quality, int requestCode) {
        if (result == null) return;
        if (result.equals(mResult)) {
            if (++mCount > 3) {//连续四次相同则显示结果（主要过滤脏数据，也可以根据条码类型自定义规则）
              /*  if (quality < 10) {
                    ToastHelper.showToast("[类型" + type + "/精度00" + quality + "]" + result, ToastHelper.LENGTH_SHORT);
                } else if (quality < 100) {
                    ToastHelper.showToast("[类型" + type + "/精度0" + quality + "]" + result, ToastHelper.LENGTH_SHORT);
                } else {
                    ToastHelper.showToast("[类型" + type + "/精度" + quality + "]" + result, ToastHelper.LENGTH_SHORT);
                }*/
                /**
                 * 得到二维码扫描结果
                 */
                Toast.makeText(ScanActivity.this, result, Toast.LENGTH_SHORT).show();
                if (isUrl(result)){
                    Intent intent=new Intent(ScanActivity.this,webActivity.class);
                    intent.putExtra("url", result);
                    startActivity(intent);
                }else {
                    switch (result){
                        case "E401":
                            send("签到",result);
                            break;
                        case "M206":
                            send("签到",result);
                            break;
                        case "M201":
                            send("签到",result);
                            break;
                        case "M211":
                            send("签到",result);
                            break;
                        case "M101":
                            send("签到",result);
                            break;
                        case "W408":
                            send("签到",result);
                            break;
                        case "W401":
                            send("签到",result);
                            break;
                        case "W207":
                            send("签到",result);
                            break;
                        default:
                            Toast.makeText(ScanActivity.this,"扫描二维码内容不合法",Toast.LENGTH_SHORT).show();
                            break;
                    }

                }
            }
        } else {
            mCount = 1;
            mResult = result;
        }
        Log.d(TAG, getClass().getName() + ".decodeComplete() -> " + mResult);
    }
    /**
     * 点击按钮之后，发送状态
     */
    private void send(String value, String result) {
        String url1="";
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody body=new FormBody.Builder()
                .add("state",value)
                .add("room",result)
                .build();
        Request request=new Request.Builder()
                .url(url1)
                .post(body)
                .build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();
                String str=response.body().string();
                if(str.equals("")){
                    Toast.makeText(ScanActivity.this,"签到已成功，请返回上一步预约座位",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ScanActivity.this,"签到失败",Toast.LENGTH_SHORT).show();


                }
                /**
                 * 根据服务器返回结果弹出Toast
                 */

                Looper.loop();
            }
        });

    }

    /**
     * 判断二维码扫描的结果是否为url
     * @param result
     * @return
     */
    private boolean isUrl(String result) {
        result=result.toLowerCase();
        String [] regex= {"http://","https://"};
        boolean isUrl=false;
        for(int i=0;i<regex.length;i++){
            isUrl=isUrl || (result.contains(regex[i]))&& result.indexOf(regex[i])==0;
        }
        return isUrl;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_flash) {
            if (v.isSelected()) {
                ((Button) v).setText(R.string.flash_open);
                v.setSelected(false);
                mCameraScanner.closeFlash();
            } else {
                ((Button) v).setText(R.string.flash_close);
                v.setSelected(true);
                mCameraScanner.openFlash();
            }
        }
    }
}
