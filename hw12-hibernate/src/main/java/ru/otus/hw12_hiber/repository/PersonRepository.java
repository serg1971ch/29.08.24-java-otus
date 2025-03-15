package ru.otus.hw12_hiber.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.hw12_hiber.model.Person;
import ru.otus.hw12_hiber.model.Product;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT p FROM Person p JOIN p.products pr WHERE pr.id = :productId")
    List<Person> findPersonsByProductId(@Param("productId") Long productId);

//    @Modifying
//    @Transactional
//    @Query("UPDATE Person c SET c.products = CONCAT(c.products, :product) WHERE c.id = :personId")
//    void addProductToPerson(@Param("product") Product product, @Param("personId") Long personId);
}
