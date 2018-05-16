/*
#include <stdio.h>
#define MAXWORD 100

void *addnode(struct tnode *, char *);
void treeprint(struct tnode *);
int getword(char *, int);

main() {
	struct tnode *root;
	struct tree t;
	char word[MAXWORD];

	root = NULL;
	t.root = root;
	t.length = 0;

	while(getword(word,MAXWORD) != EOF) {
		if (isalpha(word[0]))
			root = addnode(root, word);
	}
	treeprint(root);
	return 0;

}

*/
