package org.Automation_Service.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.Automation_Service.Business.BusinessController;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@Path("/testservice")
public class TestService {
	@POST
	@Produces({MediaType.TEXT_HTML})
	@Path("/t")
	public String runTest(String requestmodel)
	{
		String output = "";
		JSONParser jparser = new JSONParser();
		try {
			
			Object obj = jparser.parse(requestmodel);
			JSONObject jobj = (JSONObject) obj;
			BusinessController bc = new BusinessController(jobj);
			output = bc.newTest();
			
		} catch (ParseException e) {
			
			output = "failed";
			e.printStackTrace();
		}
		
		return output;
		
	}


}
