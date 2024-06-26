<script lang="ts" setup>
import type { Extensions } from '@tiptap/vue-3';
import { ref, computed, watch, onMounted } from 'vue';
import { Editor, EditorContent } from '@tiptap/vue-3';
import Blockquote from '@tiptap/extension-blockquote';
import Bold from '@tiptap/extension-bold';
import BulletList from '@tiptap/extension-bullet-list';
import Color from '@tiptap/extension-color';
import Highlight from '@tiptap/extension-highlight';
import Document from '@tiptap/extension-document';
import Dropcursor from '@tiptap/extension-dropcursor';
import Gapcursor from '@tiptap/extension-gapcursor';
import HardBreak from '@tiptap/extension-hard-break';
import Heading from '@tiptap/extension-heading';
import History from '@tiptap/extension-history';
import HorizontalRule from '@tiptap/extension-horizontal-rule';
import Italic from '@tiptap/extension-italic';
import ListItem from '@tiptap/extension-list-item';
import OrderedList from '@tiptap/extension-ordered-list';
import Paragraph from '@tiptap/extension-paragraph';
import Strike from '@tiptap/extension-strike';
import Text from '@tiptap/extension-text';
import TextStyle from '@tiptap/extension-text-style';
import Underline from '@tiptap/extension-underline';
import TextAlign from '@tiptap/extension-text-align';
import Image from '@tiptap/extension-image';
import Link from '@tiptap/extension-link';

import FormControl from '../form-control/FormControl.vue';
import Divider from '../divider/Divider.vue';
import Popover from '../popover/Popover.vue';

const defaultModel = defineModel<string>({ default: '' });

const props = withDefaults(
	defineProps<{
		label?: string;
		extension?: Extensions;
		required?: boolean;
		invalid?: boolean | string;
		help?: string;
		disabled?: boolean;
		viewonly?: boolean;
		class?: string;
	}>(),
	{
		label: '',
		extension: () => [],
		required: false,
		invalid: undefined,
		help: '',
		disabled: false,
		viewonly: false,
		class: '',
	}
);

const editor = ref<Editor>();

const editorClass = computed(() => {
	if (props.viewonly) return `min-h-54.5 ${props.class}`;
	return `border border-slate-400 rounded-b px-3 py-1 min-h-54.5 focus:outline-none focus:ring-2 focus:ring-primary-500/50 focus:border-primary-400 focus:rounded ${props.class}`;
});

const typing = ref(false);

onMounted(() => {
	editor.value = new Editor({
		editable: !props.disabled && !props.viewonly,
		extensions: [
			...props.extension,
			Blockquote,
			Bold,
			BulletList,
			Color,
			Highlight.configure({ multicolor: true }),
			Document,
			Dropcursor,
			Gapcursor,
			HardBreak,
			Heading,
			History,
			HorizontalRule,
			Italic,
			ListItem,
			OrderedList,
			Paragraph,
			Strike,
			Text,
			TextStyle,
			Underline,
			TextAlign.configure({
				types: ['heading', 'paragraph'],
			}),
			Image,
			Link,
		],
		content: defaultModel.value,
		editorProps: {
			attributes: {
				class: editorClass.value,
			},
		},
		onUpdate({ editor }) {
			typing.value = true;
			defaultModel.value = editor.getHTML();
		},
	});
});

watch(
	() => props.disabled,
	(val) => {
		editor.value?.setOptions({ editable: !val && !props.viewonly });
	}
);

function toggleHeading(level: 1 | 2 | 3 | 4) {
	if (props.disabled || props.viewonly) return;
	editor.value?.chain().focus().toggleHeading({ level }).run();
}

function setParagraph() {
	if (props.disabled || props.viewonly) return;
	editor.value?.chain().focus().setParagraph().run();
}

function setColor(color: string) {
	if (props.disabled || props.viewonly) return;
	editor.value?.chain().focus().setColor(color).run();
}

function toggleHighlight(color: string) {
	if (props.disabled || props.viewonly) return;
	editor.value?.chain().focus().toggleHighlight({ color }).run();
}

function toggleBold() {
	if (props.disabled || props.viewonly) return;
	editor.value?.chain().focus().toggleBold().run();
}

function toggleItalic() {
	if (props.disabled || props.viewonly) return;
	editor.value?.chain().focus().toggleItalic().run();
}

function toggleUnderline() {
	if (props.disabled || props.viewonly) return;
	editor.value?.chain().focus().toggleUnderline().run();
}

function toggleStrike() {
	if (props.disabled || props.viewonly) return;
	editor.value?.chain().focus().toggleStrike().run();
}

function toggleBulletList() {
	if (props.disabled || props.viewonly) return;
	editor.value?.chain().focus().toggleBulletList().run();
}

function toggleOrderedList() {
	if (props.disabled || props.viewonly) return;
	editor.value?.chain().focus().toggleOrderedList().run();
}

function setTextAlign(alignment: 'left' | 'center' | 'right' | 'justify') {
	if (props.disabled || props.viewonly) return;
	editor.value?.chain().focus().setTextAlign(alignment).run();
}

