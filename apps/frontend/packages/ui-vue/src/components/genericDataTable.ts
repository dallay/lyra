import { defineComponent, h, type VNode } from 'vue';
import BaseDataTable from './BaseDataTable.vue';
import type { ColumnInfo, ExtractComponentProps, SortedEvent } from './types';

type NonGenericProps = Omit<
	ExtractComponentProps<typeof BaseDataTable>,
	'columns' | 'items' | 'sorted'
>;

interface GenericProps<TValue> extends NonGenericProps {
	columns: ColumnInfo[];
	items: TValue[];
	sorted: SortedEvent;
}

interface DataTableItemSlot<TValue> {
	item: TValue;
}

export function useGenericDataTable<TValue = unknown>() {
	const wrapper = defineComponent((props: GenericProps<TValue>, { slots }) => {
		// Returning functions in `setup` means this is the render function
		return () => {
			// We pass the slots through
			return h(BaseDataTable, props, slots);
		};
	});

	return wrapper as typeof wrapper & {
		new (): {
			$emit: {
				(e: 'update:modelValue', value: TValue): void;
				(e: 'sorted', value: SortedEvent): void;
			};
			$slots: {
				item: (props: DataTableItemSlot<TValue>) => VNode[];
			};
		};
	};
}
