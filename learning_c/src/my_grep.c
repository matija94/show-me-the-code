


#include <string.h>
#define MAXLINE 1000

int getLine(char *line, int max);
/*
main(int argc, char *argv[]) {
	char line[MAXLINE];
	long lineno = 0;
	int c, except = 0, number = 0, found = 0;

	while(--argc > 0 && (*++argv)[0] == '-') {
		while (c = *++argv[0]) {
			switch(c) {
			case 'x':
				except = 1;
				break;
			case 'n':
				number = 1;
				break;
			default:
				break;
			}
		}
	}
	if (argc == 1) {
		while(getLine(line,MAXLINE) > 0) {
			lineno++;
			if((strstr(line, *argv) != NULL) != except) {
				if(number)
					printf("%ld:", lineno);
				printf("%s", line);
				found++;
			}
		}
	}
	return found;
}*/

