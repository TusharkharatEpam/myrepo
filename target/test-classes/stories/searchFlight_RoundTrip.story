Scenario: Search Flights - One Way

Given user open the cleartrip portal
And user search for tripType flights
When user enter flight source and destination
And enters the departDate and returnDate for tripType
And enters details of adults children And infants
And search for flights
Then user should see all flights based on deftails provided



Examples:
|tripType	|source |destination|departDate|adults |children|infants|returnDate|
|ROUNDTRIP 	|Pune	|Mumbai		|NA		   |1	   |1       |1      |NA        |