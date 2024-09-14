export default function offsetDate(offsetMonths = 0, date =  new Date()) : string{
	date.setMonth(date.getMonth() + offsetMonths);
	return date.toISOString().split('T')[0];
}
