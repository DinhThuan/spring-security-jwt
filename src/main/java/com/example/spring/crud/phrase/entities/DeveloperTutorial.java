package com.example.spring.crud.phrase.entities;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "developer_tutorial")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeveloperTutorial {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "published")
    private boolean published;

    @Column(name = "fileName")
    private String fileName;
}
