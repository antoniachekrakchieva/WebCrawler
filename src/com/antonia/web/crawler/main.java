package com.antonia.web.crawler;

import java.net.URI;

public class main {


	public static void main(String[] args) throws Exception{
		
		WebCrawler crawler = new WebCrawler();

	    URI startLocation = new URI("http://ebusiness.free.bg");
	    URI link = crawler.crawl(startLocation, "Револвираща");
	    System.out.println("result :" + link.toString()); //=> http://ebusiness.free.bg/cards_bank_cards.html
	    startLocation = new URI("http://blog.hackbulgaria.com");
	    link = crawler.crawl(startLocation,
	            "Ще минем от HTTP през Sockets през Mongo / Redis и ще стигнем до потоци и native C++ extensions");

	    System.out.println("result :" + link.toString()); // => http://blog.hackbulgaria.com/fall-of-the-hackers/

	    startLocation = new URI("http://www.youramazingplaces.com/");
	    link = crawler.crawl(startLocation,
	            "Exploring The Narrows in the Zion National Park, Utah");

	    System.out.println("result :" + link.toString()); // => http://www.youramazingplaces.com/?cat=2
	    
		
	}

}
