package ru.puzikov.universityschedule.persistence.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_gen")
    @SequenceGenerator(name = "group_gen", sequenceName = "group_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    private int number;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Group(int number) {
        this.number = number;
    }

    public Group(int number, Schedule schedule) {
        this.number = number;
        this.schedule = schedule;
    }
}