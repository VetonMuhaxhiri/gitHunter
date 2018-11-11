### simple java code to look for open /.git directory of given urls.


#### Steps:
1. $ javac gitHunter.java
2. $ java gitHunter [path to a .txt file with domains]


### Usage

$ java gitHunter /root/Desktop/example-subdomains.txt


### Info

Colors wil generally work for Unix shell prompts

If the link respond with http header 301, the color will be blue.
If the link respond with 200 the color will be green, and if it find an open /.git directory it will alert with "GIT FOUND !!!"
