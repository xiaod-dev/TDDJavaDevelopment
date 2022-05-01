package mongo;


import com.mongodb.MongoException;
import org.jongo.MongoCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class TicTacToeCollectionSpec {

    private TicTacToeCollection collection;
    private TicTacToeBean bean;
    private MongoCollection mongoCollection;

    @BeforeEach
    void setUp() throws UnknownHostException {
        collection = spy(new TicTacToeCollection());
        bean = new TicTacToeBean(3, 2, 1, 'Y');
        mongoCollection = mock(MongoCollection.class);
    }

    @Test
    public void whenInstantiatedThenMongoHasDbNameTicTacToe() throws UnknownHostException {
        assertEquals(
                "tic-tac-toe",
                collection.getMongoCollection()
                        .getDBCollection().getDB().getName());
    }

    @Test
    public void
    whenInstantiatedThenMongoCollectionHasNameGame() throws UnknownHostException {
        assertEquals(
                "game",
                collection.getMongoCollection()
                        .getName());
    }

    @Test
    void whenSaveMoveThenInvokeMongoCollectionSave() {
        doReturn(mongoCollection).when(collection)
                .getMongoCollection();
        collection.saveMove(bean);
        verify(mongoCollection, times(1)).save(bean);
    }

    @Test
    public void whenSaveMoveThenReturnTrue() {
        doReturn(mongoCollection).when(collection)
                .getMongoCollection();
        assertTrue(collection.saveMove(bean));
    }

    @Test
    public void
    givenExceptionWhenSaveMoveThenReturnFalse() {
        doThrow(new MongoException("Bla"))
                .when(mongoCollection)
                .save(any(TicTacToeBean.class));
        doReturn(mongoCollection).when(collection)
                .getMongoCollection();
        assertFalse(collection.saveMove(bean));
    }

    @Test
    public void
    whenDropThenInvokeMongoCollectionDrop() {
        doReturn(mongoCollection).when(collection)
                .getMongoCollection();
        collection.drop();
        verify(mongoCollection).drop();
    }

    @Test
    public void whenDropThenReturnTrue() {
        doReturn(mongoCollection).when(collection)
                .getMongoCollection();
        assertTrue(collection.drop());
    }

    @Test
    public void
    givenExceptionWhenDropThenReturnFalse() {
        doThrow(new MongoException("Bla"))
                .when(mongoCollection)
                .drop();
        doReturn(mongoCollection).when(collection)
                .getMongoCollection();
        assertFalse(collection.drop());
    }
}
