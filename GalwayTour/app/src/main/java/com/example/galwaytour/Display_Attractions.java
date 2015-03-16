package com.example.galwaytour;

import android.app.ListActivity;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Display_Attractions extends ListActivity{

	final String TAG_SUCCESS = "success";
    final String TAG_KEEPERS	 = "keeper_info";
    final String TAG_FNAME = "firstName";
    final String TAG_LNAME = "lastName";
    static final String KEEPER_TAG = "hivesOwned";
    List<String> returned_data = new ArrayList<String>();
    List<retrievedData> retrieved_data = new ArrayList<retrievedData>();
    
    
    
    private static String URL = null;
    public static String action_tag = null;
    private final String LOG_TAG = "Display_Hotels Class";
    JSONArray products = null;
    TextView txt;

  //  TextView view1;
    TextView httpStuff;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   //     setContentView(com.example.galwaytour.R.layout.httpexample);


                 
        URL = getIntent().getStringExtra("URL");
        action_tag = getIntent().getStringExtra("selected");

        android.app.ActionBar actionbar = getActionBar();
        actionbar.setTitle(action_tag);
        
		try {
			Log.i(LOG_TAG,"Inside try");
			 new LoadData().execute();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ListView lv = getListView();
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				// TODO Auto-generated method stub
					
								
				Class ourClass;
				try {

                        ourClass = Class.forName("com.example.galwaytour.DisplaySelected");
                        Intent intent1 = new Intent(Display_Attractions.this, ourClass);
                        //intent1.putExtra("selected", returned_data.get(position).toString());
                        intent1.putExtra("name", retrieved_data.get(position).getName());
                        intent1.putExtra("address", retrieved_data.get(position).getAddress());
                        intent1.putExtra("website", retrieved_data.get(position).getWeb());
                        intent1.putExtra("information", retrieved_data.get(position).getInfo());
                        intent1.putExtra("tel_num", retrieved_data.get(position).getTel());
                        intent1.putExtra("email", retrieved_data.get(position).getEmail());
                        intent1.putExtra("rating", retrieved_data.get(position).getRating());
                        intent1.putExtra("image id", retrieved_data.get(position).getImageID());

					startActivity(intent1);					
					
					} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}			
		});
	} 

    private class LoadData extends AsyncTask<Void,Void, List<String>>{
 	   
    	   AndroidHttpClient httpClient = AndroidHttpClient.newInstance("");

    	   	@Override   	
    	   	protected List<String> doInBackground(Void... arg0) {
    		
    	   		HttpGet request = new HttpGet(URL);
    	   		Log.i("URL",URL);
    	   		GetJsonResponse responseHandler = new GetJsonResponse();
    	   		try{
    	   			return httpClient.execute(request, responseHandler);
    	   		}catch (IOException e) {
    				e.printStackTrace();
    			}
    	   		return null;   		
    	   	} 
    	   	    	   	
    	   	@Override   
    	   	protected void onPostExecute(List<String> result) 
    	   	{
                List<String> r = new ArrayList<String>();
                r.add("Non Available");
                if(result.isEmpty() == false) {
                    setListAdapter(new ArrayAdapter<String>(Display_Attractions.this, com.example.galwaytour.R.layout.list_hotels, result));
                }
                else
                {
                    setListAdapter(new ArrayAdapter<String>(Display_Attractions.this, com.example.galwaytour.R.layout.list_hotels, r));

                }
    	   		  		
    		} 	
    	}
    
    protected class GetJsonResponse implements ResponseHandler<List<String>>
    {
 	 JSONArray Details;
 	List<String> data = new ArrayList<String>();
 	@Override
 	public List<String> handleResponse(HttpResponse arg0)
 			throws ClientProtocolException, IOException {
 		// TODO Auto-generated method stub
 		
 		  
 		String Jsonresponse = new BasicResponseHandler().handleResponse(arg0);
 		 JSONObject jobj = null;
 		
 		if(action_tag.equals("View Hotels"))
 		{ 	Log.i("Array",action_tag);
 			try
 	 		{
 			Log.i("Array",Jsonresponse);			
 			jobj = (JSONObject) new JSONTokener(Jsonresponse).nextValue();						
 		
 			
 			Details = jobj.getJSONArray("Hotels");
	 			for( int i = 0; i < Details.length(); i++) 			
	 			{		
	 				//creates object of retrieved data
	 			
	 				
	 				//creates json object of retrieved information
	 				JSONObject info = (JSONObject) Details.get(i);
	 				//data.add(TAG_FNAME + ":"+ keepers.get(TAG_FNAME));/* + ","
	 				data.add("Name" + ":"+ info.get("Comp_Name"));
	 				
	 				
	 				//data which adds to the list
	 				returned_data.add(TAG_FNAME + ":"+ info.getString("Comp_Name") + "\n"
	 						+ "Address" + ":" + info.getString("Address") + ","  + "\n"
	 						+ "Website" + ":"+ info.getString("Website") + "\n"
	 						+ "Information" + ":" + info.getString("Information") + "\n"
	 						+ "Telephone Number" + ":" + info.getString("Telephone_Number") + "\n"
	 						+ "Email" + ":" + info.getString("Email") + "\n"
	 						+ "Rating" + ":" + info.getString("Rating"));
	 				
	 				String name = (String)info.get("Comp_Name").toString();
	 				String addr = (String)info.get("Address").toString();
	 				String web = (String)info.get("Website").toString();
	 				String info1 = (String)info.get("Information").toString();
	 				String tel = (String)info.get("Telephone_Number").toString();
	 				String email = (String)info.get("Email").toString();
	 				String rating = (String)info.get("Rating").toString();
                    String image_id = (String)info.get("Image_ID");
	 				try
	 				{
	 				retrievedData rData = new retrievedData(name,addr,web,info1,tel,email,rating,image_id);
	 				retrieved_data.add(rData);
	 				}
	 				catch(Exception e)
	 				{
	 					e.printStackTrace();
	 				}
	 			} 			
 	 		}
 			catch(JSONException ex)
 	 		{
 	 			ex.printStackTrace();
 	 		}
 		}
 		else if(action_tag.equals("View Hostels"))
 		{ 	
			try
	 		{
			Log.i("Array",Jsonresponse);			
			jobj = (JSONObject) new JSONTokener(Jsonresponse).nextValue();						
			
			Details = jobj.getJSONArray("Hostels");
			for( int i = 0; i < Details.length(); i++)			
				{				
					
					JSONObject keepers = (JSONObject) Details.get(i);
					//data.add(TAG_FNAME + ":"+ keepers.get(TAG_FNAME));/* + ","
					data.add("Name" + ":"+ keepers.get("Comp_Name"));
					
					
					//data which adds to the list
	 				returned_data.add(TAG_FNAME + ":"+ keepers.get("Comp_Name") + "\n"
	 						+ "Address" + ":" + keepers.getString("Address") + ","  + "\n"
	 						+ "Website" + ":"+ keepers.get("Website") + "\n"
	 						+ "Information" + ":" + keepers.get("Information") + "\n"
	 						+ "Telephone Number" + ":" + keepers.get("Telephone_Number") + "\n"
	 						+ "Email" + ":" + keepers.get("Email") + "\n" 
	 						+ "Rating" + ":" + keepers.get("Rating"));
					
	 				String name = (String)keepers.get("Comp_Name").toString();
	 				String addr = (String)keepers.get("Address").toString();
	 				String web = (String)keepers.get("Website").toString();
	 				String info = (String)keepers.get("Information").toString();
	 				String tel = (String)keepers.get("Telephone_Number").toString();
	 				String email = (String)keepers.get("Email").toString();
	 				String rating = (String)keepers.get("Rating").toString();
                    String image_id = (String)keepers.get("Image_ID");
	 				try
	 				{
	 				retrievedData rData = new retrievedData(name,addr,web,info,tel,email,rating,image_id);
	 				retrieved_data.add(rData);
	 				}
	 				catch(Exception e)
	 				{
	 					e.printStackTrace();
	 				}
				}			
	 		}
			catch(JSONException ex)
	 		{
	 			ex.printStackTrace();
	 		}
		}
 		else if(action_tag.equals("Attractions"))
 		{ 	
			try
	 		{
			Log.i("Array",Jsonresponse);			
			jobj = (JSONObject) new JSONTokener(Jsonresponse).nextValue();						
			
			Details = jobj.getJSONArray("Attractions");
				for( int i = 0; i < Details.length(); i++)			
				{				
					
					JSONObject keepers = (JSONObject) Details.get(i);
					//data.add(TAG_FNAME + ":"+ keepers.get(TAG_FNAME));/* + ","
					data.add("Name" + ":"+ keepers.get("Comp_Name"));
					
					
					//data which adds to the list
	 				returned_data.add(TAG_FNAME + ":"+ keepers.get("Comp_Name") + "\n"
	 						+ "Address" + ":" + keepers.getString("Address") + ","  + "\n"
	 						+ "Website" + ":"+ keepers.get("Website") + "\n"
	 						+ "Information" + ":" + keepers.get("Information") + "\n"
	 						+ "Telephone Number" + ":" + keepers.get("Telephone_Number") + "\n"
	 						+ "Email" + ":" + keepers.get("Email") + "\n" 
	 						+ "Rating" + ":" + keepers.get("Rating"));
					
	 				String name = (String)keepers.get("Comp_Name").toString();
	 				String addr = (String)keepers.get("Address").toString();
	 				String web = (String)keepers.get("Website").toString();
	 				String info = (String)keepers.get("Information").toString();
	 				String tel = (String)keepers.get("Telephone_Number").toString();
	 				String email = (String)keepers.get("Email").toString();
	 				String rating = (String)keepers.get("Rating").toString();
                    String image_id = (String)keepers.get("Image_ID");
	 				try
	 				{
	 				retrievedData rData = new retrievedData(name,addr,web,info,tel,email,rating,image_id);
	 				retrieved_data.add(rData);
	 				}
	 				catch(Exception e)
	 				{
	 					e.printStackTrace();
	 				}
				}			
	 		}
			catch(JSONException ex)
	 		{
	 			ex.printStackTrace();
	 		}
		}
 		else if(action_tag.equals("View Restaurants/Pubs"))
 		{ 	
			try
	 		{
			Log.i("Array",Jsonresponse);			
			jobj = (JSONObject) new JSONTokener(Jsonresponse).nextValue();						
			
			Details = jobj.getJSONArray("Pubs");
			for( int i = 0; i < Details.length(); i++){
					
					JSONObject keepers = (JSONObject) Details.get(i);
					data.add("Name" + ":"+ keepers.get("Comp_Name"));
					
					//data which adds to the list
	 				returned_data.add(TAG_FNAME + ":"+ keepers.get("Comp_Name") + "\n"
	 						+ "Address" + ":" + keepers.getString("Address") + ","  + "\n"
	 						+ "Website" + ":"+ keepers.get("Website") + "\n"
	 						+ "Information" + ":" + keepers.get("Information") + "\n"
	 						+ "Telephone Number" + ":" + keepers.get("Telephone_Number") + "\n"
	 						+ "Email" + ":" + keepers.get("Email") + "\n" 
	 						+ "Rating" + ":" + keepers.get("Rating"));
	 				
	 				String name = (String)keepers.get("Comp_Name").toString();
	 				String addr = (String)keepers.get("Address").toString();
	 				String web = (String)keepers.get("Website").toString();
	 				String info = (String)keepers.get("Information").toString();
	 				String tel = (String)keepers.get("Telephone_Number").toString();
	 				String email = (String)keepers.get("Email").toString();
	 				String rating = (String)keepers.get("Rating").toString();
                    String image_id = (String)keepers.get("Image_ID");
	 				try
	 				{
	 				retrievedData rData = new retrievedData(name,addr,web,info,tel,email,rating,image_id);
	 				retrieved_data.add(rData);
	 				}
	 				catch(Exception e)
	 				{
	 					e.printStackTrace();
	 				}
					
				}			
	 		}
			catch(JSONException ex)
	 		{
	 			ex.printStackTrace();
	 		}
		}
 		else if(action_tag.equals("Events"))
 		{ 	
			try
	 		{
			Log.i("Array",Jsonresponse);			
			jobj = (JSONObject) new JSONTokener(Jsonresponse).nextValue();						
			
			Details = jobj.getJSONArray("Events");
				for( int i = 0; i < Details.length(); i++)			
				{								
					JSONObject keepers = (JSONObject) Details.get(i);
					//data.add(TAG_FNAME + ":"+ keepers.get(TAG_FNAME));/* + ","
					data.add("Name" + ":"+ keepers.get("Event_Name"));
					
					
					//data which adds to the list
					returned_data.add(TAG_FNAME + ":"+ keepers.get(TAG_FNAME) + "\n"
							+ "Location" + ":" + keepers.getString("Location") + ","  + "\n"
	 						+ "Start Date" + ":"+ keepers.get("Start_Date") + "\n"
	 						+ "Information" + ":" + keepers.get("Information"));					
				}				
	 		}
			catch(JSONException ex)
	 		{
	 			ex.printStackTrace();
	 		}
		}		
 		return data;
 	} 	   
    }
    
    public class retrievedData
    {
    	String name;
    	String address;
    	String website;
    	String information;
    	String tel_num;
    	String Email;
    	String rating;
        String image_id;
    	
    	public retrievedData(String name, String address, String website, String info, String tel_num, String email, String rating,String image_id)
    	{
    		this.name = name;
    		this.address = address;
    		this.website = website;
    		this.information = info;
    		this.tel_num = tel_num;
    		this.Email = email;
    		this.rating = rating;
            this.image_id = image_id;
    	}
    	public String getName()
    	{
    		return name;
    	}
    	public String getAddress()
    	{
    		return address;
    	}
    	public String getWeb()
    	{
    		return website;
    	}
    	public String getInfo()
    	{
    		return information;
    	}
    	public String getTel()
    	{
    		return tel_num;
    	}
    	
    	public String getEmail()
    	{
    		return Email;
    	}
    	public String getRating()
    	{
    		return rating;
    	}

        public String getImageID()
        {
            return image_id;
        }
    	
    	
    	
    }
 }



