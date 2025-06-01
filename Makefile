SOURCES = Cipher.java FileHandling.java BlockCipher.java StreamCipher.java

all:
	javac $(SOURCES)

clean:
	rm -f *.class
