package br.usp.rssfeedreader.test;

import android.os.Environment;
import android.test.AndroidTestCase;

import br.usp.rssfeedreader.*;

public class CreatePdfTest extends AndroidTestCase {

	EventoIme validIme;
	EventoIme nullIme;
	String validPath;
	String invalidPath;
	
	public void setUp()
	{
		validIme = new EventoIme(
				"DCC",
				"Jaci",
				"Seminarios",
				"22.05.2013",
				"16:30",
				"projeto mobi",
				"hh");
		nullIme = new EventoIme("","","","","","","");
		validPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		invalidPath = "";
	}
	
	public void test_CreatePdfFromNullEventoImeWithInvalidPath()
	{
		AndroidTestCase.assertFalse(CreatePdf.createNewPdf(invalidPath, nullIme));
	}
	
	public void test_CreatePdfWithInvalidPath()
	{
		AndroidTestCase.assertFalse(CreatePdf.createNewPdf(invalidPath, validIme));
	}
}
