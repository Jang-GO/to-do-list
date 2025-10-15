package janggo.practice.todolist.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo extends TimeStamp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    private boolean completed = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void toggleCompletion(){
        this.completed = !this.completed;
    }

    @Builder
    public Todo(String content, User user){
        this.content = content;
        this.user = user;
    }
}
