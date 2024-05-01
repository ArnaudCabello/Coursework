all: lab2.zip lab2

lab2.zip: shellA.c Makefile
	zip lab2.zip shellA.c Makefile
lab2: shellA.o 
	gcc -o lab2 shellA.o
shellA.o: shellA.c
	gcc -c shellA.c
