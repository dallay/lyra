import { ref } from 'vue'
import type { Node } from '@tiptap/pm/model'

export default function useData() {
  const currentNode = ref<Node | null>(null)
  const lastNode = ref<Node>()
  const currentNodePos = ref<number>(-1)

  const handleNodeChange = ({ node, pos }: { node: Node | null; pos: number }) => {
    currentNode.value = node
    if (node) lastNode.value = node
    currentNodePos.value = pos
  }

  return {
    currentNode,
    lastNode,
    currentNodePos,
    handleNodeChange
  }
}
