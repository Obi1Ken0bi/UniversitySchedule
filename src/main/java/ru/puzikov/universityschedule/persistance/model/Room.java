package ru.puzikov.universityschedule.persistance.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_gen")
    @SequenceGenerator(name = "room_gen", sequenceName = "room_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    private String number;


    public Room(Building building, String number) {
        this.building = building;
        this.number = number;
    }
}