function setImage() {
	if (props.disabled || props.viewonly) return;

	const url = window.prompt('URL');

	if (url) {
		editor.value?.chain().focus().setImage({ src: url }).run();
	}
}

function setLink() {
	if (props.disabled || props.viewonly) return;

	const previousUrl = editor.value?.getAttributes('link').href;
	const url = window.prompt('URL', previousUrl);

	if (url === null) return;

	if (url === '') {
		editor.value?.chain().focus().extendMarkRange('link').unsetLink().run();
		return;
	}

	editor.value?.chain().focus().extendMarkRange('link').setLink({ href: url }).run();
}

function toggleBlockquote() {
	if (props.disabled || props.viewonly) return;
	editor.value?.chain().focus().toggleBlockquote().run();
}

function setHorizontalRule() {
	if (props.disabled || props.viewonly) return;
	editor.value?.chain().focus().setHorizontalRule().run();
}

function undo() {
	if (props.disabled || props.viewonly) return;
	editor.value?.chain().focus().undo().run();
}

function redo() {
	if (props.disabled || props.viewonly) return;
	editor.value?.chain().focus().redo().run();
}

const completed = ref(false);

watch(
	() => defaultModel.value,
	(val) => {
		if (props.viewonly) {
			editor.value?.commands?.setContent(val);
		} else if (!completed.value) {
			completed.value = true;
			editor.value?.commands?.setContent(val);
		} else {
			if (!typing.value) editor.value?.commands?.setContent(val);
			typing.value = false;
		}
	}
);

defineExpose({
	editor,
});
</script>

