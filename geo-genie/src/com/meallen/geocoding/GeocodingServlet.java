package com.meallen.geocoding;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

/**
 * Servlet implementation class GeocodingServlet
 */
@WebServlet(description = "This is a Geocoding Servlet", urlPatterns = { "/latlon" })
public class GeocodingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Double default_latitude = 43.81;
	private static final Double default_longitude = -91.23;
	private static final Double min_longitude = -180.0;
	private static final Double min_latitude = -90.0;
	private static final Double max_longitude = 180.0;
	private static final Double max_latitude = 90.0;
	private static final String default_temp = "44.01";
	private static final String default_weather = "Sky is Clear";
	private static final String default_address = "1702-1720 King Street, La Crosse, WI 54601, USA"; 
	
	private HttpSession session;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GeocodingServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In Get");
		Double lat = parseCoordinate(request.getParameter("lat"),
				default_latitude, min_latitude, max_latitude);
		Double lon = parseCoordinate(request.getParameter("lon"),
				default_longitude, min_longitude, max_longitude);
		String id = getId();
		// String address = "Sermersooq, Greenland";
		//String sourceIp = "138.49.37.14";
		// long time = 1381175833227L;
		

		PrintWriter out = response.getWriter();

		// System.out.println("[");
		// System.out.println("{");
		// System.out.println("\"id\": " + "\"" + id + "\",");
		// System.out.println("\"query\": {");
		// System.out.println("\"lat\": \"" + lat + "\",");
		// System.out.println("\"lon\": \"" + lon + "\"},");
		//
		// System.out.println("\"response\": {");
		// System.out.println("\"address\": \"" + address + "\",");
		// System.out.println("\"sourceIp\": \"" + sourceIp + "\",");
		// System.out.println("\"time\": " + time + "},");
		//
		// System.out.println("}");
		// System.out.println("]");

		Map<String, String> parms = new HashMap<String, String>();
		parms.put("latlng", lat + "," + lon);
		parms.put("sensor", "false");
		
		Hit hit = new Hit();
		hit.setId(id);
		
		//Map<String, String> query = new HashMap<String, String>();
		//query.put("lat", lat.toString());
		//query.put("lon", lon.toString());
		//hit.setQuery(query);
		//Coordinate coord = new Coordinate();
		//coord.setLat(lat);
		//coord.setLon(lon);
		
		//maybe call StringCoordinate Query
		StringCoordinate coord = new StringCoordinate();
		coord.setLat(lat.toString());
		coord.setLon(lon.toString());
		hit.setQuery(coord);
	
		

		// URI uri;

		String address = getAddress(
				buildURL("http", "maps.googleapis.com",
						"/maps/api/geocode/json", parms), parms);
		String ip = getIP(buildURL("http", "ip.jsontest.com", null, null));
		
		
		Map<String, String> weatherParms = new HashMap<String, String>();
		weatherParms.put("lat", lat.toString());
		weatherParms.put("lon", lon.toString());
		weatherParms.put("cnt", "1");
		//parms.put("sensor", "false");
		
		Map<String, String> weather = getWeather(buildURL("http", "api.openweathermap.org",
				"/data/2.1/find/city", weatherParms));

