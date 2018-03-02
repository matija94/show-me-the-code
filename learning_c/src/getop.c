/*
 * getop.c
 *
 *  Created on: Mar 1, 2018
 *      Author: matija
 */

#include <ctype.h>
#include <stdio.h>
#include "calc.h"

// getop: get next operator or operand
int getop(char s[]) {
	int i, c;

	while((s[0] = c = getch()) == ' ' || c == '\t')
		;

	s[1] = '\0';

	if(!isdigit(c) && c != '.'){
		return c; // not a number
	}

	i = 0;
	if (isdigit(c)){ // collect the number
		while(isdigit(s[++i] = c = getch()))
			;
	}

	if (c == '.') { // collect the fraction part
		while(isdigit(s[++i] = c = getch()))
					;
	}

	s[i] = '\0';
	if (c!=EOF){
		ungetch(c);
	}
	return NUMBER;
}

