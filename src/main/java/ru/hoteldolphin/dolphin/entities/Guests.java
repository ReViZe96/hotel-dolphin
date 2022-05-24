package ru.hoteldolphin.dolphin.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    @Column(name = "phone")
    private String phone;
    @Column(name = "peoples_amount")
    private Integer amountOfPeoples;
    @Column(name = "rooms_amount")
    private Integer amountOfRooms;
    @Column(name = "check_in")
    private Timestamp checkIn;
    @Column(name = "check_out")
    private Timestamp checkOut;
    @Column(name = "nights_amount")
    private Integer amountOfNights;
    @Column(name = "info")
    private String info;
    @Column(name = "booked")
    private Character booked;
    @Column(name = "black_list")
    private Character blackList;

    public Guests(String name, String phone, Integer amountOfPeoples, Integer amountOfRooms, Timestamp checkIn,
                  Timestamp checkOut, Integer amountOfNight, String info, Character booked, Character blackList) {
        this.name = name;
        this.phone = phone;
        this.amountOfPeoples = amountOfPeoples;
        this.amountOfRooms = amountOfRooms;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.amountOfNights = amountOfNight;
        this.info = info;
        this.booked = booked;
        this.blackList = blackList;
    }

    public static List<Guests> sortByIn(List<Guests> sortedGuests) {
        Guests[] guestsArr = new Guests[sortedGuests.size()];
        sortedGuests.toArray(guestsArr);
        for (int left = 1; left < guestsArr.length; left++) {
            Guests value = guestsArr[left];
            int i = left - 1;
            for (; i >= 0; i--) {
                if (value.getCheckIn().after(guestsArr[i].getCheckIn())) {
                    guestsArr[i + 1] = guestsArr[i];
                } else {
                    break;
                }
            }
            guestsArr[i + 1] = value;

        }
        return Arrays.stream(guestsArr).collect(Collectors.toList());
    }

    public static List<Guests> sortByOut(List<Guests> sortedGuests) {
        Guests[] guestsArr = new Guests[sortedGuests.size()];
        sortedGuests.toArray(guestsArr);
        for (int left = 1; left < guestsArr.length; left++) {
            Guests value = guestsArr[left];
            int i = left - 1;
            for (; i >= 0; i--) {
                if (value.getCheckOut().after(guestsArr[i].getCheckOut())) {
                    guestsArr[i + 1] = guestsArr[i];
                } else {
                    break;
                }
            }
            guestsArr[i + 1] = value;

        }
        return Arrays.stream(guestsArr).collect(Collectors.toList());
    }

    public static List<Guests> sortByName(List<Guests> sortedGuests) {
        Guests[] guestsArr = new Guests[sortedGuests.size()];
        sortedGuests.toArray(guestsArr);
        for (int left = 1; left < guestsArr.length; left++) {
            Guests value = guestsArr[left];
            int i = left - 1;
            for (; i >= 0; i--) {
                if (value.getName().compareTo(guestsArr[i].getName()) < 0) {
                    guestsArr[i + 1] = guestsArr[i];
                } else {
                    break;
                }
            }
            guestsArr[i + 1] = value;

        }
        return Arrays.stream(guestsArr).collect(Collectors.toList());
    }

    public static List<Guests> sortByPeoples(List<Guests> sortedGuests) {
        Guests[] guestsArr = new Guests[sortedGuests.size()];
        sortedGuests.toArray(guestsArr);
        for (int left = 1; left < guestsArr.length; left++) {
            Guests value = guestsArr[left];
            int i = left - 1;
            for (; i >= 0; i--) {
                if (value.getAmountOfPeoples() > guestsArr[i].getAmountOfPeoples()) {
                    guestsArr[i + 1] = guestsArr[i];
                } else {
                    break;
                }
            }
            guestsArr[i + 1] = value;

        }
        return Arrays.stream(guestsArr).collect(Collectors.toList());
    }

    public static List<Guests> sortByRooms(List<Guests> sortedGuests) {
        Guests[] guestsArr = new Guests[sortedGuests.size()];
        sortedGuests.toArray(guestsArr);
        for (int left = 1; left < guestsArr.length; left++) {
            Guests value = guestsArr[left];
            int i = left - 1;
            for (; i >= 0; i--) {
                if (value.getAmountOfRooms() > guestsArr[i].getAmountOfRooms()) {
                    guestsArr[i + 1] = guestsArr[i];
                } else {
                    break;
                }
            }
            guestsArr[i + 1] = value;

        }
        return Arrays.stream(guestsArr).collect(Collectors.toList());
    }

    public static List<Guests> sortByNights(List<Guests> sortedGuests) {
        Guests[] guestsArr = new Guests[sortedGuests.size()];
        sortedGuests.toArray(guestsArr);
        for (int left = 1; left < guestsArr.length; left++) {
            Guests value = guestsArr[left];
            int i = left - 1;
            for (; i >= 0; i--) {
                if (value.getAmountOfNights() > guestsArr[i].getAmountOfNights()) {
                    guestsArr[i + 1] = guestsArr[i];
                } else {
                    break;
                }
            }
            guestsArr[i + 1] = value;

        }
        return Arrays.stream(guestsArr).collect(Collectors.toList());
    }
}