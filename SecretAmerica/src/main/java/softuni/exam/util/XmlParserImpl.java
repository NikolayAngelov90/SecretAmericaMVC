package softuni.exam.util;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class XmlParserImpl implements XmlParser {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T fromFile(String filePath, Class<T> tClass) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(tClass);

        return (T) jaxbContext.createUnmarshaller().unmarshal(new File(filePath));
    }
}
