package br.usp.rssfeedreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class DisplayRssFeedActivity extends Activity {

	private TextView title;
	private TextView date;
	private TextView description;
	private Button link; 
	private ImageButton share;
	private ImageButton calendar;
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
				// Essa opção adiciona um evento no calendário sem mostrar o calendario para o usuario
				// Necessita de permissao para escrver no calendario
				//pushAppointmentsToCalender(DisplayRssFeedActivity.this, "Oi", "Alo", "aqui mesmo", 1, new Date().getTime());
				
				// Essa opção eh mais simples, porem faz com que o usuario tenha que confirmar a adição do evento no proprio calendario.
				//Calendar cal = Calendar.getInstance();              
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
         
	}

	/*public long pushAppointmentsToCalender(Activity curActivity, String title, String addInfo, String place, int status, long startDate) {

	    String eventUriString = "content://com.android.calendar/events";
	    ContentValues eventValues = new ContentValues();

	    eventValues.put("calendar_id", 1); // id, We need to choose from
	                                        // our mobile for primary
	                                        // its 1
	    eventValues.put("title", title);
	    eventValues.put("description", addInfo);
	    eventValues.put("eventLocation", place);

	    long endDate = startDate + 1000 * 60 * 60; // For next 1hr

	    eventValues.put("dtstart", startDate);
	    eventValues.put("dtend", endDate);

	    // values.put("allDay", 1); //If it is bithday alarm or such
	    // kind (which should remind me for whole day) 0 for false, 1
	    // for true
	    eventValues.put("eventStatus", status); // This information is
	    // sufficient for most
	    // entries tentative (0),
	    // confirmed (1) or canceled
	    // (2):
	    eventValues.put("visibility", 3); // visibility to default (0),
	                                        // confidential (1), private
	                                        // (2), or public (3):
	    eventValues.put("transparency", 0); // You can control whether
	                                        // an event consumes time
	                                        // opaque (0) or transparent
	                                        // (1).
	    eventValues.put("hasAlarm", 1); // 0 for false, 1 for true

	    Uri eventUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(eventUriString), eventValues);
	    long eventID = Long.parseLong(eventUri.getLastPathSegment());
	 
	    return eventID;

	}*/
	
	public EventoIme parse(String texto) {
		EventoIme evento;
		String[] splitted;
		String title = null;
		String place = null;
		String category = null;
		String date = null;
		String hour = null;
		String description = null;
		String speaker = null;
		String summary = null;
		splitted = texto.split("\n");
		for(int i=0;i<splitted.length;i++)
		{
			if(splitted[i].startsWith("Título: ")) title = splitted[i].replace("Título: ", "");
			else if(splitted[i].startsWith("Local: ")) place = splitted[i].replace("Local: ", "");
			else if(splitted[i].startsWith("Categoria: ")) category = splitted[i].replace("Categoria: ", "");
			else if(splitted[i].startsWith("Data: ")) date = splitted[i].replace("Data: ", "").replace(".", "/");
			else if(splitted[i].startsWith("Hora: ")) hour = splitted[i].replace("Hora: ", "").replace("h", "").replace(".", ":");
			else if(splitted[i].startsWith("Palestrante: ")) speaker = splitted[i].replace("Palestrante: ", "");
			else if(splitted[i].startsWith("Descrição: ")) description = splitted[i].replace("Descrição: ", "");
			else if(splitted[i].startsWith("Resumo: ")) summary = splitted[i].replace("Resumo: ", "");
		}
		evento = new EventoIme(title,place,category,date,hour,description,speaker,summary);
		return evento;

	}
}
