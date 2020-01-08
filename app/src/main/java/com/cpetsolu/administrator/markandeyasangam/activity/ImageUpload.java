package com.cpetsolu.administrator.markandeyasangam.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.cpetsolu.administrator.markandeyasangam.R;
import com.cpetsolu.administrator.markandeyasangam.util.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ImageUpload extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 2;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private static final int GALLERY = 1;
    private static final int CAMPERMREQUEST =1 ;
    private static  Dialog PopUpDialog  ;
    private  ImageView imageView;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri filepath;
    private Bitmap bitmap;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 200;
    private View PopUpView;
    private String mCurrentPhotoPath;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        mQueue = Volley.newRequestQueue(ImageUpload.this);

        requestStoragePermission();

        imageView = (ImageView) findViewById(R.id.imageView);
    }

    private void requestStoragePermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE }, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.RESULT_CANCELED) {
            return;
        }

        if (requestCode == REQUEST_TAKE_PHOTO) {
            Log.i("Camera----> ", "Request");
            Log.i("mCurrentPhotoPath----> ", mCurrentPhotoPath);
//                uploadMultipart(mCurrentPhotoPath);
            Bitmap bm = BitmapFactory.decodeFile(mCurrentPhotoPath);
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 90, bao);

            uploadImage(bm);
        }

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY) {
                if (data != null) {
                    Uri contentURI = data.getData();
                    try {

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                         imageView.setImageBitmap(bitmap);
                        ByteArrayOutputStream bao = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);

                        uploadImage(bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void selectImage(View view) {
        if (ContextCompat.checkSelfPermission(ImageUpload.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.i("Camera Permission","DENIED");
            ActivityCompat.requestPermissions(ImageUpload.this, new String[]{Manifest.permission.CAMERA},  MY_PERMISSIONS_REQUEST_CAMERA);
            checkPermissionOnAppPermScreen("Camera");
        }else if(ContextCompat.checkSelfPermission(ImageUpload.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            Log.i("Storage Permission","DENIED");
            ActivityCompat.requestPermissions(ImageUpload.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_STORAGE);
            checkPermissionOnAppPermScreen("Storage");
        }
        else{
            ShowUploadOptions();
            Log.i("C & S Permission","GRANTED");
        }
    }
    private void ShowUploadOptions() {
        try{
            PopUpView = View.inflate(ImageUpload.this, R.layout.upload_options_view, null);
            PopUpView.startAnimation(AnimationUtils.loadAnimation(ImageUpload.this, R.anim.zoom_in_enter));
            PopUpDialog = new Dialog(ImageUpload.this, R.style.NewDialog);
            PopUpDialog.setContentView(PopUpView);
            PopUpDialog.setCancelable(true);
            PopUpDialog.show();

            Window window = this.PopUpDialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER | Gravity.CENTER;
            window.setGravity(Gravity.CENTER);
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
            wlp.dimAmount = 0.0f;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            wlp.windowAnimations = R.anim.slide_move;

            window.setAttributes(wlp);
            window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

            TextView TvCamera=(TextView)PopUpView.findViewById(R.id.tvCamera);
            TextView TvOther=(TextView)PopUpView.findViewById(R.id.tvOther);
            TextView TvHeading=(TextView)PopUpView.findViewById(R.id.tvPpUpHeading);
            TvHeading.setText("Choose option");
            ImageView v = (ImageView) this.PopUpView.findViewById(R.id.closeDialog);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopUpDialog.dismiss();
                }
            });
            TvCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopUpDialog.dismiss();
                    if (Build.VERSION.SDK_INT >= 23) {
                        String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        if (!hasPermissions(ImageUpload.this, PERMISSIONS)) {
                            ActivityCompat.requestPermissions(ImageUpload.this, PERMISSIONS, CAMPERMREQUEST );
                        } else {
                            captureImage();
                        }
                    }else{
                        captureImage();
                    }
                }
            });
            TvOther.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopUpDialog.dismiss();

                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(galleryIntent, GALLERY);
                }
            });

        }catch (Exception e){e.printStackTrace();}
    }

    private void captureImage() {
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        String storageDir = Environment.getExternalStorageDirectory() + "/TotalLSP";
        File dir = new File(storageDir);
        if (!dir.exists())
            dir.mkdir();

        File image = new File(storageDir + "/" + imageFileName + ".jpg");

        mCurrentPhotoPath = image.getAbsolutePath();
        Log.i("Path ", "photo path = " + mCurrentPhotoPath);
        return image;
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void checkPermissionOnAppPermScreen(String perm) {
        try {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar
                    .make(parentLayout, perm+" Permission are mandatory to access.", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });

            snackbar.setActionTextColor(Color.RED);

            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }catch (Exception e){e.printStackTrace();}
    }

    private void uploadImage(final Bitmap bitmap){

        String upload_URL = "http://cpetsol.com/MS/mRest/imageUpload/900000010201";
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(com.android.volley.Request.Method.POST, upload_URL,
                new com.android.volley.Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.d("resoo",new String(response.data));
                        mQueue.getCache().clear();
                        try {
                            JSONObject mainObject = new JSONObject(new String(response.data));

                            Log.i("Profile Obj-->",mainObject.toString()   );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "File is not Supported.Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                //params.put("file", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                mQueue.add(volleyMultipartRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}

