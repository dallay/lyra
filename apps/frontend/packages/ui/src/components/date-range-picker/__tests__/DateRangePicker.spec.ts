import { mount, type VueWrapper } from '@vue/test-utils';
import DateRangePicker from '../DateRangePicker.vue';
import { createLocaler } from 'vue-localer';

const localer = createLocaler({
	fallbackLocale: 'en-US',
	messages: {
		'en-US': {
			locale: {
				months: [
					'January',
					'February',
					'March',
					'April',
					'May',
					'June',
					'July',
					'August',
					'September',
					'October',
					'November',
					'December',
				],
				weekdays: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
			},
		},
	},
});
let wrapper: VueWrapper;
beforeEach(() => {
	wrapper = mount(DateRangePicker, {
		global: {
			plugins: [localer],
		},
	});
});

afterEach(() => {
	wrapper.unmount();
});

function mountWrapperComponent(
	startDate: Date = new Date(2022, 0, 1),
	endDate: Date = new Date(2022, 0, 31)
): VueWrapper {
	return mount(DateRangePicker, {
		props: {
			startDate,
			endDate,
		},
		global: {
			plugins: [localer],
		},
	});
}

describe('DateRangePicker.vue', () => {
	it('opens date picker on input focus', async () => {
		await wrapper.find('[data-testid="date-range-picker-input"]').trigger('focus');
		await wrapper.find('[data-testid="date-range-picker-input"]').trigger('click');
		// @ts-ignore
		expect(wrapper.vm.flux.showDatePicker).toBe(true);
	});

	it('closes date picker on outside click', async () => {
		await wrapper.find('[data-testid="date-range-picker-input"]').trigger('focus');
		await wrapper.find('[data-testid="date-range-picker-input"]').trigger('click');
		// add a fake div to simulate an outside click event
		const fakeDiv = document.createElement('div');
		fakeDiv.classList.add('fake-div');
		document.body.appendChild(fakeDiv);
		fakeDiv.click();
		// @ts-ignore
		expect(wrapper.vm.flux.showDatePicker).toBe(false);
	});

	it('sets start and end date on date item click', async () => {
		await wrapper.find('[data-testid="date-range-picker-input"]').trigger('focus');
		await wrapper.find('[data-testid="date-range-picker-input"]').trigger('click');
		const today = new Date();
		const date = today.getDate();
		await wrapper.find(`[data-testid="date-${date}"]`).trigger('click');
		// @ts-ignore
		expect(wrapper.vm.startValueModel).toBeDefined();
		// @ts-ignore
		expect(wrapper.vm.endValueModel).toBeDefined();
	});

	it('increments month when increment button is clicked', async () => {
		const startDate = new Date(2021, 0, 1);
		const endDate = new Date(2021, 0, 31);
		wrapper = mountWrapperComponent(startDate, endDate);

		await wrapper.find('[data-testid="date-range-picker-input"]').trigger('focus');
		await wrapper.find('[data-testid="date-range-picker-input"]').trigger('click');
		// @ts-ignore
		const initialMonth = wrapper.vm.flux.currentMoment.getMonth();

		await wrapper.vm.$nextTick();
		await wrapper.find('[data-testid="forward-date-button"]').trigger('click');
		await wrapper.find('[data-testid="date-5"]').trigger('click');
		// @ts-ignore
		expect(wrapper.vm.flux.currentMoment.getMonth()).toBe(initialMonth + 1);
	});

	it('decrements month when decrement button is clicked', async () => {
		wrapper = mountWrapperComponent();

		await wrapper.find('[data-testid="date-range-picker-input"]').trigger('focus');
		await wrapper.find('[data-testid="date-range-picker-input"]').trigger('click');
		// @ts-ignore
		const initialMonth = wrapper.vm.flux.currentMoment.getMonth();

		await wrapper.vm.$nextTick();
		await wrapper.find('[data-testid="backward-date-button"]').trigger('click');
		await wrapper.find('[data-testid="date-5"]').trigger('click');
		// @ts-ignore
		expect(wrapper.vm.flux.currentMoment.getMonth()).toBe(initialMonth - 1);
	});

	it('sets start and end date on date item click', async () => {
		await wrapper.find('[data-testid="date-range-picker-input"]').trigger('focus');
		await wrapper.find('[data-testid="date-range-picker-input"]').trigger('click');

		await wrapper.find(`[data-testid="date-1"]`).trigger('click');
		await wrapper.find(`[data-testid="date-5"]`).trigger('click');
		// add a fake div to simulate an outside click event
		const fakeDiv = document.createElement('div');
		fakeDiv.classList.add('fake-div');
		document.body.appendChild(fakeDiv);
		fakeDiv.click();
		// @ts-ignore
		expect(wrapper.vm.startValueModel).toBeDefined();
		// @ts-ignore
		expect(wrapper.vm.endValueModel).toBeDefined();
		const updateStartValueEvent = wrapper.emitted('update:startValue');
		expect(updateStartValueEvent).toBeTruthy();
		const today = new Date();
		if (updateStartValueEvent) {
			expect(updateStartValueEvent).toHaveLength(2);
			expect(updateStartValueEvent[1][0]).toBe(
				`${today.getFullYear()}/0${today.getMonth() + 1}/01`
			);
		}
		const updateEndValueEvent = wrapper.emitted('update:endValue');
		expect(updateEndValueEvent).toBeTruthy();
		if (updateEndValueEvent) {
			expect(updateEndValueEvent).toHaveLength(2);
			expect(updateEndValueEvent[1][0]).toBe(`${today.getFullYear()}/0${today.getMonth() + 1}/05`);
		}
	});
});
