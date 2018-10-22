package simpledb.log;

import simpledb.server.SimpleDB;

import java.util.Iterator;

public class TestLog {

    public static void main(String[] args) {
        SimpleDB.init("testdb");
        LogMgr logmgr = SimpleDB.logMgr();
        int lsn1 = logmgr.append(new Object[] {"a", "b"});
        int lsn2 = logmgr.append(new Object[] {"c", "d"});
        int lsn3 = logmgr.append(new Object[] {"e", "f"});
        logmgr.flush(lsn3);

        Iterator<BasicLogRecord> iterator = logmgr.iterator();
        while (iterator.hasNext()) {
            BasicLogRecord rec = iterator.next();
            String v1 = rec.nextString();
            String v2 = rec.nextString();
            System.out.println("[" + v1 + ", " + v2 + "]");
        }
    }
}
