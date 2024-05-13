package com.example.mediready.domain.dur;

import com.example.mediready.domain.medicine.Medicine;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class DurId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id1", nullable = false)
    private Medicine id1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id2", nullable = false)
    private Medicine id2;
}
