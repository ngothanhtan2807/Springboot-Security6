package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends TimeAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;
    public static void main(String[] args) {
//        IntStream.iterate(2, x -> x < 33, x -> 2 * x)
//                .forEach(System.out::println);

        Stream.ofNullable(1).forEach(System.out::println);
    }
}
