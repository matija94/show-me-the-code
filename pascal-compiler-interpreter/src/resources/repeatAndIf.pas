BEGIN {calculate the square root of 4 using Newton's pos's method}
    number := 4;
    root := number;

    foundRoot := 0;
    REPEAT
        partial := number/root + root;
        root := partial/2;
        IF root = 2 THEN
            BEGIN
                foundRoot := 1;
            END
    UNTIL root*root - number < 0.000001
END.