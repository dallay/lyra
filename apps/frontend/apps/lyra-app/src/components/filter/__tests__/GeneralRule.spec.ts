import { mount, VueWrapper } from '@vue/test-utils';
import GeneralRule from '../GeneralRule.vue';
import { ColumnProperty } from '../Filter';
import { FilterOperator, numberOperators, textOperators } from '@/components/filter/FilterOperator';
import localer from '@/plugins/localer';

let wrapper: VueWrapper;
const columns: ColumnProperty[] = [
	{
		key: 'name',
		name: 'Name',
		icon: 'i-material-symbols:drive-file-rename-outline',
		value: '',
		availableOperators: textOperators,
		inputType: 'text',
		operator: FilterOperator.EQUAL,
	},
	{
		key: 'age',
		name: 'Age',
		icon: 'i-material-symbols:calendar-month',
		value: 0,
		operator: FilterOperator.EQUAL,
		inputType: 'number',
		availableOperators: numberOperators,
	},
];

beforeEach(() => {
	wrapper = mount(GeneralRule, {
		props: {
			columns,
		},
		global: {
			plugins: [localer],
		},
	});
});

afterEach(() => {
	wrapper.unmount();
});

async function testAddFilterProperty() {
	await wrapper.find('[data-testid="add-filter"]').trigger('click');

	await wrapper.find('[data-testid="filter-name"]').trigger('click');

	const addFilterPropertyEvent = wrapper.emitted('addFilterProperty');
	expect(addFilterPropertyEvent).toBeTruthy();
	if (addFilterPropertyEvent) {
		expect(addFilterPropertyEvent).toHaveLength(1);
		expect(addFilterPropertyEvent[0][0]).toEqual(columns[0]);
	}
}

describe('GeneralFilter.vue', () => {
	it('adds filter property when addFilterProperty is called', async () => {
		await testAddFilterProperty();
	});

	it('removes filter property when removeFilterProperty is called', async () => {
		await testAddFilterProperty();

		await wrapper.find('[data-testid="clear-filter"]').trigger('click');

		const removeFilterPropertyEvent = wrapper.emitted('removeFilterProperty');
		expect(removeFilterPropertyEvent).toBeTruthy();
		if (removeFilterPropertyEvent) {
			expect(removeFilterPropertyEvent).toHaveLength(1);
			expect(removeFilterPropertyEvent[0][0]).toEqual(columns[0]);
		}
	});

	it('applies query filters when applyQueryFilters is called', async () => {
		await wrapper.find('[data-testid="add-filter"]').trigger('click');

		await wrapper.find('[data-testid="filter-name"]').trigger('click');
		await wrapper.find(`[data-testid="filter-text-field"]`).setValue('John Doe');
		await wrapper.find('[data-testid="apply-filter"]').trigger('click');

		const addFilterPropertyEvent = wrapper.emitted('addFilterProperty');
		expect(addFilterPropertyEvent).toBeTruthy();
		if (addFilterPropertyEvent) {
			expect(addFilterPropertyEvent).toHaveLength(1);
			expect(addFilterPropertyEvent[0][0]).toEqual(columns[0]);
		}

		const applyFiltersEvent = wrapper.emitted('applyFilters');
		expect(applyFiltersEvent).toBeTruthy();
		if (applyFiltersEvent) {
			expect(applyFiltersEvent).toHaveLength(2);
			expect(applyFiltersEvent[0][0]).toEqual('');
			expect(applyFiltersEvent[1][0]).toEqual('name=eq:John Doe');
		}
	});
});
