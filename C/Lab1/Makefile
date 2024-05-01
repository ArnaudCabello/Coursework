all: lab4.zip createThreads parallel1 parallel2 parallel3

lab4.zip: createThreads.c parallel1.c parallel2.c parallel3.c  Makefile
	zip lab4.zip createThreads.c parallel1.c parallel2.c parallel3.c Makefile

createThreads: createThreads.o
	gcc -lpthread -o createThreads createThreads.o

createThreads.o: createThreads.c
	gcc -c createThreads.c

parallel1: parallel1.o
	gcc -lpthread -o parallel1 parallel1.o

parallel1.o: parallel1.c
	gcc -c parallel1.c

parallel2: parallel2.o
	gcc -lpthread -o parallel2 parallel2.o

parallel2.o: parallel2.c
	gcc -c parallel2.c

parallel3: parallel3.o
	gcc -lpthread -o parallel3 parallel3.o

parallel3.o: parallel3.c
	gcc -c parallel3.c
