#!/usr/bin/python3
#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# OSU ID: <Cabello.9>
#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# Lab2Client
#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
#
import socket
import sys 

#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# Global Variables
#···············································································

serverIP='127.0.0.1'					   # loopback address (localhost)
serverPort = int(sys.argv[1])			   # user decided port 
serverAddress=(serverIP,serverPort)        # Transport layer address = IP + port
processingUserInput=True                   # Flag for processing

#···············································································

#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# Application Main
#
def main():

	#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	# 
	#
	try:
		clientSocket=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
		print('echoTCPclient :: Client socket object created')
		processingUserInput=True

		#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		# 
		#
		try:
			clientSocket.connect(serverAddress)
			print('echoTCPclient :: Connected to SIMPServer ',serverAddress)
			processingUserInput=True
		except:
			print('echoTCPclient :: Unable to connect to server ',serverAddress)
			processingUserInput=False

	except:
		print('echoTCPclient :: Unable to create client socket')
		processingUserInput=False

	#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	# prompt for and receive command strings until close
	#

	while processingUserInput:

		commandString=input('echoTCPclient► ')
		#print('echoTCPclient :: String received ',commandString)

		#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		# prepare to stop if close
		#
		if commandString == 'exit':
			processingUserInput=False

		#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		# send request to SIMP server
		#
		#print('echoTCPclient :: Sending client request ',commandString)
		clientSocket.send(commandString.encode())
		#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		# Wait for server response
		#
		serverResponse=clientSocket.recv(2048)
		serverResponse=serverResponse.decode()
		print(serverResponse)

	clientSocket.close()

if __name__ == "__main__":
	sys.exit(main())
