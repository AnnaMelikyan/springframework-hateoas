package com.example.springframeworkhateoas.model;

import lombok.*;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User extends ResourceSupport{


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_id_seq", allocationSize = 1)
    @Column
    private Long userId;
    @Column
    private String name;
    @Column
    private Double salary;
}
