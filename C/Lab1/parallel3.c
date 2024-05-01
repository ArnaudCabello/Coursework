#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#define ARRAY_SIZE 1000000
#define THREAD_NO 10

int sum = 0;
int i;
int num[THREAD_NO][ARRAY_SIZE/THREAD_NO];
pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;

void *mythread(void *arg){
    int *id = (int*)arg;
    printf("Thread: %d\n", *id);
    int local = 0;
    for(i=0; i<ARRAY_SIZE/THREAD_NO; i++){
	local += num[*id][i];
    }
    printf("Thread %d local sum: %d\n", *id, local);
    pthread_mutex_lock(&lock);
    sum += local;
    pthread_mutex_unlock(&lock);
    return NULL;
}

int main(){
    pthread_t p[THREAD_NO];
    int j;

    srand(100);
    //initialize arrays
    for( i=0; i< THREAD_NO; i++){
	for( j=0; j< ARRAY_SIZE/THREAD_NO; j++){
            num[i][j] = rand() % 100;
	}
    }
    

    int index[THREAD_NO];
    for(i=0; i< THREAD_NO; i++){
	index[i] = i;
	pthread_create(&p[i], NULL, mythread,(void *)&index[i]);
    }

    for(i = 0; i<THREAD_NO; i++){
	pthread_join(p[i], NULL);
    }

    printf("sum = %d\n", sum);
    return 0;
}
