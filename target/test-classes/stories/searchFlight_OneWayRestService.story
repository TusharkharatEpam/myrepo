Scenario: Search Flights - One Way - Rest service

Given user open the cleartrip portal
When user hit the rest service with parameters trip_type source destination  depart_date  adults children infants origin from to class ver type
Then validate the response




Examples:
|trip_type	|source |destination                                    |depart_date       |adults |children|infants|origin								   |from|to |class  |ver|type|
|ONEWAY 	|Pune	|Mumbai IN Chatrapati Shivaji Airport (BOM)		|25/10/2018		   |1	   |1       |1      |Pune%2C+IN+-+Lohegaon+(PNQ)     	   |PNQ |BOM|Economy|V2 |JSON|