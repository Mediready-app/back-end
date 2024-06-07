package com.example.mediready.domain.medicine;

import com.example.mediready.domain.medicine.dto.GetMedicineInfoRes;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Service
@RequiredArgsConstructor
public class MedicineService {

    @Value("${openApi.serviceKey}")
    private String serviceKey;
    @Value("${openApi.endpoint}")
    private String endpoint;

    private final MedicineRepository medicineRepository;

    public List<Medicine> searchMedicineByName(String keyword) {
        return medicineRepository.findMedicineByNameContaining(keyword);
    }

    public GetMedicineInfoRes getMedicineInfo(Long id) throws Exception {
        String url = endpoint +
            "?serviceKey=" + serviceKey +
            "&pageNo=" + "1" +
            "&numOfRows=" + "1" +
            "&type=" + "json" +
            "&item_seq=" + id;

        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI(url);
        String jsonString = restTemplate.getForObject(uri, String.class);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);

        JSONObject jsonBody = (JSONObject) jsonObject.get("body");
        JSONArray jsonItemList = (JSONArray) jsonBody.get("items");

        GetMedicineInfoRes result = new GetMedicineInfoRes();
        for (Object o : jsonItemList) {
            JSONObject item = (JSONObject) o;
            result.setItemName((String) item.get("ITEM_NAME"));
            result.setChart((String) item.get("CHART"));
            result.setEntpName((String) item.get("ENTP_NAME"));
            result.setEtcOtcCode((String) item.get("ETC_OTC_CODE"));
            result.setEeDocData(getTextFromXML((String) item.get("EE_DOC_DATA")));
            result.setUdDocData(getTextFromXML((String) item.get("UD_DOC_DATA")));
            result.setNbDocData(getTextFromXML((String) item.get("NB_DOC_DATA")));
        }
        return result;
    }

    private static String getTextFromXML(String xml) throws Exception {
        // XML 문자열을 Document 객체로 변환
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

        // XML 내용을 순수 텍스트로 변환
        return getTextFromNode(doc.getDocumentElement(), true);
    }

    private static String getTextFromNode(Node node, boolean preserveNewlines) {
        StringBuilder textContent = new StringBuilder();
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE
                || child.getNodeType() == Node.CDATA_SECTION_NODE) {
                textContent.append(child.getTextContent().trim());
                if (preserveNewlines) {
                    textContent.append("\n");
                } else {
                    textContent.append(" ");
                }
            } else if (child.getNodeType() == Node.ELEMENT_NODE) {
                textContent.append(getTextFromNode(child, preserveNewlines));
            }
        }
        return textContent.toString().trim();
    }
}