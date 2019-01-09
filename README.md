## Requirements

-   JDK 8 and above
-   gradle 5 or above

## Assumption
- Batch Writer mode with batch size 1000 which helps improving performance

## Getting Started

### Building

Execute:

gradle clean jar
```

### Running

Execute:

```bash
java -jar test-commitlog.jar (configFile)
```
configFile is replaceable