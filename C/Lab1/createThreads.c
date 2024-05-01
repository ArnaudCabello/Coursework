#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#define THREAD_NO 10

void *mythread(void *arg) {
    int *id = (int *)arg;
    printf("my id is %d\n", *id);
}

int main(){
    pthread_t* p = (pthread_t*) malloc(THREAD_NO*sizeof(pthread_t));
    int* index = (int*)malloc(THREAD_NO*sizeof(int));
    int i;
   
    for(i=0; i<THREAD_NO; i++){
	index[i] = i;
        pthread_create(&p[i], NULL, mythread, &index[i]);
    }

    for(i=0; i<THREAD_NO; i++){
	pthread_join(p[i], NULL);
    }

    free(p);
    free(index);
    return 0;
}
