#include <stdio.h>

struct nlist *lookup(char *);
struct nlist *install(char *, char *);


getch();
ungetch(int);

char *getWord() {
	char *w, c;
	char line[100];
	int i;
	for(i=0;!isspace((c=getch())); i++)
		line[i]=c;
	line[i] = '\0';
	w = line;
	return w;
}
/*
main(int argc, char **argv) {
	printf("%s", argv[1]);
	int c;
	while((c=getch()) != EOF) {
		if (c == '#') {
			char *key, *val;
			if (strcmp(getWord(), "define") != 0)
				continue;
			key = strdup(getWord());
			val = strdup(getWord());
			install(key, val);
			printf("Installed pair <%s,%s>\n", key, val);
		}
	}
}*/
