package com.joaoeduardoam.multipledatasources.post.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "post")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String text;
}