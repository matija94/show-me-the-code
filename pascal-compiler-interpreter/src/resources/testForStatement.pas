{test for statements}

BEGIN
	n := 5;
	
	FOR i := 1 TO n DO
		BEGIN
			FOR j := i TO n DO BEGIN x := i*j; END
		END
END.
						