import { mount, type VueWrapper } from '@vue/test-utils';
import FilterRangeDate from '@/components/filter/FilterRangeDate.vue';
import { format, subDays, startOfMonth, startOfYear, subMonths, subYears } from 'date-fns';
import localer from '@/plugins/localer';

let wrapper: VueWrapper;

beforeEach(() => {
	wrapper = mount(FilterRangeDate, {
		global: {
			plugins: [localer],
		},
	});
});

afterEach(() => {
	wrapper.unmount();
});
describe('FilterRangeDate.vue', () => {
	it('sets historical date range to 1 year on mount', async () => {
		const today = new Date();
		const expectedStartDate = format(subYears(today, 1), 'yyyy/MM/dd');
		const expectedEndDate = format(subDays(today, 1), 'yyyy/MM/dd');
		// @ts-ignore
		expect(wrapper.vm.startDate).toBe(expectedStartDate);
		// @ts-ignore
		expect(wrapper.vm.endDate).toBe(expectedEndDate);
	});

	it('updates date range to 7 days when 7D button is clicked', async () => {
		const historicalDOMWrapper = wrapper.find('[data-testid="historical-date-range-picker"]');
		await historicalDOMWrapper.trigger('focus');
		await historicalDOMWrapper.trigger('click');
		const historicalInput = historicalDOMWrapper.find(
			'[data-testid="historical-date-range-picker"]'
		);
		await historicalInput.trigger('focus');
		await historicalInput.trigger('click');
		await wrapper.find('[data-testid="7D"]').trigger('click');

		const today = new Date();
		const expectedStartDate = format(subDays(today, 7), 'yyyy/MM/dd');
		const expectedEndDate = format(subDays(today, 1), 'yyyy/MM/dd');

		// @ts-ignore
		expect(wrapper.vm.startDate).toBe(expectedStartDate);
		// @ts-ignore
		expect(wrapper.vm.endDate).toBe(expectedEndDate);
	});

	it('updates date range to start of month when MTD button is clicked', async () => {
		const historicalDOMWrapper = wrapper.find('[data-testid="historical-date-range-picker"]');
		await historicalDOMWrapper.trigger('focus');
		await historicalDOMWrapper.trigger('click');
		const historicalInput = historicalDOMWrapper.find(
			'[data-testid="historical-date-range-picker"]'
		);
		await historicalInput.trigger('focus');
		await historicalInput.trigger('click');
		await wrapper.find('[data-testid="MTD"]').trigger('click');

		const today = new Date();
		const expectedStartDate = format(startOfMonth(today), 'yyyy/MM/dd');
		const expectedEndDate = format(subDays(today, 1), 'yyyy/MM/dd');

		// @ts-ignore
		expect(wrapper.vm.startDate).toBe(expectedStartDate);
		// @ts-ignore
		expect(wrapper.vm.endDate).toBe(expectedEndDate);
	});

	it('updates date range to 14 days when 14D button is clicked', async () => {
		const historicalDOMWrapper = wrapper.find('[data-testid="historical-date-range-picker"]');
		await historicalDOMWrapper.trigger('focus');
		await historicalDOMWrapper.trigger('click');
		const historicalInput = historicalDOMWrapper.find(
			'[data-testid="historical-date-range-picker"]'
		);
		await historicalInput.trigger('focus');
		await historicalInput.trigger('click');
		await wrapper.find('[data-testid="14D"]').trigger('click');

		const today = new Date();
		const expectedStartDate = format(subDays(today, 14), 'yyyy/MM/dd');
		const expectedEndDate = format(subDays(today, 1), 'yyyy/MM/dd');

		// @ts-ignore
		expect(wrapper.vm.startDate).toBe(expectedStartDate);
		// @ts-ignore
		expect(wrapper.vm.endDate).toBe(expectedEndDate);
	});

	it('updates date range to 1 month when 1M button is clicked', async () => {
		const historicalDOMWrapper = wrapper.find('[data-testid="historical-date-range-picker"]');
		await historicalDOMWrapper.trigger('focus');
		await historicalDOMWrapper.trigger('click');
		const historicalInput = historicalDOMWrapper.find(
			'[data-testid="historical-date-range-picker"]'
		);
		await historicalInput.trigger('focus');
		await historicalInput.trigger('click');
		await wrapper.find('[data-testid="1M"]').trigger('click');

		const today = new Date();
		const expectedStartDate = format(subMonths(today, 1), 'yyyy/MM/dd');
		const expectedEndDate = format(subDays(today, 1), 'yyyy/MM/dd');

		// @ts-ignore
		expect(wrapper.vm.startDate).toBe(expectedStartDate);
		// @ts-ignore
		expect(wrapper.vm.endDate).toBe(expectedEndDate);
	});

	it('updates date range to 3 months when 3M button is clicked', async () => {
		const historicalDOMWrapper = wrapper.find('[data-testid="historical-date-range-picker"]');
		await historicalDOMWrapper.trigger('focus');
		await historicalDOMWrapper.trigger('click');
		const historicalInput = historicalDOMWrapper.find(
			'[data-testid="historical-date-range-picker"]'
		);
		await historicalInput.trigger('focus');
		await historicalInput.trigger('click');
		await wrapper.find('[data-testid="3M"]').trigger('click');

		const today = new Date();
		const expectedStartDate = format(subMonths(today, 3), 'yyyy/MM/dd');
		const expectedEndDate = format(subDays(today, 1), 'yyyy/MM/dd');

		// @ts-ignore
		expect(wrapper.vm.startDate).toBe(expectedStartDate);
		// @ts-ignore
		expect(wrapper.vm.endDate).toBe(expectedEndDate);
	});

	it('updates date range to 6 months when 6M button is clicked', async () => {
		const historicalDOMWrapper = wrapper.find('[data-testid="historical-date-range-picker"]');
		await historicalDOMWrapper.trigger('focus');
		await historicalDOMWrapper.trigger('click');
		const historicalInput = historicalDOMWrapper.find(
			'[data-testid="historical-date-range-picker"]'
		);
		await historicalInput.trigger('focus');
		await historicalInput.trigger('click');
		await wrapper.find('[data-testid="6M"]').trigger('click');

		const today = new Date();
		const expectedStartDate = format(subMonths(today, 6), 'yyyy/MM/dd');
		const expectedEndDate = format(subDays(today, 1), 'yyyy/MM/dd');

		// @ts-ignore
		expect(wrapper.vm.startDate).toBe(expectedStartDate);
		// @ts-ignore
		expect(wrapper.vm.endDate).toBe(expectedEndDate);
	});

	it('updates date range to start of year when YTD button is clicked', async () => {
		const historicalDOMWrapper = wrapper.find('[data-testid="historical-date-range-picker"]');
		await historicalDOMWrapper.trigger('focus');
		await historicalDOMWrapper.trigger('click');
		const historicalInput = historicalDOMWrapper.find(
			'[data-testid="historical-date-range-picker"]'
		);
		await historicalInput.trigger('focus');
		await historicalInput.trigger('click');
		await wrapper.find('[data-testid="YTD"]').trigger('click');

		const today = new Date();
		const expectedStartDate = format(startOfYear(today), 'yyyy/MM/dd');
		const expectedEndDate = format(subDays(today, 1), 'yyyy/MM/dd');

		// @ts-ignore
		expect(wrapper.vm.startDate).toBe(expectedStartDate);
		// @ts-ignore
		expect(wrapper.vm.endDate).toBe(expectedEndDate);
	});

	it('updates date range to 1 year when 1Y button is clicked', async () => {
		const historicalDOMWrapper = wrapper.find('[data-testid="historical-date-range-picker"]');
		await historicalDOMWrapper.trigger('focus');
		await historicalDOMWrapper.trigger('click');
		const historicalInput = historicalDOMWrapper.find(
			'[data-testid="historical-date-range-picker"]'
		);
		await historicalInput.trigger('focus');
		await historicalInput.trigger('click');
		await wrapper.find('[data-testid="1Y"]').trigger('click');

		const today = new Date();
		const expectedStartDate = format(subYears(today, 1), 'yyyy/MM/dd');
		const expectedEndDate = format(subDays(today, 1), 'yyyy/MM/dd');

		// @ts-ignore
		expect(wrapper.vm.startDate).toBe(expectedStartDate);
		// @ts-ignore
		expect(wrapper.vm.endDate).toBe(expectedEndDate);
	});
});
