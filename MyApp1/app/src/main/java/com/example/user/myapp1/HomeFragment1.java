package com.example.user.myapp1;


import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment1 extends Fragment {

    private static final String PREF_NAME = "sample_twitter_pref";
    private static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    private static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    private static final String PREF_KEY_TWITTER_LOGIN = "is_twitter_loggedin";
    private static final String PREF_USER_NAME = "twitter_user_name";

    /* Any number for uniquely distinguish your request */
    public static final int WEBVIEW_REQUEST_CODE = 102;

    private ProgressDialog pDialog;

    private static Twitter twitter;
    private static RequestToken requestToken;

    private static SharedPreferences mSharedPreferences;

    private EditText mShareEditText;
    private TextView userName;
    private View loginLayout;
    private View shareLayout;

    private String consumerKey = null;
    private String consumerSecret = null;
    private String callbackUrl = null;
    private String oAuthVerifier = null;

    private Button buttonCapture, buttonUpload;
    private ImageView mImageView;
    private ProgressDialog mProgress;
    private static final int CAMERA_REQUEST_CODE = 1;
    private StorageReference mStorage, filepath;
    private FirebaseAuth mAuth;
    public String path;
    private Uri uri;
    private Uri download;
    private TextView mText;


    private static final int PICK_IMAGE_REQUEST = 234;

    private EditText editTextName;
    private TextView textViewShow;
    private StorageReference storageReference;
    private DatabaseReference mDatabase;
    private ImageView tImageView;
    public static String address;

    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 111;
    private TextView tvPlaceDetails;
    private Button fabPickPlace;
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");
    public static String mAddress;

    private TextView mTextMessage;

    public HomeFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home1, container, false);



    }
    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestForSpecificPermission (){
        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE }, 101);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                } else {
                    //not granted
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home");

        if (!checkIfAlreadyhavePermission()) {
            requestForSpecificPermission();
        }


        tImageView = (ImageView) view.findViewById(R.id.twitter_imgvw);

        initTwitterConfigs();

		/* Enabling strict mode */
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

		/* Setting activity layout file */


        /*loginLayout = (RelativeLayout) findViewById(R.id.login_layout);
        shareLayout = (LinearLayout) findViewById(R.id.share_layout);
        mShareEditText = (EditText) findViewById(R.id.share_text);
        userName = (TextView) findViewById(R.id.user_name);

		*//* register button click listeners *//*
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_share).setOnClickListener(this);

        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        imageView = (ImageView) findViewById(R.id.imageView);
        buttonChoose.setOnClickListener(this);
*/
		/* Check if required twitter keys are set */
        if (TextUtils.isEmpty(consumerKey) || TextUtils.isEmpty(consumerSecret)) {
            Toast.makeText(getActivity(), "Twitter key and secret not configured",
                    Toast.LENGTH_SHORT).show();
            return;
        }

		/* Initialize application preferences */
        mSharedPreferences = getActivity().getSharedPreferences(PREF_NAME, 0);

        final boolean isLoggedIn = mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
        userName = (TextView) view.findViewById(R.id.user_name);

		/*  if already logged in, then hide login layout and show share layout */
        if (isLoggedIn) {
            /*loginLayout.setVisibility(View.GONE);
            shareLayout.setVisibility(View.VISIBLE);*/
            tImageView.setVisibility(View.GONE);

            String username = mSharedPreferences.getString(PREF_USER_NAME, "");
            userName.setText("Welcome, "
                    + username);

        } else {
//            loginLayout.setVisibility(View.VISIBLE);
//            shareLayout.setVisibility(View.GONE);

            Uri uri = getActivity().getIntent().getData();

            if (uri != null && uri.toString().startsWith(callbackUrl)) {

                String verifier = uri.getQueryParameter(oAuthVerifier);

                try {

					/* Getting oAuth authentication token */
                    AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);

					/* Getting user id form access token */
                    long userID = accessToken.getUserId();
                    final User user = twitter.showUser(userID);
                    final String username = user.getName();

					/* save updated token */
                    saveTwitterInfo(accessToken);

                    // loginLayout.setVisibility(View.GONE);
                    // shareLayout.setVisibility(View.VISIBLE);
                    // userName.setText(getString(R.string.notice) + username);
                    Toast.makeText(getActivity(), "Sigin in Succesful", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Log.e("Failed to login Twitter", e.getMessage());
                }
            }

        }

        //mStorage = FirebaseStorage.getInstance().getReference().child("uploads");


        mAuth = FirebaseAuth.getInstance();

        buttonCapture = (Button) view.findViewById(R.id.buttonCapture);
        buttonUpload = (Button) view.findViewById(R.id.buttonUpload);
        mImageView = (ImageView) view.findViewById(R.id.imageView);
        editTextName = (EditText) view.findViewById(R.id.editText);
        tImageView = (ImageView) view.findViewById(R.id.twitter_imgvw);
        userName = (TextView) view.findViewById(R.id.user_name);
        textViewShow = (TextView) view.findViewById(R.id.locview);

        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        mDatabase.addChildEventListener(new ChildEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Notification.Builder noti = new Notification.Builder(getContext());
                Intent notificationIntent = new Intent(getActivity(), ShowImagesActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, notificationIntent, 0);

                noti.setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setContentTitle("Emergency")
                        .setContentText("An incident has occured")
                        .setSmallIcon(R.drawable.napp_icon)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.napp_icon))
                        .build();

                NotificationManager nm = (NotificationManager) getActivity().getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                nm.notify(123, noti.build());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mProgress = new ProgressDialog(getActivity());

        buttonCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);

            }
        });

        tImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginToTwitter();

                /*Intent intent1 = new Intent(getActivity(),WebViewActivity.class);
                startActivity(intent1);*/
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isLoggedIn) {
                    final String status = editTextName.getText().toString()+textViewShow.getText().toString();


                    if (status.trim().length() > 0 && path != null) {
                        new updateTwitterStatus().execute(status);
                    } else if (path == null) {
                        Toast.makeText(getActivity(), "Please Select an image!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Message is empty!!", Toast.LENGTH_SHORT).show();
                    }

                    uploadFile();
                } else {
                    uploadFile();
                }
               /* mProgress.setMessage("Uploading image....");
                mProgress.show();

                mStorage= storageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + "jpeg");

                FirebaseUser user = mAuth.getCurrentUser();

                System.out.println(path);
                //filepath = mStorage;
                        //.child(uri.getLastPathSegment() + ".jpg");

                //Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show();
                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        mProgress.dismiss();

                        Toast.makeText(getActivity(), "Image Uploaded", Toast.LENGTH_LONG).show();
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mProgress.dismiss();
                        Toast.makeText(getActivity(), "Image Uploading Failed....", Toast.LENGTH_LONG).show();
                    }
                });*/
            }
        });

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
       // ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        fabPickPlace = (Button) view.findViewById(R.id.fab);
        tvPlaceDetails = (TextView) view.findViewById(R.id.locview);

        mGoogleApiClient = new GoogleApiClient
                .Builder(getContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
//                .enableAutoManage(getActivity(), getActivity())
                .build();


        fabPickPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                    Log.d("hammad", "ahad");
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

    }



