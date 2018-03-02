// convert numerical string representation to int type
int parseInt(char s[]) {
	 int i,n;

	 n = 0;
	 for(i=0;i<strlength(s);i++){
		 n = 10 * n + (s[i] - '0');
	 }
	 return n;
}

#include <ctype.h>
#include <stdio.h>

int getch(void);
void ungetch(int);

// getint gets next integer from the input into pointer
int getint(int *pn) {
	int c, sign;

	while(isspace(c = getch())) ; // skip whitespace

	if (!isdigit(c) && c != EOF && c!= '+' && c!='-') {
		ungetch(c); // it's not a number
		return 0;
	}

	sign = (c == '-') ? -1 : 1;
	if (c == '+' || c == '-')
		c = getch();

	for(*pn=0; isdigit(c); c=getch()) {
		*pn = 10 * *pn + (c-'0');
	}
	*pn *= sign;
	if (c!=EOF) {
		ungetch(c);
	}
	return c;
}
