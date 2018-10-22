package simpledb.file;


import simpledb.server.SimpleDB;


public class TestFile {

    public static void main(String[] args) {
        SimpleDB.init("testdb");
        Block blk = new Block("f1", 1);
        Page page = new Page();
        String value = "Testing SimpleDB.file";
        page.setString(0, value);
        page.write(blk);

        page.read(blk);
        String string = page.getString(35);
        assert string.equals(value);
    }
}
