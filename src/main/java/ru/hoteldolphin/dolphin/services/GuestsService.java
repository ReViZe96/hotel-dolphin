package ru.hoteldolphin.dolphin.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.hoteldolphin.dolphin.entities.Guests;
import ru.hoteldolphin.dolphin.repositories.GuestsRepository;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@Setter
public class GuestsService {
    @Autowired
    private GuestsRepository guestsRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void save(Guests guests) {
        guestsRepository.save(guests);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Guests> findModedGuests() {
        return guestsRepository.findWatched();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Guests> findNotModedGuests() {
        return guestsRepository.findNotWatched();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Guests> findBlockedGuests() {
        return guestsRepository.findBlockedGuests();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Guests> findGuestsByName(String name) {
        return guestsRepository.findByName(name);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Guests> findGuestsByPhone(String phone) {
        return guestsRepository.findByPhone(phone);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Guests> findByNameAndPhone(String name, String phone) {
        return guestsRepository.findByNameAndPhone(name, phone);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Guests> findGuestsByPeoplesAmount(Integer amountOfPeople) {
        return guestsRepository.findByPeoplesAmount(amountOfPeople);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Guests> findGuestsByRoomsAmount(Integer amountOfRooms) {
        return guestsRepository.findByRoomsAmount(amountOfRooms);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Guests> findGuestsByNightsAmount(Integer amountOfNight) {
        return guestsRepository.findByNightsAmount(amountOfNight);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Guests> findAllInTimeInterval(String checkIn, String checkOut) {
        String in = checkIn + " 00:00:00";
        String out = checkOut + " 23:59:59";
        List<Guests> chInInside = guestsRepository.findCheckInHereGuests(Timestamp.valueOf(in), Timestamp.valueOf(out));
        List<Guests> checkOutside = guestsRepository.findCheckInCheckOutOutsideGuests(Timestamp.valueOf(in), Timestamp.valueOf(out));
        List<Guests> chOutInside = guestsRepository.findCheckOutHereGuests(Timestamp.valueOf(in), Timestamp.valueOf(out));
        for (int i = 0; i < checkOutside.size(); i++) {
            if (!(chInInside.contains(checkOutside.get(i)))) {
                chInInside.add(checkOutside.get(i));
            }
        }
        for (int i = 0; i < chOutInside.size(); i++) {
            if (!(chInInside.contains(chOutInside.get(i)))) {
                chInInside.add(chOutInside.get(i));
            }
        }
        return chInInside;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setGuestsName(Long id, String name) {
        Guests currGuest = guestsRepository.findById(id).get();
        currGuest.setName(name);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setGuestsPhone(Long id, String phone) {
        Guests currGuest = guestsRepository.findById(id).get();
        currGuest.setPhone(phone);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setGuestsAmountOfPeoples(Long id, Integer amountOfPeoples) {
        Guests currGuest = guestsRepository.findById(id).get();
        currGuest.setAmountOfPeoples(amountOfPeoples);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setGuestsAmountOfRooms(Long id, Integer amountOfRooms) {
        Guests currGuest = guestsRepository.findById(id).get();
        currGuest.setAmountOfRooms(amountOfRooms);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setGuestsCheckIn(Long id, String checkIn) {
        Guests currGuest = guestsRepository.findById(id).get();
        String in = checkIn.replace('T', ' ') + ":00";
        currGuest.setCheckIn(Timestamp.valueOf(in));
        currGuest.setAmountOfNights(calculateAmountOfNight(Timestamp.valueOf(in), currGuest.getCheckOut()));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setGuestsCheckOut(Long id, String checkOut) {
        Guests currGuest = guestsRepository.findById(id).get();
        String out = checkOut.replace('T', ' ') + ":00";
        currGuest.setCheckOut(Timestamp.valueOf(out));
        currGuest.setAmountOfNights(calculateAmountOfNight(currGuest.getCheckIn(), Timestamp.valueOf(out)));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setGuestsAmountOfNights(Long id, Integer amountOfNights) {
        Guests currGuest = guestsRepository.findById(id).get();
        currGuest.setAmountOfNights(amountOfNights);
        long newMillsInterval = amountOfNights * 86400000;
        long inMills = currGuest.getCheckIn().getTime();
        Timestamp out = new Timestamp(inMills + newMillsInterval);
        currGuest.setCheckOut(out);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteGuest(Long id) {
        guestsRepository.deleteById(id);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setGuestsBooked(Long id, Character booked) {
        Guests currGuest = guestsRepository.findById(id).get();
        currGuest.setBooked(booked);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setGuestsBlock(Long id, Character blocked) {
        Guests currGuest = guestsRepository.findById(id).get();
        currGuest.setBlackList(blocked);
    }

    public Integer calculateAmountOfNight(Timestamp in, Timestamp out) {
        Long inTime = in.getTime();
        Long outTime = out.getTime();
        int amountOfNights = 1;
        long intervalInMills = outTime - inTime;
        if (intervalInMills > 86400000) {
            int amount = (int) (intervalInMills / 86400000);
            amountOfNights += amount;
        }
        return amountOfNights;
    }

    public List<String> convertEntitiesToStrings(List<Guests> guests) {
        List<String> currGuestsLines = new ArrayList<>(guests.size() + 1);
        currGuestsLines.add("ФИО,Номер телефона,Количество гостей,Количество комнат,Дата и время заселения,Дата и время выселения," +
                "Количество ночей,Прочие пожелания,В черном списке?");
        String currInfo;
        for (int i = 0; i < guests.size(); i++) {
            currInfo = guests.get(i).getName() + "," + guests.get(i).getPhone() + "," + guests.get(i).getAmountOfPeoples()
                    + "," + guests.get(i).getAmountOfRooms() + "," + guests.get(i).getCheckIn() + "," + guests.get(i).getCheckOut()
                    + "," + guests.get(i).getAmountOfNights() + "," + guests.get(i).getInfo() + "," + guests.get(i).getBlackList();
            currGuestsLines.add(currInfo);
        }
        return currGuestsLines;
    }

    public String writeToFile(List<String> allGuests) {
        String status = null;
        String fileSeparator = System.getProperty("file.separator");
        String absoluteFilePath = fileSeparator + "home" + fileSeparator + "revize" + fileSeparator + "historyOrBL.csv"; //разобраться, как конструировать универсальный абсолютный путь
        File history = new File(absoluteFilePath);
        try (PrintStream ps = new PrintStream(history)) {
            if (history.exists()) {
                for (int i = 0; i < allGuests.size(); i++) {
                    ps.println(allGuests.get(i));
                }
                status = "Данные успешно сохранены на вашем устройстве в файле 'historyOrBL' по адресу ... Не забудьте переместить файл из этой директории," +
                        "чтобы он не перезаписался в будущем";
            }
        } catch (IOException e) {
            status = "Произошла ошибка. Невозможно сохранить историю бронирований на устройство";
        }
        return status;
    }
}