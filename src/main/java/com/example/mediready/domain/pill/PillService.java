package com.example.mediready.domain.pill;

import com.example.mediready.domain.pill.dto.GetPillSearchReq;
import com.example.mediready.domain.pill.dto.GetPillSearchRes;
import com.example.mediready.domain.medicine.Medicine;
import com.example.mediready.domain.myMedicineList.MyMedicineListRepository;
import com.example.mediready.domain.user.User;
import com.example.mediready.global.common.PagedResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PillService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final MyMedicineListRepository myMedicineListRepository;

    public PagedResponse<GetPillSearchRes> searchPills(User user, int pageNumber, int pageSize,
        GetPillSearchReq getPillSearchReq) {
        String sql = buildSqlQuery(getPillSearchReq);

        Query query = entityManager.createNativeQuery(sql, Pill.class);
        applyPagination(query, pageNumber, pageSize);

        List<Pill> pills = query.getResultList();
        long totalElements = countTotalElements(sql);
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        List<GetPillSearchRes> pillSearchResList = pills.stream()
            .map(pill -> {
                Medicine medicine = pill.getMedicine();
                boolean stored =
                    user != null && myMedicineListRepository.existsByUserIdAndMedicineId(
                        user.getId(), medicine.getId());

                return new GetPillSearchRes(medicine, stored);
            })
            .collect(Collectors.toList());

        return createPagedResponse(pillSearchResList, pageNumber, pageSize, totalElements,
            totalPages);
    }

    private String buildSqlQuery(GetPillSearchReq getPillSearchReq) {
        StringBuilder sql = new StringBuilder("SELECT * FROM pill p WHERE ");

        boolean isFirstCondition = true;

        if (getPillSearchReq.getFrontMark() != null && !getPillSearchReq.getFrontMark().isEmpty()) {
            String frontMark = splitAndFormatMark(getPillSearchReq.getFrontMark());
            sql.append(isFirstCondition ? "" : " AND ");
            sql.append("MATCH(p.front_mark) AGAINST('").append(frontMark)
                .append("' IN BOOLEAN MODE)");
            isFirstCondition = false;
        }
        if (getPillSearchReq.getBackMark() != null && !getPillSearchReq.getBackMark().isEmpty()) {
            String backMark = splitAndFormatMark(getPillSearchReq.getBackMark());
            sql.append(isFirstCondition ? "" : " AND ");
            sql.append("MATCH(p.back_mark) AGAINST('").append(backMark)
                .append("' IN BOOLEAN MODE)");
            isFirstCondition = false;
        }
        if (getPillSearchReq.getForm() != null && !getPillSearchReq.getForm().isEmpty()) {
            sql.append(isFirstCondition ? "" : " AND ");
            sql.append("MATCH(p.form) AGAINST('").append(getPillSearchReq.getForm())
                .append("' IN BOOLEAN MODE)");
            isFirstCondition = false;
        }
        if (getPillSearchReq.getColor() != null && !getPillSearchReq.getColor().isEmpty()) {
            List<String> colorKeywords = Arrays.asList(getPillSearchReq.getColor().split("\\s+"));
            sql.append(isFirstCondition ? "" : " AND ");
            sql.append("(");
            for (int i = 0; i < colorKeywords.size(); i++) {
                if (i > 0) {
                    sql.append(" AND ");
                }
                sql.append("p.color LIKE '%").append(colorKeywords.get(i)).append("%'");
            }
            sql.append(")");
            isFirstCondition = false;
        }
        if (getPillSearchReq.getShape() != null && !getPillSearchReq.getShape().isEmpty()) {
            List<String> shapeKeywords = Arrays.asList(getPillSearchReq.getShape().split("\\s+"));
            sql.append(isFirstCondition ? "" : " AND ");
            sql.append("(");
            for (int i = 0; i < shapeKeywords.size(); i++) {
                if (i > 0) {
                    sql.append(" AND ");
                }
                sql.append("p.shape LIKE '%").append(shapeKeywords.get(i)).append("%'");
            }
            sql.append(")");
        }

        return sql.toString();
    }

    private String splitAndFormatMark(String mark) {
        return IntStream.range(0, mark.length() - 1)
            .mapToObj(i -> "+" + mark.substring(i, i + 2))
            .collect(Collectors.joining(" "));
    }

    private void applyPagination(Query query, int pageNumber, int pageSize) {
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
    }

    private long countTotalElements(String sql) {
        String countSql = sql.replaceFirst("SELECT \\*", "SELECT COUNT(*)");
        Query countQuery = entityManager.createNativeQuery(countSql);
        return ((Number) countQuery.getSingleResult()).longValue();
    }

    private PagedResponse<GetPillSearchRes> createPagedResponse(List<GetPillSearchRes> pills,
        int pageNumber,
        int pageSize, long totalElements, int totalPages) {
        PagedResponse<GetPillSearchRes> pagedResponse = new PagedResponse<>();
        pagedResponse.setContent(pills);
        pagedResponse.setPageNumber(pageNumber);
        pagedResponse.setPageSize(pageSize);
        pagedResponse.setTotalElements(totalElements);
        pagedResponse.setTotalPages(totalPages);
        pagedResponse.setLast((pageNumber + 1) == totalPages);

        return pagedResponse;
    }
}