package com.example.test4.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String isbn;
    private String title;
    private String author;
    @FutureOrPresent(message = "Ngày xuất bản phải là ngày hiện tại hoặc tương lai")
    @NotNull(message = "Cần Chọn ngày xuất bản")
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;


}
