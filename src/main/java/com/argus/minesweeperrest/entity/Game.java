package com.argus.minesweeperrest.entity;

import com.argus.minesweeperrest.model.Cell;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;


@Entity
@Table(name = "game")
@Data //mb change because mapper may not work
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID gameID;
    @Column
    private Integer width;
    @Column
    private Integer height;
    @Column
    private Integer minesCount;
    @Column
    private Boolean completed;
    @Column
    //в базу класть как джейсон
    private Cell[][] field;
}