//		Map<String, String> resp = new HashMap<String, Object>();
//		resp.put("address", address);
//		resp.put("sourceIp", ip);
//		resp.put("time", (new Date()).getTime());
//		hit.setResponse(resp);
//		
		
		Response resp = new Response();
		resp.setAddress(address);
		resp.setSourceIp(ip);
		resp.setTime((new Date()).getTime());
		resp.setTemp(weather.get("temp"));
		resp.setWeather(weather.get("weather"));
		hit.setResponse(resp);
		
		List<Hit> hits = null; // = (List<Hit>)session.getAttribute("hitList");
		
		session = request.getSession(true);
		//remember to stop at 10 items
		if(session.getAttribute("hitList") != null) {
			hits = (ArrayList<Hit>)(session.getAttribute("hitList"));
		} else {
			hits = new ArrayList<Hit>();
		}
		 ///TODO add this somewhere check size
		///if(hits.size()<10) {
		 
			
			try {
			// String hit1 = new JSONSerializer().exclude("*.class").include("query").include("response").serialize(hit);
				//hits = new ArrayList<Hit>();
				hits.add(hit);
				System.out.println(new JSONSerializer().exclude("*.class").serialize(hits)); //.replace("\\", "")
				out.println(new JSONSerializer().exclude("*.class").serialize(hits)); //.replace("\\", "")
		 //System.out.println("[{\"id\":\"d1b3eb46-3833-451f-b927-592de14cce18\",\"query\":{\"lat\":\"43.81\",\"lon\":\"-91.23\"},\"response\":{\"address\":\"1702-1720 King Street, La Crosse, WI 54601, USA\",\"sourceIp\":\"138.49.196.103\",\"temp\":\"60.80\",\"time\":1381794724959,\"weather\":\"Sky is Clear\"}}]");
		 //out.println("[{\"id\":\"d1b3eb46-3833-451f-b927-592de14cce18\",\"query\":{\"lat\":\"43.81\",\"lon\":\"-91.23\"},\"response\":{\"address\":\"1702-1720 King Street, La Crosse, WI 54601, USA\",\"sourceIp\":\"138.49.196.103\",\"temp\":\"60.80\",\"time\":1381794724959,\"weather\":\"Sky is Clear\"}}]");
		 } catch (Exception e) {
		 e.printStackTrace();
		 } finally {
		 out.close();
		 }
		 
		 session.setAttribute("hitList", hits);
		 
	}
	
	private String getIP(URI uri) {
		String entity;
		entity = getResponseBody(uri);
		Map<String,String> map = new JSONDeserializer<HashMap<String,String>>().deserialize(entity, HashMap.class);		
		return map.get("ip");
	}
	
	private Map<String, String> getWeather(URI uri) {
		Map<String, String> weather = new HashMap<String, String>();
		String entity;	
		entity = getResponseBody(uri);
		WeatherHit hit = new JSONDeserializer<WeatherHit>().deserialize(entity, WeatherHit.class);
		weather.put("temp", covertToFahrenheit(hit.getList().get(0).getMain().getTemp()).toString());  //TODO convert this from Kelvin to Fahrenheit
		weather.put("weather", hit.getList().get(0).getWeather().get(0).getDescription());
		
		System.out.println(covertToFahrenheit(hit.getList().get(0).getMain().getTemp()).toString());
		System.out.println(hit.getList().get(0).getWeather().get(0).getDescription());
		
		return weather;
	}
	
	public Double covertToFahrenheit(Double kTemp) {
		return (9/5)*(kTemp - 273) + 32;
	}

	private String getAddress(URI uri, Map<String, String> parms) { //TODO remove parms is used?
		String entity;	
		entity = getResponseBody(uri);
		GoogleHit hit = new JSONDeserializer<GoogleHit>().deserialize(entity, GoogleHit.class);
		System.out.println(hit.getResults());
		
		System.out.println(uri);
		
		List<Result> results = hit.getResults();
		String address = default_address;
		if(!results.isEmpty()) {
			address = results.get(0).getFormatted_address();
		}
		
		//TODO check at get 0 if empty return default.
		return address;
	}

	private String getResponseBody(URI uri) {
		String responseBody = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(uri.toString());
		CloseableHttpResponse response;
		
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseBody = EntityUtils.toString(entity);
			}
			response.close();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return responseBody;
	}

	private URI buildURL(String scheme, String host, String path,
			Map<String, String> parms) {

		URI uri = null;
		URIBuilder uriBuilder = new URIBuilder().setScheme(scheme)
				.setHost(host);
		if(path != null) {
			uriBuilder.setPath(path);
		}
		
		if(parms != null) {
			for (String key : parms.keySet()) {
				uriBuilder.setParameter(key, parms.get(key));
			}
		}
		try {
			uri = uriBuilder.build();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uri;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("In Post");
		
		System.out.println(request.getParameter("id"));
		session = request.getSession();
		List<Hit> hits = (ArrayList<Hit>)(session.getAttribute("hitList"));
		Hit deleteHit = new Hit();
		deleteHit.setId(request.getParameter("id").toString());
		
		System.out.println(request.getParameter("id"));
		//System.out.println(request.getParameter("id"));
		
		//hits.remove(deleteHit);
		//hits.remove(0);
//		for(Hit h : hits) {
//			if(h.getId().equals(request.getParameter("id").toString())) {
//				hits.remove(h);
//			}
//		}
		
		for(int i = 0; i < hits.size(); i++) {
			if(hits.get(i).getId().equals(request.getParameter("id").toString())) {
				hits.remove(i);
			}
		}
		
		
		System.out.println("List Size " + hits.size());
		session.setAttribute("hitList", hits);
		PrintWriter out = response.getWriter();
		try {
			// String hit1 = new JSONSerializer().exclude("*.class").include("query").include("response").serialize(hit);
				//hits = new ArrayList<Hit>();
				//hits.add(hit);
				System.out.println(new JSONSerializer().exclude("*.class").serialize(hits)); //.replace("\\", "")
				out.println(new JSONSerializer().exclude("*.class").serialize(hits)); //.replace("\\", "")
		 //System.out.println("[{\"id\":\"d1b3eb46-3833-451f-b927-592de14cce18\",\"query\":{\"lat\":\"43.81\",\"lon\":\"-91.23\"},\"response\":{\"address\":\"1702-1720 King Street, La Crosse, WI 54601, USA\",\"sourceIp\":\"138.49.196.103\",\"temp\":\"60.80\",\"time\":1381794724959,\"weather\":\"Sky is Clear\"}}]");
		 //out.println("[{\"id\":\"d1b3eb46-3833-451f-b927-592de14cce18\",\"query\":{\"lat\":\"43.81\",\"lon\":\"-91.23\"},\"response\":{\"address\":\"1702-1720 King Street, La Crosse, WI 54601, USA\",\"sourceIp\":\"138.49.196.103\",\"temp\":\"60.80\",\"time\":1381794724959,\"weather\":\"Sky is Clear\"}}]");
		 } catch (Exception e) {
		 e.printStackTrace();
		 } finally {
		 out.close();
		 }
		
	}

	/**
	 * @param coord_String
	 * @param default_coord
	 * @param min
	 * @param max
	 * @return
	 */
	private Double parseCoordinate(String coord_String, Double default_coord,
			Double min, Double max) {
		Double coordinate = null;
		if(coord_String != null) {
			try {
				coordinate = Double.parseDouble(coord_String);
			} catch (NumberFormatException nfe) {
				coordinate = default_coord;
			}
		}
		if (coord_String == null || coordinate > max || coordinate < min) {
			coordinate = default_coord;
		}
		return coordinate;
	}

	private String getId() {
		return UUID.randomUUID().toString();
	}
}
