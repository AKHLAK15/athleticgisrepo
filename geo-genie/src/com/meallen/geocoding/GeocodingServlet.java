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
 * 
 * @author Matthew Allen
 * @version October 18, 2013
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
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		session = request.getSession(true);

		List<Hit> hits = null;
		if (session.getAttribute("hitList") != null) {
			hits = (ArrayList<Hit>) (session.getAttribute("hitList"));
		} else {
			hits = new ArrayList<Hit>();
		}

		// stop at 10 items
		if (hits.size() < 10) {
			Double lat = parseCoordinate(request.getParameter("lat"),
					default_latitude, min_latitude, max_latitude);
			Double lon = parseCoordinate(request.getParameter("lon"),
					default_longitude, min_longitude, max_longitude);
			String id = getId();

			Map<String, String> parms = new HashMap<String, String>();
			parms.put("latlng", lat + "," + lon);
			parms.put("sensor", "false");

			Hit hit = new Hit();
			hit.setId(id);
			StringCoordinate coord = new StringCoordinate();
			coord.setLat(lat.toString());
			coord.setLon(lon.toString());
			hit.setQuery(coord);
			String address = getAddress(
					buildURL("http", "maps.googleapis.com",
							"/maps/api/geocode/json", parms));
			String ip = getIP(buildURL("http", "ip.jsontest.com", null, null));

			Map<String, String> weatherParms = new HashMap<String, String>();
			weatherParms.put("lat", lat.toString());
			weatherParms.put("lon", lon.toString());
			weatherParms.put("cnt", "1");

			Map<String, String> weather = getWeather(buildURL("http",
					"api.openweathermap.org", "/data/2.1/find/city",
					weatherParms));

			Response resp = new Response();
			resp.setAddress(address);
			resp.setSourceIp(ip);
			resp.setTime((new Date()).getTime());
			resp.setTemp(weather.get("temp"));
			resp.setWeather(weather.get("weather"));
			hit.setResponse(resp);
			hits.add(hit);
		}
		try {
			out.println(new JSONSerializer().exclude("*.class").serialize(hits));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		session.setAttribute("hitList", hits);

	}

	/**
	 * This method gets ip from service at http://ip.jsontest.com.
	 * 
	 * @param uri
	 * @return
	 */
	private String getIP(URI uri) {
		String entity;
		entity = getResponseBody(uri);
		Map<String, String> map = new JSONDeserializer<HashMap<String, String>>()
				.deserialize(entity, HashMap.class);
		return map.get("ip");
	}

	/**
	 * This method gets weather information from service at example
	 * http://api.openweathermap.org/data/2.1/find/city?lat=43&lon=-83&cnt=1
	 * Returns null for temp and weather if distance is greater than 50 from observation.
	 * 
	 * @param uri
	 * @return
	 */
	private Map<String, String> getWeather(URI uri) {
		Map<String, String> weather = new HashMap<String, String>();
		String entity;
		entity = getResponseBody(uri);
		WeatherHit hit = new JSONDeserializer<WeatherHit>().deserialize(entity,
				WeatherHit.class);

		Double distanceFromObservation = hit.getList().get(0).getDistance();
		String temp = null;
		String weatherDesc = null;

		// Check whether distance from observation is less than 50. If it is set
		// the weather and temp otherwise leave null.
		if (distanceFromObservation < 50.0) {
			temp = covertToFahrenheit(hit.getList().get(0).getMain().getTemp())
					.toString();
			weatherDesc = hit.getList().get(0).getWeather().get(0)
					.getDescription();
		}
		weather.put("temp", temp);
		weather.put("weather", weatherDesc);

		return weather;
	}

	/**
	 * Converts Kelvin reading to Fahrenheit
	 * 
	 * @param kTemp
	 * @return
	 */
	public Double covertToFahrenheit(Double kTemp) {
		return (9 / 5) * (kTemp - 273) + 32;
	}

	/**
	 * Uses service at http://maps.googleapis.com/maps/api/geocode/json?latlng=43.81,-91&sensor=false for example
	 * To get location information based on latitude and longitude.
	 * 
	 * @param uri
	 * @return
	 */
	private String getAddress(URI uri) {																	
		String entity;
		entity = getResponseBody(uri);
		GoogleHit hit = new JSONDeserializer<GoogleHit>().deserialize(entity,
				GoogleHit.class);

		List<Result> results = hit.getResults();
		String address = null;
		
		//if no results leave address = null otherwise set it.
		if (!results.isEmpty()) {
			address = results.get(0).getFormatted_address();
		}
		return address;
	}

	/**
	 * Gets the body from http response at given uri.
	 * 
	 * @param uri
	 * @return
	 */
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseBody;
	}

	/**
	 * Creates url based on scheme, host, path, and query parameters.
	 * 
	 * @param scheme
	 * @param host
	 * @param path
	 * @param parms
	 * @return
	 */
	private URI buildURL(String scheme, String host, String path,
			Map<String, String> parms) {
		
		URI uri = null;
		URIBuilder uriBuilder = new URIBuilder().setScheme(scheme)
				.setHost(host);
		if (path != null) {
			uriBuilder.setPath(path);
		}

		if (parms != null) {
			for (String key : parms.keySet()) {
				uriBuilder.setParameter(key, parms.get(key));
			}
		}
		try {
			uri = uriBuilder.build();
		} catch (URISyntaxException e) {
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
		
		session = request.getSession();
		List<Hit> hits = (ArrayList<Hit>) (session.getAttribute("hitList"));
		Hit deleteHit = new Hit();
		deleteHit.setId(request.getParameter("id").toString());

		//remove hit with id in query parameter
		for (int i = 0; i < hits.size(); i++) {
			if (hits.get(i).getId()
					.equals(request.getParameter("id").toString())) {
				hits.remove(i);
			}
		}

		session.setAttribute("hitList", hits);
		PrintWriter out = response.getWriter();
		try {
			out.println(new JSONSerializer().exclude("*.class").serialize(hits)); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}

	}

	/**
	 * Parses double to get coordinate. If parse fails coordinate will be a default coordinate.
	 * Also if coordinate is not valid latitude or longitude, then coordinate will be default coordinate.
	 * 
	 * @param coord_String
	 * @param default_coord
	 * @param min
	 * @param max
	 * @return
	 */
	private Double parseCoordinate(String coord_String, Double default_coord,
			Double min, Double max) {
		Double coordinate = null;
		if (coord_String != null) {
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

	
	/**
	 * Generates unique id across all users across time.
	 * 
	 * @return
	 */
	private String getId() {
		return UUID.randomUUID().toString();
	}
}
