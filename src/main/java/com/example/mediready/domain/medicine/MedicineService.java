package com.example.mediready.domain.medicine;

import com.example.mediready.domain.medicine.dto.GetMedicineInfoRes;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MedicineService {

    @Value("openApi.serviceKey")
    private String serviceKey;

    private final MedicineRepository medicineRepository;
    private final RestTemplate restTemplate;

    public List<Medicine> searchMedicineByName(String keyword) {
        return medicineRepository.findMedicineByNameContaining(keyword);
    }

    public GetMedicineInfoRes getMedicineInfo(Long id) {
        String apiUrl = "http://apis.data.go.kr/1471000/DrugPrdtPrmsnInfoService05";
        String pageNo = "1";
        String numOfRows = "1";
        String type = "json";

        String url =
            apiUrl + "?serviceKey=" + serviceKey + "&pageNo=" + pageNo + "&numOfRows=" + numOfRows
                + "&type=" + type + "&item_seq=" + id;

        ResponseEntity<GetMedicineInfoRes> responseEntity = restTemplate.getForEntity(url,
            GetMedicineInfoRes.class);

        return responseEntity.getBody();
    }
}