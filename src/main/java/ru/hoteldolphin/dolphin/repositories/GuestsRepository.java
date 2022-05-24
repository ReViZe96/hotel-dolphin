package ru.hoteldolphin.dolphin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.hoteldolphin.dolphin.entities.Guests;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface GuestsRepository extends JpaRepository<Guests, Long> {

    @Query("select g from Guests g where g.booked='Y'")
    List<Guests> findWatched();

    @Query("select g from Guests g where g.booked='N' and g.blackList='N'")
    List<Guests> findNotWatched();

    @Query("select g from Guests g where g.blackList='Y'")
    List<Guests> findBlockedGuests();

    @Query("select g from Guests g where g.name=:name")
    List<Guests> findByName(@Param("name") String name);

    @Query("select g from Guests g where g.phone=:phone")
    List<Guests> findByPhone(@Param("phone") String phone);

    @Query("select g from Guests g where g.name=:name and g.phone=:phone")
    List<Guests> findByNameAndPhone(@Param("name") String name, @Param("phone") String phone);

    @Query("select g from Guests g where g.amountOfPeoples=:amountOfPeoples")
    List<Guests> findByPeoplesAmount(@Param("amountOfPeoples") Integer amountOfPeoples);

    @Query ("select g from Guests g where g.amountOfRooms =:amountOfRooms")
    List<Guests> findByRoomsAmount (@Param("amountOfRooms") Integer amountOfRooms);

    @Query("select g from Guests g where g.amountOfNights =:amountOfNights")
    List<Guests> findByNightsAmount (@Param("amountOfNights") Integer amountOfNights);

    //JPQL не поддерживает UNION, поэтому пока - так
    @Query("select g from Guests g where g.checkIn between :checkIn and :checkOut")
    List<Guests> findCheckInHereGuests(@Param("checkIn") Timestamp checkIn, @Param("checkOut") Timestamp checkOut);

    @Query("select g from Guests g where g.checkIn>:checkIn and g.checkOut<:checkOut")
    List<Guests> findCheckInCheckOutOutsideGuests(@Param("checkIn") Timestamp checkIn, @Param("checkOut") Timestamp checkOut);

    @Query("select g from Guests g where g.checkOut between :checkIn and :checkOut")
    List<Guests> findCheckOutHereGuests(@Param("checkIn") Timestamp checkIn, @Param("checkOut") Timestamp checkOut);

    Optional<Guests> findById(Long id);

    void deleteById(Long id);
}