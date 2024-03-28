/* @unocss-include */

export interface Link {
	icon?: string;
	name: string;
	to?: string;
	permissions?: Array<'A' | 'B' | 'C' | 'D' | 'E'>;
	hidden?: boolean;
	sub?: Link[];

	level?: number;
	status?: boolean;
}

const links = [
	{ icon: 'i-ic-round-dashboard', name: 'Dashboard', to: '/dashboard' },
	{
		icon: 'i-ph:users-thin',
		name: 'Audience',
		sub: [{ icon: 'i-ph:users-three-thin', name: 'Subscribers', to: '/audience/subscribers' }],
	},
] as Link[];

const createLevel = (arr: Link[], level = 1): Link[] => {
	return [...arr].map((item) => {
		if (item.sub) {
			return { ...item, level, sub: createLevel(item.sub, level + 1) };
		}

		return { ...item, level };
	});
};

export default createLevel(links);
