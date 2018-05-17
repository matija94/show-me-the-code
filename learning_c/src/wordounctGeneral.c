
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

struct tnode *addWordNode(struct tnode *, char *);
void addWordTree(struct tree *, char *);
struct tree * treealloc(void);
void treeprint(struct tnode *);
void numTreeprint(struct tnode *);
int getword(char *, int);
void makeNumTree(struct tnode *, struct tree *);

/*main() {
	struct tree *twords;
	struct tree *tnumbers;
	char word[MAXWORD];

	twords = treealloc();

	twords->root = NULL;
	twords->length = 0;

	tnumbers = treealloc();
	tnumbers->root = NULL;
	tnumbers->length = 0;

	while(getword(word,MAXWORD) != EOF) {
		if (isalpha(word[0]))
			addWordTree(twords, word);
	}

	makeNumTree(twords->root, tnumbers);
	treeprint(twords->root);
	printf("\n");
	numTreeprint(tnumbers->root);
	printf("%d", tnumbers->length);
	return 0;

}*/


struct tnode *talloc(void);
char *strdups(char *);

struct tnode *addWordNode(struct tnode *p, char *str) {
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
		p->left = addWordNode(p->left, str);
	}
	else {
		p->right = addWordNode(p->right, str);
	}
	return p;
}

struct tnode *addNumNode(struct tnode *wordNode, struct tnode *numNode) {
	if (numNode == NULL) {
		numNode = talloc();
		numNode->word = strdup(wordNode->word);
		numNode->count = wordNode->count;
		numNode->left = numNode->right = NULL;
	}
	else if ((wordNode->count - numNode->count) <= 0) {
		numNode->left = addNumNode(wordNode, numNode->left);
	}
	else {
		numNode->right = addNumNode(wordNode, numNode->right);
	}
	return numNode;
}

void makeNumTree(struct tnode *twordsRoot, struct tree *tnumbers) {
	if (twordsRoot != NULL) {
		makeNumTree(twordsRoot->left, tnumbers);
		makeNumTree(twordsRoot->right, tnumbers);
		tnumbers->root=addNumNode(twordsRoot, tnumbers->root);
		tnumbers->length++;
	}
}

void addWordTree(struct tree *t, char *str) {
	t->root = addWordNode(t->root, str);
	t->length++;
}

void treeprint(struct tnode *p){
	if (p != NULL) {
		treeprint(p->left);
		printf("%4d %s\n", p->count, p->word);
		treeprint(p->right);
	}
}

void numTreeprint(struct tnode *p) {
	if (p != NULL) {
		numTreeprint(p->right);
		printf("%4d %s\n", p->count, p->word);
		numTreeprint(p->left);
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
