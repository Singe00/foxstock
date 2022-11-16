package kumoh.opensource.foxstock.api;

import kumoh.opensource.foxstock.api.dto.OpenDartCodeDto;
import kumoh.opensource.foxstock.api.repository.OpenDartCodeDtoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OpenDartCodeApi {

    private static final String URL = "http://opendart.fss.or.kr/api/corpCode.xml";

    private final OpenDartCodeDtoRepository openDartCodeDtoRepository;


    public void parsingXml(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        Document document;
        try {
            document = builder.parse("src/main/resources/CORPCODE.xml");
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        document.getDocumentElement().normalize();

        NodeList nodeList = document.getElementsByTagName("list");
        for (int i = 0; i < nodeList.getLength(); i++) {

            String stockCode = nodeList.item(i).getChildNodes().item(5).getTextContent();
            String corName = nodeList.item(i).getChildNodes().item(3).getTextContent();
            String corpCode = nodeList.item(i).getChildNodes().item(1).getTextContent();

            if(!stockCode.equals(" ")){
                OpenDartCodeDto openDartCodeDto = new OpenDartCodeDto();
                openDartCodeDto.setStockCode(stockCode);
                openDartCodeDto.setCorpName(corName);
                openDartCodeDto.setCorpCode(corpCode);

                openDartCodeDtoRepository.save(openDartCodeDto);
            }
        }
    }
}
