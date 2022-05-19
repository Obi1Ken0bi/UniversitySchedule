package ru.puzikov.universityschedule.persistence.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

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

    @OneToMany(cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @JoinTable(name = "group_day_mapping",
            joinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "day_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "dayOfTheWeek")
    private Map<Integer, Day> schedule;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "group")
    @ToString.Exclude
    private List<User> users;

    public Group(int number) {
        this.number = number;
    }

    public Group(int number, Map<Integer, Day> map) {
        this.number = number;
        this.schedule = map;
    }


}
