package xyz.ndlr.presentation.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import xyz.ndlr.domain.account.Account;
import xyz.ndlr.service.AccountFetchingService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountResourceTest {

    @Mock
    AccountFetchingService accountFetchingService;

    @InjectMocks
    AccountResource accountResource;

    @Test
    public void whenGettingMyAccounts_fetchesCurrentUserAccounts(){
        accountResource.getMyAccounts();

        verify(accountFetchingService).fetchCurrentUserAccounts();
    }

    @Test
    public void whenGettingMyAccounts_includesAccountsInResponse() {
        List<Account> accounts = new ArrayList<>();
        when(accountFetchingService.fetchCurrentUserAccounts())
                .thenReturn(accounts);

        ResponseEntity<List<Account>> response = accountResource.getMyAccounts();

        assertEquals(accounts, response.getBody());
    }

    @Test
    public void whenGettingMyAccounts_producesOKResponse(){
        ResponseEntity<List<Account>> response = accountResource.getMyAccounts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}