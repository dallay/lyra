import { Editor, Extension } from '@tiptap/core';
import { Node } from '@tiptap/pm/model';
import { DragHandlePlugin } from '@tiptap-pro/extension-drag-handle';
import tippy from 'tippy.js';
import { twMerge } from 'tailwind-merge';
import { useData } from './useData';

const button = twMerge(
	'bg-white dark:bg-neutral-950 text-black dark:text-white',
	'hover:bg-neutral-100 dark:hover:bg-neutral-900',
	'rounded',
	'h-8',
	'px-[3px]',
	'w-fit',
	'flex',
	'justify-center',
	'items-center',
);
const container = twMerge(
	'bg-neutral-100 dark:bg-neutral-900 text-black dark:text-white',
	'hover:bg-neutral-200 dark:hover:bg-neutral-800',
	'rounded',
	'shadow-lg',
	'dark:shadow-none',
	'p-2',
	'flex',
	'gap-x-1',
	'items-center',
);

const data = useData();

export const DragHandle = Extension.create({
	addOptions() {
		return {
			render: (editor: Editor) => {
				const div = document.createElement('div');
				const plus = document.createElement('button');
				const drag = document.createElement('button');
				const plusContainer = document.createElement('div');
				const dragContainer = document.createElement('div');
				const plusP = document.createElement('p');

				div.classList.add('flex', 'gap-x-1', 'h-10', 'items-center');
				plus.classList.add(...button.split(' '));
				drag.classList.add(...button.split(' '));
				plusContainer.classList.add(...container.split(' '));
				dragContainer.classList.add(...container.split(' '));
				plusP.classList.add('text-xs', 'font-semibold', 'p-0', 'm-0');

				plus.innerHTML = `<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="size-4"><path d="M8.75 3.75a.75.75 0 0 0-1.5 0v3.5h-3.5a.75.75 0 0 0 0 1.5h3.5v3.5a.75.75 0 0 0 1.5 0v-3.5h3.5a.75.75 0 0 0 0-1.5h-3.5v-3.5Z" /></svg>`;
				drag.innerHTML = `<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-4"><g fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"><circle cx="9" cy="12" r="1"/><circle cx="9" cy="5" r="1"/><circle cx="9" cy="19" r="1"/><circle cx="15" cy="12" r="1"/><circle cx="15" cy="5" r="1"/><circle cx="15" cy="19" r="1"/></g></svg>`;
				plusP.innerText = 'Add a new block';
				plusContainer.appendChild(plusP);

				const plusTippy = tippy(plus, {
				  content: plusContainer,
				  placement: 'top',
				  allowHTML: true,
				});
				const dragTippy = tippy(drag, {
				  content: dragContainer,
				  placement: 'right-start',
				  trigger: 'click',
				  allowHTML: true,
				});

				div.appendChild(plus);
				div.appendChild(drag);

        plus.addEventListener('click', () => {
          const { $from } = editor.state.selection;
          const pos = $from.after();
          editor.chain().focus().insertContentAt(pos, { type: 'paragraph', content: [{ type: 'text', text: '/' }] }).run();
        });

				return div;
			},
			onNodeChange: ({ node, editor }: { node: Node | null; editor: Editor }) => {
				data.handleNodeChange({ node, pos: editor.state.selection.from });
			},
		};
	},
	addCommands() {
		return {
			...this.parent?.(),
		};
	},
	addProseMirrorPlugins() {
		const tippy = this.options.render(this.editor);
		return [
			DragHandlePlugin({
				tippyOptions: {
          placement: "left",
          duration: [100, 2000],
          interactive: true,
          ...this.options.tippyOptions
        },
				element: tippy,
				editor: this.editor,
				onNodeChange: this.options.onNodeChange,
			}),
		];
	},
});

export default DragHandle;
