import { defineComponent, h, VNode } from 'vue';
import BaseDataTable from './BaseDataTable.vue';
import { ExtractComponentProps } from './types';

type NonGenericProps = Omit<ExtractComponentProps<typeof BaseDataTable>, 'columns' | 'items'>;

interface GenericProps<TValue> extends NonGenericProps {
	modelValue: TValue;
	options: TValue[];
}

interface ItemSlot<TValue> {
	option: TValue;
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
			};
			$slots: {
				item: (props: ItemSlot<TValue>) => VNode[];
			};
		};
	};
}
