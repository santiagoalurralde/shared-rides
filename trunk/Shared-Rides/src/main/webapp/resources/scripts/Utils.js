
/**
 * Returns the label defined in createLabels() according to the name passed.
 *
 * @param {String} lblName - name of the label variable that was created by createLabels().
 */
function getLabel(lblName) {
    var labelsObj = createLabels();
    return labelsObj[lblName];
}

/**
 * Returns the day's Label according to the index.
 *
 * @param {number} index - number of day to be calculated.
 */
function getDayLabel(index) {
	var label;

    if (index === 1 || index === '1') {
        label = getLabel("lblMonday");
    }
    else if (index === 2 || index === '2') {
        label = getLabel("lblTuesday");
    }
    else if (index === 3 || index === '3') {
        label = getLabel("lblWednesday");
    }
    else if (index === 4 || index === '4') {
        label = getLabel("lblThursday");
    }
    else if (index === 5 || index === '5') {
        label = getLabel("lblFriday");
    }
	return label;
}