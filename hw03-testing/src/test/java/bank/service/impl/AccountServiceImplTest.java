package bank.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.bank.dao.AccountDao;
import ru.otus.bank.entity.Account;
import ru.otus.bank.entity.Agreement;
import ru.otus.bank.service.exception.AccountException;
import ru.otus.bank.service.impl.AccountServiceImpl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {
    @Mock
    AccountDao accountDao;

    @Mock
    Agreement agreement;

    @InjectMocks
    AccountServiceImpl accountServiceImpl;

    Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setNumber("123456");
        account.setType(1);
        account.setAmount(BigDecimal.valueOf(1000));
        account.setAgreementId(1L);
        agreement = new Agreement();
        agreement.setId(1L);
    }

    @Test
    void testSaveNewAccount() {
        when(accountDao.save(any(Account.class))).thenReturn(account);
        Account createdAccount = accountServiceImpl.addAccount(agreement, "123456", 1, BigDecimal.valueOf(1000));

        assertNotNull(createdAccount);
        assertEquals(account.getNumber(), createdAccount.getNumber());
        assertEquals(account.getAmount(), createdAccount.getAmount());
        assertEquals(agreement.getId(), createdAccount.getAgreementId());
    }

    @Test
    void testGetAccounts() {
        // Arrange
        Account account = new Account();
        account.setId(1L);
        when(accountDao.findAll()).thenReturn(Collections.singletonList(account));

        // Act
        List<Account> accounts = accountServiceImpl.getAccounts();

        // Assert
        assertNotNull(accounts);
        assertEquals(1, accounts.size());
        assertEquals(account, accounts.get(0));
    }

    @Test
    void testGetAccountsByAgreement() {
        // Arrange
        Agreement agreement = new Agreement();
        agreement.setId(1L);
        Account account = new Account();
        account.setId(1L);
        when(accountDao.findByAgreementId(eq(agreement.getId()))).thenReturn(Collections.singletonList(account));

        // Act
        List<Account> accounts = accountServiceImpl.getAccounts(agreement);

        // Assert
        assertNotNull(accounts);
        assertEquals(1, accounts.size());
        assertEquals(account, accounts.get(0));
    }

    @Test
    void testCharge() {
        // Arrange
        Long accountId = 1L;
        BigDecimal chargeAmount = BigDecimal.valueOf(100);

        Account account = new Account();
        account.setId(accountId);
        account.setAmount(BigDecimal.valueOf(500));

        when(accountDao.findById(accountId)).thenReturn(Optional.of(account));
        when(accountDao.save(account)).thenReturn(account);

        // Act
        boolean success = accountServiceImpl.charge(accountId, chargeAmount);

        // Assert
        assertTrue(success);
        assertEquals(BigDecimal.valueOf(400), account.getAmount());
        verify(accountDao).save(account);
    }

    @Test
    void testChargeAccountNotFound() {
        // Arrange: Устанавливаем ID аккаунта и сумму списания
        Long accountId = 1L;
        BigDecimal chargeAmount = BigDecimal.valueOf(100);

        // Указываем, что при попытке найти аккаунт по ID, он не будет найден
        when(accountDao.findById(accountId)).thenReturn(Optional.empty());

        // Act and Assert: Проверяем, что выбрасывается исключение и содержит правильное сообщение
        AccountException exception = assertThrows(AccountException.class, () -> {
            accountServiceImpl.charge(accountId, chargeAmount);
        });

        assertEquals("No source account", exception.getMessage());
    }

    @Test
    void iterableToListTest() {
        // Arrange
        AccountServiceImpl accountService = new AccountServiceImpl(null); // Null dummy since we focus on iterableToList
        List<Account> inputList = Arrays.asList(
                new Account() {{ setId(1L); setNumber("12345"); }},
                new Account() {{ setId(2L); setNumber("67890"); }}
        );

        // Act
        List<Account> result = accountService.iterableToList(inputList);

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals(2, result.size(), "The size of the list should match the input");
        assertEquals(inputList.get(0).getId(), result.get(0).getId(), "The first account's ID should match");
        assertEquals(inputList.get(1).getId(), result.get(1).getId(), "The second account's ID should match");
    }

    @Test
    public void testTransfer() {
        Account sourceAccount = new Account();
        sourceAccount.setAmount(new BigDecimal(100));

        Account destinationAccount = new Account();
        destinationAccount.setAmount(new BigDecimal(10));

        when(accountDao.findById(eq(1L))).thenReturn(Optional.of(sourceAccount));
        when(accountDao.findById(eq(2L))).thenReturn(Optional.of(destinationAccount));

        accountServiceImpl.makeTransfer(1L, 2L, new BigDecimal(10));

        assertEquals(new BigDecimal(90), sourceAccount.getAmount());
        assertEquals(new BigDecimal(20), destinationAccount.getAmount());
    }

    @Test
    public void testSourceNotFound() {
        when(accountDao.findById(any())).thenReturn(Optional.empty());

        AccountException result = assertThrows(AccountException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                accountServiceImpl.makeTransfer(1L, 2L, new BigDecimal(10));
            }
        });
        assertEquals("No source account", result.getLocalizedMessage());
    }


    @Test
    public void testTransferWithVerify() {
        Account sourceAccount = new Account();
        sourceAccount.setAmount(new BigDecimal(100));
        sourceAccount.setId(1L);

        Account destinationAccount = new Account();
        destinationAccount.setAmount(new BigDecimal(10));
        destinationAccount.setId(2L);

        when(accountDao.findById(eq(1L))).thenReturn(Optional.of(sourceAccount));
        when(accountDao.findById(eq(2L))).thenReturn(Optional.of(destinationAccount));

        ArgumentMatcher<Account> sourceMatcher =
                argument -> argument.getId().equals(1L) && argument.getAmount().equals(new BigDecimal(90));

        ArgumentMatcher<Account> destinationMatcher =
                argument -> argument.getId().equals(2L) && argument.getAmount().equals(new BigDecimal(20));

        accountServiceImpl.makeTransfer(1L, 2L, new BigDecimal(10));

        verify(accountDao).save(argThat(sourceMatcher));
        verify(accountDao).save(argThat(destinationMatcher));
    }
}
