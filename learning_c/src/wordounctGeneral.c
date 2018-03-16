/*
 * wordounctGeneral.c
 *
 *  Created on: Mar 14, 2018
 *      Author: matija
 */


struct tnode {
	char *word;
	int count;
	struct tnode *left;
	struct tnode *right;
};

#include <stdio.h>
#include <ctype.h>
#include <string.h>

#define MAXWORD 100
struct tnode *addtree(struct tnode *, char *);
void treeprint(struct tnode *);
int getword(char *, int);

main() {
	struct tnode *root;
	char word[MAXWORD];

	root = NULL;
	while(getword(word,MAXWORD) != EOF) {
		if (isalpha(word[0]))
			root = addtree(root, word);
	}
	treeprint(root);
	return 0;

}


struct tnode *talloc(void);
char *strdup(char *);

struct tnode *addtree(struct tnode *p, char *str) {
	int comp;

	if (p == NULL) {
		p = talloc();
		p->word = strdup(str);
		p->count = 1;
		p->left = p->right = NULL;
	}
	else if ((comp = strcmp(p->word, str)) == 0) {
		p->count++;
	}
	else if (comp < 0) {
		p->left = addtree(p->left, str);
	}
	else {
		p->right = addtree(p->right, str);
	}
	return p;
}

void treeprint(struct tnode *p){
	if (p != NULL) {
		treeprint(p->left);
		printf("%4d %s\n", p->count, p->word);
		treeprint(p->right);
	}
}


struct tnode *talloc(void){
	return (struct tnode *) malloc(sizeof(struct tnode));
}

char *strdup(char *s) {
	char *p;

	p = (char *) malloc(strlen(s)+1);
	if (p!=NULL) {
		strcpy(p,s);
	}
	return p;
}
