/*BY SUBMITTING THIS FILE TO CARMEN, I CERTIFY THAT I HAVE PERFORMED ALL OF THE WORK TO CREATE THIS FILE AND/OR DETERMINE THE ANSWERS FOUND WITHIN THIS FILE MYSELF WITH NO ASSISTANCE FROM ANY PERSON(OTHER THAN THE INSTUCTOR OR GRADERS OF THIS COURSE) AND I HAVE STRICTLY ADHERED TO THE TENURES OF THE OHIO STATE UNVIERSITY'S ACADEMIC INTEGRITY POLICY*/

#include <stdio.h>
//Method to display the encoded hex 
void cipher(int key, unsigned char* text, int len){
	int i;
	unsigned char cipherText;
	//ifdef to not display text in bit_encode1
	#ifdef PROMPT
	#else
	printf("\nHex ciphertext: \n");
	#endif
	for(i=0; i < len; i++){
		cipherText = key^text[i];//xoring the key and text together 
		if((i%10)==0){//Making it so it only displays 10 hex per line
			printf("\n");
		}
		printf("%02X ", cipherText);
		
	}
	printf("\n");
}
//Method to create the key by using users input 
int createKey(unsigned char* userKey){
	int i;
	int keybuilder;
	int key;
	
	keybuilder = 0;
	key = 0;
	for(i = 0; i< 4; i++){
		if(userKey[i] == '1'){//Checking to see if the users input is a 1 or 0
			keybuilder = keybuilder + 1;//Buildint the key 
		}
		if(i<3){
			keybuilder = keybuilder << 1;//Shifts the bit one spot each time 
		}
	}
	key = keybuilder << 4;//Shifts the bits 4 spots to make space for the other end
	key = key + keybuilder;//Adds the other end
	return key;
}
//Method to display the clear text in hex form to the user
void displayHex(unsigned char* text, int len){
	int i;
	for(i = 0; i< len; i++){
		if((i%10) == 0){//Makes it so only 10 hex are displayed on 1 line
			printf("\n");
		}
		printf("%02X ",text[i]);//Prints the hex and makes sure each hex has 2 characters
	}
	printf("\n");	
}

/*
 *Arnaud Cabello 
 *We have to create a cipher that takes in clear text and a key and xors them together
*/
int main(void){

	unsigned char text[200];
	char letter;
	int len;
	unsigned char userkey[4];
	int key;
	int i = 0;
	
	//ifdef to make it so text doesnt show up for bit_encode1
	#ifdef PROMPT
	#else
	printf("Input text to be encrypted:");
	#endif
	//Getting the clear text and putting it into a char array
	len = 0;
	letter = getchar();
	while(letter != '\n' && letter != '\r'){//Making sure to not get the return or new line characters
		text[len] = letter;
		len++;
		letter = getchar();
	}
	//ifdef to make it so text and the hex display doesnt appear in bit_encode1
	#ifdef PROMPT
	getchar();
	#else
	printf("\nTexted entered is: %s\n", text);
	displayHex(text,len);
	
	printf("\nEnter a 4-bit key: ");
	#endif
	//Getting users input for the key value
	for(i = 0; i < 4; i++){
		letter = getchar();
		userkey[i] = letter;	
	}
	//Method to actually create an int key value
	key = createKey(userkey);
	//Method to xor the text and key and display it
	cipher(key, text, len);
	
return 0;
}
