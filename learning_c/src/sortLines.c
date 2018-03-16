/*
 * sortLines.c
 *
 *  Created on: Mar 5, 2018
 *      Author: matija
 */


#include <stdio.h>
#include <string.h>

#define MAXLINES 5000

char *lineptr[MAXLINES];

int readlines(char *lineptr[], int nlines);
void writelines(char *lineptr[], int nlines);

void qsort(void *lineptr[], int left, int right, int (*comp)(void *, void*));

int numcmp(char *, char *);

/*main(int argc, char *argv[]) {
	int nlines;
	int numeric = 0;  1 if  numeric sort

	if (argc > 1 && strcmp(argv[1], "-n") == 0) {
		numeric = 1;
	}

	if ((nlines=readlines(lineptr,MAXLINES))>=0) {
		qsort((void **) lineptr, 0, nlines-1, (int (*)(void*,void*))(numeric ? numcmp : strcmp));
		writelines(lineptr,nlines);
		return 0;
	}
	else {
		printf("error: input too big to sort\n");
		return 1;
	}
}*/

#define MAXLEN 100
int getLine(char *, int);
char *alloc(int);

int readlines(char *lineptr[], int maxlines) {
	int len, nlines;
	char *p, line[MAXLEN];

	nlines = 0;
	while((len = getLine(line, MAXLEN))>0)
		if (nlines>=maxlines || (p=alloc(len)) == NULL)
			return -1;
		else {
			line[len-1] = '\0'; // delete newline
			strcpy(p, line);
			lineptr[nlines++] = p;
		}
	return nlines;
}

void writelines(char *lineptr[], int nlines) {
	int i;

	for(i=0; i<nlines; i++)
		printf("%s\n", lineptr[i]);
}

int numcmp(char *s1, char *s2) {
	double v1, v2;

	v1 = atof(s1);
	v2 = atof(s2);
	if (v1 < v2) {
		return -1;
	}
	else return 0;
}
