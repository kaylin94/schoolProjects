/*
 *  You have been hired by an environmental agency to help improve the
 *  whale population. Because unscrupulous commercial interests have 
 *  dangerously lowered the whale population, whales are having
 *  synchronization problems in finding a mate. The trick is that in order
 *  to have children, three whales are needed, one male, one female, and one
 *  to play matchmaker - literally, to push the other two whales together.
 *
 *  Your job is to write the two procedures male() and female(). Eaach whale
 *  is represented by a separate thread. A male whale calls male(), which will
 *  either act as a matchmaker or wait for a female and a matchmaker. A 
 *  female acts similarly.
 *
 *  Rules:
 *   -- once a whale mates, it no longer participates in mating or matchmaking
 *   -- when a new whale is created, if it can act as a matchmaker (i.e., a
 *      male and female are already waiting), it must do so, but if a 
 *      matchmaker is not needed at this time, it should wait for a mate
 *   -- once a whale starts waiting for a mate, it should no longer act as
 *      a matchmaker
 *   -- for whales that serve as a matchmaker, after completing a match, they
 *      should check again to see if a matchmaker is needed
 *
 *  AUTHORS: KAYLIN HUNTER & ALEX SIMONEAUX
 */

#define _BSD_SOURCE	// usleep is from BSD

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
#include <sys/time.h>
sem_t maleSem;
sem_t femaleSem;
sem_t maleWaitSem;
sem_t femaleWaitSem;
sem_t mutex;
sem_t mmSem;

int count;

/*
 *  the output strings MUST be of the form:
 *  (t=TIME) WHALE_ID: ACTION
 * 
 *  with NO leading or trailing spaces
 * 
 *  TIME - the value of elapsed_time()
 *  WHALE_ID - the id passed into Male(int id) and Female(int id)
 *  ACTION - one of the followoing exact phrases (with no leading
 *				 or trailing spaces):
 *
 *	 Male Created
 *	 Female Created
 *	 Found Mate
 *	 Became MatchMaker
 *	 Mated
 */

/* thread-safe printf */
static pthread_mutex_t stdout_lock;

#define LOCKED_PRINTF(fmt, args...) \
	pthread_mutex_lock(&stdout_lock); \
	printf(fmt, args); \
	fflush(stdout); \
	pthread_mutex_unlock(&stdout_lock)

/* elapsed time calculation */
static struct timeval time0;
double elapsed_time() {
	struct timeval tv;
	gettimeofday(&tv, NULL);
	return (tv.tv_sec - time0.tv_sec + (tv.tv_usec - time0.tv_usec) / 1.e6 );
}

/* a male whale */
void male(int id) {   
   
   if(count!= 0 && count%2 == 0) {
      LOCKED_PRINTF("(t=%f) %d: %s\n", elapsed_time(), id, "Became MatchMaker");
      while(count != 0 && count%2 == 0){
         sem_post(&maleWaitSem);
         sem_post(&femaleWaitSem);
         sem_wait(&mmSem);
      }
      pthread_exit(0);
   }

   else{
      sem_wait(&maleSem);
      LOCKED_PRINTF("(t=%f) %d: %s\n", elapsed_time(), id, "Found Mate");
      sem_post(&femaleSem);
      sem_wait(&mutex);
      count++;
      sem_post(&mutex);
   }

   sem_wait(&maleWaitSem);
   LOCKED_PRINTF("(t=%f) %d: %s\n", elapsed_time(), id, "Mated");
   sem_wait(&mutex);
   count--;
   sem_post(&mutex);

}

/* a female whale */
void female(int id) {

   if(count!= 0 && count%2 == 0) {
      LOCKED_PRINTF("(t=%f) %d: %s\n", elapsed_time(), id, "Became MatchMaker");
      while(count != 0 && count%2 == 0){
         sem_post(&maleWaitSem);
         sem_post(&femaleWaitSem);
         sem_wait(&mmSem);
      }
      pthread_exit(0);
   }

   else{
      sem_post(&maleSem);
      sem_wait(&femaleSem);
      LOCKED_PRINTF("(t=%f) %d: %s\n", elapsed_time(), id, "Found Mate");
      sem_wait(&mutex);
      count++;
      sem_post(&mutex);
   }
   
   sem_wait(&femaleWaitSem);
   LOCKED_PRINTF("(t=%f) %d: %s\n", elapsed_time(), id, "Mated");
   sem_wait(&mutex);
   count--;
   sem_post(&mutex);
   sem_post(&mmSem);

}

   
/* initialization code for male */
void create_male(int id) {
   LOCKED_PRINTF("(t=%f) %d: %s\n", elapsed_time(), id, "Male Created");
   male(id);
}

/* initialization code for female */
void create_female(int id) {
   LOCKED_PRINTF("(t=%f) %d: %s\n", elapsed_time(), id, "Female Created");
   female(id);
}

/* create the requested number of whales */
void *create_whale(void* arg) {
	int id = *((int *) arg);

	// is this a male or a female?
	if (rand() & 1) {    // odd for male
		create_male(id);
	} else {             // even for female
		create_female(id);
	}

	pthread_exit(NULL);
}

/* sleep some amount of time */
void whale_sleep(int wait_time_ms) {
	usleep(wait_time_ms * 1000); // convert from ms to us
}

/* error message on usage */
void usage(char *arg0) {
	fprintf(stderr, "Usage: %s num_whales wait_time\n"
					"\tnum_whales - the total number of whales to create\n"
	 				"\twait_time - the amount of time to wait before creating another whale (seconds)\n",
					arg0);
	exit(-1);
}

int main(int argc, char *argv[]) {

	if (argc != 3) {
		usage(argv[0]);
	}
	
   /* printf mutex */
	pthread_mutex_init(&stdout_lock, NULL);

	/* seed the random number generator */
	struct timeval tv;
	gettimeofday(&tv, NULL);
	srand(tv.tv_usec);

   /* start timings */
	gettimeofday(&time0, NULL);

   /* get command line parameters */
	int num_whales = atoi(argv[1]);
	int wait_time_ms = atof(argv[2]) * 1000;

	pthread_attr_t thread_attr;
	pthread_attr_init(&thread_attr);
	pthread_attr_setdetachstate(&thread_attr, PTHREAD_CREATE_DETACHED);

   sem_init(&maleSem, 0, 0);
   sem_init(&femaleSem, 0, -1);
   sem_init(&maleWaitSem, 0, 0);
   sem_init(&femaleWaitSem, 0, 0);
   sem_init(&mutex, 0, 1);
   sem_init(&mmSem, 0, 0);

   /* create the correct number of whales */
	for (int whale = 0; whale < num_whales; whale++) {
		whale_sleep(wait_time_ms);
		pthread_t whale_thread_id;
		pthread_create(&whale_thread_id, &thread_attr, create_whale, (void *) &whale);
	}

	/* this should enough time to resolve the matches that can be resolved*/
	whale_sleep(wait_time_ms * 10.0);
	
	pthread_mutex_destroy(&stdout_lock);
}
