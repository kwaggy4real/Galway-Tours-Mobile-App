package com.example.galwaytour;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.galwaytour.*;
import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LikeView;

import java.io.*;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Toast;
import java.io.Console;
import java.util.Map;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ViewFlipper;


public class DisplaySelected extends ActionBarActivity{

    TextView displayName;
    TextView displayAddress;
    TextView displayWebsite;
    TextView displayInformation;
    TextView displayTelNum;
    TextView displayEmail;
    TextView displayRating;
    private ShareActionProvider mShareActionProvider;
    private UiLifecycleHelper uiHelper;
    private static final String TAG = "Display Selected";
    private TextView userDetails;
    public List<String> id_list = new ArrayList<String>();
    public String URL = null;
    public List<Bitmap> images = new ArrayList<Bitmap>();
    public   ImageView userpicture;
    static int count = 0;
    Button prev;
    Button next;
    GestureDetector ges_detector;
    private ViewFlipper mFlipper;
    private int mCurrentLayoutState, mCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.galwaytour.R.layout.displayselected);

        android.app.ActionBar actionbar = getActionBar();
        actionbar.setTitle("Display Details");

        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        displayName = (TextView) findViewById(com.example.galwaytour.R.id.displayName);
        String value = getIntent().getStringExtra("name");
        displayName.setText("Name: " + value);

        displayAddress = (TextView) findViewById(com.example.galwaytour.R.id.displayAddress);
        String value2 = getIntent().getStringExtra("address");
        displayAddress.setText("Address: " + value2);

        displayWebsite = (TextView) findViewById(com.example.galwaytour.R.id.displayWebsite);
        final String value3 = getIntent().getStringExtra("website");
        displayWebsite.setText("Website: " + value3);

        displayInformation = (TextView) findViewById(com.example.galwaytour.R.id.displayInformation);
        String value4 = getIntent().getStringExtra("information");
        displayInformation.setText("Information: " + value4);

        displayTelNum = (TextView) findViewById(com.example.galwaytour.R.id.displayTelNum);
        String value5 = getIntent().getStringExtra("tel_num");
        Long i = Long.parseLong(value5);

        displayTelNum.setText("Telephone Number: " + i);

        displayEmail = (TextView) findViewById(com.example.galwaytour.R.id.displayEmail);
        String value6 = getIntent().getStringExtra("email");
        displayEmail.setText("Email: " + value6);

        displayRating = (TextView) findViewById(com.example.galwaytour.R.id.displayRating);
        // String value7 =getIntent().getStringExtra("rating");
        String value7 = "4.2";
        float rating = Float.parseFloat(value7);
        displayRating.setText("Rating: " + value7 + "/5");

        String id = getIntent().getStringExtra("image id");
        URL = "http://danu6.it.nuigalway.ie/dmadugu1/View_Uploads/Get_Image_ID.php?imageid=" + id;

        RatingBar ratingBar = (RatingBar) findViewById(com.example.galwaytour.R.id.ratingBar);
        ratingBar.setRating(rating);

        ges_detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2,
                                   float velocityX, float velocityY) {

                if(e1.getX()- 250 > e2.getX())
                {

                    if(images.isEmpty() == false) {
                        if (count < images.size() - 1) {
                            count = count + 1;
                            Log.i("Count", Integer.toString(count));
                        }
                        userpicture = (ImageView) findViewById(com.example.galwaytour.R.id.displayImage);
                        userpicture.setVisibility(View.VISIBLE);
                        userpicture.setImageBitmap(images.get(count));
                    }
                }
                else if(e1.getX() < e2.getX() - 250)
                {

                    if(images.isEmpty() == false) {
                        if (count > 0) {
                            userpicture = (ImageView) findViewById(com.example.galwaytour.R.id.displayImage);
                            userpicture.setVisibility(View.VISIBLE);
                            userpicture.setImageBitmap(images.get(count - 1));
                            count = count - 1;
                        } else if (count == 0) {
                            count = 0;
                        }
                    }
                }
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

        });

        try {

            new LoadImage().execute();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        final LikeView likeView = (LikeView) findViewById(com.example.galwaytour.R.id.likeButton);
        try {
            likeView.setObjectId(value3);
        } catch (FacebookException facebookException) {
            facebookException.printStackTrace();
        }

        if (FacebookDialog.canPresentShareDialog(getApplicationContext(),
                FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
            Log.i("FAcebook Dialog", "Can use facebook dialog");
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return ges_detector.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean handled = super.dispatchTouchEvent(ev);
        handled = ges_detector.onTouchEvent(ev);
        return handled;
    }

    private String buildUserInfoDisplay(GraphUser user) {

        StringBuilder userInfo = new StringBuilder("");

        userInfo.append(String.format("Name: " + user.getName()));
        Log.i("User Data", user.getName());

        return userInfo.toString();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.example.galwaytour.R.menu.main, menu);

        MenuItem menuItem = menu.findItem(com.example.galwaytour.R.id.menu_item_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        if (item.getItemId() == com.example.galwaytour.R.id.action_settings) {
            Toast.makeText(getBaseContext(), "Clicked settings", Toast.LENGTH_LONG).show();
            return true;
        } else if (item.getItemId() == com.example.galwaytour.R.id.menu_item_share) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello, from David Madugu");
            int i = Intent.ACTION_SEND.length();
            Log.i("intent", Integer.toString(i));
            startActivity(Intent.createChooser(shareIntent, "Share your thoughts"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private com.facebook.Session.StatusCallback callback = new com.facebook.Session.StatusCallback() {
        @Override
        public void call(com.facebook.Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");

        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
            @Override
            public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle bundle) {
                Log.i("Activity", "Success");
            }

            @Override
            public void onError(FacebookDialog.PendingCall pendingCall, Exception e, Bundle bundle) {
                Log.e("Activity", String.format("Error: %s", e.toString()));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        //Destroying Activity
        finish();
    }



    private class LoadImage extends AsyncTask<Void, Void, List<String>> {
        AndroidHttpClient httpClient = AndroidHttpClient.newInstance("");

        @Override
        protected List<String> doInBackground(Void... arg0) {

            HttpGet request = new HttpGet(URL);
            Log.i("URL", URL);
            GetJsonResponse responseHandler = new GetJsonResponse();
            try {
                httpClient.execute(request, responseHandler);

                if (id_list.isEmpty() == false) {
                    for (int i = 0; i < id_list.size(); i++) {
                        URL img_value = null;
                        Bitmap image = null;
                        try {
                            //InputStream in = new java.net.URL("http://graph.facebook.com/10205084551986696/picture?type=large").openStream();
                            InputStream in = new java.net.URL("http://danu6.it.nuigalway.ie/dmadugu1/View_Uploads/Get_Images.php?id=" + id_list.get(i)).openStream();
                            image = BitmapFactory.decodeStream(in);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (image == null) {

                            Log.i("Image Output", "mIcon is empty");
                        } else {
                            images.add(image);
                            Log.i("Image Output", "mIcon is not empty");
                        }
                    }
                    Log.i("Image ArrayList Size", Integer.toString(images.size()));

                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(List<String> result) {


            count = 0;
            try {
                if (images.isEmpty() == false) {
                    ImageView userpicture = (ImageView) findViewById(com.example.galwaytour.R.id.displayImage);
                    userpicture.setVisibility(View.VISIBLE);
                    userpicture.setImageBitmap(images.get(count));
                } else if (images.isEmpty() == true) {
                    ImageView userpicture = (ImageView) findViewById(com.example.galwaytour.R.id.displayImage);
                    userpicture.setVisibility(View.VISIBLE);
                    userpicture.setImageResource(com.example.galwaytour.R.drawable.no_image_found);

                    next.setVisibility(View.INVISIBLE);
                    prev.setVisibility(View.INVISIBLE);

                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }



        }
    }

    protected class GetJsonResponse implements ResponseHandler<List<String>> {
        JSONArray Details;
        List<String> data = new ArrayList<String>();

        @Override
        public List<String> handleResponse(HttpResponse arg0)
                throws ClientProtocolException, IOException {
            // TODO Auto-generated method stub


            String Jsonresponse = new BasicResponseHandler().handleResponse(arg0);
            JSONObject jobj = null;

            try {
                jobj = (JSONObject) new JSONTokener(Jsonresponse).nextValue();


                Details = jobj.getJSONArray("Image_ID");
                for (int i = 0; i < Details.length(); i++) {
                    //creates json object of retrieved information
                    JSONObject info = (JSONObject) Details.get(i);
                    //data.add(TAG_FNAME + ":"+ keepers.get(TAG_FNAME));/* + ","
                    data.add("Name" + ":" + info.get("id"));
                    id_list.add((String) info.get("id").toString());
                    Log.i("Images ID's", (String) info.get("id").toString());

                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            return data;

        }
    }
}

