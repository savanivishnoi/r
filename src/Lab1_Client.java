import java.util.Date;
import javax.ws.rs.client.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;

class ClientRecord {
	public String lv_client;
	public String ClientId;
	public String DataInput;
	ClientRecord(String client, String Client_Id, String Data_Input) {
		lv_client = client;
		ClientId = Client_Id;
	    Date dates = new Date();
	    DataInput = Data_Input;
		//date = curdate.format();
	}
	String json_str(){
		return "{\"Client_Id\":\"" + ClientId + 
				"\", \"Client\" : \""+ lv_client + 
				"\", \"DataInput\" : \""+ DataInput + "\"}";
	}
	
}

public class Lab1_Client {
	public static void main(String[] args) throws InterruptedException  {
		String myId = args[0];
		Integer count = 0;
		for (;;)
		{
			count++;
			String client_id, DataInput;
			client_id = count.toString();
			DataInput = args[0]+" Call -"+count;//Message Creation
			ClientRecord rec = new ClientRecord(args[0],client_id, DataInput);		
			Client cl_sv = ClientBuilder.newClient();
			String input = "{\"message\":\"hello\"}";
			WebTarget sv_tar = cl_sv.target("http://localhost:8080/273_Lab1_Server/api/Input");
			Response response = sv_tar.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(rec.json_str()));
			
			response.bufferEntity();
			ObjectMapper objMap = new ObjectMapper();
			objMap.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
			System.out.println(response.readEntity(String.class) + "  Client - "+args[0]);
			Thread.sleep(5000);
		}
	}
}