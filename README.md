# CodingBoard #

UnitTests: [![Build Status](https://codingboard.ci.cloudbees.com/job/codingboard-unittests/badge/icon)](https://codingboard.ci.cloudbees.com/job/codingboard-unittests/)

[CodingBoard](http://codingboard.org) is a tiny, super-lightweight application to share code in a syntax highlighted way within a room, on short sessions. 
People join to a board (without registration) and post the newest version of their current practice code which the presenter can show on the screen. This way retrospectives can be more fruitful having the code up on the projected screen. 

## Build & Run ##

```sh
$ cd codingboard
$ ./sbt
> container:start
```
First time prepare for that the maven downloads can take up to 10-15 minutes.

Now open the site's [root page on localhost](http://localhost:8080/) in your browser.

## Running tests

```sh
 ./sbt test container:start page:test container:stop
```
### Running unit specs only

``` 
./sbt test
```
### Running PageTests only

You will need the Chromium driver for this to be in your */usr/bin/chromedriver*!
Download it from [http://code.google.com/p/chromedriver/downloads/list](http://code.google.com/p/chromedriver/downloads/list)

``` 
./sbt container:start page:test container:stop
```

### Code coverage

``` 
./sbt scct:test
```

Results are under target/scala-2.9.2/coverage-report/index.html (doesn't work with Chrome!)


## Installation for development ##

[Install Scalatra](http://www.scalatra.org/getting-started/installation.html)


## Sbt command for refreshing resources when they change (including scala & template files)##

~; copy-resources; aux-compile

That's it :) 
