/*
 * wordcount.c
 *
 *  Created on: Mar 12, 2018
 *      Author: matija
 */

#include <stdio.h>
#include <ctype.h>
#include <string.h>

#define MAXWORD 100

struct key {
	char *word;
	int count;
};

struct key keytab[] = {
		{"auto", 0},
		{"break", 0},
		{"case", 0},
		{"char", 0},
		{"const", 0},
		{"continue", 0},
		{"default", 0},
		{"/*...*/", 0},
		{"unsigned", 0},
		{"void", 0},
		{"volatile", 0},
		{"while", 0}
};

#define NKEYS (sizeof keytab / sizeof keytab[0])

int getword(char *, int);
int binsearch(char *, struct key *, int);

/*main() {

	int n;
	char word[MAXWORD];

	while(getword(word,MAXWORD) != EOF)
		if (isalpha(word[0]))
			if ((n = binsearch(word, keytab, NKEYS)) >= 0)
				keytab[n].count++;
	for (n = 0; n < NKEYS; n++)
		if (keytab[n].count > 0)
			printf("%4d %s\n", keytab[n].count, keytab[n].word);

	return 0;
}*/


int binsearch(char *word, struct key tab[], int n) {
	int res;
	int low, high, mid;

	low = 0;
	high = n - 1;
	while (low <= high) {
		mid = (low + high) / 2;
		res = strcmp(word, tab[mid].word);
		if (res > 0) {
			return low = mid + 1;
		}
		else if (res < 0) {
			high = mid - 1;
		}
		else return mid;
	}
	return -1;
}

int getword(char *word, int lim) {
	int c, getch(void);
	void ungetch(int);
	char *w = word;

	while(isspace(c = getch()))
		;

	if (c != EOF)
		*w++ = c;
	if (!isalpha(c)) {
		*w = '\0';
		return c;
	}
	for ( ; --lim > 0; w++)
		if(!isalnum(*w = getch())) {
			ungetch(*w);
			break;
		}
	*w = '\0';
	return word[0];
}
