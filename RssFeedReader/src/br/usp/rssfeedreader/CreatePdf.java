package br.usp.rssfeedreader;

import java.io.FileOutputStream;

import com.pdfjet.Box;
import com.pdfjet.Color;
import com.pdfjet.CoreFont;
import com.pdfjet.Font;
import com.pdfjet.Letter;
import com.pdfjet.PDF;
import com.pdfjet.Page;
import com.pdfjet.TextLine;

public class CreatePdf {

	public static boolean createNewPdf(String dir, EventoIme evento) {
		int pos = 100;
		
		try {
			String path = dir + "/seminario.pdf";
			FileOutputStream fos = new FileOutputStream(path);
        	PDF pdf = new PDF(fos);
        	Font f1 = new Font(pdf, CoreFont.HELVETICA_BOLD);
			pdf.setTitle("Comprovante de presen�a");
	    	Page page = new Page(pdf, Letter.PORTRAIT);
	    	
	    	pos = drawPdf("T�tulo: " + evento.getTitle(),pos,pdf,page,f1);
	    	pos = drawPdf("Local: " + evento.getPlace(),pos,pdf,page,f1);	
	    	pos = drawPdf("Categoria: " + evento.getCategory(),pos,pdf,page,f1);
	    	pos = drawPdf("Data: " + evento.getDate(),pos,pdf,page,f1);
	    	pos = drawPdf("Hora: " + evento.getHour(),pos,pdf,page,f1);
	    	pos = drawPdf("Descri��o: " + evento.getDescription(),pos,pdf,page,f1);
	    	pos = drawPdf("Resumo: " + evento.getSummary(),pos,pdf,page,f1);
			
			Box stripe = new Box();
	    	stripe.setSize(150.0f, 0.0f);
	    	stripe.setColor(Color.black);
	    	stripe.setPosition(100,700);
            stripe.drawOn(page);
            stripe.setPosition(400,700);
            stripe.drawOn(page);

            TextLine line = new TextLine(f1);
            line.setPosition(100.0, 715);
			line.setText("Assinatura Respons�vel");
			line.drawOn(page);
			line.setPosition(400.0, 715);
			line.setText("Assinatura Aluno");
			line.drawOn(page);
			
	    	pdf.flush();
	        fos.close();
	        return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	static int drawPdf(String text,int pos,PDF pdf,Page page,Font f1) {
		int len = text.length();
		TextLine line = new TextLine(f1);
		int drawSize = len - 90;
		int lines = 0;
		while (true) {
			try {
				drawSize = len - 90;
				if (drawSize <= 0) {
					line.setPosition(100.0, pos);
					line.setText(text.substring(lines * +90, lines
							* 90 + len));
					line.drawOn(page);
					pos+=30;
					return pos;
				} else {
					line.setPosition(100.0, pos);
					line.setText(text.substring(lines * +90, lines
							* 90 + 90));
					line.drawOn(page);
					if (!text.substring(lines * 90 + 89, lines * 90 + 90 +2)
							.contains(" ")) {
						line.setPosition(565, pos);
						line.setText("-");
						line.drawOn(page);
						pos+=15;
						line.setPosition(95, pos);
						line.setText("-");
						line.drawOn(page);
					}
					else pos+=15;
				}
				len = len - 90;
				lines++;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

}
