import { mount } from '@vue/test-utils';
import Card from '../Card.vue';

describe('Card.vue', () => {
	it('renders header slot content', () => {
		const wrapper = mount(Card, {
			slots: {
				header: 'Card Header',
			},
		});

		expect(wrapper.text()).toContain('Card Header');
	});

	it('renders title slot content', () => {
		const wrapper = mount(Card, {
			slots: {
				title: 'Card Title',
			},
		});

		expect(wrapper.text()).toContain('Card Title');
	});

	it('renders subtitle slot content', () => {
		const wrapper = mount(Card, {
			slots: {
				subtitle: 'Card Subtitle',
			},
		});

		expect(wrapper.text()).toContain('Card Subtitle');
	});

	it('renders default slot content', () => {
		const wrapper = mount(Card, {
			slots: {
				default: 'Card Content',
			},
		});

		expect(wrapper.text()).toContain('Card Content');
	});

	it('renders footer slot content', () => {
		const wrapper = mount(Card, {
			slots: {
				footer: 'Card Footer',
			},
		});

		expect(wrapper.text()).toContain('Card Footer');
	});

	it('does not render empty slots', () => {
		const wrapper = mount(Card, {
			slots: {
				header: '',
				title: '',
				subtitle: '',
				default: '',
				footer: '',
			},
		});

		expect(wrapper.text()).not.toContain('Card Header');
		expect(wrapper.text()).not.toContain('Card Title');
		expect(wrapper.text()).not.toContain('Card Subtitle');
		expect(wrapper.text()).not.toContain('Card Content');
		expect(wrapper.text()).not.toContain('Card Footer');
	});
});
