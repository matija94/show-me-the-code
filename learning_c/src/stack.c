/*
 * stack.c
 *
 *  Created on: Mar 1, 2018
 *      Author: matija
 */


#include <stdio.h>
#include "calc.h"
#define MAXVAL 100

int sp = 0; // next free stack position
double val[MAXVAL]; // value stack


// push f onto value stack
void push(double f) {
	if (sp < MAXVAL) {
		val[sp++] = f;
	}
	else {
		printf("error: stack full, can't push %g\n", f);
	}
}

//pop value from the stack and return it
double pop(void) {
	if (sp>0) {
		return val[--sp];
	}
	else {
		printf("error: stack empty\n");
		return 0.0;
	}
}
