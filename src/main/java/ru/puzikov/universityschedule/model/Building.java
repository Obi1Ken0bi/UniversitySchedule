package ru.puzikov.universityschedule.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "building_gen")
    @SequenceGenerator(name = "building_gen", sequenceName = "building_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private char letter;


    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }


    public Building(char letter) {
        this.letter = letter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
