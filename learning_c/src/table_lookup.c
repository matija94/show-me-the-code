#include <stdio.h>

struct nlist {
	struct nlist *next; // next in the list
	char *name; // name - key
	char *defn; // replacement text - value
};


#define HASHSIZE 101

static struct nlist *hashtab[HASHSIZE]; // pointer table

unsigned hash(char *s) {
	unsigned hashval;

	for (; *s != '\0'; s++)
		hashval = *s + 31 * hashval;
	return hashval % HASHSIZE;
}

struct nlist *lookup(char *s) {
	struct nlist *np;

	for(np = hashtab[hash(s)]; np != NULL; np = np->next)
		if (strcmp(s, np->name) == 0)
			return np;
	return NULL;
}

char *strdups(char *);

struct nlist *install(char *name, char *defn) {
	struct nlist *np;
	unsigned hashval;

	if((np = lookup(name)) == NULL) {
		np = (struct nlist *) malloc(sizeof(np));
		if (np == NULL || (np->name = strdups(name)) == NULL)
			return NULL;
		hashval = hash(name);
		np->next = NULL;
		hashtab[hashval] = np;
	}
	else
		free((void *) np->defn); // free previous defn
	if ((np->defn = strdups(defn)) == NULL)
		return NULL;
	return np;
}

void undef(char *s) {
	struct nlist *np, *prev;
	for(np = hashtab[hash(s)]; np != NULL; np = np->next) {
		if (strcmp(s, np->name) == 0){
			if (prev)
				prev->next = np->next;
			free(np);
		}
		prev = np;
	}
}

main() {
	install("matija", "car");
	undef("matija");
	printf("%s\n", lookup("matija")->defn);
	printf("%s\n", lookup("matija")->defn);
	return 0;
}




