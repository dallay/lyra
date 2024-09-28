<script setup lang="ts">
import { watch, ref, type Ref, defineProps, defineEmits, type HTMLAttributes } from 'vue';
import { Calendar as CalendarIcon } from 'lucide-vue-next';
import type { DateRange } from 'radix-vue';
import {
  CalendarDateTime,
  DateFormatter,
  getLocalTimeZone,
  parseDateTime,
  type DateValue,
} from '@internationalized/date';
import { cn } from '@/lib/utils';
import { Button } from '@/components/ui/button';
import { RangeCalendar } from '@/components/ui/range-calendar';
import { Popover, PopoverContent, PopoverTrigger } from '@/components/ui/popover';

const df = new DateFormatter('en-US', { dateStyle: 'medium' });

interface DateRangePickerProps {
  startDate: string;
  endDate: string;
}

const props = defineProps<
  DateRangePickerProps & { class?: HTMLAttributes['class']; disabled?: boolean }
>();
const emit = defineEmits(['update:startDate', 'update:endDate', 'update:dateRange']);

const calendarStartDate: CalendarDateTime = parseDateTime(props.startDate);
const calendarEndDate: CalendarDateTime = parseDateTime(props.endDate);

const value = ref({
  start: calendarStartDate,
  end: calendarEndDate,
}) as Ref<DateRange>;

const updateStartValue = (startDate: DateValue | undefined) => {
  if (startDate) {
    emit('update:startDate', startDate.toString());
  }
};

watch(value, (newValue) => {
  if (newValue.start && newValue.end) {
    emit('update:dateRange', {
      start: newValue.start.toString(),
      end: newValue.end.toString(),
    });
  }
});
</script>

<template>
  <div :class="cn('grid gap-2', props.class ?? '')">
    <Popover>
      <PopoverTrigger as-child>
        <Button
          id="date"
          :variant="'outline'"
          :class="cn(
            'w-[300px] justify-start text-left font-normal',
            !value && 'text-muted-foreground',
          )"
          :disabled="props.disabled"
        >
          <CalendarIcon class="mr-2 h-4 w-4"/>

          <template v-if="value.start">
            <template v-if="value.end">
              {{ df.format(value.start.toDate(getLocalTimeZone())) }} -
              {{ df.format(value.end.toDate(getLocalTimeZone())) }}
            </template>

            <template v-else>
              {{ df.format(value.start.toDate(getLocalTimeZone())) }}
            </template>
          </template>
          <template v-else>
            Pick a date
          </template>
        </Button>
      </PopoverTrigger>
      <PopoverContent class="w-auto p-0" align="end">
        <RangeCalendar
          v-model="value"
          weekday-format="short"
          :number-of-months="2"
          initial-focus
          :placeholder="value.start"
          @update:start-value="updateStartValue"
          :disabled="props.disabled"
        />
      </PopoverContent>
    </Popover>
  </div>
</template>
