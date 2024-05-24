package com.project.entity.concretes.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long lessonId;

    private  Integer creditScore;

    private  Boolean isCompulsory;//zorunlu dersmi buda kullaımayacak


    @JsonIgnore
    @ManyToMany(mappedBy = "lessons", cascade = CascadeType.REMOVE)
    private Set<LessonProgram> lessonPrograms;

    //not:LessonProgram


     //credi bu değişkende projede kullanılmayacak. proje sonunda    farklı action yapılabilir
}
