BEGIN {CASE statements}

    i := 3;
    ch := "m";

    CASE i+1 OF
        4:  j := i*4;
    END;

    FOR z := 1 to j DO
        BEGIN
            i := i + 1;
        END
END.