package pt.unl.fct.di.apdc.firstwebapp.resources;

import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import com.google.gson.Gson;


@Path("/utils")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8") 
public class ComputationResource {

	private static final Logger LOG = Logger.getLogger(ComputationResource.class.getName()); 
	private final Gson g = new Gson();

	private static final DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");

	public ComputationResource() {} //nothing to be done here @GET

	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public Response hello() throws IOException{
		try {
			throw new IOException("UPS");
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Exception on Method /hello", e);
			return Response.temporaryRedirect(URI.create("/error/500.html")).build();
		}
	}
	
	@GET
	@Path("/time")
	public Response getCurrentTime(@CookieParam("session::apdc") Cookie cookie) {
		if(!LoginResource.checkPermissions(cookie, LoginResource.ADMIN)) {
			return Response.status(Status.FORBIDDEN).entity("Incorrect username or password.").build();
		}
		return Response.ok().entity(g.toJson(fmt.format(new Date()))).build();
	}

}