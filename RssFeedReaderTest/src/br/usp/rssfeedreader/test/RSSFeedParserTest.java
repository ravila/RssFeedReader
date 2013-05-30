package br.usp.rssfeedreader.test;

import br.usp.rssfeedreader.*;
import android.test.AndroidTestCase;

public class RSSFeedParserTest extends AndroidTestCase {
	RSSFeed rss;
	RSSFeedParser parser;
	
	public void setUp()
	{
		parser = new RSSFeedParser();
	}
	
	public void test_getFeedRssTestInvalidXmlFail()
	{
		rss = parser.getRSSFeed("lala","awd");
		AndroidTestCase.assertTrue(rss==null);
	}
	
	public void test_getFeedRssTestValidXmlOk()
	{
		rss = parser.getRSSFeed(getValidXml(), "aaa");
		AndroidTestCase.assertEquals(rss.getTitle(),"Instituto de Matemática e Estatística | IME-USP");
	}

	private String getValidXml()
	{
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
"<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\">" +
	"<channel>" +
		"<title>Instituto de Matemática e Estatística | IME-USP</title>" +
		"<description><![CDATA[Instituto de Matemática e Estatística | IME-USP]]></description>" +
		"<link>http://www.ime.usp.br</link>" +
		"<lastBuildDate>Thu, 30 May 2013 09:32:57 -0300</lastBuildDate>" +
		"<generator>Joomla! - Open Source Content Management</generator>" +
		"<atom:link rel=\"self\" type=\"application/rss+xml\" href=\"http://www.ime.usp.br/index.php?option=com_eventlist&amp;view=categoryevents&amp;format=feed&amp;id=32&amp;type=rss\"/>" +
		"<language>pt-br</language>" +
		"<item>" +
		"	<title>Seminário ZFC: Segmentação de imagens usando Modelos deformáveis</title>" +
			"<link>http://www.ime.usp.br/index.php?option=com_eventlist&amp;view=details&amp;id=3</link>" +
		"	<guid isPermaLink=\"true\">http://www.ime.usp.br/index.php?option=com_eventlist&amp;view=details&amp;id=3</guid>" +
			"<description><![CDATA[Título: Seminário ZFC: Segmentação de imagens usando Modelos deformáveis<br />Local: Instituto de Matemática e Estatística | IME-USP / Cidade Universitária - São Paulo<br />Categoria: MAP - Demais Eventos<br />Data: 24.05.2013<br />Hora: 10.00 h<br />Descrição: Seminário ZFC: Segmentação de imagens usando Modelos deformáveis<br /><br />PALESTRANTE: Jihan Zoghbi<br /><br />LOCAL: Sala 267, Bloco A<br /><br />HORÁRIO: 24 de maio, sexta-feira, às 10h<br /><br />RESUMO: Modelos deformáveis ou contornos ativos são definidos como curvas ou superfícies dentro do domínio da imagem, que podem mudar de acordo com a influência de forças internas e externas.&nbsp; Existem dois tipos de modelos deformáveis: modelos deformáveis paramétricos: snake (Kasse Terzopoulos, 1988); e modelos deformáveis geométricos: baseados em level set (Sethian e Osher, 1988).<br /><br />Neste seminário apresentamos as definições básicas desses modelos e uma aplicação usando esses modelos.]]></description>" +
			"<category>MAP - Demais Eventos</category>" +
		"	<pubDate>Tue, 21 May 2013 16:50:41 -0300</pubDate>" +
		"</item>" +
		"<item>" +
			"<title>Seminário ZFC: Exemplos de aplicações tipo ferradura</title>" +
			"<link>http://www.ime.usp.br/index.php?option=com_eventlist&amp;view=details&amp;id=3</link>" +
			"<guid isPermaLink=\"true\">http://www.ime.usp.br/index.php?option=com_eventlist&amp;view=details&amp;id=3</guid>" +
			"<description><![CDATA[Título: Seminário ZFC: Exemplos de aplicações tipo ferradura<br />Local: Instituto de Matemática e Estatística | IME-USP / Cidade Universitária - São Paulo<br />Categoria: MAP - Demais Eventos<br />Data: 17.05.2013<br />Hora: 10.00 h<br />Descrição: <strong>Seminário ZFC: Exemplos&nbsp; de aplicações tipo&nbsp;ferradura</strong><br /><br /> PALESTRANTE: Marcelo Caetano<br /><br />LOCAL: Sala 267, Bloco A<br /><br />HORÁRIO: 17 de maio, sexta-feira, às 10h<br /><br />RESUMO: Neste seminário serão introduzidas condições para que uma função <img width=\"103\" height=\"11\" title=\"f: N \\subset \\mathbb{R}^2 \\rightarrow \\mathbb{R}^2\" alt=\"f: N \\subset \\mathbb{R}^2 \\rightarrow \\mathbb{R}^2\" src=\"http://s0.wp.com/latex.php?zoom=2&amp;latex=f%3A+N+%5Csubset+%5Cmathbb%7BR%7D%5E2+%5Crightarrow+%5Cmathbb%7BR%7D%5E2&amp;bg=ffffff&amp;fg=333333&amp;s=0\" scale=\"2\" src-orig=\"http://s0.wp.com/latex.php?latex=f%3A+N+%5Csubset+%5Cmathbb%7BR%7D%5E2+%5Crightarrow+%5Cmathbb%7BR%7D%5E2&amp;bg=ffffff&amp;fg=333333&amp;s=0\" /> seja topologicamente uma ferradura, o que, geometricamente implica ser similar a uma ferradura de Smale.<br /><br />Iremos comentar alguns exemplos onde esse tipo de aplicação ocorre, para desta forma concluir alguma característica da dinâmica das mesmas.]]></description>" +
			"<category>MAP - Demais Eventos</category>" +
			"<pubDate>Wed, 15 May 2013 01:39:39 -0300</pubDate>" +
		"</item>" +
		"</channel>" +
			"</rss>";
	}
}
