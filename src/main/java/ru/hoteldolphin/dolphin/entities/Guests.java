package ru.hoteldolphin.dolphin.entities;

import lombok.*;

import javax.persistence.*;

@Entity(name = "Guests")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Guests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "phone")
    private String phone;
    @Column(name = "check_in")
    private String checkIn;
    @Column(name = "check_out")
    private String checkOut;
    @Column(name = "watched")
    private String watched;

    public Guests(String name, Integer amount, String phone, String checkIn, String checkOut, String watched) {
        this.name = name;
        this.amount = amount;
        this.phone = phone;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.watched = watched;
    }
}