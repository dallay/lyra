import { DateFormatter } from '@internationalized/date';

export default function formatDate(date: Date | string | undefined): string {
  const userLocale = navigator.language || 'en-US';
  const df = new DateFormatter(userLocale, {
    dateStyle: 'long',
  });
  if (!date) return '';

  const dateToFormat = new Date(date);

  if (isNaN(dateToFormat.getTime())) {
    return '';
  }

  return df.format(dateToFormat);
}
