package xyz.ndlr.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import xyz.ndlr.domain.account.Account;
import xyz.ndlr.domain.provider.ProviderId;
import xyz.ndlr.domain.user.UserId;
import xyz.ndlr.repository.AccountRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountFetchingServiceTest {

    private static final String A_TOKEN = "a token";
    private static final ProviderId A_PROVIDER_ID = ProviderId.from(5);
    private static final Account AN_ACCOUNT = new Account();
    private static final UserId A_USER_ID = UserId.from(5);

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountFetchingService accountFetchingService;

    @Before
    public void setUp() {

    }

    @Test
    public void givenTokenAndProviderId_whenFetchingByToken_fetchesByToken() {
        accountFetchingService.fetchByToken(A_TOKEN, A_PROVIDER_ID);

        verify(accountRepository).getByToken(A_TOKEN, A_PROVIDER_ID);
    }

    @Test
    public void givenTokenAndProviderId_whenFetchingByToken_fetchesCorrespondingAccount(){
        when(accountRepository.getByToken(A_TOKEN, A_PROVIDER_ID))
                .thenReturn(AN_ACCOUNT);

        Account foundAccount = accountFetchingService.fetchByToken(A_TOKEN, A_PROVIDER_ID);

        assertEquals(foundAccount, AN_ACCOUNT);
    }

    @Test
    public void givenUserId_whenFetchingByUserId_fetchesByUserId(){
        accountFetchingService.fetchByUserId(A_USER_ID);

        verify(accountRepository).getByUserId(A_USER_ID);
    }

    @Test
    public void givenUserId_whenFetchingByUserId_fetchesCorrespondingAccount(){
        when(accountRepository.getByUserId(A_USER_ID))
                .thenReturn(Collections.singletonList(AN_ACCOUNT));

        List<Account> foundAccounts = accountFetchingService.fetchByUserId(A_USER_ID);

        assertEquals(1, foundAccounts.size());
        assertEquals(AN_ACCOUNT, foundAccounts.get(0));
    }
}