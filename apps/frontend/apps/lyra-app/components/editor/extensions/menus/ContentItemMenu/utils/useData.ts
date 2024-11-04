import { ref } from 'vue'
import { Editor } from '@tiptap/vue-3'
import { Node } from '@tiptap/pm/model'

export function useData() {
  const currentNode = ref<Node | null>(null)
  const currentNodePos = ref<number>(-1)

  const handleNodeChange = ({ node, pos }: { node: Node | null; pos: number }) => {
    currentNode.value = node
    currentNodePos.value = pos
  }

  return {
    currentNode,
    currentNodePos,
    handleNodeChange
  }
}
