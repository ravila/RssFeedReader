package br.usp.rssfeedreader;

import java.util.ArrayList;
import java.util.List;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class RSSDatabaseHandler extends SQLiteOpenHelper {
 
    private static final int DATABASE_VERSION = 3;
 
    private static final String DATABASE_NAME = "rssRead";
 
    private static final String TABLE_RSS = "rssfeed";
 
    private static final String KEY_ID = "id";
    private static final String KEY_NOTICIA_ID = "idNoticia";
    private static final String KEY_TITLE = "title";
    private static final String KEY_LINK = "link";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_CATEGORY_ID = "categoryId";
    private static final String KEY_PUB_DATE = "puDate";
 
    public RSSDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RSS_TABLE = "CREATE TABLE " + TABLE_RSS + "(" + KEY_ID
                + " INTEGER PRIMARY KEY," + KEY_NOTICIA_ID + " INTEGER," + KEY_TITLE + " TEXT," + KEY_LINK
                + " TEXT," + KEY_DESCRIPTION + " TEXT," + KEY_CATEGORY_ID
                + " INTEGER," + KEY_PUB_DATE + " TEXT" + ")";
        db.execSQL(CREATE_RSS_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RSS);
 
        onCreate(db);
    }
 
    public long addRSSFeed(RSSItem rssInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NOTICIA_ID, rssInfo.getNoticiaId());
        values.put(KEY_TITLE, rssInfo.getTitle());
        values.put(KEY_LINK, rssInfo.getLink());
        values.put(KEY_DESCRIPTION, rssInfo.getDescription());
        values.put(KEY_CATEGORY_ID, rssInfo.getCategoryId());
        values.put(KEY_PUB_DATE, rssInfo.getPubdate());
 
        long id = db.insert(TABLE_RSS, null, values);
        db.close();
        
        return id;
    }
 
    public List<RSSItem> getAllRSSFeeds() {
        String selectQuery = "SELECT  * FROM " + TABLE_RSS
                + " ORDER BY id ASC";
        
        return getRSSFeedsBySQLQuery(selectQuery);
    }
    
    public List<RSSItem> getRSSFeedsByCategory(Integer categoryId) {
        String selectQuery = "SELECT  * FROM " + TABLE_RSS
                + " WHERE " + KEY_CATEGORY_ID + "=" + categoryId + " ORDER BY id ASC";
        System.out.println(selectQuery);
        
        return getRSSFeedsBySQLQuery(selectQuery);
    }
 
    private List<RSSItem> getRSSFeedsBySQLQuery(String selectQuery) {
        List<RSSItem> rssList = new ArrayList<RSSItem>();
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
            	RSSItem rssItem = new RSSItem();
                rssItem.setId(cursor.getInt(0));
                rssItem.setNoticiaId(cursor.getInt(1));
                rssItem.setTitle(cursor.getString(2));
                rssItem.setLink(cursor.getString(3));
                rssItem.setDescription(cursor.getString(4));
                rssItem.setCategoryId(cursor.getInt(5));
                rssItem.setPubdate(cursor.getString(6));
                
                rssList.add(rssItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
 
        return rssList;
    }
    
    public RSSItem getRssInfo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_RSS, new String[] { KEY_ID, KEY_NOTICIA_ID, KEY_TITLE,
                KEY_LINK, KEY_DESCRIPTION, KEY_CATEGORY_ID, KEY_PUB_DATE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        RSSItem rssItem = new RSSItem(cursor.getInt(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getInt(5), cursor.getString(6));
 
        rssItem.setId(cursor.getInt(0));
        
        cursor.close();
        db.close();
        
        return rssItem;
    }
 
    public void updateById(RSSItem item, Integer id) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
        values.put(KEY_NOTICIA_ID, item.getNoticiaId());
        values.put(KEY_TITLE, item.getTitle());
        values.put(KEY_LINK, item.getLink());
        values.put(KEY_DESCRIPTION, item.getDescription());
        values.put(KEY_CATEGORY_ID, item.getCategoryId());
        values.put(KEY_PUB_DATE, item.getPubdate());
        
        int num = db.update(TABLE_RSS, values, KEY_ID + "=?", new String[] {id.toString()});
        if(num == 0)
        	;//pau
        
        db.close();
    }
    
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RSS, null, null);
        db.close();
    }
    
    public void deleteByCategory(Integer categoryId) {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RSS, KEY_CATEGORY_ID + "=?", new String[] {categoryId.toString()});
        db.close();
    }

	public boolean isEmpty() {
		boolean isEmpty = false;
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  * FROM " + TABLE_RSS;
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor.getCount() == 0)
			isEmpty = true;
		
		cursor.close();
		db.close();
		
		return isEmpty;
	}
 
}