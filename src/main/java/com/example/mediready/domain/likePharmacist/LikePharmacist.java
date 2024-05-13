package com.example.mediready.domain.likePharmacist;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class LikePharmacist {

    @EmbeddedId
    private LikePharmacistId id;

    @NotNull
    private Boolean status;
}
