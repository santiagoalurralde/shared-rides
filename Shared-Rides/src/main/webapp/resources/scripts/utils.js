function getDayLabel(index)
{
	//	Returns the day's Label according to the index.
	
	var label;

	switch (index)
	{	
		case 1:
			label = $('#lblMonday').val();
			break;
		case 2:
			label = $('#lblTuesday').val();
			break;
		case 3:
			label = $('#lblWednesday').val();
			break;
		case 4:
			label = $('#lblThursday').val();		
			break;					
		case 5:
			label = $('#lblFriday').val();		
			break;
	}	
	return label;
}