package com.project.entity.concretes.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.entity.concretes.user.User;
import com.project.entity.enums.Note;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class StudentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer absente;// yoklama bilsidir.

    private Double midtermExam; //1. sınav

    private Double finalExam; //2. sınav


    private Double examAverage; //2 notun ortalamasını alan değişkendir. zorunlu değil
    //ama her yerde tanımlamadan burada yapılmış.

    private  String infoNot;//öğrenci ile ilgili not tutulur
    //AA, BB, DD, FF Gibi karşılığa gelecek bir değişken. bunu enum yapılacak. kimisi Aa, AA gibi değerler girer bunu enum yapılması gerekir.
//kimisi ax de girebilir. bu nedenle tercih hakkı sunulur enumla oradan seçim sağlanır. enum Note oluştur

@Enumerated(EnumType.STRING)
    private Note letterGrade;

  // burada ki user studentt ta olabilir teacherda olabilir. burada iki tane değişken tanımlıyoruz.
//2 many to one yapıyoruz. user.teacher    userstudent
 // burada ki user studentt ta olabilir teacherda olabilir. burada iki tane değişken tanımlıyoruz.

   @JsonIgnore//infitine döngüyü kırmak için yazdık.
    @ManyToOne  //1 öğretmen 100 öğrenci
    private User teacher;

   @JsonIgnore
    @ManyToOne //1 öğrenci aldığı ders sayısı kadar studentınfo su vardır.
    private  User student;

    @ManyToOne
    private Lesson lesson;

    @OneToOne
    private EducationTerm educationTerm;

}
