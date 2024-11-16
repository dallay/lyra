import type { Editor } from '@tiptap/core'
import {
  DragHandlePlugin,
  dragHandlePluginDefaultKey,
  type DragHandlePluginProps,
} from '@tiptap-pro/extension-drag-handle'
import {
  defineComponent,
  h,
  onBeforeUnmount,
  onMounted,
  type PropType,
  ref,
} from 'vue'

type Optional<T, K extends keyof T> = Pick<Partial<T>, K> & Omit<T, K>;

export type DragHandleProps = Omit<Optional<DragHandlePluginProps, 'pluginKey'>, 'element'> & {
  class?: string;
  onNodeChange?: (data: { node: Node | null; editor: Editor; pos: number }) => void;
};

export const DragHandleVue = defineComponent({
  name: 'DragHandleVue',

  props: {
    pluginKey: {
      type: [String, Object] as PropType<DragHandleProps['pluginKey']>,
      default: dragHandlePluginDefaultKey,
    },

    editor: {
      type: Object as PropType<DragHandleProps['editor']>,
      required: true,
    },

    tippyOptions: {
      type: Object as PropType<DragHandleProps['tippyOptions']>,
      default: () => ({}),
    },

    onNodeChange: {
      type: Function as PropType<DragHandleProps['onNodeChange']>,
      default: null,
    },

    class: {
      type: String as PropType<DragHandleProps['class']>,
      default: 'drag-handle',
    },
  },

  setup(props, { slots }) {
    const root = ref<HTMLElement | null>(null)

    onMounted(() => {
      const {
        editor,
        pluginKey,
        onNodeChange,
        tippyOptions,
      } = props

      editor.registerPlugin(DragHandlePlugin({
        editor,
        element: root.value as HTMLElement,
        pluginKey,
        tippyOptions,
        onNodeChange,
      }))
    })

    onBeforeUnmount(() => {
      const { pluginKey, editor } = props

      editor.unregisterPlugin(pluginKey as string)
    })

    return () => h('div', { ref: root, class: props.class }, slots.default?.())
  },
})