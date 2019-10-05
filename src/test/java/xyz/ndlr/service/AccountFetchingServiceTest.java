package xyz.ndlr.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import xyz.ndlr.domain.account.Account;
import xyz.ndlr.domain.account.AccountToken;
import xyz.ndlr.domain.provider.ProviderId;
import xyz.ndlr.domain.user.UserId;
import xyz.ndlr.infrastructure.persistence.QueryExecutorAccountRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountFetchingServiceTest {

    private static final AccountToken A_TOKEN = AccountToken.from("a token");
    private static final ProviderId A_PROVIDER_ID = ProviderId.from(5);
    private static final Account AN_ACCOUNT = new Account();
    private static final UserId A_USER_ID = UserId.from(5);
    private static final AccountFetchingRequest AN_ACCOUNT_FETCHING_REQUEST =
            new AccountFetchingRequest(A_TOKEN, A_PROVIDER_ID);

    @Mock
    QueryExecutorAccountRepository accountRepository;

    @InjectMocks
    AccountFetchingService accountFetchingService;

    @Before
    public void setUp() {

    }

    @Test
    public void givenTokenAndProviderId_whenFetchingByToken_fetchesByToken() {
        accountFetchingService.fetchByToken(AN_ACCOUNT_FETCHING_REQUEST);

        verify(accountRepository).getByToken(A_TOKEN, A_PROVIDER_ID);
    }

    @Test
    public void givenTokenAndProviderId_whenFetchingByToken_fetchesCorrespondingAccount() {
        when(accountRepository.getByToken(A_TOKEN, A_PROVIDER_ID))
                .thenReturn(AN_ACCOUNT);

        Account foundAccount = accountFetchingService.fetchByToken(AN_ACCOUNT_FETCHING_REQUEST);

        assertEquals(foundAccount, AN_ACCOUNT);
    }

    @Test
    public void givenUserId_whenFetchingByUserId_fetchesByUserId() {
        accountFetchingService.fetchByUserId(A_USER_ID);

        verify(accountRepository).getByUserId(A_USER_ID);
    }

    @Test
    public void givenUserId_whenFetchingByUserId_fetchesCorrespondingAccount() {
        when(accountRepository.getByUserId(A_USER_ID))
                .thenReturn(Collections.singletonList(AN_ACCOUNT));

        List<Account> foundAccounts = accountFetchingService.fetchByUserId(A_USER_ID);

        int expectedAccountListSize = 1;

        assertEquals(expectedAccountListSize, foundAccounts.size());
        assertEquals(AN_ACCOUNT, foundAccounts.get(0));
    }
}