package xyz.ndlr.model.provider.trello;

import org.springframework.stereotype.Repository;

@Repository
public class TrelloTestRepository {

    public TrelloTestRepository() {
    }

    public void print() {
        System.out.println("Test Trello Repository");
    }
}
