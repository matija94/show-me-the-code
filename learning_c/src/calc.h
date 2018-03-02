/*
 * calc.h
 *
 *  Created on: Mar 1, 2018
 *      Author: matija
 */

#ifndef CALC_H_
#define CALC_H_

#define NUMBER '0' // signal that number was found

void push(double);
double pop(void);
int getop(char []);
int getch(void);
void ungetch(int);

#endif /* CALC_H_ */
