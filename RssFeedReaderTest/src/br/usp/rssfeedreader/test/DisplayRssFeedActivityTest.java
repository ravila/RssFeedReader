package br.usp.rssfeedreader.test;

import android.test.AndroidTestCase;
import br.usp.rssfeedreader.*;


public class DisplayRssFeedActivityTest extends AndroidTestCase {

	DisplayRssFeedActivity rssActivity;
	EventoIme exp;
	
	public void setUp()
	{
		exp = new EventoIme(
				"DCC",
				"Jaci",
				"Seminarios",
				"22.05.2013",
				"16:30",
				"projeto mobi",
				"hh");
		rssActivity = new DisplayRssFeedActivity();
	}
	
	public void test_parseOk()
	{
		EventoIme ime = rssActivity.parse("Titulo: DCC\nLocal: Jaci\nCategoria: Seminarios\nData: 22.05.2013\nHora: 16:30\nDescricao: projeto mobi\nResumo: hh");
		AndroidTestCase.assertEquals(ime.getPlace(), exp.getPlace());
	}
	
	public void test_parseFalse()
	{
		EventoIme ime = rssActivity.parse("Titulo: DCC\nLocal: B5\nCategoria: Seminarios\nData: 22.05.2013\nHora: 16:30\nDescricao: projeto mobi\nResumo: hh");
		AndroidTestCase.assertFalse(ime.getPlace() == exp.getPlace());
	}
}
