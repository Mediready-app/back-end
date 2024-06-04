package com.example.mediready.domain.folder;

import com.example.mediready.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer priority;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void createInitialFolder(User user) {
        this.name = "기본";
        this.user = user;
        this.priority = 0;
    }

    public Folder(String name, int priority, User user) {
        this.name = name;
        this.priority = priority+1;
        this.user = user;
    }
}
