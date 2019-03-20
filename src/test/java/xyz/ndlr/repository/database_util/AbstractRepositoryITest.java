package xyz.ndlr.repository.database_util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class AbstractRepositoryITest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    class MockObject {}

    class MockAbstractRepository extends AbstractRepository<MockObject> {

        protected MockAbstractRepository() {
            super(MockObject.class);
        }
    }
}