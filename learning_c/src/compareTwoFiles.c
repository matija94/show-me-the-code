#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#define MAXLINE 1000

/*int main(int argc, const char *argv[])
{
    char id[MAX];
    char name[MAX];
    FILE *fp1;
    FILE *fp2;
    if(argc==3)
    {
        fp1 = fopen(argv[1],"r");
        fp2 = fopen(argv[2],"r");
        if((fp1==NULL)||(fp2==NULL))
        {
            printf("one of the file is not opened");
        }
        while(fgets(id, sizeof id, fp1) != NULL)
        {
            printf("ID: %s\n",id);
        }

        while(fgets(name, sizeof name, fp2) != NULL)
        {
            printf("Name: %s\n",name);
        }
    }
    else
    {
        printf("Please check command line arguments");
    }
    fclose(fp1);
    fclose(fp2);
    return 0;
}*/
int main(int argc, char **argv) {

	FILE *fp1, *fp2;
	char s1[MAXLINE], s2[MAXLINE];

	if (argc < 3) {
		fprintf(stderr, "Invalid arguments\n");
		return 1;
	}

	fp1 = fopen(*++argv, "r");
	fp2 = fopen(*++argv, "r");

	while( (fgets(s1, sizeof s1, fp1) != NULL) && (fgets(s2, sizeof s2, fp2)) != NULL)
		if(strcmp(s1,s2) != 0)
			break;
	printf("Files differ at lines %s\t%s", s1, s2);
	fclose(fp1);
	fclose(fp2);

	return 0;
}
