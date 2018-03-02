/*
 * getch.c
 *
 *  Created on: Mar 1, 2018
 *      Author: matija
 */

#include <stdio.h>

#define BUFFSIZE 100 // buffer size

int bp = 0; // buffer pointer
char buffer[BUFFSIZE]; // input buffer

int getch(void) {
	return bp > 0 ? buffer[--bp] : getchar();
}

void ungetch(int c) {
	if(bp < BUFFSIZE) {
		buffer[bp++] = c;
	}
	else {
		printf("error: too many chars\n");
	}
}


