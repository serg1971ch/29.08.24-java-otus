package ru.otus.hw12_hiber.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.otus.hw12_hiber.model.Person;
import ru.otus.hw12_hiber.model.Product;
import ru.otus.hw12_hiber.repository.PersonRepository;
import ru.otus.hw12_hiber.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ShopService {
    private final PersonRepository personRepository;
    private final ProductRepository productRepository;

    public ShopService(PersonRepository personRepository, ProductRepository productRepository) {
        this.personRepository = personRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Product findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    @Transactional
    public void addProductToPerson(Long productId, Long personId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        // Assuming person has a method getProducts() that returns a List<Product>
        person.getProducts().add(product); // Add the product to the person's product list
        personRepository.save(person); // Save the updated person entity
    }

    public Set<Product> getProductsByPersonId(Long personId) {
        return personRepository.findById(personId)
                .map(Person::getProducts)
                .orElseThrow(() -> new RuntimeException("Person not found"));
    }

    public List<Person> getPersonsByProductId(Long productId) {
        return personRepository.findPersonsByProductId(productId);
    }

    @Transactional
    public void deletePerson(Long personId) {
        personRepository.deleteById(personId);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
