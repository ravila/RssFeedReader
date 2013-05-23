package br.usp.rssfeedreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayRssFeedActivity extends Activity {

	private TextView title;
	private TextView date;
	private TextView description;
	private Button link; 
	private ImageButton share;
	private ImageButton calendar;
	private ImageButton pdf;
	private RSSItem rssItem;
	private EventoIme eventoIme;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_rss_feed);
		
		title = (TextView) findViewById(R.id.title2);
		date = (TextView) findViewById(R.id.pub_date2);
		description = (TextView) findViewById(R.id.description2);
		link = (Button) findViewById(R.id.verArtigo);
		share = (ImageButton) findViewById(R.id.share);
		calendar = (ImageButton) findViewById(R.id.calendar);
		pdf = (ImageButton) findViewById(R.id.pdf);
		
		RSSDatabaseHandler rssDB = new RSSDatabaseHandler(getApplicationContext());
		
		Intent in = getIntent();
        String rss_id = in.getStringExtra("rss_id");
        
        rssItem = rssDB.getRssInfo(Integer.parseInt(rss_id));
        
        title.setText(rssItem.getTitle());
        date.setText(rssItem.getPubdate());
        String text = Html.fromHtml(rssItem.getDescription()).toString();
        eventoIme = parse(text);
        description.setText(text);
        
        link.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), DisplayWebPageActivity.class);
                
                i.putExtra("page_url", rssItem.getLink());
                startActivity(i);
			}
		});
        
        share.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				shareIntent.putExtra(Intent.EXTRA_TEXT, rssItem.getLink());
				startActivity(Intent.createChooser(shareIntent, "Compartilhar com ..."));
			}
		});
        
        calendar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_EDIT);
				intent.setType("vnd.android.cursor.item/event");
				intent.putExtra("beginTime", eventoIme.getMiliSeconds());
				intent.putExtra("endTime", eventoIme.getMiliSeconds()+60*60*1000);
				intent.putExtra("title", eventoIme.getTitle());
				intent.putExtra("description", eventoIme.getDescription());
				intent.putExtra("eventLocation", eventoIme.getPlace());
				startActivity(intent);
			}
		});
        
        pdf.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean success = CreatePdf.createNewPdf(Environment.getExternalStorageDirectory().getAbsolutePath(), eventoIme);
				if(success) {
					Toast.makeText(getApplicationContext(), "Arquivo criado com sucesso.", Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(getApplicationContext(), "Erro ao criar arquivo.", Toast.LENGTH_SHORT).show();
				}
			}
		});
         
	}
	
	public EventoIme parse(String texto) {
		EventoIme evento;
		String[] splitted;
		String title = null;
		String place = null;
		String category = null;
		String date = null;
		String hour = null;
		String description = null;
		String summary = null;
		splitted = texto.split("\n");
		for(int i=0;i<splitted.length;i++)
		{
			if(splitted[i].startsWith("Título: ")) title = splitted[i].replace("Título: ", "");
			else if(splitted[i].startsWith("Local: ")) place = splitted[i].replace("Local: ", "");
			else if(splitted[i].startsWith("Categoria: ")) category = splitted[i].replace("Categoria: ", "");
			else if(splitted[i].startsWith("Data: ")) date = splitted[i].replace("Data: ", "").replace(".", "/");
			else if(splitted[i].startsWith("Hora: ")) hour = splitted[i].replace("Hora: ", "").replace("h", "").replace(".", ":");
			else if(splitted[i].startsWith("Descrição: ")) description = splitted[i].replace("Descrição: ", "");
			else if(splitted[i].startsWith("Resumo: ")) summary = splitted[i].replace("Resumo: ", "");
		}
		evento = new EventoIme(title,place,category,date,hour,description,summary);
		return evento;

	}
}
