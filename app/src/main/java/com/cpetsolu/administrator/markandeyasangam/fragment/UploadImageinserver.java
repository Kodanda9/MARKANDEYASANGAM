package com.cpetsolu.administrator.markandeyasangam.fragment;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cpetsolu.administrator.markandeyasangam.R;
import com.cpetsolu.administrator.markandeyasangam.activity.MainActivity;
import com.cpetsolu.administrator.markandeyasangam.helper.AppHelper;
import com.cpetsolu.administrator.markandeyasangam.util.AppHelper1;
import com.cpetsolu.administrator.markandeyasangam.util.MyApplication;
import com.cpetsolu.administrator.markandeyasangam.util.SessinSave;
import com.cpetsolu.administrator.markandeyasangam.util.SessionManager;
import com.cpetsolu.administrator.markandeyasangam.util.VolleyMultipartRequest;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadImageinserver extends Fragment {

    private View rootView;
    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView mAvatar;
    private Uri mCropImageUri;
    private LinearLayout mContainer;
    private SessionManager mSessionManager;

    public UploadImageinserver() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);

        }try {

            rootView = inflater.inflate(R.layout.fragment_upload_image, container, false);
            AppHelper.setupHideleyboard(rootView, getActivity());
            getActivity().setTitle("Upload Profile Image");
            mSessionManager = new SessionManager(getActivity());

            mContainer = rootView.findViewById(R.id.container);
            mAvatar =rootView. findViewById(R.id.profile_avatar);
            ImageView cameraAction =rootView. findViewById(R.id.camera_action);

            if (!"hello".equalsIgnoreCase(mSessionManager.getUrl())) {
                Picasso.get().load(mSessionManager.getUrl()).placeholder(R.drawable.lissa).into(mAvatar);
            }
            cameraAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectImage();
                }
                private void selectImage() {
                    CropImage.startPickImageActivity(getActivity());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return rootView;
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(getActivity(), data);

            if (CropImage.isReadExternalStoragePermissionsRequired(getActivity(), imageUri)) {
                mCropImageUri = imageUri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                }
            } else {
                startCropImageActivity(imageUri);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri uri = result.getUri();
                try {
                    File file = new Compressor(getActivity()).compressToFile(new File(uri.getPath()));
                    Picasso.get().load(file).into(mAvatar);
                    Toast.makeText(getActivity(), "Compressed", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed Compress", Toast.LENGTH_SHORT).show();
                    Picasso.get().load(uri).into(mAvatar);
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        uploadAvatar();
                    }
                }, 1000);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getActivity(), "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCropImageActivity(mCropImageUri);
        } else {
            Toast.makeText(getActivity(), "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAllowFlipping(false)
                .setActivityTitle("Crop Image")
                .setCropMenuCropButtonIcon(R.drawable.ic_check)
                .setAllowRotation(true)
                .setInitialCropWindowPaddingRatio(0)
                .setFixAspectRatio(true)
                .setAspectRatio(1, 1)
                .setOutputCompressQuality(80)
                .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                .setMultiTouchEnabled(true)
                .start(getActivity());
    }

    private void uploadAvatar() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        progressDialog.setMessage("Uploading image...");
        progressDialog.show();

        final String id = "1";
        String url = "http://cpetsol.com/MS/mRest/imageUpload/"+SessinSave.getsessin("id",getActivity());

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                progressDialog.dismiss();
                String resultResponse = new String(response.data);
                try {
                    JSONObject obj = new JSONObject(resultResponse);
                    if (!obj.getBoolean("error")) {
                        String avatar = obj.getString("avatar");
                        mSessionManager.setUrl(avatar);
                        Toast.makeText(getActivity(), "Avatar Changed", Toast.LENGTH_SHORT).show();

                    } else {
                        Log.d(TAG, "Response: " + resultResponse);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "JSON Error: " + e);
                    showUploadSnackBar();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d(TAG, "Volley Error: " + error);
                showUploadSnackBar();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                if (mAvatar == null) {
                    Log.i(TAG, "avatar null");
                }
                params.put("file", new DataPart("img_" + id + ".jpg", AppHelper1.getFileDataFromDrawable(getActivity(), mAvatar.getDrawable()), "image/jpg"));
                return params;
            }
        };

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().addToRequestQueue(multipartRequest);
    }


    private void showUploadSnackBar() {
        Snackbar.make(mContainer, "Network Error. Failed to upload image", Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uploadAvatar();
                    }
                }).show();
    }

}
