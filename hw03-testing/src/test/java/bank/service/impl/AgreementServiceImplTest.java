package bank.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.bank.dao.AgreementDao;
import ru.otus.bank.entity.Agreement;
import ru.otus.bank.service.impl.AgreementServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AgreementServiceImplTest {

    @Mock
    private AgreementDao dao;
    @InjectMocks
    AgreementServiceImpl agreementServiceImpl;

    Agreement agreement;

    @BeforeEach
    public void init() {
        agreementServiceImpl = new AgreementServiceImpl(dao);
        agreement = new Agreement();
        agreement.setId(1L);
        agreement.setName("Test Agreement");
    }

    @Test
    void testAddAgreement() {
        // Arrange
        Agreement agreement = new Agreement();
        agreement.setId(1L);
        agreement.setName("Test Agreement");

        when(dao.save(any(Agreement.class))).thenReturn(agreement);

        // Act
        Agreement createdAgreement = agreementServiceImpl.addAgreement("Test Agreement");

        // Assert
        assertNotNull(createdAgreement);
        assertEquals("Test Agreement", createdAgreement.getName());
        assertEquals(1L, createdAgreement.getId());
    }

    @Test
    public void testFindByName() {

        when(dao.findByName(agreement.getName())).thenReturn(
                Optional.of(agreement));

        Optional<Agreement> result = agreementServiceImpl.findByName(agreement.getName());

        assertTrue(result.isPresent());
        assertEquals(1, agreement.getId());
    }

    @Test
    public void testFindByNameWithCaptor() {
        String name = "test";
        Agreement agreement = new Agreement();
        agreement.setId(10L);
        agreement.setName(name);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        when(dao.findByName(captor.capture())).thenReturn(
                Optional.of(agreement));

        Optional<Agreement> result = agreementServiceImpl.findByName(name);

        assertEquals("test", captor.getValue());
        assertTrue(result.isPresent());
        assertEquals(10, agreement.getId());
    }
}
