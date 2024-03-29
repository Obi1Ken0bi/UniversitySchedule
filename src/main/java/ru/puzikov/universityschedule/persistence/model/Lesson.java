package ru.puzikov.universityschedule.persistence.model;

import lombok.*;

import javax.persistence.*;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_gen")
    @SequenceGenerator(name = "lesson_gen", sequenceName = "lesson_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "room_id")
    private Room room;

    private LessonType type;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "subject_id")
    private Subject subject;


    public Lesson(Room room, LessonType type, Teacher teacher, Subject subject) {
        this.room = room;
        this.type = type;
        this.teacher = teacher;
        this.subject = subject;
    }

}
