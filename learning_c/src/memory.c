/*
 * memory.c
 *
 *  Created on: Mar 2, 2018
 *      Author: matija
 */


#define ALLOCSIZE 10000 /* size of avail splace */

// private attributes
static char allocbuf[ALLOCSIZE]; // buffer
static char *allocp = allocbuf; // next free position

// return pointer to in characters
char *alloc(int n) {
	if (allocbuf + ALLOCSIZE - allocp >= n) { // fits
		allocp += n; // increment next free position pointer by n
		return allocp -n; // return old allocp ( represents start of returned n character spaces )
	}
	else // not enough room
		return 0;
}

// free storage pointed by p
void afree(char *p) {
	if (p >= allocbuf && p < allocbuf + ALLOCSIZE) {
		allocp = p;
	}
}
