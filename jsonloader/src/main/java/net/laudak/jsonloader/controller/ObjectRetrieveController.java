package net.laudak.jsonloader.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.persistence.jaxb.JAXBContextProperties;

import net.laudak.jsonloader.model.Info;

@Path("object")
public class ObjectRetrieveController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Info info() throws JAXBException, IOException {
        JAXBContext ctx = jaxbContext(moxyConfigForJson(), Info.class);
		Unmarshaller unmarshaller = ctx.createUnmarshaller();
		Info info = null;
		try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("info.json");) {
			info = unmarshaller.unmarshal(new StreamSource(inputStream), Info.class).getValue();
		}
		return info;
		
	}
	
	private JAXBContext jaxbContext(Map<String, Object> config, Class<?> ... classesToBeBound) throws JAXBException {
		return JAXBContext.newInstance(classesToBeBound, config);
	}
	
	private Map<String, Object> moxyConfigForJson() {
		Map<String, Object> jaxbProperties = new HashMap<>(2);
        jaxbProperties.put(JAXBContextProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
        jaxbProperties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
		return jaxbProperties;
	}
}
