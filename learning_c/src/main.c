/*
 * main.c
 *
 *  Created on: Mar 1, 2018
 *      Author: matija
 */

#include <stdio.h>

void swap(int *, int *);
int getint(int *);
int strlenp(char *);

main() {
	int *px;
	int x =5, y =10;
	px = &y;
	*px = x;
	printf("%d\n", y);



	char s[] = "Matija";
	printf("Length of the array is %d\n", strlenp(s));

	int a, b;
	getint(&a);
	getint(&b);

	swap(&a,&b);

	printf("a = %d; b = %d\n", a, b);




}
