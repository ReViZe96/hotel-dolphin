package ru.hoteldolphin.dolphin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.hoteldolphin.dolphin.entities.Guests;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestsRepository extends JpaRepository<Guests, Long> {

    @Query("select g from Guests g where g.watched='Y'")
    List<Guests> findWatched();

    @Query("select g from Guests g where g.watched='N'")
    List<Guests> findNotWatched();

    @Query("select g from Guests g where g.name=:name")
    List<Guests> findByName(@Param("name") String name);

    @Query("select g from Guests g where g.phone=:phone")
    List<Guests> findByPhone(@Param("phone") String phone);

    @Query("select g from Guests g where g.amount=:amount")
    List<Guests> findByAmount(@Param("amount") Integer amount);

    @Query("select g from Guests g where g.checkIn=:checkIn")
    List<Guests> findByCheckIn(@Param("checkIn") String checkIn);

    @Query("select g from Guests g where g.checkOut=:checkOut")
    List<Guests> findByCheckOut(@Param("checkOut") String checkOut);

    Optional<Guests> findById(Long id);

    void deleteById(Long id);
}