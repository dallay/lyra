<template>
  <node-view-wrapper>
    <Button :variant="attrs.variant" :size="attrs.size" @click="handleClick">
      {{ attrs.label }}
    </Button>
  </node-view-wrapper>
</template>

<script setup lang="ts">
import { defineProps, defineEmits } from "vue";
import { Button } from "@/components/ui/button";
import { nodeViewProps, NodeViewWrapper } from "@tiptap/vue-3";

// Props destructuring
const props = defineProps(nodeViewProps);
const { node, updateAttributes, editor } = props;
const attrs = node.attrs;

const emits = defineEmits(["click"]);

const handleClick = () => {
  emits("click");
};
</script>
