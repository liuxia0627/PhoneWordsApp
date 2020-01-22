# Phone Words App
> App gets and prints encoded phone words for provided phone numbers
## Requirement
- Today, Phonewords (https://en.wikipedia.org/wiki/Phoneword) are being used by
many companies as their phone numbers.
- Write a program to display possible matches for a list of provided phone numbers.
- For each number read, the program should output all possible word replacements
from a dictionary. The program should try to replace every digit of the provided
phone number with a letter from a dictionary word.
- However, if no complete match can be made a single digit can be left as is.
No two consecutive digits can remain unchanged.
- If a match cannot be made, the program should produce no output for that number.

- To allow the user to set a dictionary, implement a -dictionary command-line option.
The dictionary should consist of one word per line.
- The program should be case insensitive.
- Punctuation and whitespaces should be ignored in both the dictionary file and the
phone numbers.
- Output should be in capital letters and digits separated with a single dash (-).
- The output should also display one possible word replacement per line.
- Digit-to-characters encoding the program should use is as follows:

DIGIT | CHARACTERS |
----------------- | ----------- |
Image | IMG | 
2 | ABC | 
3 | DEF | 
4 | GHI | 
5 | JKL | 
6 | MNO | 
7 | PQRS | 
8 | TUV | 
9 | WXYZ | 
## Installation
### Clone

- Clone this repo to your local machine using `https://github.com/liuxia0627/PhoneWordsApp.git`

### Build

> **Install Gradle**(Skip this step if Gradle is installed )

Please check this link <a href="https://gradle.org/install/" target="_blank">**Gradle Install**</a> to install Gradle

> Go to the root folder of the project, and run the below command to clean and build the project

```shell
$ gradle clean
$ gradle build
```

### Run

- Navigate to **{ProjectRootFolder}/build/libs** folder, you will see **PhoneWordsApp-1.0-SNAPSHOT.jar** file in the folder

- Create the file to store provided phone numbers (e.g., phoneNumbers.txt) Each line of this file will contain a single phone number.

- Create another file to store provided dictionary words (e.g., dict.txt) Each line of this file will contain a single dictionary word.

- Run the bellow command to execute the program:

```shell
$ java -Ddictionary="dict.txt" -jar PhoneWordsApp-1.0-SNAPSHOT.jar phoneNumbers.txt
```
> Tips

- You can give any names to the phone number and dictionary files as you wish, and make sure to pass the correct names in the java command line
- phone numbers file name is passed by java command line args while dictionary file name is configured by java command line options -Ddictionary
- To test the program for your convenient, you can find those <a href="https://github.com/liuxia0627/PhoneWordsAppSampleFiles" target="_blank">**two sample files here**</a> 

### Output
Outputs will be different in different scenarios:

> If you don't create those two files or you pass the incorrect file names in the java command line, the program will be terminated and print the below error message：

```shell
$ phoneNumbers.txt (system cannot find the file specified)
$ dict.txt (system cannot find the file specified)

```

> If you don't create those two files or you pass the incorrect file names in the java command line, the program will be terminated and print the below error message：

```shell
$ phoneNumbers.txt (system cannot find the file specified)
$ dict.txt (system cannot find the file specified)

```

> If you don't pass the phone numbers file name as args in the java command line, the program will be terminated and print the below error message：

```shell
$ Please add the file name of phone number list as args in command line!

```

> If you don't pass the dictionary file name as -Ddictionary option in the java command line, the program will be terminated and print the below error message：

```shell
$ Please add the file name of dictionary as -Ddictionary <filepath> as option in comman
d line!

```

>If you add texts other than numbers in phone number file, the program will be terminated and print the following error:

```shell
$ phone numbers file can only contain numbers!

```

>If you add texts other than letters in dictionary file, the program will be terminated and print the following error:

```shell
$ dictionary file can only contain alphabets!

```

>If you add correct contents in those two files and pass the correct file name in the command line, the program will print the following info:

```shell
$ Matched phone words for phone number 54889 are:
$ [KITTY]
$ Matched phone words for phone number 782792771 are:
$ [STAR-WARS-1]
$ [STA-RWARS-1]
$ There are no matched phone words for phone number 872982342

```




