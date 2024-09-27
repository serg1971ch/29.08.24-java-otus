package bank.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.bank.entity.Account;
import ru.otus.bank.entity.Agreement;
import ru.otus.bank.service.AccountService;
import ru.otus.bank.service.impl.PaymentProcessorImpl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentProcessorImplTest {

    @Mock
    AccountService accountService;

    @InjectMocks
    PaymentProcessorImpl paymentProcessor;

    Agreement sourceAgreement;
    Agreement destinationAgreement;
    Account sourceAccount;
    Account destinationAccount;

    @BeforeEach
    public void init() {
        sourceAgreement = new Agreement();
        sourceAgreement.setId(1L);

        destinationAgreement = new Agreement();
        destinationAgreement.setId(2L);

        sourceAccount = new Account();
        sourceAccount.setId(1L);
        sourceAccount.setAgreementId(1L);
        sourceAccount.setAmount(BigDecimal.TEN);
        sourceAccount.setType(0);

        destinationAccount = new Account();
        destinationAccount.setId(2L);
        destinationAccount.setAgreementId(2L);
        destinationAccount.setAmount(BigDecimal.ZERO);
        destinationAccount.setType(0);
    }

    @Test
    public void testTransfer() {


        when(accountService.getAccounts(argThat(new ArgumentMatcher<Agreement>() {
            @Override
            public boolean matches(Agreement argument) {
                return argument != null && argument.getId() == 1L;
            }
        }))).thenReturn(List.of(sourceAccount));

        when(accountService.getAccounts(argThat(new ArgumentMatcher<Agreement>() {
            @Override
            public boolean matches(Agreement argument) {
                return argument != null && argument.getId() == 2L;
            }
        }))).thenReturn(List.of(destinationAccount));

        paymentProcessor.makeTransfer(sourceAgreement, destinationAgreement,
                0, 0, BigDecimal.ONE);

    }

    @Test
    void makeTransferWithComissionTest() {
        // Arrange
        Agreement sourceAgreement = new Agreement();
        sourceAgreement.setId(1L);
        sourceAgreement.setName("Source Agreement");

        Agreement destinationAgreement = new Agreement();
        destinationAgreement.setId(2L);
        destinationAgreement.setName("Destination Agreement");

        Account sourceAccount = new Account();
        sourceAccount.setId(1L); // Ensure this is correctly set
        sourceAccount.setType(1);
        sourceAccount.setAmount(BigDecimal.valueOf(500));

        Account destinationAccount = new Account();
        destinationAccount.setId(2L); // Ensure this is correctly set
        destinationAccount.setType(2);
        destinationAccount.setAmount(BigDecimal.valueOf(100));

        // Mock the account service responses
        when(accountService.getAccounts(sourceAgreement)).thenReturn(Arrays.asList(sourceAccount));
        when(accountService.getAccounts(destinationAgreement)).thenReturn(Arrays.asList(destinationAccount));

        // Assume transfer returns true
        when(accountService.makeTransfer(anyLong(), anyLong(), any(BigDecimal.class))).thenReturn(true);
        when(accountService.charge(anyLong(), any(BigDecimal.class))).thenReturn(true);

        BigDecimal transferAmount = BigDecimal.valueOf(100);
        BigDecimal commissionPercent = BigDecimal.valueOf(0.05); // 5% commission

        // Act
        boolean result = paymentProcessor.makeTransferWithComission(sourceAgreement, destinationAgreement,
                sourceAccount.getType(),
                destinationAccount.getType(),
                transferAmount,
                commissionPercent);

        // Assert
        assertTrue(result, "The transfer with commission should be successful");

        // Check the charge method is called with the correct parameters
        verify(accountService).charge(sourceAccount.getId(), transferAmount.multiply(commissionPercent).negate()); // Should charge -5.00
        verify(accountService).makeTransfer(sourceAccount.getId(), destinationAccount.getId(), transferAmount);
    }
}
