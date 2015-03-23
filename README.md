# simplegraphiteclient
A simple java client for sending metrics to graphite. 

## Usage: 

```Java
SimpleGraphiteClient graphiteClient = new SimpleGraphiteClient("my_graphite_host", 2003);

// send single value with current timestamp
graphiteClient.sendMetric("universal.answer", 42);

// send single value with custom timestamp
graphiteClient.sendMetric("universal.answer", 42, 1360848777l);

// send multiple values
Map<String, Integer> allAnswers = new HashMap<String, Integer>() {{
	put("where.is.my.towel", 42);
	put("where.are.the.dolphins", 42);
}};
graphiteClient.sendMetrics(allAnswers);

````

## Compile

```
mvn package
```

## Install

```
mvn install:install-file -Dfile=./target/simplegraphiteclient-1.0.0.jar -DgroupId=com.zanox.lib.simplegraphiteclient -DartifactId=simplegraphiteclient -Dversion=1.0.0 -Dpackaging=jar
```


## Download

The client jar is distributed via maven central.
```xml
<dependency>
	<groupId>com.zanox.lib.simplegraphiteclient</groupId>
	<artifactId>simplegraphiteclient</artifactId>
    <version>1.0.0</version>
</dependency>
```

