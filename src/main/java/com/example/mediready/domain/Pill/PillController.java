package com.example.mediready.domain.Pill;

import com.example.mediready.domain.Pill.dto.GetPillSearchPageRes;
import com.example.mediready.domain.Pill.dto.GetPillSearchReq;
import com.example.mediready.global.config.BaseResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pills")
@RequiredArgsConstructor
public class PillController {

    private final PillService pillService;

    @GetMapping("/search")
    public BaseResponse<GetPillSearchPageRes<Pill>> searchPills(
        @RequestParam int pageNumber,
        @RequestParam int pageSize,
        @RequestBody GetPillSearchReq getPillSearchReq) {
        return new BaseResponse<>("검색 결과입니다.",
            pillService.searchPills(pageNumber, pageSize, getPillSearchReq));
    }
}
