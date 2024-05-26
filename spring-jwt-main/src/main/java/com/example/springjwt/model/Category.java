package com.example.springjwt.model;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class Category {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "categoryId")
    private int categoryId;
    private String CategoryTitle;
    @OneToMany(mappedBy = "category")
    private List<Lesson> lessons;
}

