#include <stdio.h>
/*
int main(int argc, char *argv[]) {

	FILE *fp;
	void filecopy(FILE *, FILE *);
	char *prog = argv[0];

	if (argc == 1) {
		filecopy(stdin, stdout);
	}

	else {
		while (--argc > 0) {
			if ((fp = fopen(*++argv, "r")) == NULL) {
				fprintf(stderr, "cat: can't open %s\n", prog, *argv);
				exit(1);
			}
			else {
				filecopy(fp, stdout);
				fclose(fp);
			}
		}

	}
	exit(0);
}*/


void filecopy(FILE *src, FILE *dest) {
	int c;
	while((c = getc(src)) != EOF) {
		putc(c, dest);
	}
}