<template>
	<FormControl :label="label" :required="required" :invalid="invalid" :help="help">
		<div v-if="editor" class="w-full" :class="[disabled ? 'cursor-not-allowed opacity-60' : '']">
			<div
				v-if="!viewonly"
				class="flex flex-wrap rounded-t border border-b-0 border-slate-400 px-2 py-1"
			>
				<div class="flex gap-1">
					<div class="i-mdi-format-header-1 h-6 w-6" @click="toggleHeading(1)"></div>
					<div class="i-mdi-format-header-2 h-6 w-6" @click="toggleHeading(2)"></div>
					<div class="i-mdi-format-header-3 h-6 w-6" @click="toggleHeading(3)"></div>
					<div class="i-mdi-format-header-4 h-6 w-6" @click="toggleHeading(4)"></div>
					<div class="i-mdi-format-paragraph h-6 w-6" @click="setParagraph"></div>

					<Popover :disabled="disabled">
						<div class="i-mdi-format-color-text h-6 w-6"></div>

						<template #content>
							<div class="grid grid-cols-5 gap-2 p-4 md:grid-cols-10">
								<div class="Color bg-black" @click="setColor('black')"></div>
								<div class="Color bg-white" @click="setColor('white')"></div>
								<div class="Color bg-gray-500" @click="setColor('#6b7280')"></div>
								<div class="Color bg-red-500" @click="setColor('#ef4444')"></div>
								<div class="Color bg-orange-500" @click="setColor('#f97316')"></div>
								<div class="Color bg-amber-500" @click="setColor('#f59e0b')"></div>
								<div class="Color bg-yellow-500" @click="setColor('#eab308')"></div>
								<div class="Color bg-lime-500" @click="setColor('#84cc16')"></div>
								<div class="Color bg-green-500" @click="setColor('#22c55e')"></div>
								<div class="Color bg-emerald-500" @click="setColor('#10b981')"></div>
								<div class="Color bg-teal-500" @click="setColor('#14b8a6')"></div>
								<div class="Color bg-cyan-500" @click="setColor('#06b6d4')"></div>
								<div class="Color bg-sky-500" @click="setColor('#0ea5e9')"></div>
								<div class="Color bg-blue-500" @click="setColor('#3b82f6')"></div>
								<div class="Color bg-indigo-500" @click="setColor('#6366f1')"></div>
								<div class="Color bg-violet-500" @click="setColor('#8b5cf6')"></div>
								<div class="Color bg-purple-500" @click="setColor('#a855f7')"></div>
								<div class="Color bg-fuchsia-500" @click="setColor('#d946ef')"></div>
								<div class="Color bg-pink-500" @click="setColor('#ec4899')"></div>
								<div class="Color bg-rose-500" @click="setColor('#f43f5e')"></div>
							</div>
						</template>
					</Popover>

					<Popover :disabled="disabled">
						<div class="i-mdi-format-color-fill h-6 w-6"></div>

						<template #content>
							<div class="grid grid-cols-5 gap-2 p-4 md:grid-cols-10">
								<div class="Color bg-black" @click="toggleHighlight('black')"></div>
								<div class="Color bg-white" @click="toggleHighlight('white')"></div>
								<div class="Color bg-gray-500" @click="toggleHighlight('#6b7280')"></div>
								<div class="Color bg-red-500" @click="toggleHighlight('#ef4444')"></div>
								<div class="Color bg-orange-500" @click="toggleHighlight('#f97316')"></div>
								<div class="Color bg-amber-500" @click="toggleHighlight('#f59e0b')"></div>
								<div class="Color bg-yellow-500" @click="toggleHighlight('#eab308')"></div>
								<div class="Color bg-lime-500" @click="toggleHighlight('#84cc16')"></div>
								<div class="Color bg-green-500" @click="toggleHighlight('#22c55e')"></div>
								<div class="Color bg-emerald-500" @click="toggleHighlight('#10b981')"></div>
								<div class="Color bg-teal-500" @click="toggleHighlight('#14b8a6')"></div>
								<div class="Color bg-cyan-500" @click="toggleHighlight('#06b6d4')"></div>
								<div class="Color bg-sky-500" @click="toggleHighlight('#0ea5e9')"></div>
								<div class="Color bg-blue-500" @click="toggleHighlight('#3b82f6')"></div>
								<div class="Color bg-indigo-500" @click="toggleHighlight('#6366f1')"></div>
								<div class="Color bg-violet-500" @click="toggleHighlight('#8b5cf6')"></div>
								<div class="Color bg-purple-500" @click="toggleHighlight('#a855f7')"></div>
								<div class="Color bg-fuchsia-500" @click="toggleHighlight('#d946ef')"></div>
								<div class="Color bg-pink-500" @click="toggleHighlight('#ec4899')"></div>
								<div class="Color bg-rose-500" @click="toggleHighlight('#f43f5e')"></div>
							</div>
						</template>
					</Popover>
				</div>

				<Divider orientation="vertical" class="!mx-2" />

				<div class="flex gap-1">
					<div class="i-mdi-format-bold h-6 w-6" @click="toggleBold"></div>
					<div class="i-mdi-format-italic h-6 w-6" @click="toggleItalic"></div>
					<div class="i-mdi-format-underline h-6 w-6" @click="toggleUnderline"></div>
					<div class="i-mdi-format-strikethrough-variant h-6 w-6" @click="toggleStrike"></div>
				</div>

				<Divider orientation="vertical" class="!mx-2" />

				<div class="flex gap-1">
					<div class="i-mdi-format-list-bulleted h-6 w-6" @click="toggleBulletList"></div>
					<div class="i-mdi-format-list-numbered h-6 w-6" @click="toggleOrderedList"></div>
				</div>

				<Divider orientation="vertical" class="!mx-2" />

				<div class="flex gap-1">
					<div class="i-mdi-format-align-left h-6 w-6" @click="setTextAlign('left')"></div>
					<div class="i-mdi-format-align-center h-6 w-6" @click="setTextAlign('center')"></div>
					<div class="i-mdi-format-align-right h-6 w-6" @click="setTextAlign('right')"></div>
					<div class="i-mdi-format-align-justify h-6 w-6" @click="setTextAlign('justify')"></div>
				</div>

				<Divider orientation="vertical" class="!mx-2" />

				<div class="flex gap-1">
					<div class="i-mdi-image-outline h-6 w-6" @click="setImage"></div>
					<div class="i-mdi-link h-6 w-6" @click="setLink"></div>
					<div class="i-mdi-format-quote-close h-6 w-6" @click="toggleBlockquote"></div>
					<div class="i-mdi-border-horizontal h-6 w-6" @click="setHorizontalRule"></div>
				</div>

				<Divider orientation="vertical" class="!mx-2" />

				<div class="flex gap-1">
					<div class="i-mdi-undo h-6 w-6" @click="undo"></div>
					<div class="i-mdi-redo h-6 w-6" @click="redo"></div>
				</div>
			</div>

			<div class="RichTextEditor">
				<EditorContent :editor="editor" />
			</div>
		</div>
	</FormControl>
</template>

<style lang="scss" scoped>
.RichTextEditor {
	:deep(p) {
		@apply my-1 leading-tight;
	}

	:deep(h1) {
		@apply my-1 text-4xl font-extrabold;
	}

	:deep(h2) {
		@apply my-1 text-3xl font-bold;
	}

	:deep(h3) {
		@apply my-1 text-2xl font-semibold;
	}

	:deep(h4) {
		@apply my-1 text-xl font-medium;
	}

	:deep(ul) {
		@apply my-1 ml-6 list-disc;
	}

	:deep(ol) {
		@apply my-1 ml-6 list-decimal;
	}

	:deep(blockquote) {
		@apply my-1 border-l-4 border-slate-500 px-2;
	}

	:deep(a) {
		@apply text-primary-500 hover:text-primary-600 hover:underline;
	}

	:deep(img) {
		@apply my-1;
	}

	:deep(hr) {
		@apply my-4 border-t border-gray-200;
	}
}

.dark .RichTextEditor {
	:deep(a) {
		@apply hover:text-primary-400;
	}

	:deep(hr) {
		@apply border-gray-700;
	}
}

.Color {
	@apply h-5 w-5 cursor-pointer rounded-full border border-gray-300 dark:border-gray-700;
}
</style>
