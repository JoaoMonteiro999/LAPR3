#include <stdio.h>
#include "asm.h"

int main(void)
{

	char x[] = "sensor_id:8#type:atmospheric_temperature#value:21.60#unit:celsius#time:2470030";

	char y[] = "value:";
	//char y[] = "time";
	//char y[] = "time";
	
	int z = 0;
	
	extract_token(x,y,&z);
	
	printf("%d\n", z);
	
	return 0;
}
