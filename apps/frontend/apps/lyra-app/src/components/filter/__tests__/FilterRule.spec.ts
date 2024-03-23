import { mount, VueWrapper } from '@vue/test-utils';
import FilterRule from '@/components/filter/FilterRule.vue';
import { dateOperators, FilterOperator, textOperators } from '@/components/filter/FilterOperator';
import { createLocaler } from 'vue-localer';
import { ColumnProperty } from '@/components/filter/Filter';

const localer = createLocaler({
	fallbackLocale: 'en-US',
	messages: {
		'en-US': {
			email: `This must be a valid email`,
			minLength: `This must be at least {0} characters`,
			minValue: `This must be greater than {0}`,
			required: `This is a required field`,
		},
	},
});
const columnProperty: ColumnProperty = {
	icon: 'i-ic-round-dashboard',
	name: 'Name',
	key: 'name',
	operator: FilterOperator.EQUAL,
	value: 'John Doe',
	availableOperators: textOperators,
	inputType: 'text',
};

let wrapper: VueWrapper;

afterEach(() => {
	wrapper.unmount();
});
describe('FilterRule.vue', () => {
	it('should emit removeFilterProperty event when remove icon is clicked', async () => {
		wrapper = mount(FilterRule, {
			props: {
				modelValue: columnProperty,
				'onUpdate:modelValue': (event: ColumnProperty) => wrapper.setProps({ modelValue: event }),
			},
			global: {
				plugins: [localer],
			},
		});

		await wrapper.find('[data-testid="clear-filter"]').trigger('click');

		const removeFilterPropertyEvent = wrapper.emitted('removeFilterProperty');
		expect(removeFilterPropertyEvent).toBeTruthy();
		if (removeFilterPropertyEvent) {
			expect(removeFilterPropertyEvent).toHaveLength(1);
			expect(removeFilterPropertyEvent[0]).toEqual([columnProperty]);
		}
	});

	it('should emit applyFilter event when Apply button is clicked and form is valid', async () => {
		wrapper = mount(FilterRule, {
			props: {
				modelValue: columnProperty,
				'onUpdate:modelValue': (event: ColumnProperty) => wrapper.setProps({ modelValue: event }),
			},
			global: {
				plugins: [localer],
			},
		});
		await wrapper.find(`[data-testid="filter-${columnProperty.key}"]`).trigger('click');
		await wrapper.find('[data-testid="apply-filter"]').trigger('click');

		expect(wrapper.emitted().applyFilter).toBeTruthy();
	});

	it('should emit removeFilterProperty event when remove icon is clicked when the popover is open', async () => {
		wrapper = mount(FilterRule, {
			props: {
				modelValue: columnProperty,
				'onUpdate:modelValue': (event: ColumnProperty) => wrapper.setProps({ modelValue: event }),
			},
			global: {
				plugins: [localer],
			},
		});
		await wrapper.find(`[data-testid="filter-${columnProperty.key}"]`).trigger('click');
		await wrapper.find('[data-testid="close-filter"]').trigger('click');
		const removeFilterPropertyEvent = wrapper.emitted('removeFilterProperty');
		expect(removeFilterPropertyEvent).toBeTruthy();
		if (removeFilterPropertyEvent) {
			expect(removeFilterPropertyEvent).toHaveLength(1);
			expect(removeFilterPropertyEvent[0]).toEqual([columnProperty]);
		}
	});

	it('should not emit applyFilter event when Apply button is clicked and form is invalid', async () => {
		const emailColumnProperty: ColumnProperty = {
			...columnProperty,
			name: 'Email',
			key: 'email',
			value: 'john.doe@test.com',
		};
		wrapper = mount(FilterRule, {
			props: {
				modelValue: emailColumnProperty,
				'onUpdate:modelValue': (event: ColumnProperty) => wrapper.setProps({ modelValue: event }),
			},
			global: {
				plugins: [localer],
			},
		});
		await wrapper.find(`[data-testid="filter-${emailColumnProperty.key}"]`).trigger('click');
		await wrapper.find(`[data-testid="filter-text-field"]`).setValue('invalid email');
		await wrapper.find('[data-testid="apply-filter"]').trigger('click');
		const applyFilterEvent = wrapper.emitted('applyFilter');
		expect(applyFilterEvent).toBeTruthy();
		if (applyFilterEvent) {
			expect(applyFilterEvent).toHaveLength(1);
			expect(applyFilterEvent[0]).toEqual([]);
		}
	});

	it('should display date picker when inputType is date', async () => {
		const startDateColumnProperty: ColumnProperty = {
			...columnProperty,
			name: 'Start Date',
			key: 'start_date',
			value: '2021-01-01',
			inputType: 'date',
			availableOperators: dateOperators,
		};
		wrapper = mount(FilterRule, {
			props: {
				modelValue: startDateColumnProperty,
				'onUpdate:modelValue': (event: ColumnProperty) => wrapper.setProps({ modelValue: event }),
			},
			global: {
				plugins: [localer],
			},
		});
		await wrapper.find(`[data-testid="filter-${startDateColumnProperty.key}"]`).trigger('click');
		expect(wrapper.find('[data-testid="filter-date-picker"]').exists()).toBe(true);
	});

	it('should display text field when inputType is text', async () => {
		wrapper = mount(FilterRule, {
			props: {
				modelValue: columnProperty,
				'onUpdate:modelValue': (event: ColumnProperty) => wrapper.setProps({ modelValue: event }),
			},
			global: {
				plugins: [localer],
			},
		});
		await wrapper.find(`[data-testid="filter-${columnProperty.key}"]`).trigger('click');
		expect(wrapper.find('[data-testid="filter-text-field"]').exists()).toBe(true);
	});

	it('should display select field when inputType is select', async () => {
		const selectColumnProperty: ColumnProperty = {
			...columnProperty,
			name: 'Status',
			key: 'status',
			value: 'active',
			inputType: 'select',
			availableOperators: textOperators,
			options: [
				{ label: 'Active', value: 'active' },
				{ label: 'Inactive', value: 'inactive' },
			],
		};
		wrapper = mount(FilterRule, {
			props: {
				modelValue: selectColumnProperty,
				'onUpdate:modelValue': (event: ColumnProperty) => wrapper.setProps({ modelValue: event }),
			},
			global: {
				plugins: [localer],
			},
		});
		await wrapper.find(`[data-testid="filter-${selectColumnProperty.key}"]`).trigger('click');
		expect(wrapper.find('[data-testid="filter-select"]').exists()).toBe(true);
	});
});
