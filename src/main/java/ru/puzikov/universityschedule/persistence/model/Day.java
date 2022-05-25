package ru.puzikov.universityschedule.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.puzikov.universityschedule.dto.PairDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Day {
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinTable(name = "day_pairs")
    List<Pair> pairs;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "day_gen")
    @SequenceGenerator(name = "day_gen", sequenceName = "day_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private int dayOfWeek;
    @JsonIgnore
    public List<PairDto> getAllPairsWeekDependent(){
        List<PairDto> pairDtos=new ArrayList<>();
        for (Pair pair : pairs) {
            PairDto pairDto=new PairDto(pair.getLesson(),pair.getTime());
            pairDtos.add(pairDto);
        }
        return pairDtos;
    }


    public Day(int dayOfWeek, List<Pair> pairs) {
        this.dayOfWeek = dayOfWeek;
        this.pairs = pairs;
    }


}
