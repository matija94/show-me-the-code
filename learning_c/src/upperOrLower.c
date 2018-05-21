#include <stdio.h>
#include <ctype.h>

/*
int main(int argc, char **argv) {
	typedef int (*TransformFunc)(int);
	TransformFunc trans[2] = {tolower, toupper};

	int fn = 0; // default function

	if (argc > 1)
		fn = atoi(argv[1]);

	int c;
	while ((c=getchar()) != EOF)
		putchar(trans[fn](c));

	return 0;
}
*/
