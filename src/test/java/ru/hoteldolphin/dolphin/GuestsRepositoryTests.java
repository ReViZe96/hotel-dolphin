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
        firstGuest = new Guests("First", 2, "88888888888", "01.01.01", "02.02.02", "N");
        testEntityManager.persist(firstGuest);
        expectedFirst.add(firstGuest);
        secondGuest = new Guests("First", 2, "88888888888", "01.01.01", "02.02.02", "N");
        testEntityManager.persist(secondGuest);
        expectedFirst.add(secondGuest);
        thirdGuest = new Guests("Second", 6, "89999999999", "11.11.2022", "12.12.2022", "Y");
        testEntityManager.persist(thirdGuest);
        expectedSec.add(thirdGuest);
        fourthGuest = new Guests("Second", 6, "89999999999", "11.11.2022", "12.12.2022", "Y");
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
    public void findByAmountTest() {
        List<Guests> actual = guestsRepository.findByAmount(6);
        Assertions.assertEquals(expectedSec, actual);
    }

    @Test
    public void findByCheckInTest() {
        List<Guests> actual = guestsRepository.findByCheckIn("01.01.01");
        Assertions.assertEquals(expectedFirst, actual);
    }

    @Test
    public void findByCheckOutTest() {
        List<Guests> actual = guestsRepository.findByCheckOut("12.12.2022");
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