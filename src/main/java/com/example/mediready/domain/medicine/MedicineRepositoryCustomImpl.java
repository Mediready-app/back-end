package com.example.mediready.domain.medicine;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class MedicineRepositoryCustomImpl implements MedicineRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Transactional(readOnly = true)
    public List<Medicine> findMedicineByNameContaining(String keyword) {
        QMedicine medicine = QMedicine.medicine;

        String[] keywords = keyword.split("\\s+");
        List<BooleanExpression> predicates = new ArrayList<>();
        for (String k : keywords) {
            predicates.add(medicine.name.like(escapeKeyword(k), '\\'));
        }

        BooleanExpression combinedPredicate = predicates.stream()
            .reduce(BooleanExpression::and)
            .orElse(null);

        return queryFactory.selectFrom(medicine)
            .where(combinedPredicate)
            .fetch();
    }

    private String escapeKeyword(String keyword) {
        return "%" + keyword
            .replace("\\", "\\\\")
            .replace("%", "\\%")
            .replace("_", "\\_")
            .replace("[", "\\[")
            .replace("]", "\\]")
            .replace("(", "\\(")
            .replace(")", "\\)")
            .replace(":", "\\:")
            .replace("-", "\\-")
            + "%";
    }
}