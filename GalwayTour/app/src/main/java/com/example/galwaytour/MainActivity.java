package com.example.galwaytour;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;
import android.R.layout.*;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.Toast;


import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.galwaytour.R.drawable.ic_launcher;

public class MainActivity extends ActionBarActivity{

	int i = 0;
	Button hotels,Events;
    ListView menu_list;
	String menu[] = {"View Hotels","View Hostels","View Restaurants/Pubs","Attractions","Events","Camera","Log Out"};
    private ShareActionProvider mShareActionProvider;
    View view1;
    private TextView userDetails;
    static ImageView userpicture;
    String userID;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);


     //   LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     //   view1 = inflater.inflate(R.layout.list_text_design,null);

/*

        userDetails = (TextView) findViewById(com.example.galwaytour.R.id.userDetails);
       final Session session1 = Session.getActiveSession();
        if(session1.isOpened())
        {
            Log.i("User Data","Session is opened");
            Request.executeMeRequestAsync(session1, new Request.GraphUserCallback() {
                @Override
                public void onCompleted(GraphUser graphUser, Response response) {

                    if(graphUser != null)
                    {
                        TextView v1 = (TextView) findViewById(R.id.userDetails);
                        v1.setVisibility(View.VISIBLE);
                        Log.i("User Data","User exists and getting Data");
                        userDetails.setText(buildUserInfoDisplay(graphUser));
                        new LoadImage().execute();
                    }

                    new Request(session1,"/me/picture",null, HttpMethod.GET,new Request.Callback() {
                        public void onCompleted(Response response) {
            /* handle the result
                            Log.i("Response",response.toString());


                        }
                    }
                    ).executeAsync();
                }
            });
        }*/

   //     userpicture.setImageBitmap(mIcon1);

       menu_list = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_text_design,menu);
        menu_list.setAdapter(adapter);

        menu_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
     {
         @Override
         public void onItemClick(AdapterView<?> a, View v,int position, long id)
         {
             switch (position)
             {
                 case 0:
                  //   Drawable img = getBaseContext().getResources().getDrawable( R.drawable.ic_launcher );
                  //   img.setBounds( 0, 0, 100, 100 );
                  //   TextView v1 = (TextView) v.findViewById(R.id.list_text_design1);
                //     v1.setCompoundDrawables(img,null,null,null);

                     String URL = "http://danu6.it.nuigalway.ie/dmadugu1/View_Uploads/List_Hotels.php";
                     Class ourClass = null;
                     try {
                         ourClass = Class.forName("com.example.galwaytour.Display_Attractions");

                     } catch (ClassNotFoundException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
                     }
                     Intent intent = new Intent(MainActivity.this,ourClass);
                     intent.putExtra("selected", menu[position]);
                     intent.putExtra("URL",URL);
                     startActivity(intent);
                     break;

                 case 1:
                     String URL1 = "http://danu6.it.nuigalway.ie/dmadugu1/View_Uploads/List_Hostels.php";
                     //String URL1 = "http://danu6.it.nuigalway.ie/dmadugu1/View_Uploads/List_Attractions.php";
                     Class ourClass1 = null;
                     try {
                         ourClass1 = Class.forName("com.example.galwaytour.Display_Attractions");
                     } catch (ClassNotFoundException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
                     }
                     Intent intent1 = new Intent(MainActivity.this,ourClass1);
                     intent1.putExtra("selected", menu[position]);
                     intent1.putExtra("URL", URL1);
                     startActivity(intent1);
                     break;
                 case 2:
                     String URL2 = "http://danu6.it.nuigalway.ie/dmadugu1/View_Uploads/List_Restaur_Pubs.php";
                     Class ourClass2 = null;
                     try {
                         ourClass2 = Class.forName("com.example.galwaytour.Display_Attractions");
                     } catch (ClassNotFoundException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
                     }
                     Intent intent2 = new Intent(MainActivity.this,ourClass2);
                     intent2.putExtra("selected", menu[position]);
                     intent2.putExtra("URL", URL2);
                     startActivity(intent2);
                     break;
                 case 3:
                     //	String URL3 = "http://danu6.it.nuigalway.ie/dmadugu1/View_Uploads/List_Hostels.php";
                     String URL3 = "http://danu6.it.nuigalway.ie/dmadugu1/View_Uploads/List_Attractions.php";
                     Class ourClass3 = null;
                     try {
                         ourClass3 = Class.forName("com.example.galwaytour.Display_Attractions");
                     } catch (ClassNotFoundException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
                     }
                     Intent intent3 = new Intent(MainActivity.this,ourClass3);
                     intent3.putExtra("selected", menu[position]);
                     intent3.putExtra("URL", URL3);
                     startActivity(intent3);
                     break;
                 case 4:
                     //	String URL3 = "http://danu6.it.nuigalway.ie/dmadugu1/View_Uploads/List_Hostels.php";
                     String URL4 = "http://danu6.it.nuigalway.ie/dmadugu1/View_Uploads/List_Events.php";
                     Class ourClass4 = null;
                     try {
                         ourClass4 = Class.forName("com.example.galwaytour.Display_Attractions");
                     } catch (ClassNotFoundException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
                     }
                     Intent intent4 = new Intent(MainActivity.this,ourClass4);
                     intent4.putExtra("selected", menu[position]);
                     intent4.putExtra("URL", URL4);
                     startActivity(intent4);
                     break;
                 case 5:
                     Class ourClass5 = null;
                     try
                     {
                         ourClass5 = Class.forName("com.example.galwaytour.Camera");
                         Intent intent5 = new Intent(MainActivity.this,ourClass5);
                         intent5.putExtra("selected", menu[position]);
                         startActivity(intent5);
                     }
                     catch(ClassNotFoundException e)
                     {
                         e.printStackTrace();
                     }
                     break;
                 case 6:

                     Session session = Session.getActiveSession();
                     if(session.isOpened() == true) {
                         session.close();
                         Log.i("Session Status", "Session is now closed");
                         Intent intent5 = new Intent(MainActivity.this,FB_Login.class);
                         startActivity(intent5);
                     }
                     else
                     {
                         Log.i("Session Status", "Session is not opened");
                     }

                     break;
             }
             Toast.makeText(getBaseContext(), "Clicked:" + menu[position], Toast.LENGTH_LONG).show();
         }
     }

        );
      
    //  setListAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, menu));
    }



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_item_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      final  int id = item.getItemId();

                            if (id == R.id.action_settings) {
                                Toast.makeText(getBaseContext(), "Clicked settings", Toast.LENGTH_LONG).show();
                                return true;
                            }else if(id == R.id.menu_item_share)
                            {
                                Intent shareIntent = new Intent();
                                shareIntent.setAction(Intent.ACTION_SEND);
                                shareIntent.setType("text/plain");
                                shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello, from David Madugu");
                                int i = Intent.ACTION_SEND.length();
                                Log.i("intent",Integer.toString(i));
                                startActivity(Intent.createChooser(shareIntent, "Share your thoughts"));
                                Toast.makeText(getBaseContext(), "Clicked Share", Toast.LENGTH_LONG).show();
                                return true;
                            }

        return super.onOptionsItemSelected(item);
    }

    private String buildUserInfoDisplay(GraphUser user) {

        StringBuilder userInfo = new StringBuilder("");

        userInfo.append(String.format(user.getName()));
        Log.i("User Data", user.getName());
        userID = user.getId();
        Log.i("User ID", user.getId());

        return userInfo.toString();
    }

    private class LoadImage extends AsyncTask<String,Void,Bitmap> {

        AndroidHttpClient httpClient = AndroidHttpClient.newInstance("");


        @Override
        protected  Bitmap doInBackground(String... arg0) {


            URL img_value = null;
            Bitmap image = null;
            try {
                //InputStream in = new java.net.URL("http://graph.facebook.com/10205084551986696/picture?type=large").openStream();
                InputStream in = new java.net.URL("http://danu6.it.nuigalway.ie/dmadugu1/View_Uploads/Get_Images.php?id=0a3646190aa4").openStream();
                image = BitmapFactory.decodeStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //    Bitmap mIcon1 = null;


                //   Log.i("Image Output",mIcon1.toString());
                //mIcon1 = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());

                if (image == null) {
                    Log.i("Image Output", "mIcon is empty");
                } else {
                    Log.i("Image Output", "mIcon is not empty");
                }



            return image;
        }

        @Override
        protected void onPostExecute(Bitmap result)
        {
            userpicture = (ImageView) findViewById(R.id.userImage);
         userpicture.setVisibility(View.VISIBLE);
       //     userpicture.setImageResource(R.drawable.ic_hotel68);
         userpicture.setImageBitmap(result);


        }
    }
}


