# CodingBoard #

[CodingBoard](http://codingboard.org) is a tiny, super-lightweight application to share code in a syntax highlighted way within a room, on short sessions. 
People join to a board (without registration) and post the newest version of their current practice code which the presenter can show on the screen. This way retrospectives can be more fruitful having the code up on the projected screen. 

## Build & Run ##

```sh
$ cd sharethecode
$ ./sbt
> container:start
```
First time prepare for that the maven downloads can take up to 10-15 minutes.

Now open the site's [root page](http://localhost:8080/) in your browser.

## Running all tests

```sh
 ./sbt test container:start page:test container:stop
```
### Running unit specs only

``` 
./sbt test
```
### Running PageTests only

``` 
./sbt container:start page:test container:stop
```

## Installation for development ##

[Install Scalatra](http://www.scalatra.org/getting-started/installation.html)


That's it :) 
