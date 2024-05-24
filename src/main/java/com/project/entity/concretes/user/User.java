package com.project.entity.concretes.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.entity.concretes.business.LessonProgram;
import com.project.entity.concretes.business.Meet;
import com.project.entity.concretes.business.StudentInfo;
import com.project.entity.enums.Gender;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "t_user")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String ssn;

    private String name;

    private String surname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDay;

    private String birthPlace;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private Boolean built_in; //dokunulamayan admin

    private String motherName; //öğrenci için belirleyici özellik

    private String fatherName;//öğrenci için belirleyici özellik

    private int studentNumber;//öğrenci için belirleyici özellik

    private boolean isActive;

    private Boolean isAdvisor;//bir öğretmenin rehber old. belirler.

    private Long advisorTeacherId; // bu Ogrenciler icin lazim, kendi rehber ogretmeninin
    // id si buraya yazilacak

    @Enumerated(EnumType.STRING)//enums package içerisiinde Gender adında enum class açılmış. cinsiyeti sabitlemiş. enum clasla
    private Gender gender; // MALE , male , Male , MalE

    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserRole userRole;

    //student info ile bağlantısı
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.REMOVE)//teacher değişkeni nerede ise setleme orada oluşsun dedik.
    private List<StudentInfo> studentInfos;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_lessonprogram",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_program_id")
    )
    private Set<LessonProgram> lessonsProgramList;


    //not:LessonProgram-Meet ilişkisi var.
//cascade type: student silinirse infolarıda gitsin

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name ="meet_student_table",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "meet_id")
    )
    private List<Meet> meetList;


}
/*
sn tc kimlik numarası evrensel olarak.
LocalDate sadece tarih alır. localdate leri kullanıyorsan json formatı kullanmalısınız.

jsonProperty: izinleri yapılır.

user kayıt ederlen; uniq olanları kontrol edilmelidir.
user yeni bir tel girdi bununda kontrolü yapılmalıdır. service katmanında.
email uniq olmalıdır.
*bir kullanıcıyı diğer kullanıcan ayıran özellikleri: id, username, ssn, phoneNuumber, email:
uniqleri tru olanlar kullanıcıları yaırıcı kılan özelliklerdir.

entityde validasyon yapmaya gerek yok. dto kısmında yapılır.

**built_in: farzedinki üç admin var sistemde. adminler sistemdeki bütün işlemleri yapar.
herhangi bir kullanıcıyı siler. 1. admin 3. admini ve 2. admini sildi ve kendisinide silmek isterse onuda siler
sistemde admin yoksa servere kapat ve yeniden ayağa kaldır.
bu sıkıntı olmasın diye bazı adminlere buil in özelliği verilir.
build in true ise bu admine dokunamazsınız.

bütün deleteuser, updateuser özelliklerine bakılır. build in özelliği true olanlar silinemez

araç kiralama sisteminde: test aracı belirlersiniz bunu true yapılır buildir özelliği ve
bunu kimse silemez.

bizim userlar student, admin, teacher da olabilir.
user öğrenci ise student number özelliği olmalıdır.
user teacher ise bununda vioser özelliği olmalıdır.

build in özelliği ortak olanlar yazılılır.
mothername ve fathername sadece studentlar içindir.
student numberda öğrenci içindir.

değişkenleri yazarken tip türlerine dikkat edilmelidir. Primitive ve none primitive

öğretmenler için private Boolean isAdvisor, bir öğretmenin rehber old. belirler.

studentlar için sadece yazıldı.   private Long advisorTeacherId;  bir öğrencini danışmanı bulmak için

cinsiyette enum clası yapıldı. gender adında

her kullanıcının bir rolü olmalıdır. onetoone
jsonProperty


ödev:
 */