//       buttonCapture = (Button) view.findViewById(R.id.buttonChoose);
//        buttonUpload = (Button) view.findViewById(R.id.buttonUpload);
//        mImageView = (ImageView) view.findViewById(R.id.imageView);
//        editTextName = (EditText) view.findViewById(R.id.editText);
//        textViewShow = (TextView) view.findViewById(R.id.textViewShow);


        //storageReference = FirebaseStorage.getInstance().getReference();
       // mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    //@Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Snackbar.make(fabPickPlace, connectionResult.getErrorMessage() + "", Snackbar.LENGTH_LONG).show();
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getApplicationContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        //checking if file is available
        if (path != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Sending... Please wait.");
            progressDialog.show();

            //getting the storage reference
            StorageReference sRef = storageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(uri));

            //adding the file to reference
            sRef.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            progressDialog.dismiss();

                            //displaying success toast
                           // Toast.makeText(getActivity(), "File Sent ", Toast.LENGTH_LONG).show();
                            Snackbar.make(getView(), "File Sent", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();



                            @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            //creating the upload object to store uploaded image details
                          // Upload upload = new Upload(editTextName.getText().toString().trim(),downloadUrl.toString());
                            //adding an upload to firebase database
                            address= Constants.ADDRESS;
                            Log.d("hammy","adress:"+Constants.ADDRESS);
                            Upload upload = new Upload(editTextName.getText().toString().trim(),downloadUrl.toString(),address);

                            String uploadId = mDatabase.push().getKey();
                            mDatabase.child(uploadId).setValue(upload);


                            Intent intent = new Intent(getActivity(),Youtube.class);
                            startActivity(intent);

                        }

                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            //double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Sending...");
                        }
                    });
        } else {
            Toast.makeText(getActivity(),"Please Capture an Image!",Toast.LENGTH_SHORT).show();
        }
    }

    /*public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();

            final Bitmap bitmap = (Bitmap) extras.get("data");

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            path = MediaStore.Images.Media.insertImage(getActivity().getApplicationContext().getContentResolver(), bitmap, "Title", null);
            uri = Uri.parse(path);
            mImageView.setImageURI(uri);
        }
    }*/

    private void saveTwitterInfo(AccessToken accessToken) {

        long userID = accessToken.getUserId();

        User user;
        try {
            user = twitter.showUser(userID);

            String username = user.getName();

			/* Storing oAuth tokens to shared preferences */
            SharedPreferences.Editor e = mSharedPreferences.edit();
            e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
            e.putString(PREF_KEY_OAUTH_SECRET, accessToken.getTokenSecret());
            e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
            e.putString(PREF_USER_NAME, username);
            e.commit();

        } catch (TwitterException e1) {
            e1.printStackTrace();
        }
    }

    /* Reading twitter essential configuration parameters from strings.xml */
    private void initTwitterConfigs() {
        consumerKey = getString(R.string.twitter_consumer_key);
        consumerSecret = getString(R.string.twitter_consumer_secret);
        callbackUrl = getString(R.string.twitter_callback);
        oAuthVerifier = getString(R.string.twitter_oauth_verifier);
    }


    private void loginToTwitter() {
        boolean isLoggedIn = mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);

        if (!isLoggedIn) {

            final ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(consumerKey);
            builder.setOAuthConsumerSecret(consumerSecret);

            final Configuration configuration = builder.build();
            final TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();

            try {
                requestToken = twitter.getOAuthRequestToken(callbackUrl);

                /**
                 *  Loading twitter login page on webview for authorization
                 *  Once authorized, results are received at onActivityResult
                 *  */
                final Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(WebViewActivity.EXTRA_URL, requestToken.getAuthenticationURL());
                startActivityForResult(intent, WEBVIEW_REQUEST_CODE);

            } catch (TwitterException e) {
                e.printStackTrace();
            }
        } else {

            tImageView.setVisibility(View.GONE);

            /*loginLayout.setVisibility(View.GONE);
            shareLayout.setVisibility(View.VISIBLE);*/
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getContext(), data);
                StringBuilder stBuilder = new StringBuilder();
                String placename = String.format("%s", place.getName());
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                String address = String.format("%s", place.getAddress());

                Constants.ADDRESS = address;

                mAddress = (String) place.getAddress();
                Log.d("hamm", " place picker:" + mAddress);


                stBuilder.append("Name: ");
                stBuilder.append(placename);
                stBuilder.append("\n");

                stBuilder.append("Latitude: ");
                stBuilder.append(latitude);
                myRef = database.getReference("users");
                myRef.child("latitude").push().setValue(latitude.toString());

                stBuilder.append("\n");
                stBuilder.append("Longitude: ");
                stBuilder.append(longitude);
                myRef = database.getReference("users");
                myRef.child("longitude").push().setValue(longitude.toString());

                stBuilder.append("\n");
                stBuilder.append("Address: ");
                stBuilder.append(address);
                myRef = database.getReference("users");
                myRef.child("Address").push().setValue(address.toString());

                tvPlaceDetails.setText(address.toString());
            }

        }

        if (requestCode != PLACE_PICKER_REQUEST) {


            boolean isLoggedIn = mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
            if (!isLoggedIn) {
                if (resultCode == Activity.RESULT_OK) {
                    String verifier = data.getExtras().getString(oAuthVerifier);
                    try {
                        AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);

                        long userID = accessToken.getUserId();
                        final User user = twitter.showUser(userID);
                        String username = user.getName();

                        saveTwitterInfo(accessToken);

                        tImageView.setVisibility(View.GONE);
                        //loginLayout.setVisibility(View.GONE);
                        //shareLayout.setVisibility(View.VISIBLE);
                        userName.setText("Welcome, " + username);


                    } catch (Exception e) {
                        Log.e("Twitter Login Failed", e.getMessage());
                    }
                }
                if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {

                    Bundle extras = data.getExtras();

                    final Bitmap bitmap = (Bitmap) extras.get("data");

                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    path = MediaStore.Images.Media.insertImage(getActivity().getApplicationContext().getContentResolver(), bitmap, "Title", null);
                    uri = Uri.parse(path);
                    mImageView.setImageURI(uri);
                }

                super.onActivityResult(requestCode, resultCode, data);
            } else {
                super.onActivityResult(requestCode, resultCode, data);
           /* if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                filePath = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    imageView.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("hammad", "ahad");
            }*/
                if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {

                    Bundle extras = data.getExtras();

                    final Bitmap bitmap = (Bitmap) extras.get("data");

                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    path = MediaStore.Images.Media.insertImage(getActivity().getApplicationContext().getContentResolver(), bitmap, "Title", null);
                    uri = Uri.parse(path);
                    mImageView.setImageURI(uri);

                /*Bundle extras = data.getExtras();

                final Bitmap bitmap = (Bitmap) extras.get("data");*/

                    //ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                /*path = MediaStore.Images.Media.insertImage(getActivity().getApplicationContext().getContentResolver(), bitmap, "Title", null);
                uri = Uri.parse(path);
                mImageView.setImageURI(uri);*/
                }
            }
        }
    }

    class updateTwitterStatus extends AsyncTask<String, String, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Posting to twitter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected Void doInBackground(String... args) {

            String status = args[0];
            try {
                ConfigurationBuilder builder = new ConfigurationBuilder();
                builder.setOAuthConsumerKey(consumerKey);
                builder.setOAuthConsumerSecret(consumerSecret);

                // Access Token
                String access_token = mSharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, "");
                // Access Token Secret
                String access_token_secret = mSharedPreferences.getString(PREF_KEY_OAUTH_SECRET, "");

                AccessToken accessToken = new AccessToken(access_token, access_token_secret);
                Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);

                // Update status


                StatusUpdate statusUpdate = new StatusUpdate(status);
                //InputStream is = getResources().openRawResource(+R.drawable.kohli);
                if(uri!=null) {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), uri);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    ByteArrayInputStream fileInputStream = new ByteArrayInputStream(bytes.toByteArray());


                    statusUpdate.setMedia("test.jpg", fileInputStream);

                    twitter4j.Status response = twitter.updateStatus(statusUpdate);

                    Log.d("Status", response.getText());

                }
                else
                    Log.d("hammad", "ahad");
                // Toast.makeText(TwitterClass.this, "Select an image!", Toast.LENGTH_SHORT).show();
            } catch (TwitterException e) {
                Log.d("Failed to post!", e.getMessage());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {

			/* Dismiss the progress dialog after sharing */
            pDialog.dismiss();

            Toast.makeText(getActivity(), "Posted to Twitter!", Toast.LENGTH_LONG).show();

            // Clearing EditText field
            //mShareEditText.setText("");
        }

    }



}


