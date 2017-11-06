/*
 ============================================================================
 Name        : learning_c.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>

//int getLine(char s[]);
int isComment(char s[]);

/*int main(void) {
	char s[1000];
	int len;
	while((len = getLine(s)) > 0) {
		if (isComment(s)==0)
			printf("%s\n",s);
	}

	char s[] = "MATIJA";
	ascii_lower(s);
	printf("%s\n",s);
}*/


/*int getLine(char s[]) {
	int i;
	char c;

	i=0;
	while((c = getchar()) != '\n' && c != EOF) {
		s[i] = c;
		i+=1;
	}
	s[i] = '\0';
	return i;
}*/

int isComment(char s[]) {
	int n = strlen(s);
	if (s[0]=='/') {
		if ((s[1] == '*' && s[n-2] == '*' && s[n-1] =='/') || s[1] == '/') {
			return 1;
		}
		else {
			return 0;
		}
	}
	else {
		return 0;
	}
}

/*void getLines(char s[][1000]) {
	int i,len;
	i=0;
	char line[1000];
	while((len=getLine(line)) > 0) {
		s[i] = line;
		i+=1;
	}
}*/

