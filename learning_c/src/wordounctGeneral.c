
struct tnode {
	char *word;
	int count;
	struct tnode *left;
	struct tnode *right;
};

struct tree {
	struct tnode *root;
	int length;
};

#include <stdio.h>
#include <ctype.h>
#include <string.h>

#define MAXWORD 100

struct tnode *addnode(struct tnode *, char *);
void addtree(struct tree *, char *);
struct tree * treealloc(void);
void treeprint(struct tnode *);
int getword(char *, int);

main() {
	struct tree *t;
	char word[MAXWORD];

	t = treealloc();

	t->root = NULL;
	t->length = 0;

	while(getword(word,MAXWORD) != EOF) {
		if (isalpha(word[0]))
			addtree(t, word);
	}
	treeprint(t->root);
	printf("%d", t->length);
	return 0;

}


struct tnode *talloc(void);
char *strdups(char *);

struct tnode *addnode(struct tnode *p, char *str) {
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
		p->left = addnode(p->left, str);
	}
	else {
		p->right = addnode(p->right, str);
	}
	return p;
}


void addtree(struct tree *t, char *str) {
	t->root = addnode(t->root, str);
	t->length++;
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

struct tree *treealloc(void) {
	return (struct tree *) malloc(sizeof(struct tree));
}

char *strdups(char *s) {
	char *p;

	p = (char *) malloc(strlen(s)+1);
	if (p!=NULL) {
		strcpy(p,s);
	}
	return p;
}
