#!/usr/bin/python3
#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# OSU ID: <Cabello.9>
#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# Lab2Server 
#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
#
import socket
import sys
import datetime

#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# Global Variables
#···············································································

serverIP='127.0.0.1'                       # loopback address (localhost)
serverPort = int(sys.argv[1])              # user decided port
serverAddress=(serverIP,serverPort)        # Transport layer address = IP + port
processingClientRequests=True              # Flag for processing


#···············································································
#Application Functions
def catalogDef():
    if len(listOfList) < 1:
        return '\nNo lists found\n'
    else:
        catalog_response = "\n".join(f"{index}. {title[0]}" for index, title in enumerate(listOfList, start=1))
        return '\n' + catalog_response + '\n'
    
def createDef(title):
    itemInList = [] 
    listOfList.append([title,itemInList])
    output = '\nAdded ' + title + ' to catalog\n'
    return output

def editDef(aList):
    title = aList[0]
    editStr = '\n' + title + " in edit mode:\n" + "1. show \n" + "2. add\n" + "3. remove\n" + "4. quit\n"
    return editStr

def displayDef(aList):
    title = aList[0]
    itemList = aList[1]
    if len(itemList) < 1:
        outputStr = '\nNo items in ' + title + '\n'
        return outputStr
    else:
        response = "\n".join(f"{index}. {item}" for index, item in enumerate(itemList, start=1))
        return title + ": \n" + response + '\n'

def deleteDef(num):
    ele = listOfList.pop(int(num)-1)
    output = '\nDeleted ' + ele[0] + ' from catalog\n'
    return output

def showDef(aList):
    title = aList[0]
    itemList = aList[1]
    if len(itemList) < 1:
        outputStr = '\nNo items in ' + title + '\n'
        return outputStr
    else:
        catalog_response = "\n".join(f"{index}. {item}" for index, item in enumerate(itemList, start=1))
        return '\n' + catalog_response + '\n'
    
def addDef(aList, item):
    title = aList[0]
    itemList = aList[1] 
    itemList.append(item)
    output = '\nAdded ' + item + ' to ' + title + '\n'
    return output

def remDef(aList, num):
    title = aList[0]
    itemList = aList[1]
    ele = itemList.pop(int(num) - 1)
    output = '\nRemoved ' + ele + ' from ' + title + '\n'
    return output
    
def quitDef():
    return False

def logRequest(type, message):
    timestamp = datetime.datetime.now().strftime('%a %b %d %H:%M:%S %Y')
    log = timestamp + ' REQUEST ' + type + ' ' + message
    with open(logFile, 'a') as f:
        f.write(log + '\n')

def logResponse(type, message):
    timestamp = datetime.datetime.now().strftime('%a %b %d %H:%M:%S %Y')
    log = timestamp + ' RESPONSE ' + type + ' ' + message
    with open(logFile, 'a') as f:
        f.write(log + '\n')
        
