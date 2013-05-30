package br.usp.rssfeedreader.test;


import android.test.AndroidTestCase;

import br.usp.rssfeedreader.*;


public class EventoImeTest extends AndroidTestCase {
	EventoIme ime;
	
	public void setUp()
	{
		ime = new EventoIme("a","b","c","d","e","f","g");
	}
	
	public void test_eventoImeTest()
	{
		AndroidTestCase.assertEquals(ime.getTitle(),"a");
	}
}
