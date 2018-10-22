package simpledb;

import simpledb.planner.Planner;
import simpledb.query.Scan;
import simpledb.server.SimpleDB;
import simpledb.tx.Transaction;


public class TestSimpleDB {
    public static void main(String[] args) {
        SimpleDB.init("testdb");
        Planner planner = SimpleDB.planner();
        Transaction tx = new Transaction();

        String s = "create table STUDENT(SId int, SName varchar(10), MajorId int, GradYear int)";
        planner.executeUpdate(s, tx);
        System.out.println("Table STUDENT created.");

        s = "insert into STUDENT(SId, SName, MajorId, GradYear) values ";
        String[] studvals = {"(1, 'joe', 10, 2004)",
                "(2, 'amy', 20, 2004)",
                "(3, 'max', 10, 2005)",
                "(4, 'sue', 20, 2005)",
                "(5, 'bob', 30, 2003)",
                "(6, 'kim', 20, 2001)",
                "(7, 'art', 30, 2004)",
                "(8, 'pat', 20, 2001)",
                "(9, 'lee', 10, 2004)"};
        for (int i = 0; i < studvals.length; i++)
            planner.executeUpdate(s + studvals[i], tx);
        System.out.println("STUDENT records inserted.");


        s = "create table DEPT(DId int, DName varchar(8))";
        planner.executeUpdate(s, tx);
        System.out.println("Table DEPT created.");

        s = "insert into DEPT(DId, DName) values ";
        String[] deptvals = {"(10, 'compsci')",
                "(20, 'math')",
                "(30, 'drama')"};
        for (int i=0; i<deptvals.length; i++)
            planner.executeUpdate(s + deptvals[i], tx);
        System.out.println("DEPT records inserted.");
        tx.commit();
        String qry = "select SName, DName "
                + "from DEPT, STUDENT "
                + "where MajorId = DId";
        Scan rs = planner.createQueryPlan(qry, new Transaction()).open();

        // Step 3: loop through the result set
        System.out.println("Name\tMajor");
        while (rs.next()) {
            String sname = rs.getString("sname");
            String dname = rs.getString("dname");
            System.out.println(sname + "\t" + dname);
        }
        rs.close();
    }
}
