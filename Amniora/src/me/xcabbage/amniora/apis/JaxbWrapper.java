package me.xcabbage.amniora.apis;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import me.xcabbage.amniora.GameAmn;

/**
 * The Jaxb.java class responsible for
 * 
 * @author xCabbage [github.com/xcabbage]
 * 
 * @info for the Amniora project [github.com/xcabbage/amniora] created 29. 9.
 *       2014 12:19:30
 * 
 */
public class JaxbWrapper {
	public static void saveVariables(Variables var) { 
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(Variables.class);

			// marshal into XML via System.out
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(var, System.out);
			

		} catch (JAXBException e) {

			GameAmn.error(e.getStackTrace());
		}

	}
}
