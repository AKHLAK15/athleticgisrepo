package com.meallen.geocoding;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Servlet implementation class GeocodingServlet
 */
@WebServlet(description = "This is a Geocoding Servlet", urlPatterns = { "/latlon" })
public class GeocodingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Double default_longitude = 43.81;
	private static final Double default_latitude = -91.23;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GeocodingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In Get");
		Double lat = parseCoordinate(request.getParameter("lat"), default_longitude);
		Double lon = parseCoordinate(request.getParameter("lon"), default_latitude);
		String id = getId();
		String address = "Sermersooq, Greenland";
		String sourceIp = "138.49.37.14";
		long time = 1381175833227L;
		
		PrintWriter out = response.getWriter();
		
//		System.out.println("[");
//		System.out.println("{");
//		System.out.println("\"id\": " + "\"" + id + "\",");
//		System.out.println("\"query\": {");
//		System.out.println("\"lat\": \"" + lat + "\",");
//		System.out.println("\"lon\": \"" + lon + "\"},");
//		
//		System.out.println("\"response\": {");
//		System.out.println("\"address\": \"" + address + "\",");
//		System.out.println("\"sourceIp\": \"" + sourceIp + "\",");
//		System.out.println("\"time\": " + time + "},");
//		
//		System.out.println("}");
//		System.out.println("]");
		
		Map<String, String> parms = new HashMap<String, String>();
		parms.put("latlng", lat + "," + lon);
		parms.put("sensor","false");
		
		URI uri;
		String entity;
		try {
			uri = buildURL("http","maps.googleapis.com", "/maps/api/geocode/json", parms);
			entity = getResponseBody(uri);
			System.out.println(entity);
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			System.out.println("[{\"id\":\"d1b3eb46-3833-451f-b927-592de14cce18\",\"query\":{\"lat\":\"43.81\",\"lon\":\"-91.23\"},\"response\":{\"address\":\"1702-1720 King Street, La Crosse, WI 54601, USA\",\"sourceIp\":\"138.49.196.103\",\"temp\":\"60.80\",\"time\":1381794724959,\"weather\":\"Sky is Clear\"}}]");
			out.println("[{\"id\":\"d1b3eb46-3833-451f-b927-592de14cce18\",\"query\":{\"lat\":\"43.81\",\"lon\":\"-91.23\"},\"response\":{\"address\":\"1702-1720 King Street, La Crosse, WI 54601, USA\",\"sourceIp\":\"138.49.196.103\",\"temp\":\"60.80\",\"time\":1381794724959,\"weather\":\"Sky is Clear\"}}]");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
	
	private String getResponseBody(URI uri) throws ClientProtocolException, IOException {
		String responseBody;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(uri.toString());
		CloseableHttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		responseBody = EntityUtils.toString(entity);
		//maybe surround with try catch and put response.close in finally
		
		response.close();
		return responseBody;
	}
	
	private URI buildURL(String scheme, String host, String path, Map<String, String> parms) throws URISyntaxException {
		URIBuilder uriBuilder = new URIBuilder()
					.setScheme(scheme)
					.setHost(host)
					.setPath(path);
		for(String key : parms.keySet()) {
			uriBuilder.setParameter(key, parms.get(key));
		}
		return uriBuilder.build();
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("In Post");
	}
	
	/**
	 * @param coord_String
	 * @param default_coord
	 * @return
	 */
	private Double parseCoordinate(String coord_String, Double default_coord) {
		Double coordinate; 
		try
		{
			coordinate = Double.parseDouble(coord_String);
		}
		catch(NumberFormatException nfe)
		{
			coordinate = default_coord;
		}
		return coordinate;
	}
	
	private String getId() {
		return UUID.randomUUID().toString();
	}
}
