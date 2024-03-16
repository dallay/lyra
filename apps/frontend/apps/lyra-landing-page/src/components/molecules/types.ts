export interface Project {
	id: string;
	name: string;
	icon: string;
}

export interface Menu {
	id: string;
	title: string;
	link: string;
}

export const navMenus: Menu[] = [
	{
		id: 'about',
		title: 'About',
		link: '/about',
	},
	{
		id: 'documentation',
		title: 'Documentation',
		link: '/documentation',
	},
	{
		id: 'download',
		title: 'Download',
		link: '/download',
	},
	{
		id: 'app',
		title: 'App',
		link: '/app',
	},
];

export interface BreadcrumbItem {
	label: string;
	href: string;
	icon?: string;
}
