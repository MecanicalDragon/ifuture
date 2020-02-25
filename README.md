### Account Service Infrastructure

#### AccountService
Service is meant to manage amount values for different id values.<br/>
Swagger UI is accessible by `<host>:<port>/openapi` url<br/>
Service can send statistics to Atlas Metrics server or to custom statistics aggregation application.<br/>
Define it, specifying **account.service.metrics.mode** parameter in properties file:

    - 'atlas' for Atlas usage
    - 'custom' for Statistics app usage
    
Atlas server can be downloaded here: <br/>
https://github.com/Netflix/atlas/releases/download/v1.6.4/atlas-1.6.4-standalone.jar<br/>
By default server picks port 7101, but you can override it using this documentation:<br/>
https://github.com/Netflix/atlas/wiki<br/>
You can change Atlas accessing url for AccountService application in properties file, modifying corresponding property.<br/>
Also you can specify frequency of data collecting in seconds, but not less than 10.<br/>
If you've picked custom statistics service, look next chapter. 


#### StatisticsService
Meant to collect statistic of AccountService requests. By default uses 8081 port. Does scheduled job of collecting 
statistics from AccountService. AccountService root url and cron expression can be denoted in properties.
App shows collected statistics by GET `<host>:<port>/<amount>` url, where 'amount' - quantity of last added entries.<br/>
Statistic can be evicted hitting with POST or DELETE to `<host>:<port>/reset` url or with a button at the bottom of webpage. Server restart does not evict the statistic.

#### TestClient
Just launch it, specifying required parameters in properties file or in command line arguments. Command line arguments have precedence.<br/>

    - 'rCount' - amount of sending 'getAmount'-requests threads
    - 'wCount' - amount of sending 'addAmount'-requests threads
    - 'idList' - list of ids. Specify it, separating digits with commas or hyphens for denoting exact values or ranges respectively.
    - 'account.service.url' - root url of AccountService.
    
By default TestClient will be launched on port 8082. Hit */potDoNotCook* url with any method to stop application.