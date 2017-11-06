#include <ctype.h>

int strlength(const char s[]) {
	int i=0;
	while(s[i] != '\0') {
		i++;
	}
	return i;
}


void reverse(char s[]) {
	int c,i,j;
	for(i=0,j=strlength(s)-1;i < j;i++,j--) {
		c = s[i];
		s[i] = s[j];
		s[j] = c;
	}
}

// convert n to characters in s
void parseString(int n, char s[]) {
	int i,sign;

	if ((sign=n)<0) {
		n=-n; // turn positive
	}
	i=0;
	do {
		s[i++] = n % 10 + '0';
	}while((n/=10 ) > 0);
	if(sign < 0) {
		s[i++]='-';
	}
	reverse(s);
	s[i] = '\0';
}

// convert string to lower case; ASCII only
void ascii_lower(char s[]) {
	int c,i;
	i=0;
	while((c=s[i]) != '\0') {
		if (c >= 'A' && c <= 'Z') {
			s[i]=c + 'a' - 'A';
			i++;
		}
	}
}

// returns first occurrence of index of character in s1 that matches any character in s2
int any(char s1[],char s2[]) {
	int i,j;
	for(i=0;i<strlength(s1);i++) {
		for(j=0;j<strlength(s2);j++){
			if (s1[i] == s2[j]) {
				return i;
			}
		}
	}
	return -1;
}

// expands expressions like a-z from s1 into s2 as complete list abcd...xyz. Assumes s2 is big enough
void expand(char s1[], char s2[]) {
	int c=0,i=0,j=0,z=0;
	for(j=0;j<strlength(s1);j++){
		if(s1[j] == '-'){
			while(c<s1[j+1]) {
				c = s1[j-1]+z;
				s2[i++] = (char)c;
				z++;
			}
		}
		z=0;
		c=0;
	}

	s2[i] ='\0';
}

// copy content in 'from' into 'to' assume 'to' is big enough
void copy(char to[], char from[]) {
	int i;
	i =0;
	while((to[i] = from[i])!='\0') {
		i++;
	}
}

// remove trailing blanks, tabs, newlines
int trimEnd(char s[]) {
	int tail;
	int le = strlen(s);
	for (tail = le - 1; tail >= 0; tail--) {
		if (s[tail] != ' ' && s[tail] != '\t' && s[tail] != '\n')
			break;
	}
	s[tail+1] = '\0';
	return tail;
}

int main(void) {
	char s1[] = "a-r0-9";
	char s2[30];
	expand(s1,s2);
	printf("%s\n", s2);
	char s[5];
	parseString(1541,s);
	printf("%s\n",s);
}


