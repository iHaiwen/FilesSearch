package com.mobile.hw.filessearch.ui.util;

import android.content.*;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.*;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.*;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mobile.hw.filessearch.R;
import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;
    private static final int RED = 0xFFFF0000;
    private static final int CLEAR = 0;

    public final static int SCALE_IMAGE_SIZE = 700;

    private static final String TAG = Utils.class.getSimpleName();

    public static boolean hasShortCut(Context context) {
        if (context == null) {
            Log.i(TAG, "hasShortCut context == null");
            return false;
        }

        String url1 = "content://com.android.launcher.settings/favorites?notify=true";
        String url2 = "content://com.android.launcher2.settings/favorites?notify=true";

        try {
            ContentResolver resolver = context.getContentResolver();
            Cursor cursor = resolver.query(Uri.parse(url1), null, "title=?",
                new String[] { context.getString(R.string.app_name) }, null);
            if (cursor == null) {
                cursor = resolver.query(Uri.parse(url2), null, "title=?",
                    new String[] { context.getString(R.string.app_name) }, null);
            }

            boolean ret = false;
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    ret = true;
                }
                cursor.close();
            }

            Log.i(TAG, "hasShortCut return " + ret);
            return ret;
        } catch (Exception ex) {
            return true;
        }
    }

    public static String parseHost(Intent intent) {
        return (intent == null ? null : intent.getData()) == null ? "" : intent.getData().getHost();
    }

    /**
     * 提取重复代码，startAddShopActivity
     * 
     * @param context
     * @param keyword
     */
    public static void startAddShopActivity(Context context, String keyword) {

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("dianping://addshop?shopName="
                + (TextUtils.isEmpty(keyword) ? "" : keyword)));
        context.startActivity(i);
    }

    public static boolean isWap(Context mContext) {
        ConnectivityManager connManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo == null)
            return false;
        if (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return false;
        }
        if (activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = activeNetInfo.getExtraInfo();
            if (extraInfo == null)
                return false;
            extraInfo = extraInfo.toLowerCase();
            if (extraInfo.contains("cmnet"))
                return false;
            if (extraInfo.contains("cmwap"))
                return true;
            if (extraInfo.contains("3gnet"))
                return false;
            if (extraInfo.contains("3gwap"))
                return true;
            if (extraInfo.contains("uninet"))
                return false;
            if (extraInfo.contains("uniwap"))
                return true;
            if (extraInfo.contains("ctnet"))
                return false;
            if (extraInfo.contains("ctwap"))
                return true;
            if (extraInfo.contains("#777")) {
                try {
                    Cursor c = mContext.getContentResolver().query(
                        Uri.parse("content://telephony/carriers/preferapn"),
                        new String[] { "proxy", "port" }, null, null, null);
                    if (c.moveToFirst()) {
                        String host = c.getString(0);
                        if (host.length() > 3) {
                            return true;
                        }
                    }
                    return false;
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean checkPhotoSize(String imageUri) {
        try {
            FileInputStream ins = new FileInputStream(imageUri);
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(ins, null, opt);
            ins.close();
            if (opt.outWidth < 200 || opt.outHeight < 200) {
                return false;
            }
            return true;

        } catch (Exception e) {
            return true;
        }
    }

//    public static int getCategoryIconId(int id) {
//        switch (id) {
//            case 0:
//                return R.drawable.ic_category_0;
//            case 2:
//                return R.drawable.ic_category_20;
//            case 10:
//
//                return R.drawable.ic_category_10;
//            case 20:
//
//                return R.drawable.ic_category_20;
//            case 30:
//
//                return R.drawable.ic_category_30;
//
//            case 40:
//                return R.drawable.ic_category_80;
//
//            case 45:
//                return R.drawable.ic_category_45;
//
//            case 50:
//                return R.drawable.ic_category_50;
//
//            case 55:
//                return R.drawable.ic_category_55;
//            case 60:
//
//                return R.drawable.ic_category_60;
//
//            case 65:
//
//                return R.drawable.ic_category_65;
//            case 70:
//
//                return R.drawable.ic_category_70;
//
//            case 80:
//
//                return R.drawable.ic_category_80;
//            case 160:
//
//                return R.drawable.ic_category_160;
//            case 2002:
//
//                return R.drawable.ic_category_2002;
//            case -2147483648: // hard code　热门分类
//
//                return R.drawable.ic_category_2147483648;
//            default:
//                return R.drawable.ic_category_none;
//        }
//    }

    public static void hideKeyboard(View view) {
        ((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
            view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void popupKeyboard(final View view) {
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        view.requestFocus();
                        ((InputMethodManager) view.getContext().getSystemService(
                            Context.INPUT_METHOD_SERVICE)).toggleSoftInput(0,
                            InputMethodManager.HIDE_NOT_ALWAYS);
                        break;

                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        }.sendEmptyMessageDelayed(1, 300);

    }

//    public static boolean shouldImageInMobileNewwork(Context context, boolean isshown) {
//        if (!isshown && !DPUtils.isWIFIConnection(context)) {
//            return false;
//        }
//        return true;
//    }
//
//    public static boolean shouldImageInMobileNewwork(Context context) {
//        boolean showImage = preferences(context).getBoolean("isShowListImage", true);
//        if (!showImage && !DPUtils.isWIFIConnection(context)) {
//            return false;
//        }
//        return true;
//    }

    public static SharedPreferences preferences(final Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    // 从assets 文件夹中获取文件并读取数据
    public String getFromAssets(Context context, String fileName) {
        String result = "";
        try {
            InputStream in = context.getAssets().open(fileName);
            // 获取文件的字节数
            int lenght = in.available();
            // 创建byte数组
            byte[] buffer = new byte[lenght];
            // 将文件中的数据读到byte数组中
            in.read(buffer);
            result = EncodingUtils.getString(buffer, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Determines if given points are inside view
     * 
     * @param x
     *            - x coordinate of point
     * @param y
     *            - y coordinate of point
     * @param view
     *            - view object to compare
     * @return true if the points are within view bounds, false otherwise
     */
    public static boolean isPointInsideView(float x, float y, View view) {
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        // point is inside view bounds
        if ((x > viewX && x < (viewX + view.getWidth()))
                && (y > viewY && y < (viewY + view.getHeight()))) {
            return true;
        } else {
            return false;
        }
    }

//    public static String processParam(Context context, String url) {
//        int i = url.indexOf('?');
//        if (i < 0)
//            return url;
//        String head = url.substring(0, i + 1);
//        String params = url.substring(i + 1);
//        if (params.contains("agent=!")) {
//            params = params.replace("agent=!", "agent=android");
//        }
//        if (params.contains("agent=*")) {
//            params = params.replace("agent=*", "agent=android");
//        }
//        if (params.contains("version=!")) {
//            params = params.replace("version=!", "version=" + version(context));
//        }
//        if (params.contains("version=*")) {
//            params = params.replace("version=*", "version=" + version(context));
//        }
//        if (params.contains("screen=!")) {
//            params = params.replace("screen=!", "screen=" + screen(context));
//        }
//        if (params.contains("screen=*")) {
//            params = params.replace("screen=*", "screen=" + screen(context));
//        }
//
//        if (params.contains("phone=!")) {
//            params = params.replace("phone=!", "phone=" + phone());
//        }
//        if (params.contains("phone=*")) {
//            params = params.replace("phone=*", "phone=" + phone());
//        }
//        if (params.contains("sessionid=!")) {
//            params = params.replace("sessionid=!", "sessionid=" + sessionId());
//        }
//        if (params.contains("sessionid=*")) {
//            params = params.replace("sessionid=*", "sessionid=" + sessionId());
//        }
//        if (params.contains("deviceid=!")) {
//            params = params.replace("deviceid=!", "deviceid=" + deviceid());
//        }
//        if (params.contains("deviceid=*")) {
//            params = params.replace("deviceid=*", "deviceid=" + deviceid());
//        }
//        if (params.contains("uuid=!")) {
//            params = params.replace("uuid=!", "uuid=" + uuid());
//        }
//        if (params.contains("uuid=*")) {
//            params = params.replace("uuid=*", "uuid=" + uuid());
//        }
//        return head + params;
//    }

//    public static String version(Context context) {
//        String version = "4.1";
//        try {
//            if (DPApplication.instance().getPackageName().equals(context.getPackageName())) {
//                return Environment.versionName();
//            }
//            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
//            version = pi.versionName;
//        } catch (NameNotFoundException e) {
//        }
//        return version;
//    }

    public static String screen(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels + "x" + dm.heightPixels;
    }

//    public static String phone() {
//        try {
//            String phoneNo1 = Environment.phone();
//            if (phoneNo1 != null && phoneNo1.length() > 10) {
//                return phoneNo1;
//            } else {
//                return "";
//            }
//        } catch (Exception e) {
//            return "";
//        }
//    }
//
//    public static String sessionId() {
//        return Environment.sessionId();
//    }
//
//    public static String deviceid() {
//        return Environment.deviceId();
//    }
//
//    public static String uuid() {
//        return Environment.uuid();
//    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static boolean isIntentAvailable(final Context context, final Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        final List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list != null && list.size() > 0;
    }

    // 生成二维码
    public static Bitmap encodeAsBitmap(String contents, int dimen_width, int dimen_height)
            throws WriterException {
        Hashtable<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contents);
        if (encoding != null) {
            hints = new Hashtable<EncodeHintType, Object>(2);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        }
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix result = writer.encode(contents, BarcodeFormat.QR_CODE, dimen_width,
            dimen_height, hints);
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        // All are 0, or black, by default
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : CLEAR;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    public static boolean hasNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    private static final DecimalFormat DF = new DecimalFormat("######0.00");
    private static final int K = 1024;
    private static final int M = K * K;

    public static String formatByteSize(long itemSize) {
        String size = "";
        if (itemSize / M > 0) {
            size = DF.format(itemSize / (double) M) + "M";
        } else if (itemSize / K > 0) {
            size = DF.format(itemSize / (double) K) + "K";
        } else {
            size = itemSize + "Byte";
        }
        return size;
    }

    public static void fixBackgroundRepeat(View view) {
        Drawable bg = view.getBackground();
        if (bg != null) {
            if (bg instanceof BitmapDrawable) {
                BitmapDrawable bmp = (BitmapDrawable) bg;
                bmp.mutate(); // make sure that we aren't sharing state anymore
                bmp.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            }
        }
    }


    public static final DecimalFormat PRICE_DF = new DecimalFormat("#.##");

    public static String formatPrice(double price) {
        try {
            return PRICE_DF.format(price);
        } catch (Exception e) {
            return "#.##";
        }
    }

    public static String formatPrice(String price) {
        try {
            return formatPrice(Double.valueOf(price));
        } catch (Exception e) {
            return "#.##";
        }
    }

    // 保存图片
    public static void savePhotoToAlbum(ImageView view, final Context context) {
        if (!android.os.Environment.getExternalStorageState().equals(
            android.os.Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context, "您手机里没有内存卡，无法保存图片", Toast.LENGTH_LONG).show();
            return;
        }
        if (view.getDrawable() instanceof BitmapDrawable) {
            Bitmap mBitmap = ((BitmapDrawable) view.getDrawable()).getBitmap();
            if (mBitmap != null) {
                new AsyncTask<Bitmap, Void, Boolean>() {
                    @Override
                    public Boolean doInBackground(Bitmap... params) {
                        Bitmap bmp = params[0];
                        try {
                            File fDir = new File(
                                    android.os.Environment.getExternalStorageDirectory(),
                                    "dianping");
                            if (!fDir.exists()) {
                                fDir.mkdirs();
                            }

                            String name = "promphoto" + System.currentTimeMillis() + ".jpg";

                            File file = new File(fDir, name);
                            FileOutputStream os = new FileOutputStream(file);
                            bmp.compress(CompressFormat.JPEG, 100, os);
                            os.close();

                            ContentValues values = new ContentValues(7);
                            values.put(Images.Media.TITLE, name);
                            values.put(Images.Media.DISPLAY_NAME, name);
                            values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());
                            values.put(Images.Media.MIME_TYPE, "image/jpeg");
                            values.put(Images.Media.DATA, file.getAbsolutePath());

                            ContentResolver resolver = context.getContentResolver();
                            Cursor csr = resolver.query(Images.Media.EXTERNAL_CONTENT_URI, null,
                                Images.Media.DATA + "=?", new String[] { file.getAbsolutePath() },
                                null);
                            if (csr.moveToFirst()) {
                                long _id = csr.getLong(csr.getColumnIndex(Images.Media._ID));
                                Uri idUri = Uri.withAppendedPath(Images.Media.EXTERNAL_CONTENT_URI,
                                    "" + _id);
                                resolver.update(idUri, values, null, null);
                            } else {
                                resolver.insert(Images.Media.EXTERNAL_CONTENT_URI, values);
                            }
                            csr.close();

                            return true;
                        } catch (Exception e) {
                            return false;
                        }
                    }

                    @Override
                    public void onPostExecute(Boolean result) {
                        if (result.booleanValue()) {
                            Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                }.execute(mBitmap);
            } else {
                Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    // / time: e.g. DPObject.getTime("XXXX")
    // / dateFormat: e.g. yyyy-MM-dd E HH:mm
    // / timeZone: e.g. GMT+8
    public static String formatDate2TimeZone(long time, String dateFormat, String timeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        return sdf.format(time);
    }

    public static String formatDate2TimeZone(Date date, String dateFormat, String timeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        return sdf.format(date);
    }

    public static int transformPriceToValue(int price, boolean isLow) {
        if (price < 0) {
            return -1;
        }

        int[] a = { 0, 20, 50, 100, 200, 400, 800, 1400 };

        if (price <= a[1]) {
            if (isLow) {
                return price * 100 / (a[1] - a[0]);
            } else {
                return 0;
            }

        } else if (price >= a[a.length - 2]) {
            if (isLow) {
                return 100;
            } else {
                return (price - a[a.length - 2]) * 100 / (a[a.length - 1] - a[a.length - 2]);
            }

        }

        int i;
        for (i = 1; i < a.length - 1; i++) {
            if (price > a[i]) {
                continue;
            }
            if (price == a[i]) {
                break;
            }
            if (price < a[i]) {
                i--;
                break;
            }
        }

        if (i == a.length - 2) {
            return 100;
        }

        int fieldLength = 100 / (a.length - 3);

        return (price - a[i] + (i - 1) * (a[i + 1] - a[i])) * fieldLength / (a[i + 1] - a[i]);
    }

    public static String transformValueToPrice(int v, int field) {
        String LOG_TAG = Utils.class.getSimpleName();

        if (v < 0 || v > 100) {
            Log.e(LOG_TAG, "the value for transform is " + v
                    + ", which can't be smaller than 0 or bigger than 100");
            return null;
        }

        int[] a = { 0, 20, 50, 100, 200, 400, 800, 1400 };

        if (field == 0) {
            return String.valueOf(v * (a[1] - a[0]) / 100);
        } else if (field == 2) {
            if (v == 100) {
                return "不限";
            }
            return String.valueOf(v * (a[a.length - 1] - a[a.length - 2]) / 100 + a[a.length - 2]);
        }

        int fieldLength = 100 / (a.length - 3);
        int index = v / fieldLength;
        int more = v % fieldLength;

        int reuslt = (a[index + 2] - a[index + 1]) * more / fieldLength + a[index + 1];

        return String.valueOf(reuslt);
    }

//    /**
//     * 生成用于上传的图片，尺寸小于700
//     *
//     * @param imgUri
//     * @return
//     */
//    public static Bitmap createUploadImage(String imgUri, int direction) {
//
//        int sampling = 1;
//        Bitmap bitmap = null;
//        Bitmap resizedBitmap = null;
//
//        try {
//            // FileInputStream ins = new FileInputStream(imgUri);
//            BitmapFactory.Options opt = new BitmapFactory.Options();
//            opt.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(imgUri, opt);
//            // ins.close();
//
//            int size = opt.outWidth > opt.outHeight ? opt.outWidth : opt.outHeight;
//            sampling = (size / 1400) + 1;
//
//        } catch (Exception e) {
//            return null;
//        }
//
//        for (; sampling <= 8 && bitmap == null; sampling *= 2) {
//            try {
//                // FileInputStream ins = new FileInputStream(imgUri);
//                BitmapFactory.Options opt = new BitmapFactory.Options();
//                opt.inSampleSize = sampling;
//                bitmap = BitmapFactory.decodeFile(imgUri, opt);
//                // ins.close();
//            } catch (OutOfMemoryError oom) {
//                System.gc();
//                return null;
//            } catch (Exception e) {
//                return null;
//            }
//        }
//        if (bitmap == null) {
//            return null;
//        }
//        float scale = 0f;
//        try {
//            float origSize = bitmap.getWidth() > bitmap.getHeight() ? bitmap.getWidth()
//                    : bitmap.getHeight();
//            scale = SCALE_IMAGE_SIZE / origSize;
//            scale = ((int) scale) < 1 ? scale : 1; // Fix bug 图片在小于700*700不应该拉伸
//
//            resizedBitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * scale),
//                (int) (bitmap.getHeight() * scale), true);
//        } catch (Exception e) {
//            return null;
//        }
//        if (direction != 0) {
//            resizedBitmap = UploadPhotoUtil.rotateImg(resizedBitmap, direction);
//        }
//        return resizedBitmap;
//    }

    /*
     * Json字段包括：
     * "text" 文本 String
     * "textsize" 字体大小（单位：px）int
     * "textcolor" 字体颜色 String "#FF000000"(ARGB)
     * "backgroundcolor" 字体背景色 String "#FF000000"(ARGB)
     * "textstyle" 字体样式 String "Default"/"Bold"/"Italic/Bold_Italic"
     * "strikethrough" 字体是否删除线 boolean
     * "underline" 字体是否有下划线 boolean
     */
    public static SpannableStringBuilder jsonParseText(String textJsonString) {
        if (TextUtils.isEmpty(textJsonString))
            return null;
        
        SpannableStringBuilder resultString = new SpannableStringBuilder();

        JSONArray textJsonArray;

        try {
            textJsonArray = new JSONArray(textJsonString);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }

        for (int i = 0; i < textJsonArray.length(); i++) {
            JSONObject announceJson = null;
            try {
                announceJson = textJsonArray.getJSONObject(i);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                continue;
            }

            SpannableString spanText = new SpannableString(announceJson.optString("text"));

            if (announceJson.optInt("textsize") / 2 != 0) {
                spanText.setSpan((new AbsoluteSizeSpan(announceJson.optInt("textsize") / 2, true)),
                    0, spanText.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            if (stringParseColor(announceJson.optString("textcolor")) != Float.NaN) {
                spanText.setSpan(
                    new ForegroundColorSpan((int)stringParseColor(announceJson.optString("textcolor"))),
                    0, spanText.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            if (stringParseColor(announceJson.optString("backgroundcolor")) != Float.NaN) {
                spanText.setSpan(
                    new BackgroundColorSpan(
                            (int)stringParseColor(announceJson.optString("backgroundcolor"))), 0,
                    spanText.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }

            if (!TextUtils.isEmpty(announceJson.optString("textstyle"))) {
                String style = announceJson.optString("textstyle");

                int styleCode = Typeface.NORMAL;

                if (style.equalsIgnoreCase("Bold")) {
                    styleCode = Typeface.BOLD;
                }
                if (style.equalsIgnoreCase("Italic")) {
                    styleCode = Typeface.ITALIC;
                }
                if (style.equalsIgnoreCase("Bold_Italic")) {
                    styleCode = Typeface.BOLD_ITALIC;
                }

                spanText.setSpan(new StyleSpan(styleCode), 0, spanText.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }

            if (announceJson.optBoolean("strikethrough")) {
                spanText.setSpan(new StrikethroughSpan(), 0, spanText.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }

            if (announceJson.optBoolean("underline")) {
                spanText.setSpan(new UnderlineSpan(), 0, spanText.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }

            resultString.append(spanText);

        }

        return resultString;
    }

    public static float stringParseColor(String color) {
        if (TextUtils.isEmpty(color))
            return Float.NaN;
        try {
            return Color.parseColor(color);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return Float.NaN;
        }
    }

}
