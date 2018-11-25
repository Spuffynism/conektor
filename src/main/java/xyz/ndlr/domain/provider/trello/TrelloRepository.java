package xyz.ndlr.domain.provider.trello;

import org.springframework.stereotype.Repository;

@Repository
public class TrelloRepository {

    public TrelloRepository() {
    }

    public void print() {
        System.out.println("Test Trello Repository");
    }
}
