package com.antonia.web.crawler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {

	private URI currentGivanUri;
	private String needle;
	private Queue<URI> links=new LinkedList<URI>();
	private URI searchedURI;

	private void setNeedle(String needle){
		this.needle = needle;
	}
	
	private void setCurrentGivanUri(URI currentGivanUri){
		this.currentGivanUri = currentGivanUri;
	}
	
	public URI crawl(URI uri, final String needle) throws URISyntaxException, IOException{
		setNeedle(needle);
		setCurrentGivanUri(uri);
		links.add(uri);
		getUrls();
		links.clear();
		return searchedURI;
	}
	
	private void getUrls() throws URISyntaxException, IOException{

		String content = getContentOfURI(links.poll());
		getChildrenLinksFromHtml(content);
		while (!links.isEmpty()){
			String currentContent = getContentOfURI(links.peek());
			if(currentContent.contains(needle)){
				searchedURI = links.peek();
				break;
			} else {
				getChildrenLinksFromHtml(currentContent);
			}
			links.poll();
		}

	}
		
	private void getChildrenLinksFromHtml(String content) throws URISyntaxException, UnknownHostException{
		Pattern pattern = Pattern.compile("<a href=\"(.*?)\">");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			URI current = makeURIFromString(matcher.group(1));
			if(!current.equals(null) && !links.contains(current)){
				links.add(current);
			}
			
		}
	}
	
	private URI makeURIFromString(String item) throws URISyntaxException, UnknownHostException{
		String currentUrl;
		if (item.startsWith("http")){
			currentUrl = item;
			
		} else if(item.endsWith("/")){
			currentUrl = this.currentGivanUri + item;
		} else {
			currentUrl = this.currentGivanUri + "/" + item;
		}
		if (currentUrl.contains("../")){
			return new URI(currentUrl.replace("../", ""));
		} 
		try{
	        URI path = new URI(currentUrl);
	        String host = path.getHost();

	    }catch(URISyntaxException e){
	    	return new URI(this.currentGivanUri.toString());
	    }
		return new URI(currentUrl);
	}
	
	private String getContentOfURI(URI uri) throws IOException, FileNotFoundException{
		URL url = uri.toURL();
		BufferedReader in;
		try{
			in = new BufferedReader(new InputStreamReader(url.openStream()));

		}
		catch (FileNotFoundException e){
			return "";
		} catch(UnknownHostException e){
			return "";
		}
		StringBuilder content= new StringBuilder();
        while (in.readLine() != null)
            content.append(in.readLine());
        in.close();
		return content.toString();
	}
	
}
