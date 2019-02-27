
import os
import socket
import subprocess
import time

class Client():
    def __int__(self):
        self.host='192.168.1.11'
        self.port=9009
        self.s = None

    def socket_create(self):
        try:
            self.s = socket.socket()
            print('Socket Created')
        except socket.error as e:
            print('Socket creation error: '+ str(e))


    def socket_connect(self):
        try:
            self.s.connect(('192.168.1.11',9009))
            print('Socket connected')

        except socket.error as e:
            print('Socket connection error: '+ str(e))
            return


    def send_data(self,data):
        self.s.send(bytes(data, 'UTF-8'))
        print('Data sent')

    '''def recv_data(self):
        data = str(self.s.recv(100000), 'utf-8')
        print(data)'''

def main():
    client=Client()
    client.socket_create()
    while True:
        #client.recv_data()
        try:
            client.socket_connect()
        except Exception as e:
            print('Error on socket connections: '+str(e))
        else:
            break
    while True:
        #client.recv_data()
        inp = input('Data:')
        if inp == 'exit':
            break
        client.send_data(inp)

    client.s.close()

if __name__=='__main__':
    while True:
        main()
