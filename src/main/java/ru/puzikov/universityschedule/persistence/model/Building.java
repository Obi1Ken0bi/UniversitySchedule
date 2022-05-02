package ru.puzikov.universityschedule.persistence.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "building_gen")
    @SequenceGenerator(name = "building_gen", sequenceName = "building_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private char letter;


    public Building(char letter) {
        this.letter = letter;
    }


}
