import type {ColumnDef} from '@tanstack/vue-table';
import {h} from 'vue';

import {priorities, statuses} from './data/data';
import type {Subscriber, SubscriberStatus} from '@lyra/domain';
import DataTableColumnHeader from './DataTableColumnHeader.vue';
import DataTableRowActions from './DataTableRowActions.vue';
import {Checkbox} from '@/components/ui/checkbox';
import {Badge} from '@/components/ui/badge';
import {NuxtLink} from '#components';
import {formatDate} from '@lyra/utilities';

export const columns: ColumnDef<Subscriber>[] = [
	{
		id: 'select',
		header: ({ table }) =>
			h(Checkbox, {
				checked:
					table.getIsAllPageRowsSelected() ||
					(table.getIsSomePageRowsSelected() && 'indeterminate'),
				'onUpdate:checked': (value) => table.toggleAllPageRowsSelected(!!value),
				ariaLabel: 'Select all',
				class: 'translate-y-0.5',
			}),
		cell: ({ row }) =>
			h(Checkbox, {
				checked: row.getIsSelected(),
				'onUpdate:checked': (value) => row.toggleSelected(!!value),
				ariaLabel: 'Select row',
				class: 'translate-y-0.5',
			}),
		enableSorting: false,
		enableHiding: false,
	},
	{
		accessorKey: 'email',
		header: ({ column }) => h(DataTableColumnHeader, { column, title: 'Email' }),

		cell: ({ row }) => {
			const tags = Array.isArray(row.original.attributes?.tags) ? row.original.attributes.tags : [];

			return h('div', { class: 'flex space-x-2 items-center' }, [
				...tags.map((tag: string) => h(Badge, { variant: 'outline' }, () => tag)),
				h(
					NuxtLink,
					{
						to: `/subscribers/${row.original.id}`,
						class: 'max-w-1/2 truncate font-medium hover:underline text-primary',
					},
					row.getValue('email'),
				),
			]);
		},
	},
	{
		accessorKey: 'status',
		header: ({ column }) => h(DataTableColumnHeader, { column, title: 'Status' }),

		cell: ({ row }) => {
			const status = statuses.find((status) => status.value === row.getValue('status'));

			if (!status) return null;

			const statusClasses: Record<SubscriberStatus, string> = {
				ENABLED:
					'bg-green-100/80 text-green-800/80 dark:bg-gray-700/80 dark:text-green-400/80  border-green-400/80',
				DISABLED:
					'bg-yellow-100/80 text-yellow-800/80 dark:bg-gray-700/80 dark:text-yellow-400/80  border-yellow-400/80',
				BLOCKLISTED: 'bg-red-100/80 text-red-800/80 dark:bg-gray-700/80 dark:text-red-400/80  border-red-400/80',
			};

			const rowStatus = row.getValue('status') as SubscriberStatus;

			return h(
				Badge,
				{
					variant: 'outline',
					class: `flex w-[130px] items-center justify-between ${statusClasses[rowStatus]}`,
				},
				[
					h('span', status.label),
					status.icon && h(status.icon, { class: 'h-4 w-4 text-muted-foreground' }),
				],
			);
		},
		filterFn: (row, id, value) => {
			return value.includes(row.getValue(id));
		},
	},
	{
		accessorFn: (row) => row.attributes?.priority,
		id: 'priority',
		header: ({ column }) => h(DataTableColumnHeader, { column, title: 'Priority' }),
		cell: ({ row }) => {
			const priority = priorities.find((priority) => priority.value === row.getValue('priority'));

			if (!priority) return null;

			return h('div', { class: 'flex items-center' }, [
				priority.icon && h(priority.icon, { class: 'mr-2 h-4 w-4 text-muted-foreground' }),
				h('span', {}, priority.label),
			]);
		},
		filterFn: (row, id, value) => {
			return value.includes(row.getValue(id));
		},
	},
	{
		accessorKey: 'createdAt',
		header: ({ column }) => h(DataTableColumnHeader, { column, title: 'Subscribed On' }),
		cell: ({ row }) => {
			const createdAt: string = row.getValue('createdAt');
			return h('time', { datetime: createdAt }, formatDate(createdAt));
		},
		filterFn: (row, id, value) => {
			return value.includes(row.getValue(id));
		},
	},
	{
		id: 'actions',
		cell: ({ row }) => h(DataTableRowActions, { row }),
	},
];