#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# Application Main
#
def main():

    #~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    # 
    #
    timestamp = datetime.datetime.now().strftime('%a %b %d %H:%M:%S %Y')
    time = timestamp + ' bound to port ' + str(serverPort)
    try:
        serverSocket=socket.socket(socket.AF_INET,socket.SOCK_STREAM) #Creates TCP socket
        processingClientRequests=True

        try:    
            #~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            # 
            #
            serverSocket.bind(serverAddress) #Binds the socket to the specific server address
            print(time)
            processingClientRequests=True
        except:
            print('Unable to bind to port '+str(serverPort))
            processingClientRequests=False

    except:
        print('Unable to create a server socket')
        processingClientRequests=False

    #~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    # Continue if socket opend and bound to a port
    #
    if processingClientRequests:

        #~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        # 
        #
        print('Listening on the server port')
        serverSocket.listen() #Listens for the client connections

        #~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        # 
        #
        clientConnection,clientAddress=serverSocket.accept()
        print('Accepted connection from ',str(clientAddress))

        #~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        # Process client requests
        #
        while processingClientRequests:

            #~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            # Wait for a client request, max request is 2K
            #
            clientRequest=clientConnection.recv(2048).decode()
            #print('Client request received ',clientRequest)

            #~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            # Check if client wants to close
            #
            
            logRequest('INFO', clientRequest)
            
            if clientRequest.upper() == 'CATALOG':
                message = catalogDef()
                clientConnection.send(message.encode())
                logResponse('INFO', 'CATALOG COMMAND')

            elif clientRequest.upper().find('CREATE') != -1:
                title = clientRequest.split(" ")[1]
                if isinstance(title,str):
                    message = createDef(title)
                    clientConnection.send(message.encode())
                    logResponse('INFO', 'CREATE COMMAND')
                else: 
                    outputStr = '\nInvalid command: ' + clientRequest + '\n' + 'Valid commad: create <list title>\n'
                    message = outputStr
                    clientConnection.send(message.encode())
                    logResponse('ERROR', 'CREATE ERROR')
                            
            elif clientRequest.upper().find('EDIT') != -1:
                num = clientRequest.split(" ")[1]
                userChoice = int(num) - 1
                if len(listOfList) > userChoice >= 0:
                    aList = listOfList[userChoice]
                    message = editDef(aList)
                    clientConnection.send(message.encode())
                    logResponse('INFO', 'EDIT COMMAND')
                    
                    bool = True
                    while bool:
                        clientRequest=clientConnection.recv(2048).decode()
                        if clientRequest.upper() == 'SHOW':
                            message = showDef(aList)
                            clientConnection.send(message.encode())
                            logResponse('INFO', 'SHOW COMMAND')
                            
                        elif clientRequest.upper().find('ADD') != -1:
                            item = clientRequest.split(" ")[1]
                            if isinstance(item,str):
                                message = addDef(aList, item)
                                clientConnection.send(message.encode())
                                logResponse('INFO', 'ADD COMMAND')
                            else: 
                                outputStr = '\nInvalid command: ' + clientRequest + '\n' + 'Valid commad: add "list item text"\n'
                                message = outputStr
                                clientConnection.send(message.encode())
                                logResponse('ERROR', 'ADD ERROR')
                                
                        elif clientRequest.upper().find('REMOVE') != -1:
                            num = clientRequest.split(" ")[1]
                            if len(aList[1]) > int(num) - 1 >= 0 :
                                message = remDef(aList, num)
                                clientConnection.send(message.encode())
                                logResponse('INFO', 'REMOVE COMMAND')
                            else: 
                                outputStr = '\nInvalid command: ' + clientRequest + '\n' + 'Valid commad: remove <list item number>\n'
                                message = outputStr
                                clientConnection.send(message.encode())
                                logResponse('ERROR', 'REMOVE ERROR')
                            
                        elif clientRequest.upper() == 'QUIT':
                            bool = quitDef()
                            outputStr = '\nExiting edit mode\n'
                            message = outputStr
                            clientConnection.send(message.encode())
                            logResponse('EXIT', 'EXIT COMMAND')
                        else:
                            outputStr = '\nInvalid command: ' + clientRequest + '\n' + 'Valid commands are: \n' + '1. show \n' + '2. add "list item text"\n' + '3. remove <list item number>\n' + '4. quit\n'
                            message = outputStr
                            clientConnection.send(message.encode())
                            logResponse('ERROR', 'ERROR COMMAND')
                else:
                    outputStr = '\nInvalid command: ' + clientRequest + '\n' + 'Valid commad: edit <list number>\n'
                    message = outputStr
                    clientConnection.send(message.encode())
                    logResponse('ERROR', 'ERROR COMMAND')

            elif clientRequest.upper().find('DISPLAY') != -1:
                num = clientRequest.split(" ")[1]
                userChoice = int(num) - 1
                if len(listOfList) > userChoice >= 0:  
                    aList = listOfList[userChoice]
                    message = displayDef(aList)
                    clientConnection.send(message.encode())
                    logResponse('INFO', 'DISPLAY COMMAND')
                    
                else: 
                    outputStr = '\nInvalid command: ' + clientRequest + '\n' + 'Valid commad: display <list number>\n'
                    message = outputStr
                    clientConnection.send(message.encode())
                    logResponse('ERROR', 'DISPLAY ERROR')

            elif clientRequest.upper().find('DELETE') != -1:
                num = clientRequest.split(" ")[1]
                if len(listOfList) > int(num) - 1 >= 0:
                    message = deleteDef(num)
                    clientConnection.send(message.encode())
                    logResponse('INFO', 'DELETE COMMAND')
                    
                else:
                    outputStr = '\nInvalid command: ' + clientRequest + '\n' + 'Valid commad: delete <list number>\n'
                    message = outputStr
                    clientConnection.send(message.encode())
                    logResponse('ERROR', 'DELETE ERROR')

            elif clientRequest.upper() == 'EXIT':
                processingClientRequests=False
                logResponse('EXIT','EXIT COMMAND')

            else:
                outputStr = '\nInvalid command: ' + clientRequest + '\n' + "Valid commands are:\n" + "Catalog\n" + "Create <list title>\n" + "Edit <list number>\n" + "Display <list number>\n" + "Delete <list number>\n" + "Exit\n"
                message = outputStr
                clientConnection.send(message.encode())
                logResponse('ERROR', 'ERROR COMMAND')

        clientConnection.close()

if __name__ == "__main__":
    listOfList = []
    logFile = 'log.txt'
    sys.exit(main())
