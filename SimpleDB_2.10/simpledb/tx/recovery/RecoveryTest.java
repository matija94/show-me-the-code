package simpledb.tx.recovery;

import simpledb.file.Block;
import simpledb.server.SimpleDB;
import simpledb.tx.Transaction;

public class RecoveryTest {

    public static void main(String args[]) {
        SimpleDB.init("simpleDB");
        Transaction tx = new Transaction();

        Block blk = tx.append("test", p -> {
            p.setString(0, "Sample format");
        });

        tx.pin(blk);
        tx.setString(blk, 40, "dummy value");
        tx.unpin(blk);
        tx.commit();
    }
}
