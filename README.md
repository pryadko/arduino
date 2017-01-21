install local env
### http://fizzed.com/oss/rxtx-for-java ###
1) for linux users need: 
    sudo apt-get install librxtx-java
    # we have lib in /usr/lib/jni/
    # and add VM option -Djava.library.path="/usr/lib/jni/"

2) for linux system user need access to port:
    sudo usermod -a -G dialout $USER
    # and relogin
3)
    We may face with some problem, try to run this command
    sudo apt-get remove modemmanager

