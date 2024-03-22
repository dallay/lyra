// @ts-nocheck
import { mount } from '@vue/test-utils';
import DatePicker from '../DatePicker.vue';
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
describe('DatePicker.vue', () => {
	it('should emit update:value and change events when a valid date is selected', async () => {
		const wrapper = mount(DatePicker, {
			global: {
				plugins: [localer],
			},
		});
		wrapper.vm.flux.selectDateItem({ date: new Date() });
		await wrapper.vm.$nextTick();
		expect(wrapper.emitted('update:value')).toBeTruthy();
		expect(wrapper.emitted('change')).toBeTruthy();
	});

	it('should not emit update:value and change events when a disabled date is selected', async () => {
		const wrapper = mount(DatePicker, {
			props: {
				disabled: true,
			},
			global: {
				plugins: [localer],
			},
		});
		await wrapper.vm.$nextTick();
		expect(wrapper.emitted('update:value')).toBeFalsy();
		expect(wrapper.emitted('change')).toBeFalsy();
	});

	it('should correctly increment and decrement the current moment', async () => {
		const wrapper = mount(DatePicker, {
			global: {
				plugins: [localer],
			},
		});
		const initialMoment = wrapper.vm.flux.currentMoment.getTime();
		wrapper.vm.flux.increment();
		await wrapper.vm.$nextTick();
		expect(wrapper.vm.flux.currentMoment.getTime()).toBeGreaterThan(initialMoment);
		wrapper.vm.flux.decrement();
		await wrapper.vm.$nextTick();
		expect(wrapper.vm.flux.currentMoment.getTime()).toBe(initialMoment);
	});

	it('should correctly select a year and a month', async () => {
		const wrapper = mount(DatePicker, {
			global: {
				plugins: [localer],
			},
		});
		wrapper.vm.flux.selectYear(2022);
		wrapper.vm.flux.selectMonth(11);
		await wrapper.vm.$nextTick();
		expect(wrapper.vm.flux.year).toBe(2022);
		expect(wrapper.vm.flux.month).toBe(11);
	});

	it('should correctly handle keydown events', async () => {
		const wrapper = mount(DatePicker, {
			global: {
				plugins: [localer],
			},
		});
		const textField = wrapper.findComponent({ ref: 'input' });
		await textField.trigger('keydown', { code: 'Space' });
		expect(wrapper.vm.flux.showDatePicker).toBe(false);
		await textField.trigger('keydown', { code: 'Escape' });
		expect(wrapper.vm.flux.showDatePicker).toBe(false);
	});
});
