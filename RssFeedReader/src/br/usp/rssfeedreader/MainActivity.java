package br.usp.rssfeedreader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
 
public class MainActivity extends ListActivity {
     
	private static String URL(int id) { return "http://www.ime.usp.br/index.php?option=com_eventlist&view=categoryevents&format=feed&id=" + id + "&type=rss"; }
 
    ArrayList<HashMap<String, String>> rssItemList = new ArrayList<HashMap<String,String>>();
    RSSFeedParser rssParser = new RSSFeedParser();
    List<RSSItem> rssItems = new ArrayList<RSSItem>();
    RSSFeed rssFeed;
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    private static String TAG_TITLE = "title";
    private static String TAG_ID = "id";
    private static String TAG_DESRIPTION = "description";
    private static String TAG_CATEGORY = "category";
    private static String TAG_PUB_DATE = "pubDate";
    //private static long TRINTA_MINUTOS = 60000;
    private static final int MOBILE = 2;
    private static final int WIFI = 1;
    
    private Button bNext;
    private Button bBack;
    private Spinner spinner;
    private ImageButton btnRefresh;
    
    private int offset = 1;
    private int max_page = 5;
    private int total = 0;
    
    private int departamentoId = 62; // DCC
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        bNext = (Button) findViewById(R.id.next);
        bBack = (Button) findViewById(R.id.back);
        spinner = (Spinner) findViewById(R.id.spinner);
        btnRefresh = (ImageButton) findViewById(R.id.btnRefresh);
        
        ListView lv = getListView();
  
        lv.setOnItemClickListener(new OnItemClickListener() {
  
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(getApplicationContext(), DisplayRssFeedActivity.class);
                 
                String sid = ((TextView) view.findViewById(R.id.rss_id)).getText().toString();
                showToast("Carregando o id: " + sid);
                in.putExtra("rss_id", sid);
                startActivity(in);
            }
        });
        
        bNext.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				offset = offset + max_page;
				updateButtons();
				new LoadRSSFeedFromDB().execute();
			}
		});
        
        bBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				offset = offset - max_page;
				updateButtons();
				new LoadRSSFeedFromDB().execute();
			}
		});
        
        btnRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				refreshRSSFeed();
			}
		});
        
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				departamentoId = Categorias.getIdByName(parent.getItemAtPosition(pos).toString());
				refreshRSSFeed();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
    }
    
    protected void refreshRSSFeed() {
    	switch (isNetworkAvailable()) {
			case MOBILE:
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("Você está usando 3G");
				builder.setMessage("Deseja atualizar as notícias?");
				builder.setPositiveButton("Sim, atualizar com 3G", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						new UpdateRSSFeedItems().execute();
					}
				});
				builder.setNegativeButton("Não, utilizar antigas.", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						new LoadRSSFeedFromDB().execute();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
				break;
			case WIFI:
				new UpdateRSSFeedItems().execute();
				break;
			case 0:
				showToast("Sem conexão com a internet. Carregando notícias salvas.");
				new LoadRSSFeedFromDB().execute();
				break;
			default:
				break;
    	}
	}

	private void updateButtons() {
    	if(offset >= max_page) 
    		bBack.setEnabled(true);
    	else
    		bBack.setEnabled(false);
    	
    	if(offset + max_page <= total)
    		bNext.setEnabled(true);
    	else
    		bNext.setEnabled(false);
    }
     
	/*public boolean isUpdateNeeded() {
		RSSDatabaseHandler rssDB = new RSSDatabaseHandler(getApplicationContext());
		SharedPreferences settings = getSharedPreferences("preferences", 0);
        String sLastUpdate = settings.getString("lastUpdate", "");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date newUpdate = new Date();
        Date lastUpdate;
        long diffTime = 0;
		try {
			lastUpdate = dateFormat.parse(sLastUpdate);
			diffTime = newUpdate.getTime() - lastUpdate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
		if(!sLastUpdate.equals("") && diffTime < TRINTA_MINUTOS && !rssDB.isEmpty()) 
            return false;
		
		return true;
	}*/
	
	private int isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    if(activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
	    	if(activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI)
	    		return 1;
	    	if(activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
	    		return 2;
	    }
	    return 0;
	}

	private void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}

	class UpdateRSSFeedItems extends AsyncTask<String, String, String> {
 
		ProgressDialog pDialog;
		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Atualizando as notícias...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        @Override
        protected String doInBackground(String... args) {
            RSSDatabaseHandler rssDB = new RSSDatabaseHandler(getApplicationContext());
            
            rssFeed = rssParser.getRSSFeed(URL(departamentoId));
        	if(rssFeed == null) {
            	return null;
            }
        	rssItems = rssFeed.getItems();
        	//rssDB.deleteAll();
        	rssDB.deleteByCategory(departamentoId);
        	
            for(RSSItem item : rssItems){
            	rssDB.addRSSFeed(item);
            }
            
            String sDateFormat = dateFormat.format(new Date());
        	SharedPreferences settings = getSharedPreferences("preferences", 0);
        	SharedPreferences.Editor editor = settings.edit();
        	editor.putString("lastUpdate", sDateFormat);
        	editor.commit();
        	
        	return null;
        }
         
        protected void onPostExecute(String args) {
        	if(rssFeed == null)
        		showToast("Erro ao atualizar notícias. Mostrando notícias antigas");
            pDialog.dismiss();
            offset = 1;
            new LoadRSSFeedFromDB().execute();
        }
    }
	
	class LoadRSSFeedFromDB extends AsyncTask<String, String, String> {

		ProgressDialog progress;
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(MainActivity.this);
            progress.setMessage("Carregando as notícias...");
            progress.setIndeterminate(false);
            progress.setCancelable(false);
            progress.show();
        }
		
		@Override
		protected synchronized String doInBackground(String... params) {
			RSSDatabaseHandler rssDB = new RSSDatabaseHandler(getApplicationContext());
			
			rssItemList.clear();
			rssItems = rssDB.getRSSFeedsByCategory(departamentoId);
			
			for (RSSItem item : rssItems) {
				int id = item.getNoticiaId();
				int categoryId = item.getCategoryId();
				if(id < offset || id > offset+max_page-1)
					continue;
				HashMap<String, String> map = new HashMap<String, String>();
		        map.put(TAG_ID, Integer.toString(item.getId()));
		        map.put(TAG_TITLE, item.getTitle());
		        map.put(TAG_PUB_DATE, item.getPubdate());
		        String description = item.getDescription();
		        if(description.length() > 100){
		            description = description.substring(0, 97) + "..";
		        }
		        map.put(TAG_DESRIPTION, Html.fromHtml(description).toString());
		        map.put(TAG_CATEGORY, Integer.toString(categoryId));
		        
		        rssItemList.add(map);
			}
	            
			return null;
		}
		
		protected void onPostExecute(String args) {
			progress.dismiss();
            total = rssItems.size();
        	updateButtons();
			ListAdapter adapter = new SimpleAdapter(
					MainActivity.this,
					rssItemList, R.layout.list_row,
					new String[] { TAG_ID, TAG_TITLE, TAG_PUB_DATE, TAG_DESRIPTION },
					new int[] { R.id.rss_id, R.id.title, R.id.pub_date, R.id.description });

			setListAdapter(adapter);
        }
	}

}
