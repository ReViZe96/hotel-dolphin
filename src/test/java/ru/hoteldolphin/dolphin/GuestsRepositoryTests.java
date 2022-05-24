package ru.hoteldolphin.dolphin;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import ru.hoteldolphin.dolphin.entities.Guests;
import ru.hoteldolphin.dolphin.repositories.GuestsRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@ContextConfiguration(classes = DolphinApplication.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Getter
@Setter
public class GuestsRepositoryTests {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private GuestsRepository guestsRepository;
    private Guests firstGuest;
    private Guests secondGuest;
    private Guests thirdGuest;
    private Guests fourthGuest;
    private List<Guests> expectedFirst;

    private List<Guests> expectedSec;

    @BeforeEach
    public void initilaizeTestDatas() {
        expectedFirst = new ArrayList<>();
        expectedSec = new ArrayList<>();
        firstGuest = new Guests("First", "88888888888", 2, 1, Timestamp.valueOf("2001-01-01 12:00:00"),
                Timestamp.valueOf("2002-02-02 12:00:00"), 4, "Some information", 'N', 'N');
        testEntityManager.persist(firstGuest);
        expectedFirst.add(firstGuest);
        secondGuest = new Guests("First", "88888888888", 2, 1, Timestamp.valueOf("2001-01-01 12:00:00"),
                Timestamp.valueOf("2002-02-02 12:00:00"), 4, "Some information", 'N', 'N');
        testEntityManager.persist(secondGuest);
        expectedFirst.add(secondGuest);
        thirdGuest = new Guests("Second", "89999999999", 6, 2, Timestamp.valueOf("2022-11-11 12:00:00"),
                Timestamp.valueOf("2022-12-12 12:00:00"), 8, "Some information", 'Y', 'Y');
        testEntityManager.persist(thirdGuest);
        expectedSec.add(thirdGuest);
        fourthGuest = new Guests("Second", "89999999999", 6, 2, Timestamp.valueOf("2022-11-11 12:00:00"),
                Timestamp.valueOf("2022-12-12 12:00:00"), 8, "Some information", 'Y', 'Y');
        testEntityManager.persist(fourthGuest);
        expectedSec.add(fourthGuest);
    }

    @Test
    public void findNotWatchedTest() {
        List<Guests> actual = guestsRepository.findNotWatched();
        Assertions.assertEquals(expectedFirst, actual);
    }

    @Test
    public void findWatchedTest() {
        List<Guests> actual = guestsRepository.findWatched();
        Assertions.assertEquals(expectedSec, actual);
    }

    @Test
    public void findBlockedGuestsTest() {
        List<Guests> actual = guestsRepository.findBlockedGuests();
        Assertions.assertEquals(expectedSec, actual);
    }

    @Test
    public void findByNameTest() {
        List<Guests> actual = guestsRepository.findByName("First");
        Assertions.assertEquals(expectedFirst, actual);
    }

    @Test
    public void findByPhoneTest() {
        List<Guests> actual = guestsRepository.findByPhone("88888888888");
        Assertions.assertEquals(expectedFirst, actual);
    }

    @Test
    public void findByNameAndPhoneTest() {
        List<Guests> actual = guestsRepository.findByNameAndPhone("First", "88888888888");
        Assertions.assertEquals(expectedFirst, actual);
    }

    @Test
    public void findByPeoplesAmountTest() {
        List<Guests> actual = guestsRepository.findByPeoplesAmount(6);
        Assertions.assertEquals(expectedSec, actual);
    }

    @Test
    public void findByRoomsAmountTest() {
        List<Guests> actual = guestsRepository.findByRoomsAmount(2);
        Assertions.assertEquals(expectedSec, actual);
    }

    @Test
    public void findByNightsAmountTest() {
        List<Guests> actual = guestsRepository.findByNightsAmount(4);
        Assertions.assertEquals(expectedFirst, actual);
    }

    @Test
    public void findCheckInHereGuestsTest() {
        List<Guests> actual = guestsRepository.findCheckInHereGuests(Timestamp.valueOf("2001-01-01 12:00:00"),
                Timestamp.valueOf("2001-01-31 14:00:00"));
        Assertions.assertEquals(expectedFirst, actual);
    }

    @Test
    public void findCheckInCheckOutOutsideGuestsTest() {
        List<Guests> actual = guestsRepository.findCheckInCheckOutOutsideGuests(Timestamp.valueOf("2000-12-30 12:00:00"),
                Timestamp.valueOf("2002-02-10 12:00:00"));
        Assertions.assertEquals(expectedFirst, actual);
    }

    @Test
    public void findCheckOutHereGuestsTest() {
        List<Guests> actual = guestsRepository.findCheckOutHereGuests(Timestamp.valueOf("2022-12-10 14:00:00"),
                Timestamp.valueOf("2022-12-12 12:00:00"));
        Assertions.assertEquals(expectedSec, actual);
    }

    @Test
    public void findByIdTest() {
        List<Guests> all = guestsRepository.findAll();
        Guests currGuest = all.get(1);
        long id = currGuest.getId();
        Guests actual = guestsRepository.findById(id).get();
        Assertions.assertEquals(currGuest, actual);
    }

    @Test
    public void deleteByIdTest() {
        List<Guests> all = guestsRepository.findAll();
        Guests currGuest = all.get(1);
        long id = currGuest.getId();
        guestsRepository.deleteById(id);
        all.remove(1);
        List<Guests> actual = guestsRepository.findAll();
        Assertions.assertEquals(all, actual);
    }
}