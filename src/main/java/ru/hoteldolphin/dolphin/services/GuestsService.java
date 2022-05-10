package ru.hoteldolphin.dolphin.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.hoteldolphin.dolphin.entities.Guests;
import ru.hoteldolphin.dolphin.repositories.GuestsRepository;

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
    public List<Guests> findGuestsByName(String name) {
        return guestsRepository.findByName(name);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Guests> findGuestsByPhone(String phone) {
        return guestsRepository.findByPhone(phone);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Guests> findGuestsByAmount(Integer amount) {
        return guestsRepository.findByAmount(amount);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Guests> findGuestsByCheckIn(String checkIn) {
        return guestsRepository.findByCheckIn(checkIn);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Guests> findGuestsByCheckOut(String checkout) {
        return guestsRepository.findByCheckOut(checkout);
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
    public void setGuestsAmount(Long id, Integer amount) {
        Guests currGuest = guestsRepository.findById(id).get();
        currGuest.setAmount(amount);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setGuestsCheckIn(Long id, String checkIn) {
        Guests currGuest = guestsRepository.findById(id).get();
        currGuest.setCheckIn(checkIn);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setGuestsCheckOut(Long id, String checkOut) {
        Guests currGuest = guestsRepository.findById(id).get();
        currGuest.setCheckOut(checkOut);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteGuest(Long id) {
        guestsRepository.deleteById(id);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setGuestsWatched(Long id, String watched) {
        Guests currGuest = guestsRepository.findById(id).get();
        currGuest.setWatched(watched);
    }
}