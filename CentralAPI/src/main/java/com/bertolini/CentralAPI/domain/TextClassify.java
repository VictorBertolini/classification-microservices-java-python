package com.bertolini.CentralAPI.domain;

import com.bertolini.CentralAPI.schema.text.TextClassifiedResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "text_classify_tb")
public class TextClassify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User requester;

    @Column(name = "text")
    private String text;

    @Column(name = "label")
    private String label;

    @Column(name = "score")
    private Double score;

    public TextClassify(User user, TextClassifiedResponse response) {
        this.requester = user;
        this.text = response.text();
        this.label = response.label();
        this.score = response.score();
    }

}
